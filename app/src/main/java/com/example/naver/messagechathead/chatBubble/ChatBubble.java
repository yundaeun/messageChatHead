package com.example.naver.messagechathead.chatBubble;

import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.OverScroller;
import android.widget.RelativeLayout;
import com.example.naver.messagechathead.chatRoom.ChatRoomCreator;
import com.example.naver.messagechathead.chatRoom.ChatRoomListCreator;
import com.example.naver.messagechathead.R;
import com.example.naver.messagechathead.utils.ChatBubbleConfig;
import com.example.naver.messagechathead.utils.ChatBubbleHelper;

/**
 * Created by DAEUN on 16. 8. 11..
 */
public class ChatBubble extends RelativeLayout {
	private WindowManager windowManager;
	private WindowManager.LayoutParams faceIconParams;
	private GestureDetector gestureDetector;
	private boolean bubbleFlag;
	private View faceIcon;
	ChatBubbleDeleteBtn chatBubbleDeleteBtn;
	ChatRoomCreator chatRoomCreator;
	ChatRoomListCreator chatRoomListCreator;
	private OverScroller scroller;

	public ChatBubble(boolean bubbleFlag, Context context, WindowManager windowManager, int visible,
		ChatBubbleDeleteBtn chatBubbleDeleteBtn) {
		super(context);
		this.windowManager = windowManager;
		this.bubbleFlag = bubbleFlag;
		this.chatBubbleDeleteBtn = chatBubbleDeleteBtn;

		int faceIconSize = getMaxX() / ChatBubbleConfig.BUBBLE_NUM;
		LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		faceIcon = layoutInflater.inflate(R.layout.face_icon_layout, null);
		addView(faceIcon);
		faceIconParams = attachLayout(this, Gravity.START | Gravity.TOP, visible, faceIconSize);

		gestureDetector = new GestureDetector(context, new SimpleGestureListener());

		// 하나의 버블 당 하나의 채팅방을 갖는다.
		if (bubbleFlag) {
			chatRoomCreator = new ChatRoomCreator(context, windowManager);
		} else {
			chatRoomListCreator = new ChatRoomListCreator(context, windowManager);
		}
		scroller = new OverScroller(getContext());
		setBindData();
	}

	private void setBindData() {
		// 데이터 삽입 (이미지 적용)
	}

	// 중복 코드
	// Utils 함수 사용하면 NullPointerException 발생
	private WindowManager.LayoutParams attachLayout(View view, int location, int visibilty, int size) {
		WindowManager.LayoutParams params =
			new WindowManager.LayoutParams(size, size, WindowManager.LayoutParams.TYPE_PRIORITY_PHONE,
				WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
				PixelFormat.TRANSLUCENT);
		params.gravity = location;
		windowManager = (WindowManager)getContext().getSystemService(getContext().WINDOW_SERVICE);
		windowManager.addView(view, params);
		view.setVisibility(visibilty);
		return params;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		boolean deleteArea = chatBubbleDeleteBtn.deleteArea(faceIconParams);

		if (deleteArea) {
			chatBubbleDeleteBtn.changeDeleteButtonForNear();
		} else {
			chatBubbleDeleteBtn.changeDeleteButtonForFar();
		}

		switch (event.getAction()) {

			case MotionEvent.ACTION_UP:
				if (deleteArea) {
					deleteChatRooms();
					setVisibility(View.GONE);
				}
				chatBubbleDeleteBtn.deleteAreaHide();
/*
				if (isLeftSide()) {
					moveToLeft();
				} else {
					moveToRight();
				}
				//windowManager.updateViewLayout(v, faceIconParams);
*/
				if (chatRoomCreator.getVisibility() == View.GONE) {
					scroller.startScroll(faceIconParams.x, faceIconParams.y, 0, 0, 1000);
					ViewCompat.postInvalidateOnAnimation(this);
				}
				break;
		}

		// 상대 좌표를 절대 좌표로 변경
		event.setLocation(event.getRawX(), event.getRawY());
		gestureDetector.onTouchEvent(event);
		return false;

	}

	@Override
	public void computeScroll() {
		if(scroller.computeScrollOffset()) {
			int currX = scroller.getCurrX(); //현재 x값
			int currY = scroller.getCurrY(); //현재 y값

			if(scroller.isFinished()) {
				requestLayout();
			} else {
				faceIconParams.x = currX;
				faceIconParams.y = currY;
				windowManager.updateViewLayout(this, faceIconParams);
			}
			ViewCompat.postInvalidateOnAnimation(this);

		}
	}

	private void deleteChatRooms() {
		if (chatRoomCreator != null) {
			chatRoomCreator.setChangeVisible();
		}
		if (chatRoomListCreator != null) {
			chatRoomListCreator.setChangeVisible();
		}
	}

	private boolean isLeftSide() {
		return faceIconParams.x < getCenterX();
	}

	private void moveToLeft() {
		faceIconParams.x = 0;
	}

	private void moveToRight() {
		faceIconParams.x = getMaxX() * 4 / 5;
	}

	private int getMaxX() {
		return ChatBubbleHelper.displayWidth;
	}

	private int getCenterX() {
		return getMaxX() * 2 / 5;
	}

	class SimpleGestureListener extends GestureDetector.SimpleOnGestureListener {
		private int faceIconParamsOnDown_x;
		private int faceIconParamsOnDown_y;

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
				faceIconParams.x = getMaxX() * 4 / 5;
				faceIconParams.y = 0;
				windowManager.updateViewLayout(ChatBubble.this, faceIconParams);
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
			faceIconParamsOnDown_x = faceIconParams.x;
			faceIconParamsOnDown_y = faceIconParams.y;

			scroller.forceFinished(true); //스크롤 멈추기
			ViewCompat.postInvalidateOnAnimation(ChatBubble.this);
			return true;
		}

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
			int x = (int)(e2.getX() - e1.getX());
			int y = (int)(e2.getY() - e1.getY());
			faceIconParams.x = faceIconParamsOnDown_x + x;
			faceIconParams.y = faceIconParamsOnDown_y + y;
			windowManager.updateViewLayout(ChatBubble.this, faceIconParams);
			return true;
		}
	}
	private void fling(int velocityX, int velocityY) {
		scroller.fling(
			//current scroll position
			faceIconParams.x, faceIconParams.y, velocityX, velocityY,
			//min X, max X
			0, ChatBubbleHelper.displayWidth - 100,
			//min Y, max Y
			0, ChatBubbleHelper.displayHeight - 100 );
		ViewCompat.postInvalidateOnAnimation(this);
	}
}

