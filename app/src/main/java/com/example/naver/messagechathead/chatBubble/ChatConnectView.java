package com.example.naver.messagechathead.chatBubble;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import com.example.naver.messagechathead.R;

/**
 * Created by Naver on 16. 8. 23..
 */
public class ChatConnectView extends ImageView {

	WindowManager.LayoutParams layoutParams;
	ImageView imageView;
	int connectionSize = 200;
	ChatBubbleContainer container;

	public ChatConnectView(Context context, ChatBubbleContainer container) {
		super(context);
		this.container = container;
	}

	public void init() {
		imageView = (ImageView)this;
		imageView.setImageResource(R.drawable.chat_bubble_room_semo);

		container.attachLayout(this, Gravity.START | Gravity.TOP, View.GONE, connectionSize, connectionSize, WindowManager.LayoutParams.TYPE_PHONE);
		layoutParams = (WindowManager.LayoutParams)this.getLayoutParams();
		setPosition(0);
	}

	public void setPosition(int bubblePosition) {
		layoutParams.x = getXWhenOpen(bubblePosition);
		layoutParams.y = container.getBubbleSize() - 65;
		container.updateViewLayout(imageView, layoutParams);
	}

	public int getXWhenOpen(int index) {
		return container.getOptimizeWidth() - container.getBubbleSize() * index + container.getBubbleSize() / 2 - connectionSize / 2;
	}

}
