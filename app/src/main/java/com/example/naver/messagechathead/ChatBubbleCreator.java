package com.example.naver.messagechathead;

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

/**
 * Created by DAEUN on 16. 8. 11..
 */
public class ChatBubbleCreator extends LinearLayout implements View.OnTouchListener{
	private Context context;
	private int displayWidth;
	private int displayHeight;
	private WindowManager windowManager;
	private WindowManager.LayoutParams faceIconParams;
	private GestureDetector gestureDetector;
	private float START_X, START_Y;
	private int PREV_X, PREV_Y;

	LayoutInflater layoutInflater;
	private View faceIcon;
	ChatBubbleDeleter chatBubbleDeleter;

	public ChatBubbleCreator(Context context, WindowManager windowManager) {
		super(context);
		this.context = context;
		this.windowManager = windowManager;

		getDisplaySize();
		int faceIconSize = displayWidth / 5;

		layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		faceIcon = layoutInflater.inflate(R.layout.face_icon_layout, null);
		faceIconParams = attachLayout(faceIcon, Gravity.START | Gravity.TOP, View.VISIBLE, faceIconSize, faceIconSize, WindowManager.LayoutParams.TYPE_PRIORITY_PHONE);

		// move action
		faceIcon.setOnTouchListener(this);
		// click listener
		gestureDetector = new GestureDetector(context, new SimpleGestureListener());
		// delete action
		chatBubbleDeleter = new ChatBubbleDeleter(context, windowManager);

	}

	// 중복 코드
	private void getDisplaySize() {
		DisplayMetrics disp = context.getResources().getDisplayMetrics();
		displayWidth = disp.widthPixels;
		displayHeight = disp.heightPixels;
	}

	// 중복 코드
	private WindowManager.LayoutParams attachLayout(View view, int location, int visibilty, int width, int height, int type) {
		WindowManager.LayoutParams params = new WindowManager.LayoutParams(width, height, type,
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

		boolean stopService = chatBubbleDeleter.onSelectedDeleteButton(event.getAction(), faceIconParams);
		chatBubbleDeleter.showBubbleDeleteBtn(event.getAction());

		if (!stopService) {
			faceIcon.setVisibility(View.GONE);
		} else {
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
					break;
			}
		}
		gestureDetector.onTouchEvent(event);
		return false;
	}

	// 자유로운 상태 (chatRoom이 닫힌 상태)
	// 동작 : 버블이 모여서 함께 움직임
	// 애니 : 좌, 우 끝으로 이동

	// 고정된 상태 (chatRoom이 펼쳐진 상태)
	// 동작 : 하나씩 이동 가능
	// 애니 : up 시 제자리로 이동
}

class SimpleGestureListener extends GestureDetector.SimpleOnGestureListener {

	@Override
	public boolean onSingleTapConfirmed (MotionEvent e) {

		return true;
	}
}