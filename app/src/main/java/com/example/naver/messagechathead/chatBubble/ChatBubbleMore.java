package com.example.naver.messagechathead.chatBubble;

import java.util.ArrayList;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import com.example.naver.messagechathead.chatRoom.ChatRoomCreator;
import com.example.naver.messagechathead.chatRoom.ChatRoomListCreator;
import com.example.naver.messagechathead.utils.ChatBubbleHelper;

/**
 * Created by Naver on 16. 8. 18..
 */
public class ChatBubbleMore extends ChatBubbleOpen{
	public ChatBubbleMore(Context context, WindowManager windowManager,
		ChatBubbleDeleteBtn chatBubbleDeleteBtn, ChatRoomCreator chatRoomCreator,
		ChatRoomListCreator chatRoomListCreator) {
		super(context, windowManager, chatBubbleDeleteBtn, chatRoomCreator, chatRoomListCreator);
	}

	@Override
	public void moveBubbleOnEventUp(WindowManager.LayoutParams layoutParams, ArrayList<ChatBubble> bubbleList) {
	}

	@Override
	protected void showBubbleRoomView() {
		chatRoomListView.setChangeVisible();
		if (chatRoomView.getChatRoomVisibility()) {
			chatRoomView.setChangeVisible();
		}

		if (!chatRoomView.getChatRoomVisibility() && !chatRoomListView.getChatRoomListVisibility()) {
			chatBubbleContainer.changeToCloseBubbleList();
			for (int i = 0; i < chatBubbleContainer.getBubbleList().size(); i++) {
				chatBubbleContainer.getBubbleList().get(i).moveTo(ChatBubbleHelper.getOptimizeWidth(), 0);
			}
		}
	}

	@Override
	protected void moveToFirstBubbleFace() {
	}

	@Override
	public void scrollBubbleFace(ChatBubble view, ArrayList<ChatBubble> bubbleList, float distanceX, float distanceY) {
	}

	@Override
	protected void flingBubbleFace(View view, WindowManager.LayoutParams layoutParams,
		ArrayList<ChatBubble> bubbleList, int velocityX, int velocityY) {
	}
}
