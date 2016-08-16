package com.example.naver.messagechathead.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import com.example.naver.messagechathead.chatBubble.ChatBubble;
import com.example.naver.messagechathead.chatBubble.ChatBubbleDeleteBtn;
import com.example.naver.messagechathead.utils.ChatBubbleConfig;

/**
 * Created by DAEUN on 16. 7. 12..
 */
public class ChatBubbleUIService extends Service {
	private WindowManager windowManager;
	LayoutInflater layoutInflater;

	@Override
	public void onCreate() {
		super.onCreate();
		layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		ChatBubbleDeleteBtn chatBubbleDeleteBtn = new ChatBubbleDeleteBtn(getApplicationContext(), windowManager);
		chatBubbleDeleteBtn.init();

		// default icon
		ChatBubble chatBubbleDefault =
			new ChatBubble(false, getApplicationContext(), windowManager, View.GONE, chatBubbleDeleteBtn);


		// int bubble1 = ChatBubbleConfig.BUBBLE_NUM;
		// bubble
		int bubble = 1;

		for (int i = 0; i < bubble; i++) {
			ChatBubble chatBubble =
				new ChatBubble(true, getApplicationContext(), windowManager, View.VISIBLE, chatBubbleDeleteBtn);
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
