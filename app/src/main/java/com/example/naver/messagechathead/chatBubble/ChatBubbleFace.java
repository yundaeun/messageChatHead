package com.example.naver.messagechathead.chatBubble;

import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.widget.OverScroller;
import com.example.naver.messagechathead.chatRoom.ChatRoomCreator;
import com.example.naver.messagechathead.chatRoom.ChatRoomListCreator;

/**
 * Created by DAEUN on 16. 8. 11..
 */
public class ChatBubbleFace extends ChatBubble {

	public ChatBubbleFace(Context context, WindowManager windowManager,
		ChatBubbleDeleteBtn chatBubbleDeleteBtn, ChatRoomCreator chatRoomCreator,
		ChatRoomListCreator chatRoomListCreator) {
		super(context, windowManager, chatBubbleDeleteBtn, chatRoomCreator, chatRoomListCreator);
	}

	/*
	*  직접 드래그하는 경우
	* */
	public void moveFaceIcon(float distanceX, float distanceY) {
		if (checkDialogState()) {
			chatBubbleOpen.flingBubble(this, windowManager, layoutParams, distanceX, distanceY);
		} else {
			chatBubbleClose.updateViewBubbles(windowManager, distanceX, distanceY);
		}
	}

	@Override
	public void moveToFirst(OverScroller scroller, WindowManager.LayoutParams layoutParams) {
		chatBubbleOpen.moveToStart(scroller, layoutParams);
	}

	@Override
	public void onClickChatBubble(OverScroller scroller) {
		if (checkDialogState()) {
			if (chatRoomListView.getChatRoomListVisibility()) {
				chatRoomListView.setChangeVisible();
			} else {
				// 오픈되어있는데 또누르면 ? 클로즈
				chatBubbleClose.moveToTopAndRight(scroller);
			}
		} else {
			// 클로즈에서 누르면? 오픈
			chatBubbleClose.moveToUpAndArrangeBubbles();
		}
		chatRoomView.setChangeVisible();
	}

	@Override
	public void flingBubble(View view, OverScroller scroller, WindowManager.LayoutParams layoutParams, int velocityX,
		int velocityY) {
		chatBubbleOpen.flingBubble(this, scroller, layoutParams, velocityX, velocityY);
	}
}

