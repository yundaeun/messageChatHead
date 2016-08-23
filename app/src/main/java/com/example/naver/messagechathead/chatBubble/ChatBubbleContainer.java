package com.example.naver.messagechathead.chatBubble;

import java.util.ArrayList;

import android.util.Log;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by Naver on 16. 8. 15..
 */
public class ChatBubbleContainer {

	static WindowManager windowManager;
	/*
	* TODO static 제거
	* */
	public static ArrayList<ChatBubble> bubbleList;
	public static ArrayList<ChatBubble> bubbleOpenList;
	public static ArrayList<ChatBubble> bubbleCloseList;
	public static int prev_param_x;
	public static int prev_param_y;

	public ChatBubbleContainer(WindowManager windowManager) {
		this.windowManager = windowManager;
	}

	public static void addChatBubble(ChatBubble chatBubble) {
		if (bubbleList == null) {
			bubbleList = new ArrayList<>();
		}
		bubbleList.add(chatBubble);
	}

	public static ArrayList<ChatBubble> getBubbleList() {
		return bubbleList;
	}

	public static void addChatBubbleOpen(ChatBubble chatBubble) {
		if (bubbleOpenList == null) {
			bubbleOpenList = new ArrayList<>();
		}
		bubbleOpenList.add(chatBubble);
	}

	public static ArrayList<ChatBubble> getBubbleOpenList() {
		return bubbleOpenList;
	}

	public static void addChatBubbleClose (ChatBubble chatBubble) {
		if (bubbleCloseList == null) {
			bubbleCloseList = new ArrayList<>();
		}
		bubbleCloseList.add(chatBubble);
	}

	public static ArrayList<ChatBubble> getBubbleCloseList() {
		return bubbleCloseList;
	}

	public static ArrayList<ChatBubble> changeToOpenBubbleList() {
		bubbleOpenList = getBubbleOpenList();
		bubbleCloseList = getBubbleCloseList();
		bubbleList = bubbleOpenList;
		changeToOpenParams(bubbleList, bubbleCloseList);
		return bubbleList;
	}

	private static void changeToOpenParams(ArrayList<ChatBubble> bubbleList, ArrayList<ChatBubble> bubbleCloseList) {

		for (int i=0; i<bubbleList.size(); i++ ) {
			bubbleList.get(i).layoutParams.x = bubbleCloseList.get(i).layoutParams.x;
			bubbleList.get(i).layoutParams.y = bubbleCloseList.get(i).layoutParams.y;
			bubbleOpenList.get(i).setVisibility(View.VISIBLE);
			bubbleCloseList.get(i).setVisibility(View.GONE);
			windowManager.updateViewLayout(bubbleList.get(i), bubbleList.get(i).layoutParams);
		}
	}

	public static ArrayList<ChatBubble> changeToCloseBubbleList() {
		bubbleOpenList = getBubbleOpenList();
		bubbleCloseList = getBubbleCloseList();
		bubbleList = getBubbleList();
		bubbleList = bubbleCloseList;
		changeToCloseParams(bubbleList, bubbleOpenList);

		return bubbleList;
	}

	private static void changeToCloseParams(ArrayList<ChatBubble> bubbleList, ArrayList<ChatBubble> bubbleOpenList) {
		for (int i=0; i<bubbleList.size(); i++ ) {
			bubbleList.get(i).layoutParams.x = bubbleOpenList.get(i).layoutParams.x;
			bubbleList.get(i).layoutParams.y = bubbleOpenList.get(i).layoutParams.y;
			bubbleCloseList.get(i).setVisibility(View.VISIBLE);
			bubbleOpenList.get(i).setVisibility(View.GONE);
			windowManager.updateViewLayout(bubbleList.get(i), bubbleList.get(i).layoutParams);
		}
	}

	public void saveParamsBeforeBubbleOpen(int prev_param_x, int prev_param_y) {
		this.prev_param_x = prev_param_x;
		this.prev_param_y = prev_param_y;
	}

	public int getParamsXBeforeBubbleOpen() {
		Log.d("yde", "yde x : " + prev_param_x);
		return prev_param_x;
	}

	public int getParamsYBeforeBubbleOpen() {
		Log.d("yde", "yde y : " + prev_param_y);
		return prev_param_y;
	}
}
