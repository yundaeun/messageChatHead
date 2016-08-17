package com.example.naver.messagechathead.chatBubble;

import android.content.Context;
import android.graphics.PixelFormat;
import android.support.v4.view.ViewCompat;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.OverScroller;
import android.widget.RelativeLayout;
import com.example.naver.messagechathead.chatRoom.ChatRoomCreator;
import com.example.naver.messagechathead.chatRoom.ChatRoomListCreator;
import com.example.naver.messagechathead.R;
import com.example.naver.messagechathead.utils.ChatBubbleHelper;

/**
 * Created by DAEUN on 16. 8. 11..
 */
public class ChatBubble extends RelativeLayout {
	private WindowManager windowManager;
	private WindowManager.LayoutParams layoutParams;
	private GestureDetector gestureDetector;
	private boolean bubbleFlag;
	private OverScroller scroller;
	private int bubbleSize;
	ChatBubbleDeleteBtn chatBubbleDeleteBtn;
	ChatRoomCreator chatRoomCreator;
	ChatRoomListCreator chatRoomListCreator;

	public ChatBubble(boolean bubbleFlag, Context context, WindowManager windowManager, int visible,
		ChatBubbleDeleteBtn chatBubbleDeleteBtn) {
		super(context);
		this.windowManager = windowManager;
		this.bubbleFlag = bubbleFlag;
		this.chatBubbleDeleteBtn = chatBubbleDeleteBtn;

		bubbleSize = ChatBubbleHelper.getBubbleSize();

		inflate(context, R.layout.face_icon_layout, this);
		layoutParams = attachLayout(this, Gravity.START | Gravity.TOP, bubbleSize);

		gestureDetector = new GestureDetector(context, new SimpleGestureListener());
		scroller = new OverScroller(getContext());

		// 하나의 버블 당 하나의 채팅방을 갖는다.
		if (bubbleFlag) {
			chatRoomCreator = new ChatRoomCreator(context, windowManager);
		} else {
			chatRoomListCreator = new ChatRoomListCreator(context, windowManager);
		}
		setVisibility(visible);
	}

	private WindowManager.LayoutParams attachLayout(View view, int location, int size) {
		WindowManager.LayoutParams params =
			new WindowManager.LayoutParams(size, size, WindowManager.LayoutParams.TYPE_PRIORITY_PHONE,
				WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
				PixelFormat.TRANSLUCENT);
		params.gravity = location;
		windowManager = (WindowManager)getContext().getSystemService(getContext().WINDOW_SERVICE);
		windowManager.addView(view, params);
		return params;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
			case MotionEvent.ACTION_UP:
				serviceStopWithDeleteBtn();

				if (isLeftSide()) {
					moveToLeft();
				} else {
					moveToRight();
				}
				ViewCompat.postInvalidateOnAnimation(this);
				break;
		}

		/* 상대좌표를 절대좌표로 변경시켜주는 함수
		 * getRawX getX 모두 절대좌표로 사용 가능 */
		event.setLocation(event.getRawX(), event.getRawY());
		gestureDetector.onTouchEvent(event);
		return false;
	}

	private void serviceStopWithDeleteBtn() {
		if (chatBubbleDeleteBtn.isOverlayDeleteArea(layoutParams)) {
			//TODO chatRoom GONE
			setVisibility(View.GONE);
		}
		chatBubbleDeleteBtn.deleteAreaHide();
	}

	private boolean isLeftSide() {
		return layoutParams.x + bubbleSize / 2 < ChatBubbleHelper.getDisplayCenter();
	}

	private void moveToLeft() {
		scroller.startScroll(layoutParams.x, layoutParams.y, -layoutParams.x, 0);
	}

	private void moveToRight() {
		scroller.startScroll(layoutParams.x, layoutParams.y, ChatBubbleHelper.getOptimizeWidth() - layoutParams.x, 0);
	}

	@Override
	public void computeScroll() {
		if (scroller.computeScrollOffset()) {
			if (scroller.isFinished()) {
				requestLayout();
			} else {
				layoutParams.x = scroller.getCurrX();
				layoutParams.y = scroller.getCurrY();
				windowManager.updateViewLayout(this, layoutParams);
			}
			ViewCompat.postInvalidateOnAnimation(this);
		}
	}

	private void fling(int velocityX, int velocityY) {
		scroller.fling(
			layoutParams.x, layoutParams.y, velocityX, velocityY,
			0, ChatBubbleHelper.getOptimizeWidth(),
			0, ChatBubbleHelper.getOptimizeHeight());
		ViewCompat.postInvalidateOnAnimation(this);
	}

	class SimpleGestureListener extends GestureDetector.SimpleOnGestureListener {
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			scroller.forceFinished(true);
			fling((int)velocityX, (int)velocityY);
			return true;
		}

		@Override
		public boolean onSingleTapConfirmed(MotionEvent e) {
			// 사람 bubble을 클릭한 경우
			if (bubbleFlag) {
				layoutParams.x = ChatBubbleHelper.getOptimizeWidth();
				layoutParams.y = 0;
				windowManager.updateViewLayout(ChatBubble.this, layoutParams);
				chatRoomCreator.setChangeVisible();

			} else {
				// default bubble을 클릭 한 경우
				chatRoomListCreator.setChangeVisible();
			}
			return true;
		}

		@Override
		public boolean onDown(MotionEvent e) {
			chatBubbleDeleteBtn.deleteAreaShow();
			scroller.forceFinished(true);
			ViewCompat.postInvalidateOnAnimation(ChatBubble.this);
			return true;
		}

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
			moveFaceIcon(distanceX, distanceY);
			changeDeleteButtonImage();
			return true;
		}

		private void moveFaceIcon(float distanceX, float distanceY) {
			layoutParams.x -= (int)distanceX;
			layoutParams.y -= (int)distanceY;
			windowManager.updateViewLayout(ChatBubble.this, layoutParams);
		}

		private void changeDeleteButtonImage() {
			boolean deleteArea = chatBubbleDeleteBtn.isOverlayDeleteArea(layoutParams);

			if (deleteArea) {
				chatBubbleDeleteBtn.changeDeleteButtonForNear();
			} else {
				chatBubbleDeleteBtn.changeDeleteButtonForFar();
			}
		}
	}
}

