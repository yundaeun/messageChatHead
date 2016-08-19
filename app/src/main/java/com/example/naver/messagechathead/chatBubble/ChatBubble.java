package com.example.naver.messagechathead.chatBubble;

import android.animation.Animator;
import android.content.Context;
import android.graphics.PixelFormat;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.OverScroller;
import android.widget.RelativeLayout;
import com.example.naver.messagechathead.R;
import com.example.naver.messagechathead.chatRoom.ChatRoomCreator;
import com.example.naver.messagechathead.chatRoom.ChatRoomListCreator;
import com.example.naver.messagechathead.service.ChatBubbleUIService;
import com.example.naver.messagechathead.utils.ChatBubbleHelper;

/**
 * Created by Naver on 16. 8. 18..
 */
public abstract class ChatBubble extends RelativeLayout {
	protected WindowManager windowManager;
	protected WindowManager.LayoutParams layoutParams;
	private GestureDetector gestureDetector;
	private OverScroller scroller;
	private int bubbleSize;
	ChatBubbleDeleteBtn chatBubbleDeleteBtn;
	ChatRoomListCreator chatRoomListView;
	ChatRoomCreator chatRoomView;
	ChatBubbleOpen chatBubbleOpen;
	ChatBubbleClose chatBubbleClose;
	ImageView faceIcon;

	public ChatBubble(Context context, WindowManager windowManager, ChatBubbleDeleteBtn chatBubbleDeleteBtn, ChatRoomCreator chatRoomCreator, ChatRoomListCreator chatRoomListCreator) {
		super(context);
		this.windowManager = windowManager;
		this.chatBubbleDeleteBtn = chatBubbleDeleteBtn;
		this.chatRoomView = chatRoomCreator;
		this.chatRoomListView = chatRoomListCreator;
	}

	public void setImageResource(int resourceId) {
		faceIcon.setImageResource(resourceId);
	}

	public void init() {
		bubbleSize = ChatBubbleHelper.getBubbleSize();
		faceIcon = (ImageView) inflate(getContext(), R.layout.face_icon_layout, null);
		addView(faceIcon);
		layoutParams = attachLayout(this, Gravity.START | Gravity.TOP, bubbleSize);
		ChatBubbleContainer.addChatBubbleParams(layoutParams);

		gestureDetector = new GestureDetector(getContext(), new SimpleGestureListener());
		scroller = new OverScroller(getContext());

		chatBubbleOpen = new ChatBubbleOpen();
		chatBubbleClose = new ChatBubbleClose();
	}

	public boolean checkDialogState() {
		return chatRoomListView.getChatRoomListVisibility() || chatRoomView.getChatRoomVisibility();
	}

	private WindowManager.LayoutParams attachLayout(View view, int location, int size) {
		WindowManager.LayoutParams params =
			new WindowManager.LayoutParams(size, size, WindowManager.LayoutParams.TYPE_PRIORITY_PHONE,
				WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
				PixelFormat.TRANSLUCENT);
		params.gravity = location;
		windowManager = (WindowManager)getContext().getSystemService(getContext().WINDOW_SERVICE);
		windowManager.addView(view, params);
		return params;
	}

	public void moveTo(int finalX, int finalY) {
		scroller.forceFinished(true);
		scroller.startScroll(layoutParams.x, layoutParams.y, finalX - layoutParams.x, finalY - layoutParams.y);

		ViewCompat.postInvalidateOnAnimation(this);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
			case MotionEvent.ACTION_UP:
				if (checkDialogState()) {
					moveToFirst(scroller, layoutParams);
				} else {
					chatBubbleClose.moveToLeftOrRight(scroller);
				}
				ViewCompat.postInvalidateOnAnimation(this);
				serviceStopWithDeleteBtn();
				break;
		}

		/* 상대좌표를 절대좌표로 변경시켜주는 함수
		 * getRawX getX 모두 절대좌표로 사용 가능 */
		event.setLocation(event.getRawX(), event.getRawY());
		gestureDetector.onTouchEvent(event);
		return false;
	}

	private void serviceStopWithDeleteBtn() {
		if (chatBubbleDeleteBtn.isOverlayDeleteArea(layoutParams)) {
			ChatBubbleUIService chatBubbleUIService = new ChatBubbleUIService();
			chatBubbleUIService.stopService();
		}
		chatBubbleDeleteBtn.deleteAreaHide();
	}

	@Override
	public void computeScroll() {
		if (scroller.computeScrollOffset()) {
			if (scroller.isFinished()) {
				requestLayout();
				ChatBubbleAnimator chatBubbleAnimator = new ChatBubbleAnimator(getContext());
				chatBubbleAnimator.setAnimation(R.anim.bounce, ChatBubble.this);
				if (checkDialogState()) {
					moveToFirst(scroller, layoutParams);
				}
			} else {
				if (checkDialogState()) {
					chatBubbleOpen.scrollBubble(this, scroller, windowManager, layoutParams);
				} else {
					chatBubbleClose.scrollBubbles(scroller, windowManager);
				}
			}
			ViewCompat.postInvalidateOnAnimation(this);
		}
	}

	private void fling(int velocityX, int velocityY) {
		if (checkDialogState()) {
			flingBubble(this, scroller, layoutParams, velocityX, velocityY);

		} else {
			chatBubbleClose.flingBubbles(scroller, velocityX, velocityY);
		}
	}

	class SimpleGestureListener extends GestureDetector.SimpleOnGestureListener {
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			scroller.forceFinished(true);
			fling((int)velocityX / 3, (int)velocityY / 3 );
			return true;
		}

		@Override
		public boolean onSingleTapConfirmed(MotionEvent e) {
			scroller.forceFinished(true);
			onClickChatBubble(scroller);
			return true;
		}

		@Override
		public boolean onDown(MotionEvent e) {
			chatBubbleDeleteBtn.deleteAreaShow();
			scroller.forceFinished(true);
			return true;
		}

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
			moveFaceIcon(distanceX, distanceY);
			changeDeleteButtonImage();
			return true;
		}

		private void changeDeleteButtonImage() {
			boolean deleteArea = chatBubbleDeleteBtn.isOverlayDeleteArea(layoutParams);
			if (deleteArea) {
				chatBubbleDeleteBtn.changeDeleteButtonForNear();
			} else {
				chatBubbleDeleteBtn.changeDeleteButtonForFar();
			}
		}
	}

	public abstract void moveFaceIcon(float distanceX, float distanceY);
	public abstract void moveToFirst(OverScroller scroller, WindowManager.LayoutParams layoutParams);
	public abstract void onClickChatBubble(OverScroller scroller);
	public abstract void flingBubble(View view, OverScroller scroller, WindowManager.LayoutParams layoutParams, int velocityX, int velocityY);
}
