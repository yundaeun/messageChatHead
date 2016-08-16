package com.example.naver.messagechathead.chatRoom;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import com.example.naver.messagechathead.R;
import com.example.naver.messagechathead.utils.ChatBubbleConfig;
import com.example.naver.messagechathead.utils.ChatBubbleHelper;

/**
 * Created by Naver on 16. 8. 12..
 */
public class ChatRoomCreator extends LinearLayout {
	private WindowManager windowManager;
	private View chatView;

	public ChatRoomCreator(Context context, WindowManager windowManager) {
		super(context);
		this.windowManager = windowManager;

		int bubbleSize = ChatBubbleHelper.displayWidth / ChatBubbleConfig.BUBBLE_NUM;
		int dialogWidth = ChatBubbleHelper.displayWidth - 65;
		int dialogHeight = ChatBubbleHelper.displayHeight - bubbleSize - 65;

		LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		chatView = layoutInflater.inflate(R.layout.chat_view_layout, null);

		ChatBubbleHelper chatBubbleHelper = new ChatBubbleHelper(context, windowManager);
		chatBubbleHelper.attachLayout(chatView, Gravity.BOTTOM, View.GONE, dialogWidth, dialogHeight,
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