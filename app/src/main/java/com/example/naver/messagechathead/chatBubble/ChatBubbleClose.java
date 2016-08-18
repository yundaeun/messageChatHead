package com.example.naver.messagechathead.chatBubble;

import java.util.ArrayList;
import android.support.v4.view.ViewCompat;
import android.view.WindowManager;
import android.widget.OverScroller;
import com.example.naver.messagechathead.utils.ChatBubbleHelper;

/**
 * Created by Naver on 16. 8. 17..
 */
public class ChatBubbleClose {

	private ArrayList<ChatBubble> bubbleList;
	private ArrayList<WindowManager.LayoutParams> bubbleParamList;

	public ChatBubbleClose() {
		bubbleList = ChatBubbleContainer.getBubbleList();
		bubbleParamList = ChatBubbleContainer.getBubbleParams();
	}

	public OverScroller moveToLeftOrRight(OverScroller scroller) {
		OverScroller sc;
		if (isLeftSide()) {
			sc = moveToLeft(scroller);
		} else {
			sc = moveToRight(scroller);
		}
		return sc;
	}

	private boolean isLeftSide() {
		return bubbleParamList.get(0).x + ChatBubbleHelper.getBubbleSize() / 2 < ChatBubbleHelper.getDisplayCenter();
	}

	private OverScroller moveToLeft(OverScroller scroller) {
		scroller.startScroll(bubbleParamList.get(0).x, bubbleParamList.get(0).y, -bubbleParamList.get(0).x, 0);
		return scroller;
	}

	private OverScroller moveToRight(OverScroller scroller) {
		scroller.startScroll(bubbleParamList.get(0).x, bubbleParamList.get(0).y, ChatBubbleHelper.getOptimizeWidth() - bubbleParamList.get(0).x, 0);
		return scroller;
	}

	public OverScroller scrollBubbles(OverScroller scroller, WindowManager windowManager) {
		if (bubbleList == null) {
		} else {
			for (int i = 0; i < bubbleList.size(); i++) {
				bubbleParamList.get(i).x = scroller.getCurrX();
				bubbleParamList.get(i).y = scroller.getCurrY();
				windowManager.updateViewLayout(bubbleList.get(i), bubbleParamList.get(i));
			}
		}
		return scroller;
	}

	public OverScroller flingBubbles(OverScroller scroller, int velocityX, int velocityY) {
		for (int i=0; i<bubbleList.size(); i++) {
			scroller.fling(
				bubbleParamList.get(i).x, bubbleParamList.get(i).y, velocityX, velocityY,
				0, ChatBubbleHelper.getOptimizeWidth(),
				0, ChatBubbleHelper.getOptimizeHeight());
			ViewCompat.postInvalidateOnAnimation(bubbleList.get(i));
		}
		return scroller;
	}

	public void updateViewBubbles(WindowManager windowManager, float distanceX, float distanceY) {
		for (int i = bubbleList.size()-1; i >= 0; i--) {
			if (i == bubbleList.size()-1) {
				bubbleParamList.get(i).x -= (int)distanceX;
				bubbleParamList.get(i).y -= (int)distanceY;
			} else {
				bubbleParamList.get(i).x = bubbleParamList.get(i+1).x + (int)distanceX;
				bubbleParamList.get(i).y = bubbleParamList.get(i+1).y + (int)distanceY;
			}
			windowManager.updateViewLayout(bubbleList.get(i), bubbleParamList.get(i));
		}
	}

	public OverScroller moveToUpAndArrangeBubbles(OverScroller scroller) {
		int finalX = ChatBubbleHelper.getOptimizeWidth() - bubbleParamList.get(bubbleList.size() - 1).x;
		for (int i = bubbleList.size() - 1; i >= 0; i--) {
			int finalY = -bubbleParamList.get(i).y;
			bubbleList.get(i).moveTo(finalX, finalY, bubbleParamList.get(i));
			ViewCompat.postInvalidateOnAnimation(bubbleList.get(i));
			finalX -= ChatBubbleHelper.getBubbleSize();
		}
		return scroller;
	}

	public OverScroller moveToTopAndRight(OverScroller scroller) {
		for (int i = 0; i < bubbleList.size(); i++) {
			int finalX = ChatBubbleHelper.getOptimizeWidth() - bubbleParamList.get(i).x;
			int finalY = -bubbleParamList.get(i).y;
			bubbleList.get(i).moveTo(finalX, finalY, bubbleParamList.get(i));
			ViewCompat.postInvalidateOnAnimation(bubbleList.get(i));
		}
		return scroller;
	}
}
