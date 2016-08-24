package com.example.naver.messagechathead.service;

import android.annotation.TargetApi;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import com.example.naver.messagechathead.chatBubble.ChatBubble;
import com.example.naver.messagechathead.chatBubble.ChatConnectView;
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

	ChatBubbleContainer chatBubbleContainer;
	WindowManager windowManager;

	ChatConnectView connectView;
	ChatBubbleMore openChatBubbleMore;
	ChatBubbleFace openChatBubbleFace;
	ChatBubbleClose closeChatBubbleMore;
	ChatBubbleClose closeChatBubbleFace;

	@Override
	public void onCreate() {
		super.onCreate();

		windowManager = (WindowManager)getApplicationContext().getSystemService(getApplicationContext().WINDOW_SERVICE);


		chatBubbleContainer = new ChatBubbleContainer(getApplicationContext(), windowManager, connectView);

		connectView = new ChatConnectView(getApplicationContext(), chatBubbleContainer);
		connectView.init();

		ChatBubbleDeleteBtn chatBubbleDeleteBtn = new ChatBubbleDeleteBtn(getApplicationContext(), chatBubbleContainer);
		chatBubbleDeleteBtn.init();

		ChatRoomCreator chatRoomCreator = new ChatRoomCreator(getApplicationContext(), chatBubbleContainer);
		ChatRoomListCreator chatRoomListCreator = new ChatRoomListCreator(getApplicationContext(), chatBubbleContainer);




		// open chat bubble
		openChatBubbleMore = new ChatBubbleMore(getApplicationContext(), chatBubbleContainer, chatBubbleDeleteBtn, chatRoomCreator, chatRoomListCreator, connectView);
		openChatBubbleMore.init();
		chatBubbleContainer.addChatBubbleOpen(openChatBubbleMore);

		openChatBubbleFace = new ChatBubbleFace(getApplicationContext(), chatBubbleContainer, chatBubbleDeleteBtn, chatRoomCreator, chatRoomListCreator, connectView);
		openChatBubbleFace.init();
		chatBubbleContainer.addChatBubbleOpen(openChatBubbleFace);

		// close chat bubble
		closeChatBubbleMore = new ChatBubbleClose(getApplicationContext(), chatBubbleContainer, chatBubbleDeleteBtn, chatRoomCreator, chatRoomListCreator, connectView);
		closeChatBubbleMore.init();
		chatBubbleContainer.addChatBubbleClose(closeChatBubbleMore);


		closeChatBubbleFace = new ChatBubbleClose(getApplicationContext(), chatBubbleContainer, chatBubbleDeleteBtn, chatRoomCreator, chatRoomListCreator, connectView);
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

	@TargetApi(Build.VERSION_CODES.KITKAT)
	@Override
	public void onDestroy() {
		super.onDestroy();

		if (openChatBubbleMore.isAttachedToWindow()) {
			windowManager.removeViewImmediate(openChatBubbleMore);
		}

		if (openChatBubbleFace.isAttachedToWindow()) {
			windowManager.removeViewImmediate(openChatBubbleFace);
		}

		if (closeChatBubbleMore.isAttachedToWindow()) {
			windowManager.removeViewImmediate(closeChatBubbleMore);
		}

		if (closeChatBubbleFace.isAttachedToWindow()) {
			windowManager.removeViewImmediate(closeChatBubbleFace);
		}

	}

}
