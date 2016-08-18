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

	public void moveToLeftOrRight(OverScroller scroller) {
		if (isLeftSide()) {
			moveToLeft(scroller);
		} else {
			moveToRight(scroller);
		}
	}

	private boolean isLeftSide() {
		return bubbleParamList.get(0).x + ChatBubbleHelper.getBubbleSize() / 2 < ChatBubbleHelper.getDisplayCenter();
	}

	private void moveToLeft(OverScroller scroller) {
		scroller.startScroll(bubbleParamList.get(0).x, bubbleParamList.get(0).y, -bubbleParamList.get(0).x, 0);
	}

	private void moveToRight(OverScroller scroller) {
		scroller.startScroll(bubbleParamList.get(0).x, bubbleParamList.get(0).y, ChatBubbleHelper.getOptimizeWidth() - bubbleParamList.get(0).x, 0);
	}

	public void scrollBubbles(OverScroller scroller, WindowManager windowManager) {
		if (bubbleList == null) {
		} else {
			for (int i = 0; i < bubbleList.size(); i++) {
				bubbleParamList.get(i).x = scroller.getCurrX();
				bubbleParamList.get(i).y = scroller.getCurrY();
				windowManager.updateViewLayout(bubbleList.get(i), bubbleParamList.get(i));
			}
		}
	}

	public void flingBubbles(OverScroller scroller, int velocityX, int velocityY) {
		for (int i=0; i<bubbleList.size(); i++) {
			scroller.fling(
				bubbleParamList.get(i).x, bubbleParamList.get(i).y, velocityX, velocityY,
				0, ChatBubbleHelper.getOptimizeWidth(),
				0, ChatBubbleHelper.getOptimizeHeight());
			ViewCompat.postInvalidateOnAnimation(bubbleList.get(i));
		}
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

	public void moveToUpAndArrangeBubbles(OverScroller scroller) {
		int finalX = ChatBubbleHelper.getOptimizeWidth() - bubbleParamList.get(bubbleList.size() - 1).x;
		for (int i = bubbleList.size() - 1; i >= 0; i--) {
			int finalY = -bubbleParamList.get(i).y;
			bubbleList.get(i).moveTo(finalX, finalY, bubbleParamList.get(i));
			ViewCompat.postInvalidateOnAnimation(bubbleList.get(i));
			finalX -= ChatBubbleHelper.getBubbleSize();
		}
	}

	public void moveToTopAndRight(OverScroller scroller) {
		for (int i = 0; i < bubbleList.size(); i++) {
			int finalX = ChatBubbleHelper.getOptimizeWidth() - bubbleParamList.get(i).x;
			int finalY = -bubbleParamList.get(i).y;
			bubbleList.get(i).moveTo(finalX, finalY, bubbleParamList.get(i));
			ViewCompat.postInvalidateOnAnimation(bubbleList.get(i));
		}
	}
}
