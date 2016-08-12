package com.example.naver.messagechathead.data;

import java.net.URL;

/**
 * Created by Naver on 16. 8. 12..
 */
public class ChatRoomListData {
	//URL chatRoomImage;
	String chatRoomName;

	String chatRoomLastMessage;

	public ChatRoomListData(/*URL chatRoomImage, */String chatRoomName, String chatRoomLastMessage) {
		//this.chatRoomImage = chatRoomImage;
		this.chatRoomName = chatRoomName;
		this.chatRoomLastMessage = chatRoomLastMessage;
	}

	public String getChatRoomName() {
		return chatRoomName;
	}

	public String getChatRoomLastMessage() {
		return chatRoomLastMessage;
	}

	public void setChatRoomName(String chatRoomName) {
		this.chatRoomName = chatRoomName;
	}

	public void setChatRoomLastMessage(String chatRoomLastMessage) {
		this.chatRoomLastMessage = chatRoomLastMessage;
	}
}
