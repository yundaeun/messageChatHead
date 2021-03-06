package com.example.naver.messagechathead.chatBubble;

import java.util.ArrayList;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.OverScroller;
import android.widget.RelativeLayout;
import com.bumptech.glide.Glide;
import com.example.naver.messagechathead.R;
import com.example.naver.messagechathead.chatRoom.ChatRoomCreator;
import com.example.naver.messagechathead.chatRoom.ChatRoomListCreator;
import com.example.naver.messagechathead.service.ChatBubbleUIService;
import com.example.naver.messagechathead.utils.RoundImageTransform;

/**
 * Created by Naver on 16. 8. 18..
 */
public abstract class ChatBubble extends RelativeLayout {
	protected WindowManager.LayoutParams layoutParams;
	private GestureDetector gestureDetector;
	protected OverScroller scroller;
	private int bubbleSize;
	ChatBubbleDeleteBtn chatBubbleDeleteBtn;
	ChatRoomListCreator chatRoomListView;
	ChatRoomCreator chatRoomView;
	ImageView faceIcon;
	ArrayList<ChatBubble> bubbleList;
	ChatBubbleContainer container;

	public ChatBubble(Context context, ChatBubbleContainer container, ChatBubbleDeleteBtn chatBubbleDeleteBtn, ChatRoomCreator chatRoomCreator, ChatRoomListCreator chatRoomListCreator) {
		super(context);
		this.container = container;
		this.chatBubbleDeleteBtn = chatBubbleDeleteBtn;
		this.chatRoomView = chatRoomCreator;
		this.chatRoomListView = chatRoomListCreator;
	}

	public void setImageURL() {
		Glide.with(getContext())
			.load("http://inthecheesefactory.com/uploads/source/glidepicasso/cover.jpg")
			.centerCrop()
			.override(bubbleSize, bubbleSize)
			.transform(new RoundImageTransform(getContext()))
			.into(faceIcon);
	}

	public void init() {
		bubbleSize = container.getBubbleSize();
		faceIcon = (ImageView) inflate(getContext(), R.layout.chat_bubble_layout, null);
		addView(faceIcon);

		container.attachLayout(this, Gravity.START | Gravity.TOP, View.VISIBLE, bubbleSize, bubbleSize, WindowManager.LayoutParams.TYPE_PRIORITY_PHONE);

		gestureDetector = new GestureDetector(getContext(), new SimpleGestureListener());
		scroller = new OverScroller(getContext());

		layoutParams = (WindowManager.LayoutParams)this.getLayoutParams();
		bubbleList = container.getBubbleCloseList();
		setImageURL();

	}

	public void show() {
		setAlpha(0f);
		animate().alpha(1).setDuration(1);
	}

	public void hide() {
		setAlpha(1f);
		animate().alpha(0).setDuration(1);
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
				moveBubbleOnActionUp(layoutParams, bubbleList);
				serviceStopWithDeleteBtn();
				break;
		}

		/* 상대좌표를 절대좌표로 변경시켜주는 함수
		 * getRawX getX 모두 절대좌표로 사용 가능 */
		event.setLocation(event.getRawX(), event.getRawY());
		gestureDetector.onTouchEvent(event);
		return false;
	}

	@TargetApi(Build.VERSION_CODES.KITKAT)
	private void serviceStopWithDeleteBtn() {
		if (chatBubbleDeleteBtn.isOverlayDeleteArea(layoutParams)) {

			Intent intent = new Intent(getContext().getApplicationContext(), ChatBubbleUIService.class);
			getContext().getApplicationContext().stopService(intent);
			Log.d("Yde", "yde stop service");
		}
		chatBubbleDeleteBtn.deleteAreaHide();
	}

	@Override
	public void computeScroll() {
		if (scroller.computeScrollOffset()) {
			if (scroller.isFinished()) {
				requestLayout();
				scrollToStartOnScrollIsFinished(layoutParams);
			} else {
				scrollBubbleOnComputeScroll(this, layoutParams, bubbleList);
			}
			ViewCompat.postInvalidateOnAnimation(this);
		}
	}

	private void fling(int velocityX, int velocityY) {
		flingBubble(this, layoutParams, bubbleList, velocityX, velocityY);
	}

	class SimpleGestureListener extends GestureDetector.SimpleOnGestureListener {
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			scroller.forceFinished(true);
			fling((int)velocityX / 2 , (int)velocityY / 2);
			return true;
		}

		@Override
		public boolean onSingleTapConfirmed(MotionEvent e) {
			scroller.forceFinished(true);
			changeBubbleState();
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
			scrollBubble(ChatBubble.this, bubbleList, distanceX, distanceY);
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

	public abstract void moveBubbleOnActionUp(WindowManager.LayoutParams layoutParams, ArrayList<ChatBubble> bubbleList);
	public abstract void scrollToStartOnScrollIsFinished(WindowManager.LayoutParams layoutParams);
	public abstract void scrollBubbleOnComputeScroll(View view,
		WindowManager.LayoutParams layoutParams, ArrayList<ChatBubble> bubbleList);
	public abstract void flingBubble(View view, WindowManager.LayoutParams layoutParams, ArrayList<ChatBubble> bubbleList, int velocityX, int velocityY);
	public abstract void scrollBubble(ChatBubble view, ArrayList<ChatBubble> bubbleList, float distanceX, float distanceY);
	public abstract void changeBubbleState();

}
