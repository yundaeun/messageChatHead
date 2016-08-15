package com.example.naver.messagechathead.chatBubble;

import android.content.Context;
import android.graphics.PixelFormat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.OverScroller;
import com.example.naver.messagechathead.chatRoom.ChatRoomCreator;
import com.example.naver.messagechathead.chatRoom.ChatRoomListCreator;
import com.example.naver.messagechathead.R;
import com.example.naver.messagechathead.utils.ChatBubbleHelper;

/**
 * Created by DAEUN on 16. 8. 11..
 */
public class ChatBubble extends LinearLayout implements View.OnTouchListener {
	private Context context;
	private WindowManager windowManager;
	private WindowManager.LayoutParams faceIconParams;
	private GestureDetector gestureDetector;
	private boolean bubbleFlag;
	private View faceIcon;
	private int MAX_X;
	private int MAX_Y;
	ChatBubbleDeleteBtn chatBubbleDeleteBtn;
	ChatRoomCreator chatRoomCreator;
	ChatRoomListCreator chatRoomListCreator;


	public ChatBubble(boolean bubbleFlag, Context context, WindowManager windowManager, int visible,
		ChatBubbleDeleteBtn chatBubbleDeleteBtn) {
		super(context);
		this.context = context;
		this.windowManager = windowManager;
		this.bubbleFlag = bubbleFlag;
		this.chatBubbleDeleteBtn = chatBubbleDeleteBtn;

		MAX_X = ChatBubbleHelper.displayWidth * 4/5;
		MAX_Y = ChatBubbleHelper.displayHeight;

		int faceIconSize = ChatBubbleHelper.displayWidth / 5;
		LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		faceIcon = layoutInflater.inflate(R.layout.face_icon_layout, null);
		faceIconParams = attachLayout(faceIcon, Gravity.START | Gravity.TOP, visible, faceIconSize);

		// interaction
		faceIcon.setOnTouchListener(this);
		gestureDetector = new GestureDetector(context, new SimpleGestureListener());

		// 하나의 버블 당 하나의 채팅방을 갖는다.
		if (bubbleFlag) {
			chatRoomCreator = new ChatRoomCreator(context, windowManager);
		} else {
			chatRoomListCreator = new ChatRoomListCreator(context, windowManager);
		}

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
		windowManager = (WindowManager)context.getSystemService(context.WINDOW_SERVICE);
		windowManager.addView(view, params);
		view.setVisibility(visibilty);
		return params;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {

		boolean deleteArea = chatBubbleDeleteBtn.deleteArea(faceIconParams);

		if (deleteArea) {
			chatBubbleDeleteBtn.changeDeleteButtonForNear();
		} else {
			chatBubbleDeleteBtn.changeDeleteButtonForFar();
		}

		switch (event.getAction()) {

			case MotionEvent.ACTION_UP:
				if (deleteArea) {
					faceIcon.setVisibility(View.GONE);
				}
				chatBubbleDeleteBtn.deleteAreaHide();

				if (faceIconParams.x > ChatBubbleHelper.displayWidth / 2) {
					faceIconParams.x = ChatBubbleHelper.displayWidth * 4/5;
				} else {
					faceIconParams.x = 0;
				}
				windowManager.updateViewLayout(v, faceIconParams);

				break;
		}

		// 상대 좌표를 절대 좌표로 설정
		event.setLocation(event.getRawX(), event.getRawY());
		gestureDetector.onTouchEvent(event);
		return false;
	}

	class SimpleGestureListener extends GestureDetector.SimpleOnGestureListener {

		private int faceIconParamsOnDown_x;
		private int faceIconParamsOnDown_y;
		private OverScroller mScroller;

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {


			return true;
		}

		@Override
		public boolean onSingleTapConfirmed(MotionEvent e) {
			// 사람 bubble을 클릭한 경우
			if (bubbleFlag) {
				faceIconParams.x = ChatBubbleHelper.displayWidth;
				faceIconParams.y = 0;
				windowManager.updateViewLayout(faceIcon, faceIconParams);
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
			return true;
		}

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
			int x = (int)(e2.getX() - e1.getX());
			int y = (int)(e2.getY() - e1.getY());

			faceIconParams.x = faceIconParamsOnDown_x + x;
			faceIconParams.y = faceIconParamsOnDown_y + y;
			windowManager.updateViewLayout(faceIcon, faceIconParams);
			return true;
		}


	}
}

