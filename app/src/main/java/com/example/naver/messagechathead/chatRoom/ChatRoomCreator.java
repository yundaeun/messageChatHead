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
import com.example.naver.messagechathead.utils.ChatBubbleUtils;

/**
 * Created by Naver on 16. 8. 12..
 */
public class ChatRoomCreator extends LinearLayout {
	private Context context;
	private WindowManager windowManager;
	private int displayWidth;
	private int displayHeight;
	private View chatView;

	public ChatRoomCreator(Context context, WindowManager windowManager) {
		super(context);
		this.context = context;
		this.windowManager = windowManager;

		int faceIconSize = ChatBubbleUtils.displayWidth / 5;
		int dialogHeight = ChatBubbleUtils.displayHeight - faceIconSize - 65; // 화면 넘게 그려짐
		int dialogWidth = ChatBubbleUtils.displayWidth - 50;

		LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		chatView = layoutInflater.inflate(R.layout.chat_view_layout, null);

		ChatBubbleUtils chatBubbleUtils = new ChatBubbleUtils(context, windowManager);
		chatBubbleUtils.attachLayout(chatView, Gravity.BOTTOM, View.GONE, dialogWidth, dialogHeight,
			WindowManager.LayoutParams.TYPE_PHONE);
	}

	public void setChangeVisible() {
		if (chatView.getVisibility() == GONE) {
			chatView.setVisibility(View.VISIBLE);
		} else {
			chatView.setVisibility(View.GONE);
		}
	}
}