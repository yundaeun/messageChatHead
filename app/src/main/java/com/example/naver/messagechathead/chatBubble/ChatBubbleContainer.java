package com.example.naver.messagechathead.chatBubble;

import java.util.ArrayList;

import android.util.Log;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by Naver on 16. 8. 15..
 */
public class ChatBubbleContainer {

	public static ArrayList<ChatBubble> bubbleList;
	public static ArrayList<ChatBubble> bubbleOpenList;
	public static ArrayList<ChatBubble> bubbleCloseList;

	public ChatBubbleContainer() {

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
			bubbleCloseList.get(i).setVisibility(View.GONE);
			bubbleOpenList.get(i).setVisibility(View.VISIBLE);
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
			bubbleOpenList.get(i).setVisibility(View.GONE);
			bubbleCloseList.get(i).setVisibility(View.VISIBLE);
		}
	}
}
