package com.example.naver.messagechathead.chatRoom;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import com.example.naver.messagechathead.R;
import com.example.naver.messagechathead.utils.ChatBubbleHelper;

/**
 * Created by Naver on 16. 8. 12..
 */
public class ChatRoomCreator {
	private Context context;
	private WindowManager windowManager;
	private View chatView;

	public ChatRoomCreator(Context context, WindowManager windowManager) {
		this.context = context;
		this.windowManager = windowManager;

		int bubbleSize = ChatBubbleHelper.getBubbleSize();
		int dialogWidth = ChatBubbleHelper.displayWidth;
		int dialogHeight = ChatBubbleHelper.displayHeight - bubbleSize - 110;

		LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		chatView = layoutInflater.inflate(R.layout.chat_room_layout, null);

		ChatBubbleHelper chatBubbleHelper = new ChatBubbleHelper(context, windowManager);
		chatBubbleHelper.attachLayout(chatView, Gravity.BOTTOM, View.GONE, dialogWidth, dialogHeight,
			WindowManager.LayoutParams.TYPE_PHONE);

	}

	public void setChangeVisible() {
		if (chatView.getVisibility() == View.GONE) {
			chatView.setVisibility(View.VISIBLE);
		} else if (chatView.getVisibility() == View.VISIBLE){
			chatView.setVisibility(View.GONE);
		}
	}

	public boolean getChatRoomVisibility() {
		return chatView.getVisibility() == View.VISIBLE;
	}

}