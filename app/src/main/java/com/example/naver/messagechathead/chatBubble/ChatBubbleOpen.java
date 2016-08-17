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
	private ArrayList<ChatBubble> bubbleList;
	private ArrayList<WindowManager.LayoutParams> bubbleParamList;

	public ChatBubbleOpen() {
		bubbleList = ChatBubbleContainer.getBubbleList();
		bubbleParamList = ChatBubbleContainer.getBubbleParams();
	}
	public void moveToStart() {

	}

	public OverScroller updateViewBubble(View view, OverScroller scroller, WindowManager.LayoutParams layoutParams, int velocityX, int velocityY) {
		scroller.fling(
			layoutParams.x, layoutParams.y, velocityX, velocityY,
			0, ChatBubbleHelper.getOptimizeWidth(),
			0, ChatBubbleHelper.getOptimizeHeight());
		ViewCompat.postInvalidateOnAnimation(view);
		return scroller;
	}

	public OverScroller scrollBubble(View view, OverScroller scroller, WindowManager windowManager, WindowManager.LayoutParams layoutParams) {
		layoutParams.x = scroller.getCurrX();
		layoutParams.y = scroller.getCurrY();
		windowManager.updateViewLayout(view, layoutParams);
		return scroller;
	}

	public void updateViewBubble(View view, WindowManager windowManager, WindowManager.LayoutParams layoutParams, float distanceX,
		float distanceY) {
		layoutParams.x -= (int)distanceX;
		layoutParams.y -= (int)distanceY;
		windowManager.updateViewLayout(view, layoutParams);
	}


}

