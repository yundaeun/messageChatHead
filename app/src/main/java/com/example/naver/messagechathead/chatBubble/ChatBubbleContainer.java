package com.example.naver.messagechathead.chatBubble;

import java.util.ArrayList;

import android.view.View;
import android.view.WindowManager;

/**
 * Created by Naver on 16. 8. 15..
 */
public class ChatBubbleContainer {
	// ChatBubble 목록과 삭제버튼을 관리
	// 터치 다운된 챗버블은 다른 챗버블보다 z-order가 높아야함
	public static ArrayList<ChatBubble> bubbleList;
	public static ArrayList<WindowManager.LayoutParams> bubbleParamsList;

	public ChatBubbleContainer() {
	}

	public static void addChatBubble(ChatBubble chatBubble) {
		if (bubbleList == null) {
			bubbleList = new ArrayList<>();
		}
		bubbleList.add(chatBubble);
	}

	public static void addChatBubbleParams(WindowManager.LayoutParams params) {
		if (bubbleParamsList == null) {
			bubbleParamsList = new ArrayList<>();
		}
		bubbleParamsList.add(params);
	}

	public static ArrayList<ChatBubble> getBubbleList() {
		return bubbleList;
	}

	public static ArrayList<WindowManager.LayoutParams> getBubbleParams() {
		return bubbleParamsList;
	}


}
