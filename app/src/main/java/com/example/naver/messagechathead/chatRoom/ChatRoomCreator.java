package com.example.naver.messagechathead.chatRoom;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import com.example.naver.messagechathead.R;
import com.example.naver.messagechathead.chatBubble.ChatBubbleContainer;
import com.example.naver.messagechathead.utils.ChatBubbleHelper;

/**
 * Created by Naver on 16. 8. 12..
 */
public class ChatRoomCreator extends LinearLayout {
	private WindowManager windowManager;
	private View chatView;
	private ArrayList<View> chatRoomList;

	public ChatRoomCreator(Context context, WindowManager windowManager) {
		super(context);
		this.windowManager = windowManager;

		int bubbleSize = ChatBubbleHelper.getBubbleSize();
		int dialogWidth = ChatBubbleHelper.displayWidth - 65;
		int dialogHeight = ChatBubbleHelper.displayHeight - bubbleSize - 65;

		LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		chatView = layoutInflater.inflate(R.layout.chat_view_layout, null);

		ChatBubbleHelper chatBubbleHelper = new ChatBubbleHelper(context, windowManager);
		chatBubbleHelper.attachLayout(chatView, Gravity.BOTTOM, View.GONE, dialogWidth, dialogHeight,
			WindowManager.LayoutParams.TYPE_PHONE);
		ChatBubbleContainer.addChatRoom(chatView);

		chatRoomList = ChatBubbleContainer.getChatRoomList();
	}

	public void setChangeVisible() {
		for (int i = 0; i < chatRoomList.size(); i++) {
			if (chatRoomList.get(i).getVisibility() == View.GONE) {
				chatRoomList.get(i).setVisibility(View.VISIBLE);
			} else if (chatRoomList.get(i).getVisibility() == View.VISIBLE) {
				chatRoomList.get(i).setVisibility(View.GONE);
			}
		}
	}

	public boolean getChatRoomVisibility() {
		for (int i = 0; i < chatRoomList.size(); i++) {
			if (chatRoomList.get(i).getVisibility() == View.VISIBLE) {
				return true;
			}
		}
		return false;
	}

}