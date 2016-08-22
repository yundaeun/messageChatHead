package com.example.naver.messagechathead.chatBubble;

	import java.util.ArrayList;

	import android.content.Context;
	import android.view.View;
	import android.view.WindowManager;
	import com.example.naver.messagechathead.chatRoom.ChatRoomCreator;
	import com.example.naver.messagechathead.chatRoom.ChatRoomListCreator;
	import com.example.naver.messagechathead.utils.ChatBubbleHelper;

/**
 * Created by DAEUN on 16. 8. 11..
 */
public abstract class ChatBubbleOpen extends ChatBubble {

	ChatBubbleContainer chatBubbleContainer;

	public ChatBubbleOpen(Context context, WindowManager windowManager,
		ChatBubbleDeleteBtn chatBubbleDeleteBtn, ChatRoomCreator chatRoomCreator,
		ChatRoomListCreator chatRoomListCreator) {
		super(context, windowManager, chatBubbleDeleteBtn, chatRoomCreator, chatRoomListCreator);

		chatBubbleContainer = new ChatBubbleContainer();
	}

	@Override
	public void moveBubbleOnEventUp(WindowManager.LayoutParams layoutParams, ArrayList<ChatBubble> bubbleList) {
		moveTo(ChatBubbleHelper.getOptimizeWidth(), 0);
	}

	@Override
	public void changeBubbleState() {
		showBubbleRoomView();
	}

	@Override
	public void scrollToStartOnScrollIsFinished(WindowManager.LayoutParams layoutParams) {
		moveToFirstBubbleFace();
	}

	@Override
	public void scrollBubbleOnComputeScroll(View view, WindowManager windowManager,
		WindowManager.LayoutParams layoutParams, ArrayList<ChatBubble> bubbleList) {
		layoutParams.x = scroller.getCurrX();
		layoutParams.y = scroller.getCurrY();
		windowManager.updateViewLayout(view, layoutParams);
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
	protected abstract void moveToFirstBubbleFace();
	protected abstract void flingBubbleFace(View view, WindowManager.LayoutParams layoutParams, ArrayList<ChatBubble> bubbleList, int velocityX, int velocityY);
	public abstract void scrollBubbleFace(ChatBubble view, ArrayList<ChatBubble> bubbleList, float distanceX, float distanceY);
}

