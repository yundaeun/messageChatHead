package com.example.naver.messagechathead.chatBubble;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import com.example.naver.messagechathead.R;
import com.example.naver.messagechathead.utils.ChatBubbleHelper;

/**
 * Created by Naver on 16. 8. 23..
 */
public class ChatConnectView extends ImageView {

	WindowManager windowManager;
	WindowManager.LayoutParams layoutParams;
	ImageView imageView;
	int connectionSize = 200;

	public ChatConnectView(Context context, WindowManager windowManager) {
		super(context);
		this.windowManager = windowManager;
	}

	public void init() {
		imageView = (ImageView)this;
		imageView.setImageResource(R.drawable.semo);

		ChatBubbleHelper chatBubbleHelper = new ChatBubbleHelper(getContext(), windowManager);
		chatBubbleHelper.attachLayout(this, Gravity.START | Gravity.TOP, View.GONE, connectionSize, connectionSize, WindowManager.LayoutParams.TYPE_PHONE);
		layoutParams = (WindowManager.LayoutParams)this.getLayoutParams();
		setPosition(0);
	}

	public void setPosition(int bubblePosition) {
		layoutParams.x = getXWhenOpen(bubblePosition);
		layoutParams.y = ChatBubbleHelper.getBubbleSize() - 65;
		windowManager.updateViewLayout(imageView, layoutParams);
	}

	public int getXWhenOpen(int index) {
		return ChatBubbleHelper.getOptimizeWidth() - ChatBubbleHelper.getBubbleSize() * index + ChatBubbleHelper.getBubbleSize() / 2 - connectionSize / 2;
	}

}
