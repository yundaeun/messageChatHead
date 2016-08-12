package com.example.naver.messagechathead.chatRoom;

import android.content.Context;
import android.graphics.PixelFormat;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import com.example.naver.messagechathead.R;

/**
 * Created by Naver on 16. 8. 12..
 */
public class ChatRoomCreator extends LinearLayout {
	private Context context;
	private WindowManager windowManager;
	private int displayWidth;
	private int displayHeight;
	private View chatView;
	LayoutInflater layoutInflater;

	public ChatRoomCreator(Context context, WindowManager windowManager) {
		super(context);
		this.context = context;
		this.windowManager = windowManager;

		getDisplaySize();
		int faceIconSize = displayWidth / 5;
		int dialogSize = displayHeight - faceIconSize - 65; // 화면 넘게 그려짐

		layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		chatView = layoutInflater.inflate(R.layout.chat_view_layout, null);
		attachLayout(chatView, Gravity.BOTTOM, View.GONE, displayWidth - 50, dialogSize,
			WindowManager.LayoutParams.TYPE_PHONE);
	}

	// 중복 코드
	private void getDisplaySize() {
		DisplayMetrics disp = context.getResources().getDisplayMetrics();
		displayWidth = disp.widthPixels;
		displayHeight = disp.heightPixels;
	}

	// 중복 코드
	private WindowManager.LayoutParams attachLayout(View view, int location, int visibilty, int width, int height,
		int type) {
		WindowManager.LayoutParams params = new WindowManager.LayoutParams(width, height, type,
			WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
			PixelFormat.TRANSLUCENT);
		params.gravity = location;
		windowManager = (WindowManager)context.getSystemService(context.WINDOW_SERVICE);
		windowManager.addView(view, params);
		view.setVisibility(visibilty);
		return params;
	}

	public void setChangeVisible() {
		if (chatView.getVisibility() == GONE) {
			chatView.setVisibility(View.VISIBLE);
		} else {
			chatView.setVisibility(View.GONE);
		}
	}
}