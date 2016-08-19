package com.example.naver.messagechathead.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import com.example.naver.messagechathead.R;
import com.example.naver.messagechathead.chatBubble.ChatBubble;
import com.example.naver.messagechathead.chatBubble.ChatBubbleFace;
import com.example.naver.messagechathead.chatBubble.ChatBubbleContainer;
import com.example.naver.messagechathead.chatBubble.ChatBubbleDeleteBtn;
import com.example.naver.messagechathead.chatBubble.ChatBubbleMore;
import com.example.naver.messagechathead.chatRoom.ChatRoomCreator;
import com.example.naver.messagechathead.chatRoom.ChatRoomListCreator;
import com.example.naver.messagechathead.utils.ChatBubbleHelper;

/**
 * Created by DAEUN on 16. 7. 12..
 */
public class ChatBubbleUIService extends Service {
	private WindowManager windowManager;
	ChatRoomCreator chatRoomCreator;
	ChatRoomListCreator chatRoomListCreator;

	@Override
	public void onCreate() {
		super.onCreate();

		windowManager = (WindowManager)getApplicationContext().getSystemService(Context.WINDOW_SERVICE);

		ChatBubbleDeleteBtn chatBubbleDeleteBtn = new ChatBubbleDeleteBtn(getApplicationContext(), windowManager);
		chatBubbleDeleteBtn.init();

		ChatRoomCreator chatRoomCreator = new ChatRoomCreator(getApplicationContext(), windowManager);
		ChatRoomListCreator chatRoomListCreator = new ChatRoomListCreator(getApplicationContext(), windowManager);

		ChatBubbleMore
			chatBubbleMore = new ChatBubbleMore(getApplicationContext(), windowManager, chatBubbleDeleteBtn, chatRoomCreator, chatRoomListCreator);
		chatBubbleMore.init();
		chatBubbleMore.setImageResource(R.drawable.image);
		ChatBubbleContainer.addChatBubble(chatBubbleMore);

		ChatBubbleFace
			chatBubbleFace = new ChatBubbleFace(getApplicationContext(), windowManager, chatBubbleDeleteBtn, chatRoomCreator, chatRoomListCreator);
		chatBubbleFace.init();
		ChatBubbleContainer.addChatBubble(chatBubbleFace);

	}

	@Nullable
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	public static void stopService() {
		for (ChatBubble chatBubble : ChatBubbleContainer.getBubbleList()) {
			chatBubble.setVisibility(View.GONE);
		}


	}
}
