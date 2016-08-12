package com.example.naver.messagechathead.chatRoom;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.PixelFormat;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;
import com.example.naver.messagechathead.R;
import com.example.naver.messagechathead.adapter.ChatRoomListAdapter;
import com.example.naver.messagechathead.data.ChatRoomListData;

/**
 * Created by Naver on 16. 8. 12..
 */
public class ChatRoomListCreator {
	private Context context;
	private WindowManager windowManager;
	private int displayWidth;
	private int displayHeight;
	private LayoutInflater layoutInflater;
	private View ChatRoomListView;

	public ChatRoomListCreator (Context context, WindowManager windowManager) {
		this.context = context;
		this.windowManager = windowManager;
		getDisplaySize();
		int faceIconSize = displayWidth / 5;
		int dialogSize = displayHeight - faceIconSize - 65; // 화면 넘게 그려짐

		layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		ChatRoomListView = layoutInflater.inflate(R.layout.chat_room_list_layout, null);
		ListView chatRoomListView = (ListView) ChatRoomListView.findViewById(R.id.chat_room_listview);

		ArrayList<ChatRoomListData> chatRoomList = new ArrayList<>();
		chatRoomList.add(new ChatRoomListData("윤다은", "알겠습니다."));
		chatRoomList.add(new ChatRoomListData("김미미", "감사합니다."));
		chatRoomList.add(new ChatRoomListData("슈슈니", "오케이!"));
		chatRoomList.add(new ChatRoomListData("윤다은", "알겠습니다."));
		chatRoomList.add(new ChatRoomListData("김미미", "감사합니다."));
		chatRoomList.add(new ChatRoomListData("슈슈니", "오케이!"));
		chatRoomList.add(new ChatRoomListData("윤다은", "알겠습니다."));
		chatRoomList.add(new ChatRoomListData("김미미", "감사합니다."));
		chatRoomList.add(new ChatRoomListData("슈슈니", "오케이!"));

		ChatRoomListAdapter
			chatRoomListAdapter = new ChatRoomListAdapter(context, R.layout.chat_room_list_item, chatRoomList);
		// 어댑터를 생성합니다.
		chatRoomListView.setAdapter(chatRoomListAdapter);


		attachLayout(ChatRoomListView, Gravity.BOTTOM, View.GONE, displayWidth - 50, dialogSize,
			WindowManager.LayoutParams.TYPE_PHONE);

	}
	// 중복 코드
	private void getDisplaySize() {
		DisplayMetrics disp = context.getResources().getDisplayMetrics();
		displayWidth = disp.widthPixels;
		displayHeight = disp.heightPixels;
	}

	// 중복 코드
	private WindowManager.LayoutParams attachLayout(View view, int location, int visibilty, int width, int height, int type) {
		WindowManager.LayoutParams params =
			new WindowManager.LayoutParams(width, height, type,
				WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
				PixelFormat.RGBX_8888);
		params.gravity = location;
		windowManager = (WindowManager)context.getSystemService(context.WINDOW_SERVICE);
		windowManager.addView(view, params);
		view.setVisibility(visibilty);
		return params;
	}

	public void setChangeVisible() {
		if (ChatRoomListView.getVisibility() == View.GONE) {
			ChatRoomListView.setVisibility(View.VISIBLE);
		} else {
			ChatRoomListView.setVisibility(View.GONE);
		}
	}
}
