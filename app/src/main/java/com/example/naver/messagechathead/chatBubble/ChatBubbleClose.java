package com.example.naver.messagechathead.chatBubble;

import java.util.ArrayList;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.WindowManager;
import com.example.naver.messagechathead.chatRoom.ChatRoomCreator;
import com.example.naver.messagechathead.chatRoom.ChatRoomListCreator;

/**
 * Created by Naver on 16. 8. 17..
 */
public class ChatBubbleClose extends ChatBubble {

	ChatConnectView connectView;

	public ChatBubbleClose(Context context, ChatBubbleContainer container,
		ChatBubbleDeleteBtn chatBubbleDeleteBtn, ChatRoomCreator chatRoomCreator,
		ChatRoomListCreator chatRoomListCreator, ChatConnectView connectView) {
		super(context, container, chatBubbleDeleteBtn, chatRoomCreator, chatRoomListCreator);
		this.connectView = connectView;
	}

	@Override
	public void moveBubbleOnActionUp(WindowManager.LayoutParams layoutParams, ArrayList<ChatBubble> bubbleList) {
		if (isLeftSide(bubbleList)) {
			moveToLeft();
		} else {
			moveToRight();
		}
	}

	private boolean isLeftSide(ArrayList<ChatBubble> bubbleList) {
		return bubbleList.get(0).layoutParams.x + container.getBubbleSize() / 2 < container.getDisplayCenter();
	}

	private void moveToLeft() {
		moveTo(-30, bubbleList.get(0).layoutParams.y);
	}

	private void moveToRight() {
		moveTo(container.getOptimizeWidth() + 30, bubbleList.get(0).layoutParams.y);
	}

	@Override
	public void scrollToStartOnScrollIsFinished(WindowManager.LayoutParams layoutParams) {
	}

	@Override
	public void scrollBubbleOnComputeScroll(View view,
		WindowManager.LayoutParams layoutParams, ArrayList<ChatBubble> bubbleList) {
		if (bubbleList == null) {
		} else {
			for (ChatBubble bubble : bubbleList) {
				bubble.layoutParams.x = scroller.getCurrX();
				bubble.layoutParams.y = scroller.getCurrY();
				container.updateViewLayout(bubble, bubble.layoutParams);
			}
		}
	}

	@Override
	public void flingBubble(View view, WindowManager.LayoutParams layoutParams, ArrayList<ChatBubble> bubbleList, int velocityX, int velocityY) {
		for (ChatBubble bubbles : bubbleList) {
			scroller.fling(
				bubbles.layoutParams.x, bubbles.layoutParams.y, velocityX, velocityY,
				- 30, container.getOptimizeWidth() + 30,
				- 30, container.getOptimizeHeight() + 30, 60, 60);
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
			container.updateViewLayout(bubbleList.get(i), bubbleList.get(i).layoutParams);
		}
	}

	@Override
	public void changeBubbleState() {
		int paramx = layoutParams.x;
		int paramy = layoutParams.y;
		container.saveParamsBeforeBubbleOpen(paramx, paramy);

		container.changeToOpenBubbleList();
		changeChatRoomViewVisibility();
		arrangeChatBubbles();
	}

	private void changeChatRoomViewVisibility() {
		chatRoomView.setChangeVisible();
	}

	private void arrangeChatBubbles() {
		for (int i = 0; i < container.getBubbleList().size(); i++) {
			container.getBubbleList().get(i).moveTo(getXWhenOpen(container.getBubbleList().size()-i-1), 0);
		}
	}

	public int getXWhenOpen(int index) {
		return container.getOptimizeWidth() - container.getBubbleSize() * index;
	}

}
