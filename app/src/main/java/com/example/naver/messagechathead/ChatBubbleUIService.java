package com.example.naver.messagechathead;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

/**
 * Created by DAEUN on 16. 7. 12..
 */
public class ChatBubbleUIService extends Service {
	private WindowManager.LayoutParams deleteParams;
	private WindowManager windowManager;
	LayoutInflater layoutInflater;
	private int displayWidth, displayHeight;
	private View deleteView;

	private ImageView deleteIcon;

	ImageView faceImage;

	boolean touchorclick;

	@Override
	public void onCreate() {
		super.onCreate();

		getDisplaySize();

		layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);


		ChatBubbleCreator chatBubbleCreator = new ChatBubbleCreator(getApplicationContext(), windowManager);
		ChatRoomCreator chatRoomCreator = new ChatRoomCreator(getApplicationContext(), windowManager);

		ChatBubbleDeleter chatBubbleDeleter = new ChatBubbleDeleter(getApplicationContext(), windowManager);
		chatBubbleDeleter.init();


		// 사이즈 지정
		int faceIconSize = displayWidth / 5; //아이콘 갯수



	}

	@Nullable
	@Override
	public IBinder onBind(Intent intent) {return null;}

	/* view inflate
	* @param view : inflate할 view
	* @param visibilty : 처음 실행 시 View의 상태를 결정
	* */
	private WindowManager.LayoutParams attachLayout(View view, int location, int visibilty, int width, int height, int type) {
		WindowManager.LayoutParams params = new WindowManager.LayoutParams(width, height, type,
			WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
			PixelFormat.TRANSLUCENT);
		params.gravity = location;
		windowManager = (WindowManager)getSystemService(WINDOW_SERVICE);
		windowManager.addView(view, params);
		view.setVisibility(visibilty);
		return params;
	}

	/*
	*  디스플레이 사이즈 측정
	* */
	private void getDisplaySize() {
		DisplayMetrics disp = getApplicationContext().getResources().getDisplayMetrics();
		displayWidth = disp.widthPixels;
		displayHeight = disp.heightPixels;
	}
}
