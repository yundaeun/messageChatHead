package com.example.naver.messagechathead.chatRoom;

import java.util.ArrayList;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;
import com.example.naver.messagechathead.R;
import com.example.naver.messagechathead.adapter.ChatRoomListAdapter;
import com.example.naver.messagechathead.data.ChatRoomListData;
import com.example.naver.messagechathead.utils.ChatBubbleConfig;
import com.example.naver.messagechathead.utils.ChatBubbleHelper;

/**
 * Created by Naver on 16. 8. 12..
 */
public class ChatRoomListCreator {
	private Context context;
	private WindowManager windowManager;
	private View ChatRoomListView;

	public ChatRoomListCreator(Context context, WindowManager windowManager) {
		this.context = context;
		this.windowManager = windowManager;

		int faceIconSize = ChatBubbleHelper.displayWidth / ChatBubbleConfig.BUBBLE_NUM;
		int dialogHeight = ChatBubbleHelper.displayHeight - faceIconSize - 65; // 화면 넘게 그려짐
		int dialogWidth = ChatBubbleHelper.displayWidth - 50;

		LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		ChatRoomListView = layoutInflater.inflate(R.layout.chat_room_list_layout, null);
		ChatBubbleHelper chatBubbleHelper = new ChatBubbleHelper(context, windowManager);
		chatBubbleHelper.attachLayout(ChatRoomListView, Gravity.BOTTOM, View.GONE, dialogWidth, dialogHeight,
			WindowManager.LayoutParams.TYPE_PHONE);

		ListView chatRoomListView = (ListView)ChatRoomListView.findViewById(R.id.chat_room_listview);

		ArrayList<ChatRoomListData> chatRoomList = new ArrayList<>();
		chatRoomList.add(new ChatRoomListData("윤다은", "알겠습니다."));
		chatRoomList.add(new ChatRoomListData("김미미", "감사합니다."));
		chatRoomList.add(new ChatRoomListData("슈슈니", "오케이!"));

		ChatRoomListAdapter
			chatRoomListAdapter = new ChatRoomListAdapter(context, R.layout.chat_room_list_item, chatRoomList);
		chatRoomListView.setAdapter(chatRoomListAdapter);
	}

	public void setChangeVisible() {
		if (ChatRoomListView.getVisibility() == View.GONE) {
			ChatRoomListView.setVisibility(View.VISIBLE);
		} else {
			ChatRoomListView.setVisibility(View.GONE);
		}
	}
}
