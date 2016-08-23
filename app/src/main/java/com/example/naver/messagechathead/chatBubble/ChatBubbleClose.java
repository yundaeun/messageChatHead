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
 * Created by Naver on 16. 8. 17..
 */
public class ChatBubbleClose extends ChatBubble {

	ChatBubbleContainer chatBubbleContainer;

	public ChatBubbleClose(Context context, WindowManager windowManager,
		ChatBubbleDeleteBtn chatBubbleDeleteBtn, ChatRoomCreator chatRoomCreator,
		ChatRoomListCreator chatRoomListCreator) {
		super(context, windowManager, chatBubbleDeleteBtn, chatRoomCreator, chatRoomListCreator);
		chatBubbleContainer = new ChatBubbleContainer(windowManager);
	}

	@Override
	public void moveBubbleOnEventUp(WindowManager.LayoutParams layoutParams, ArrayList<ChatBubble> bubbleList) {
		if (isLeftSide(bubbleList)) {
			moveToLeft();
		} else {
			moveToRight();
		}
	}

	private boolean isLeftSide(ArrayList<ChatBubble> bubbleList) {
		return bubbleList.get(0).layoutParams.x + ChatBubbleHelper.getBubbleSize() / 2 < ChatBubbleHelper.getDisplayCenter();
	}

	private void moveToLeft() {
		moveTo(0, bubbleList.get(0).layoutParams.y);
	}

	private void moveToRight() {
		moveTo(ChatBubbleHelper.getOptimizeWidth(), bubbleList.get(0).layoutParams.y);
	}

	@Override
	public void scrollToStartOnScrollIsFinished(WindowManager.LayoutParams layoutParams) {
	}

	@Override
	public void scrollBubbleOnComputeScroll(View view, WindowManager windowManager,
		WindowManager.LayoutParams layoutParams, ArrayList<ChatBubble> bubbleList) {
		if (bubbleList == null) {
		} else {
			for (ChatBubble bubble : bubbleList) {
				bubble.layoutParams.x = scroller.getCurrX();
				bubble.layoutParams.y = scroller.getCurrY();
				windowManager.updateViewLayout(bubble, bubble.layoutParams);
			}
		}
	}

	@Override
	public void flingBubble(View view, WindowManager.LayoutParams layoutParams, ArrayList<ChatBubble> bubbleList, int velocityX, int velocityY) {
		for (ChatBubble bubbles : bubbleList) {
			scroller.fling(
				bubbles.layoutParams.x, bubbles.layoutParams.y, velocityX, velocityY,
				+ 30, ChatBubbleHelper.getOptimizeWidth() - 30,
				+ 30, ChatBubbleHelper.getOptimizeHeight() - 30, 30, 30);
			ViewCompat.postInvalidateOnAnimation(bubbles);
		}
	}

	@Override
	public void scrollBubble(ChatBubble view, ArrayList<ChatBubble> bubbleList, float distanceX, float distanceY) {
		for (int i = bubbleList.size()-1; i >= 0; i--) {
			if (i == bubbleList.size()-1) {
				bubbleList.get(i).layoutParams.x -= (int)distanceX;
				bubbleList.get(i).layoutParams.y -= (int)distanceY;
			} else {
				bubbleList.get(i).layoutParams.x = bubbleList.get(i+1).layoutParams.x + (int)distanceX;
				bubbleList.get(i).layoutParams.y = bubbleList.get(i+1).layoutParams.y + (int)distanceY;
			}
			windowManager.updateViewLayout(bubbleList.get(i), bubbleList.get(i).layoutParams);
		}
	}

	@Override
	public void changeBubbleState() {
		int paramx = layoutParams.x;
		int paramy = layoutParams.y;
		chatBubbleContainer.saveParamsBeforeBubbleOpen(paramx, paramy);

		chatBubbleContainer.changeToOpenBubbleList();
		changeChatRoomViewVisibility();
		for (int i = 0; i < chatBubbleContainer.getBubbleList().size(); i++) {
			chatBubbleContainer.getBubbleList().get(i).moveTo(getXWhenOpen(chatBubbleContainer.getBubbleList().size()-i-1), 0);
		}
	}

	private void changeChatRoomViewVisibility() {
		chatRoomView.setChangeVisible();
	}

	public int getXWhenOpen(int index) {
		return ChatBubbleHelper.getOptimizeWidth() - ChatBubbleHelper.getBubbleSize() * index;
	}

}
