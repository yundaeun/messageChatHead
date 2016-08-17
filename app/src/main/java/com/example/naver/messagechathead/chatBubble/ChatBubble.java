package com.example.naver.messagechathead.chatBubble;

import android.content.Context;
import android.graphics.PixelFormat;
import android.support.v4.view.ViewCompat;
import android.util.Log;
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
	ChatBubbleOpen chatBubbleOpen;
	ChatBubbleClose chatBubbleClose;


	public ChatBubble(boolean bubbleFlag, Context context, WindowManager windowManager,
		ChatBubbleDeleteBtn chatBubbleDeleteBtn) {
		super(context);
		this.bubbleFlag = bubbleFlag;
		this.windowManager = windowManager;
		this.chatBubbleDeleteBtn = chatBubbleDeleteBtn;
	}

	public void init() {
		bubbleSize = ChatBubbleHelper.getBubbleSize();

		inflate(getContext(), R.layout.face_icon_layout, this);
		layoutParams = attachLayout(this, Gravity.START | Gravity.TOP, bubbleSize);
		ChatBubbleContainer.addChatBubbleParams(layoutParams);

		gestureDetector = new GestureDetector(getContext(), new SimpleGestureListener());
		scroller = new OverScroller(getContext());

		// 하나의 버블 당 하나의 채팅방을 갖는다.
		if (bubbleFlag) {
			chatRoomCreator = new ChatRoomCreator(getContext(), windowManager);
			setVisibility(View.VISIBLE);
			//		} else {
			//			chatRoomListCreator = new ChatRoomListCreator(getContext(), windowManager);
			//			setVisibility(View.GONE);
		}
		chatBubbleOpen = new ChatBubbleOpen();
		chatBubbleClose = new ChatBubbleClose();
	}

	public boolean checkDialogState() {
		if (chatRoomCreator.getChatRoomVisibility()) {// || (chatRoomListCreator.getChatRoomListVisibility())) {
			return true;
		} else {
			return false;
		}
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

	public void moveTo(int finalX, int finalY, WindowManager.LayoutParams layoutParams) {
		scroller.startScroll(layoutParams.x, layoutParams.y, finalX, finalY);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
			case MotionEvent.ACTION_UP:
				if (checkDialogState()) {
					chatBubbleOpen.moveToStart();
				} else {
					scroller = chatBubbleClose.moveToLeftOrRight(scroller);
				}
				ViewCompat.postInvalidateOnAnimation(this);
				serviceStopWithDeleteBtn();
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

	@Override
	public void computeScroll() {
		if (scroller.computeScrollOffset()) {
			if (scroller.isFinished()) {
				requestLayout();
			} else {
				if (checkDialogState()) {
					scroller = chatBubbleOpen.scrollBubble(this, scroller, windowManager, layoutParams);
				} else {
					scroller = chatBubbleClose.scrollBubbles(scroller, windowManager);
				}
			}
			ViewCompat.postInvalidateOnAnimation(this);
		}
	}

	private void fling(int velocityX, int velocityY) {
		if (checkDialogState()) {
			scroller = chatBubbleOpen.updateViewBubble(this, scroller, layoutParams, velocityX, velocityY);
		} else {
			scroller = chatBubbleClose.flingBubbles(scroller, velocityX, velocityY);
		}
	}

	class SimpleGestureListener extends GestureDetector.SimpleOnGestureListener {
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			scroller.forceFinished(true);
			fling((int)velocityX/2, (int)velocityY );
			return true;
		}

		@Override
		public boolean onSingleTapConfirmed(MotionEvent e) {
			if (checkDialogState()) {
				// open 각 채팅방으로 이동
				chatBubbleClose.moveToTopAndRight(scroller);
			} else {
				// close 위로 이동
				chatBubbleClose.moveToUpAndArrangeBubbles(scroller);

			}
			chatRoomCreator.setChangeVisible();
			return true;
		}

		@Override
		public boolean onDown(MotionEvent e) {
			chatBubbleDeleteBtn.deleteAreaShow();
			scroller.forceFinished(true);
			return true;
		}

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
			moveFaceIcon(distanceX, distanceY);
			changeDeleteButtonImage();
			return true;
		}

		private void moveFaceIcon(float distanceX, float distanceY) {
			if (checkDialogState()) {
				chatBubbleOpen.updateViewBubble(ChatBubble.this, windowManager, layoutParams, distanceX, distanceY);
			} else {
				chatBubbleClose.updateViewBubbles(windowManager, distanceX, distanceY);
			}
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

