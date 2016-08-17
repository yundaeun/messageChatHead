package com.example.naver.messagechathead.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.view.WindowManager;
import com.example.naver.messagechathead.chatBubble.ChatBubble;
import com.example.naver.messagechathead.chatBubble.ChatBubbleContainer;
import com.example.naver.messagechathead.chatBubble.ChatBubbleDeleteBtn;
import com.example.naver.messagechathead.utils.ChatBubbleConfig;

/**
 * Created by DAEUN on 16. 7. 12..
 */
public class ChatBubbleUIService extends Service {
	private WindowManager windowManager;

	@Override
	public void onCreate() {
		super.onCreate();

		ChatBubbleDeleteBtn chatBubbleDeleteBtn = new ChatBubbleDeleteBtn(getApplicationContext(), windowManager);
		chatBubbleDeleteBtn.init();

		// default icon
		//ChatBubble chatBubbleDefault =
		//	new ChatBubble(false, getApplicationContext(), windowManager, chatBubbleDeleteBtn);
		//chatBubbleDefault.init();

		int bubble = ChatBubbleConfig.BUBBLE_NUM;
		for (int i = 0; i < bubble; i++) {
			ChatBubble chatBubble = new ChatBubble(true, getApplicationContext(), windowManager, chatBubbleDeleteBtn);
			chatBubble.init();
			ChatBubbleContainer.addChatBubble(chatBubble);
		}
	}

	@Nullable
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	public static void stopService() {
		stopService();
	}
}
