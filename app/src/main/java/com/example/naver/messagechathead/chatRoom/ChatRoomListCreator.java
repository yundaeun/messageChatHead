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
import com.example.naver.messagechathead.chatBubble.ChatBubbleContainer;
import com.example.naver.messagechathead.data.ChatRoomListData;

/**
 * Created by Naver on 16. 8. 12..
 */
public class ChatRoomListCreator {
	private Context context;
	private ChatBubbleContainer container;
	private View chatRoomListView;

	public ChatRoomListCreator(Context context, ChatBubbleContainer container) {
		this.context = context;
		this.container = container;
		init();
	}

	private void init() {
		attachView();

		ListView chatRoomListView = (ListView)this.chatRoomListView.findViewById(R.id.chat_room_listview);
		ChatRoomListAdapter
			chatRoomListAdapter = new ChatRoomListAdapter(context, R.layout.chat_room_list_item, getChatRoomListDataList());
		chatRoomListView.setAdapter(chatRoomListAdapter);
	}

	private void attachView() {
		int dialogWidth = container.getWidth();
		int dialogHeight = container.getOptimizeHeight() - 120;

		LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		chatRoomListView = layoutInflater.inflate(R.layout.chat_room_list_layout, null);
		container.attachLayout(chatRoomListView, Gravity.BOTTOM, View.GONE, dialogWidth, dialogHeight,
			WindowManager.LayoutParams.TYPE_PHONE);
	}

	private ArrayList<ChatRoomListData> getChatRoomListDataList() {
		ArrayList<ChatRoomListData> chatRoomList = new ArrayList<>();
		chatRoomList.add(new ChatRoomListData("윤다은", "알겠습니다."));
		chatRoomList.add(new ChatRoomListData("김미미", "감사합니다."));
		chatRoomList.add(new ChatRoomListData("슈슈니", "오케이!"));
		chatRoomList.add(new ChatRoomListData("윤다은", "알겠습니다."));
		chatRoomList.add(new ChatRoomListData("김미미", "감사합니다."));
		chatRoomList.add(new ChatRoomListData("슈슈니", "오케이!"));
		return chatRoomList;
	}

	public void setChangeVisible() {
			if (chatRoomListView.getVisibility() == View.GONE) {
				chatRoomListView.setVisibility(View.VISIBLE);
			} else {
				chatRoomListView.setVisibility(View.GONE);
			}
	}

	public boolean getChatRoomListVisibility() {
		return chatRoomListView.getVisibility() == View.VISIBLE;
	}
}
