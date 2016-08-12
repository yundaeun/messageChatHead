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

/**
 * Created by Naver on 16. 8. 12..
 */
public class ChatBubbleDeleter {
	private Context context;
	private WindowManager windowManager;
	private LayoutInflater layoutInflater;
	private int displayWidth;
	private int displayHeight;

	private View deleteView;
	private ImageView deleteIcon;

	public ChatBubbleDeleter(Context context, WindowManager windowManager) {
		this.context = context;
		this.windowManager = windowManager;
	}

	public void init() {
		getDisplaySize();
		int faceIconSize = displayWidth / 5;

		layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		deleteView = layoutInflater.inflate(R.layout.delete_icon_layout, null);
		deleteIcon = (ImageView)deleteView.findViewById(R.id.deleteImage);
		attachLayout(deleteView, Gravity.BOTTOM | Gravity.CENTER, View.GONE, faceIconSize, faceIconSize, WindowManager.LayoutParams.TYPE_PRIORITY_PHONE);
	}

	// 중복 코드
	private WindowManager.LayoutParams attachLayout(View view, int location, int visibilty, int width, int height, int type) {
		WindowManager.LayoutParams params = new WindowManager.LayoutParams(width, height, type,
			WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
			PixelFormat.TRANSLUCENT);
		params.gravity = location;
		windowManager = (WindowManager)context.getSystemService(context.WINDOW_SERVICE);
		windowManager.addView(view, params);
		view.setVisibility(visibilty);
		return params;
	}

	// 중복 코드
	private void getDisplaySize() {
		DisplayMetrics disp = context.getResources().getDisplayMetrics();
		displayWidth = disp.widthPixels;
		displayHeight = disp.heightPixels;
	}

	/*
	*  삭제 버튼 활성화
	*  motion event가 up일 때, 서비스 종료 (return false)
	*  @param motion : touch event
	* */
	public boolean onSelectedDeleteButton(WindowManager.LayoutParams faceIconParams) {
		int[] location = new int[2];
		deleteIcon.getLocationOnScreen(location);
		int minWidth = location[0] - deleteIcon.getWidth();
		int minHeight = location[1] - deleteIcon.getHeight();
		int maxWidth = location[0] + deleteIcon.getWidth();
		int maxHeight = location[1] + deleteIcon.getHeight();

		if (faceIconParams.x < maxWidth && faceIconParams.x > minWidth && faceIconParams.y < maxHeight && faceIconParams.y > minHeight) {
			return true;
		}
		return false;
	}

	/*
	*  영역에 들어오면 삭제 버튼 활성화
	* */
	public void changeImageBubbleDeleteBtn(boolean flag){
		if (flag) {
			deleteIcon.setImageResource(R.drawable.bug);
		} else {
			deleteIcon.setImageResource(R.drawable.image);
		}
	}

	public void showBubbleDeleteBtn(int motion) {
		ChatBubbleAnimator chatBubbleAnimator = new ChatBubbleAnimator(context);
		if (motion == MotionEvent.ACTION_DOWN) {
			chatBubbleAnimator.setAnimation(R.anim.slide_up_in, deleteIcon, deleteView, false);
		} else if (motion == MotionEvent.ACTION_UP) {
			chatBubbleAnimator.setAnimation(R.anim.slide_down_out, deleteIcon, deleteView, true);
		}
	}
}
