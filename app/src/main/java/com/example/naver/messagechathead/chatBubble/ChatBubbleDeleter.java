package com.example.naver.messagechathead.chatBubble;

import android.content.Context;
import android.graphics.PixelFormat;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import com.example.naver.messagechathead.R;
import com.example.naver.messagechathead.service.ChatBubbleUIService;
import com.example.naver.messagechathead.utils.ChatBubbleUtils;

/**
 * Created by Naver on 16. 8. 12..
 */
public class ChatBubbleDeleter {
	private Context context;
	private WindowManager windowManager;

	private View deleteView;
	private ImageView deleteIcon;
	private ChatBubbleAnimator chatBubbleAnimator;

	public ChatBubbleDeleter(Context context, WindowManager windowManager) {
		this.context = context;
		this.windowManager = windowManager;
		chatBubbleAnimator = new ChatBubbleAnimator(context);
	}

	public void init() {
		int faceIconSize = ChatBubbleUtils.displayWidth / 5;

		LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		deleteView = layoutInflater.inflate(R.layout.delete_icon_layout, null);
		deleteIcon = (ImageView)deleteView.findViewById(R.id.deleteImage);
		ChatBubbleUtils chatBubbleUtils = new ChatBubbleUtils(context, windowManager);
		chatBubbleUtils.attachLayout(deleteView, Gravity.BOTTOM | Gravity.CENTER, View.GONE, faceIconSize, faceIconSize,
			WindowManager.LayoutParams.TYPE_PRIORITY_PHONE);
	}

	/* 삭제 버튼
	 * 영역에 들어왔을 때,  */
	public boolean deleteArea(WindowManager.LayoutParams faceIconParams) {
		int[] location = new int[2];
		deleteIcon.getLocationOnScreen(location);
		int minWidth = location[0] - deleteIcon.getWidth();
		int minHeight = location[1] - deleteIcon.getHeight();
		int maxWidth = location[0] + deleteIcon.getWidth();
		int maxHeight = location[1] + deleteIcon.getHeight();

		if (faceIconParams.x < maxWidth && faceIconParams.x > minWidth && faceIconParams.y < maxHeight
			&& faceIconParams.y > minHeight) {
			return true;
		}
		return false;
	}

	/*
	*  영역에 들어왔을 때
	* */
	public void changeDeleteButtonForNear() {
		deleteIcon.setImageResource(R.drawable.bug);
	}

	/*
	*  영역 밖으로 나갔을 떄
	* */
	public void changeDeleteButtonForFar() {
		deleteIcon.setImageResource(R.drawable.image);
	}

	public void deleteAreaShow() {
		chatBubbleAnimator.setAnimation(R.anim.slide_up_in, deleteIcon, deleteView, false);
	}

	public void deleteAreaHide() {
		chatBubbleAnimator.setAnimation(R.anim.slide_down_out, deleteIcon, deleteView, true);
	}
}
