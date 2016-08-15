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

/**
 * Created by DAEUN on 16. 7. 12..
 */
public class ChatBubbleUIService extends Service {
	private WindowManager windowManager;
	public static int BUBBLES;  // 최고 4개까지 가능
	LayoutInflater layoutInflater;

	@Override
	public void onCreate() {
		super.onCreate();

		layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		ChatBubbleDeleteBtn chatBubbleDeleteBtn = new ChatBubbleDeleteBtn(getApplicationContext(), windowManager);
		chatBubbleDeleteBtn.init();

		// bubble
		BUBBLES = 1;
		for (int i = 0; i < BUBBLES; i++) {
			ChatBubble chatBubble =
				new ChatBubble(true, getApplicationContext(), windowManager, View.VISIBLE, chatBubbleDeleteBtn);
		}

		// default icon
		ChatBubble chatBubbleDefault =
			new ChatBubble(false, getApplicationContext(), windowManager, View.VISIBLE, chatBubbleDeleteBtn);
	}

	@Nullable
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}


}
