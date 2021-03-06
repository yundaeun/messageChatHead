package com.example.naver.messagechathead.chatBubble;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import com.example.naver.messagechathead.chatRoom.ChatRoomCreator;
import com.example.naver.messagechathead.chatRoom.ChatRoomListCreator;

/**
 * Created by DAEUN on 16. 8. 11..
 */
public abstract class ChatBubbleOpen extends ChatBubble {

	ChatConnectView connectView;

	public ChatBubbleOpen(Context context, ChatBubbleContainer container,
		ChatBubbleDeleteBtn chatBubbleDeleteBtn, ChatRoomCreator chatRoomCreator,
		ChatRoomListCreator chatRoomListCreator, ChatConnectView connectView) {
		super(context, container, chatBubbleDeleteBtn, chatRoomCreator, chatRoomListCreator);
		this.connectView = connectView;
	}

	@Override
	public void moveBubbleOnActionUp(WindowManager.LayoutParams layoutParams, ArrayList<ChatBubble> bubbleList) {
		moveTo(container.getOptimizeWidth(), 0);
	}

	@Override
	public void changeBubbleState() {
		showBubbleRoomView();
		showConnectView();
	}

	@Override
	public void scrollToStartOnScrollIsFinished(WindowManager.LayoutParams layoutParams) {
		moveToFirstBubbleFace();
	}

	@Override
	public void scrollBubbleOnComputeScroll(View view,
		WindowManager.LayoutParams layoutParams, ArrayList<ChatBubble> bubbleList) {
		layoutParams.x = scroller.getCurrX();
		layoutParams.y = scroller.getCurrY();
		container.updateViewLayout(view, layoutParams);
	}

	@Override
	public void flingBubble(View view, WindowManager.LayoutParams layoutParams, ArrayList<ChatBubble> bubbleList, int velocityX, int velocityY) {
		flingBubbleFace(view, layoutParams, bubbleList, velocityX, velocityY);
	}

	@Override
	public void scrollBubble(ChatBubble view, ArrayList<ChatBubble> bubbleList, float distanceX, float distanceY) {
		scrollBubbleFace(view, bubbleList, distanceX, distanceY);
	}

	protected abstract void showBubbleRoomView();
	public abstract void showConnectView();
	protected abstract void moveToFirstBubbleFace();
	protected abstract void flingBubbleFace(View view, WindowManager.LayoutParams layoutParams, ArrayList<ChatBubble> bubbleList, int velocityX, int velocityY);
	public abstract void scrollBubbleFace(ChatBubble view, ArrayList<ChatBubble> bubbleList, float distanceX, float distanceY);
}

