package com.example.naver.messagechathead.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import com.example.naver.messagechathead.R;
import com.example.naver.messagechathead.chatBubble.ChatBubble;
import com.example.naver.messagechathead.chatBubble.ChatBubbleClose;
import com.example.naver.messagechathead.chatBubble.ChatBubbleContainer;
import com.example.naver.messagechathead.chatBubble.ChatBubbleDeleteBtn;
import com.example.naver.messagechathead.chatBubble.ChatBubbleFace;
import com.example.naver.messagechathead.chatBubble.ChatBubbleMore;
import com.example.naver.messagechathead.chatRoom.ChatRoomCreator;
import com.example.naver.messagechathead.chatRoom.ChatRoomListCreator;

/**
 * Created by DAEUN on 16. 7. 12..
 */
public class ChatBubbleUIService extends Service {

	private WindowManager windowManager;
	ChatBubbleContainer chatBubbleContainer;

	@Override
	public void onCreate() {
		super.onCreate();

		windowManager = (WindowManager)getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
		chatBubbleContainer = new ChatBubbleContainer(windowManager);

		ChatBubbleDeleteBtn chatBubbleDeleteBtn = new ChatBubbleDeleteBtn(getApplicationContext(), windowManager);
		chatBubbleDeleteBtn.init();

		ChatRoomCreator chatRoomCreator = new ChatRoomCreator(getApplicationContext(), windowManager);
		ChatRoomListCreator chatRoomListCreator = new ChatRoomListCreator(getApplicationContext(), windowManager);


		// open chat bubble
		ChatBubbleMore openChatBubbleMore = new ChatBubbleMore(getApplicationContext(), windowManager, chatBubbleDeleteBtn, chatRoomCreator, chatRoomListCreator);
		openChatBubbleMore.init();
		chatBubbleContainer.addChatBubbleOpen(openChatBubbleMore);

		ChatBubbleFace openChatBubbleFace = new ChatBubbleFace(getApplicationContext(), windowManager, chatBubbleDeleteBtn, chatRoomCreator, chatRoomListCreator);
		openChatBubbleFace.init();
		chatBubbleContainer.addChatBubbleOpen(openChatBubbleFace);

		// close chat bubble
		ChatBubbleClose closeChatBubbleMore = new ChatBubbleClose(getApplicationContext(), windowManager, chatBubbleDeleteBtn, chatRoomCreator, chatRoomListCreator);
		closeChatBubbleMore.init();
		chatBubbleContainer.addChatBubbleClose(closeChatBubbleMore);


		ChatBubbleClose closeChatBubbleFace = new ChatBubbleClose(getApplicationContext(), windowManager, chatBubbleDeleteBtn, chatRoomCreator, chatRoomListCreator);
		closeChatBubbleFace.init();
		chatBubbleContainer.addChatBubbleClose(closeChatBubbleFace);

		// 사용 값
		chatBubbleContainer.addChatBubble(closeChatBubbleMore);
		chatBubbleContainer.addChatBubble(closeChatBubbleFace);

		for(ChatBubble bubbles : chatBubbleContainer.getBubbleOpenList()) {
			bubbles.setVisibility(View.GONE);
		}
	}

	@Nullable
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	public void stopService() {
		Intent intent = new Intent(getApplicationContext(), ChatBubbleUIService.class);
		getApplicationContext().stopService(intent);
	}

}
