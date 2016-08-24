package com.example.naver.messagechathead.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
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
public class ChatRoomListAdapter extends RecyclerView.Adapter<ChatRoomListAdapter.ViewHolder> {
	private ArrayList<ChatRoomListData> chatRoomList;

	public static class ViewHolder extends RecyclerView.ViewHolder {
		public TextView chatRoomName;
		public TextView chatRoomLastMessage;

		public ViewHolder(View view) {
			super(view);
			chatRoomName = (TextView)view.findViewById(R.id.chat_room_name);
			chatRoomLastMessage = (TextView)view.findViewById(R.id.chat_room_last_message);
		}
	}

	public ChatRoomListAdapter(ArrayList<ChatRoomListData> chatRoomList) {
		this.chatRoomList = chatRoomList;
	}

	@Override
	public ChatRoomListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
		int viewType) {
		View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_bubble_room_list_item, parent, false);
		ViewHolder vh = new ViewHolder(v);
		return vh;
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		holder.chatRoomName.setText(chatRoomList.get(position).getChatRoomName());
		holder.chatRoomLastMessage.setText(chatRoomList.get(position).getChatRoomLastMessage());
	}

	@Override
	public int getItemCount() {
		return chatRoomList.size();
	}
}





