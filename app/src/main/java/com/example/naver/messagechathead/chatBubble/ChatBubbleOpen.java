package com.example.naver.messagechathead.chatBubble;

	import java.util.ArrayList;

	import android.support.v4.view.ViewCompat;
	import android.view.View;
	import android.view.WindowManager;
	import android.widget.OverScroller;
	import com.example.naver.messagechathead.utils.ChatBubbleHelper;

/**
 * Created by DAEUN on 16. 8. 11..
 */
public class ChatBubbleOpen  {

	public ChatBubbleOpen() {

	}

	public void moveToStart(OverScroller scroller, WindowManager.LayoutParams layoutParams) {
		int finalX = ChatBubbleHelper.getOptimizeWidth() - layoutParams.x;
		int finalY = -layoutParams.y;
		scroller.startScroll(layoutParams.x, layoutParams.y, finalX, finalY);
	}

	public void updateViewBubble(View view, OverScroller scroller, WindowManager.LayoutParams layoutParams, int velocityX, int velocityY) {
		scroller.fling(
			layoutParams.x, layoutParams.y, velocityX, velocityY,
			0, ChatBubbleHelper.getOptimizeWidth(),
			0, ChatBubbleHelper.getOptimizeHeight());
		ViewCompat.postInvalidateOnAnimation(view);
	}

	public void scrollBubble(View view, OverScroller scroller, WindowManager windowManager, WindowManager.LayoutParams layoutParams) {
		layoutParams.x = scroller.getCurrX();
		layoutParams.y = scroller.getCurrY();
		windowManager.updateViewLayout(view, layoutParams);
	}

	public void updateViewBubble(View view, WindowManager windowManager, WindowManager.LayoutParams layoutParams, float distanceX,
		float distanceY) {
		layoutParams.x -= (int)distanceX;
		layoutParams.y -= (int)distanceY;
		windowManager.updateViewLayout(view, layoutParams);
	}


}

