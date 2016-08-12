package com.example.naver.messagechathead.chatBubble;

import android.content.Context;
import android.graphics.PixelFormat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import com.example.naver.messagechathead.chatRoom.ChatRoomCreator;
import com.example.naver.messagechathead.chatRoom.ChatRoomListCreator;
import com.example.naver.messagechathead.R;

/**
 * Created by DAEUN on 16. 8. 11..
 */
public class ChatBubble extends LinearLayout implements View.OnTouchListener {
	private Context context;
	private int displayWidth;
	private int displayHeight;
	private WindowManager windowManager;
	private WindowManager.LayoutParams faceIconParams;
	private GestureDetector gestureDetector;
	private float START_X, START_Y;
	private int PREV_X, PREV_Y;
	private boolean bubbleFlag;
	LayoutInflater layoutInflater;
	private View faceIcon;
	ChatBubbleDeleter chatBubbleDeleter;
	ChatRoomCreator chatRoomCreator;
	ChatRoomListCreator chatRoomListCreator;

	public ChatBubble(boolean bubbleFlag, Context context, WindowManager windowManager, int visible,
		ChatBubbleDeleter chatBubbleDeleter) {
		super(context);
		this.context = context;
		this.windowManager = windowManager;
		this.bubbleFlag = bubbleFlag;
		this.chatBubbleDeleter = chatBubbleDeleter;

		getDisplaySize();
		int faceIconSize = displayWidth / 5;

		layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
	private void getDisplaySize() {
		DisplayMetrics disp = context.getResources().getDisplayMetrics();
		displayWidth = disp.widthPixels;
		displayHeight = disp.heightPixels;
	}

	// 중복 코드
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

		boolean stopService = chatBubbleDeleter.onSelectedDeleteButton(faceIconParams);
		chatBubbleDeleter.changeImageBubbleDeleteBtn(stopService);
		chatBubbleDeleter.showBubbleDeleteBtn(event.getAction());

		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				START_X = event.getRawX();
				START_Y = event.getRawY();
				PREV_X = faceIconParams.x;
				PREV_Y = faceIconParams.y;
				break;

			case MotionEvent.ACTION_MOVE:
				int x = (int)(event.getRawX() - START_X);
				int y = (int)(event.getRawY() - START_Y);
				faceIconParams.x = PREV_X + x;
				faceIconParams.y = PREV_Y + y;
				windowManager.updateViewLayout(v, faceIconParams);
				break;

			case MotionEvent.ACTION_UP:
				if (stopService) {
					faceIcon.setVisibility(View.GONE);
				}
				break;
		}

		gestureDetector.onTouchEvent(event);
		return false;
	}

	class SimpleGestureListener extends GestureDetector.SimpleOnGestureListener {

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			return super.onFling(e1, e2, velocityX, velocityY);
		}

		@Override
		public boolean onSingleTapConfirmed(MotionEvent e) {
			// 사람 bubble을 클릭한 경우
			if (bubbleFlag) {
				faceIconParams.x = displayWidth;
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
		public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
			//TODO move action onScroll 로 수정
			Log.d("yde", "yde onScroll");
			return super.onScroll(e1, e2, distanceX, distanceY);

		}
	}
}

