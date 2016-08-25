package com.example.naver.messagechathead.chatBubble;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.PixelFormat;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import com.example.naver.messagechathead.chatBubble.ChatBubble;
import com.example.naver.messagechathead.chatBubble.ChatConnectView;
import com.example.naver.messagechathead.utils.ChatBubbleConfig;

/**
 * Created by Naver on 16. 8. 15..
 */
public class ChatBubbleContainer {

	private final Context context;
	WindowManager windowManager;
	ChatConnectView connectView;
	/*
	* TODO 제거
	* */
	public ArrayList<ChatBubble> bubbleList;
	public ArrayList<ChatBubble> bubbleOpenList;
	public ArrayList<ChatBubble> bubbleCloseList;
	public int prev_param_x;
	public int prev_param_y;
	private int displayWidth;
	private int displayHeight;

	public ChatBubbleContainer(Context context, WindowManager windowManager, ChatConnectView connectView) {
		this.context = context;
		this.connectView = connectView;
		this.windowManager = windowManager;

		getDisplaySize();
	}

	public WindowManager.LayoutParams attachLayout(View view, int location, int visibilty, int width, int height, int type) {
		WindowManager.LayoutParams params =
			new WindowManager.LayoutParams(width, height, type,
				WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
				PixelFormat.TRANSPARENT);
		params.gravity = location;
		windowManager.addView(view, params);
		view.setVisibility(visibilty);
		return params;
	}

	public void clear() {
		bubbleList.clear();
		bubbleCloseList.clear();
		bubbleOpenList.clear();
	}

	public void addChatBubble(ChatBubble chatBubble) {
		if (bubbleList == null) {
			bubbleList = new ArrayList<>();
		}
		bubbleList.add(chatBubble);
	}

	public ArrayList<ChatBubble> getBubbleList() {
		return bubbleList;
	}

	public void addChatBubbleOpen(ChatBubble chatBubble) {
		if (bubbleOpenList == null) {
			bubbleOpenList = new ArrayList<>();
		}
		bubbleOpenList.add(chatBubble);
	}

	public ArrayList<ChatBubble> getBubbleOpenList() {
		return bubbleOpenList;
	}

	public void addChatBubbleClose (ChatBubble chatBubble) {
		if (bubbleCloseList == null) {
			bubbleCloseList = new ArrayList<>();
		}
		bubbleCloseList.add(chatBubble);
	}

	public ArrayList<ChatBubble> getBubbleCloseList() {
		return bubbleCloseList;
	}

	public ArrayList<ChatBubble> changeToOpenBubbleList() {
		bubbleOpenList = getBubbleOpenList();
		bubbleCloseList = getBubbleCloseList();
		bubbleList = bubbleOpenList;
		changeToOpenParams(bubbleList, bubbleCloseList);
		return bubbleList;
	}

	private void changeToOpenParams(ArrayList<ChatBubble> bubbleList, ArrayList<ChatBubble> bubbleCloseList) {

		for (int i=0; i<bubbleList.size(); i++ ) {
			bubbleList.get(i).layoutParams.x = bubbleCloseList.get(i).layoutParams.x;
			bubbleList.get(i).layoutParams.y = bubbleCloseList.get(i).layoutParams.y;
			bubbleCloseList.get(i).setVisibility(View.GONE);
			bubbleCloseList.get(i).hide();
			bubbleOpenList.get(i).setVisibility(View.VISIBLE);
			bubbleOpenList.get(i).show();
			//connectView.setVisibility(View.VISIBLE);
			windowManager.updateViewLayout(bubbleList.get(i), bubbleList.get(i).layoutParams);
		}
	}

	public ArrayList<ChatBubble> changeToCloseBubbleList() {
		bubbleOpenList = getBubbleOpenList();
		bubbleCloseList = getBubbleCloseList();
		bubbleList = getBubbleList();
		bubbleList = bubbleCloseList;
		changeToCloseParams(bubbleList, bubbleOpenList);

		return bubbleList;
	}

	private void changeToCloseParams(ArrayList<ChatBubble> bubbleList, ArrayList<ChatBubble> bubbleOpenList) {
		for (int i=0; i<bubbleList.size(); i++ ) {
			bubbleList.get(i).layoutParams.x = bubbleOpenList.get(i).layoutParams.x;
			bubbleList.get(i).layoutParams.y = bubbleOpenList.get(i).layoutParams.y;

			bubbleOpenList.get(i).setVisibility(View.GONE);
			bubbleOpenList.get(i).hide();
			bubbleCloseList.get(i).setVisibility(View.VISIBLE);
			bubbleCloseList.get(i).show();
			//connectView.setVisibility(View.GONE);
			windowManager.updateViewLayout(bubbleList.get(i), bubbleList.get(i).layoutParams);
		}
	}

	public void saveParamsBeforeBubbleOpen(int prev_param_x, int prev_param_y) {
		this.prev_param_x = prev_param_x;
		this.prev_param_y = prev_param_y;
	}

	public int getParamsXBeforeBubbleOpen() {
		return prev_param_x;
	}

	public int getParamsYBeforeBubbleOpen() {
		return prev_param_y;
	}

	private void getDisplaySize() {
		DisplayMetrics disp = context.getResources().getDisplayMetrics();
		displayWidth = disp.widthPixels;
		displayHeight = disp.heightPixels;
	}

	public int getBubbleSize() {
		return displayWidth / ChatBubbleConfig.BUBBLE_NUM;
	}

	public int getOptimizeWidth() {
		return displayWidth - getBubbleSize();
	}

	public int getOptimizeHeight() {
		return displayHeight - getBubbleSize();
	}

	public int getDisplayCenter() {
		return displayWidth / 2;
	}

	public int getWidth() {
		return displayWidth;
	}

	public int getHeight() {
		return displayHeight;
	}

	public void updateViewLayout(View bubble, WindowManager.LayoutParams layoutParams) {
		windowManager.updateViewLayout(bubble, layoutParams);
	}
}
