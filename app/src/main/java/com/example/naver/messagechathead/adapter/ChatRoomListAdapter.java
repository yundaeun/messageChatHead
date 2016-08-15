package com.example.naver.messagechathead.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.naver.messagechathead.R;
import com.example.naver.messagechathead.data.ChatRoomListData;

/**
 * Created by Naver on 16. 8. 12..
 */
public class ChatRoomListAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private ArrayList<ChatRoomListData> chatRoomList;
	private int layout;

	public ChatRoomListAdapter(Context context, int layout, ArrayList<ChatRoomListData> chatRoomList) {
		this.layout = layout;
		this.chatRoomList = chatRoomList;
		this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return chatRoomList.size();
	}

	@Override
	public Object getItem(int position) {
		return chatRoomList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ChatRoomListHolder viewHolder;

		if (convertView == null) {
			convertView = inflater.inflate(layout, parent, false);

			viewHolder = new ChatRoomListHolder();
			viewHolder.chatRoomName = (TextView)convertView.findViewById(R.id.chat_room_name);
			viewHolder.chatRoomLastMessage = (TextView)convertView.findViewById(R.id.chat_room_last_message);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ChatRoomListHolder)convertView.getTag();
		}

		viewHolder.chatRoomName.setText(chatRoomList.get(position).getChatRoomName());
		viewHolder.chatRoomLastMessage.setText(chatRoomList.get(position).getChatRoomLastMessage());

		return convertView;
	}

	class ChatRoomListHolder {
		//ImageView icon;
		TextView chatRoomName;
		TextView chatRoomLastMessage;
	}
}




