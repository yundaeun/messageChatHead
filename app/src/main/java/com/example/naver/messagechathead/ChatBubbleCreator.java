package com.example.naver.messagechathead;

import android.content.Context;
import android.graphics.PixelFormat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

/**
 * Created by Naver on 16. 8. 11..
 */
public class ChatBubbleCreator extends LinearLayout implements View.OnTouchListener{
	private Context context;
	private int displayWidth;
	private int displayHeight;
	private WindowManager windowManager;
	private WindowManager.LayoutParams faceIconParams;

	private float START_X, START_Y; //움직이기 위해 터치한 시작 점
	private int PREV_X, PREV_Y; //움직이기 이전에 뷰가 위치한 점
	private int MAX_X = -1, MAX_Y = -1; //뷰의 위치 최대 값

	LayoutInflater layoutInflater;
	private View faceIcon;


	public ChatBubbleCreator(Context context, WindowManager windowManager) {
		super(context);
		this.context = context;
		this.windowManager = windowManager;

		int faceIconSize = displayWidth / 5; //아이콘 갯수

		layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		faceIcon = layoutInflater.inflate(R.layout.face_icon_layout, null);
	//	faceIconParams = attachLayout(faceIcon, Gravity.START | Gravity.TOP, View.VISIBLE, faceIconSize, faceIconSize, WindowManager.LayoutParams.TYPE_PRIORITY_PHONE);
		faceIcon.setOnTouchListener(this);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		return false;
	}

}
