package com.example.naver.messagechathead.chatBubble;

import java.util.ArrayList;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.WindowManager;
import com.example.naver.messagechathead.chatRoom.ChatRoomCreator;
import com.example.naver.messagechathead.chatRoom.ChatRoomListCreator;

/**
 * Created by DAEUN on 16. 8. 11..
 */
public class ChatBubbleFace extends ChatBubbleOpen{

	ChatConnectView connectView;

	public ChatBubbleFace(Context context, ChatBubbleContainer container,
		ChatBubbleDeleteBtn chatBubbleDeleteBtn, ChatRoomCreator chatRoomCreator,
		ChatRoomListCreator chatRoomListCreator, ChatConnectView connectView) {
		super(context, container, chatBubbleDeleteBtn, chatRoomCreator, chatRoomListCreator, connectView);
		this.connectView = connectView;
	}

	@Override
	public void moveBubbleOnActionUp(WindowManager.LayoutParams layoutParams, ArrayList<ChatBubble> bubbleList) {
		moveTo(container.getOptimizeWidth(), 0);
	}

	@Override
	protected void showBubbleRoomView() {
		if (chatRoomListView.getChatRoomListVisibility()) {
			chatRoomListView.setChangeVisible();
		} else {
			chatRoomView.setChangeVisible();
		}

		if (!chatRoomView.getChatRoomVisibility() && !chatRoomListView.getChatRoomListVisibility()) {
			container.changeToCloseBubbleList();
			scrollToPrevPosition();
		}
	}

	private void scrollToPrevPosition() {
		for (ChatBubble bubble : container.getBubbleList()) {
			bubble.moveTo(container.getParamsXBeforeBubbleOpen(), container.getParamsYBeforeBubbleOpen());
			container.updateViewLayout(bubble, bubble.layoutParams);
		}
	}

	@Override
	protected void moveToFirstBubbleFace() {
		int finalX = container.getOptimizeWidth() - layoutParams.x;
		int finalY = -layoutParams.y;
		scroller.startScroll(layoutParams.x, layoutParams.y, finalX, finalY);
	}

	@Override
	public void scrollBubbleFace(ChatBubble view, ArrayList<ChatBubble> bubbleList, float distanceX, float distanceY) {
		view.layoutParams.x -= (int)distanceX;
		view.layoutParams.y -= (int)distanceY;
		container.updateViewLayout(view, view.layoutParams);
	}

	@Override
	public void showConnectView() {
		connectView.setPosition(0);
	}

	@Override
	protected void flingBubbleFace(View view, WindowManager.LayoutParams layoutParams,
		ArrayList<ChatBubble> bubbleList, int velocityX, int velocityY) {
		scroller.fling(
			layoutParams.x, layoutParams.y, velocityX, velocityY,
			30, container.getOptimizeWidth()-30,
			30, container.getOptimizeHeight()-30, 30, 30);
		ViewCompat.postInvalidateOnAnimation(view);
	}
}


