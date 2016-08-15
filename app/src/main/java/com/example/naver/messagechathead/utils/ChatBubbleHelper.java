package com.example.naver.messagechathead.utils;

import android.content.Context;
import android.graphics.PixelFormat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by Naver on 16. 8. 12..
 */
public class ChatBubbleHelper {

	private Context context;
	private WindowManager windowManager;
	public static int displayWidth;
	public static int displayHeight;

	public ChatBubbleHelper(Context context, WindowManager windowManager) {
		this.context = context;
		this.windowManager = windowManager;
		getDisplaySize();
	}

	// 중복 코드
	public WindowManager.LayoutParams attachLayout(View view, int location, int visibilty, int width, int height,
		int type) {
		WindowManager.LayoutParams params =
			new WindowManager.LayoutParams(width, height, type,
				WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
				PixelFormat.TRANSPARENT);
		params.gravity = location;
		windowManager = (WindowManager)context.getSystemService(context.WINDOW_SERVICE);
		windowManager.addView(view, params);
		view.setVisibility(visibilty);
		return params;
	}

	private void getDisplaySize() {
		DisplayMetrics disp = context.getResources().getDisplayMetrics();
		displayWidth = disp.widthPixels;
		displayHeight = disp.heightPixels;
	}

}