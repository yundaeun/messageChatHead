package com.example.naver.messagechathead.chatBubble;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.OverScroller;
import com.example.naver.messagechathead.chatRoom.ChatRoomCreator;
import com.example.naver.messagechathead.chatRoom.ChatRoomListCreator;

/**
 * Created by Naver on 16. 8. 18..
 */
public class ChatBubbleMore extends ChatBubble {

	public ChatBubbleMore(Context context, WindowManager windowManager,
		ChatBubbleDeleteBtn chatBubbleDeleteBtn, ChatRoomCreator chatRoomCreator, ChatRoomListCreator chatRoomListCreator) {
		super(context, windowManager, chatBubbleDeleteBtn, chatRoomCreator, chatRoomListCreator);
	}

	/*
	* 직접 드래그 하는 경우 없음
	* */
	@Override
	public void moveFaceIcon(float distanceX, float distanceY) {
		}

	@Override
	public void flingBubble(View view, OverScroller scroller, WindowManager.LayoutParams layoutParams, int velocityX,
		int velocityY) {
	}

	@Override
	public void moveToFirst(OverScroller scroller, WindowManager.LayoutParams layoutParams) {
	}

	@Override
	public void onClickChatBubble(OverScroller scroller) {
		chatRoomView.setChangeVisible();
		chatRoomListView.setChangeVisible();
	}



}
