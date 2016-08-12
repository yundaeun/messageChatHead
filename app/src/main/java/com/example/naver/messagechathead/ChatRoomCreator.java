package com.example.naver.messagechathead;

import android.content.Context;
import android.graphics.PixelFormat;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by Naver on 16. 8. 12..
 */
public class ChatRoomCreator {
	private Context context;
	private WindowManager windowManager;
	private int displayWidth;
	private int displayHeight;

	public ChatRoomCreator(Context context, WindowManager windowManager) {
		this.context = context;
		this.windowManager = windowManager;



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
}
