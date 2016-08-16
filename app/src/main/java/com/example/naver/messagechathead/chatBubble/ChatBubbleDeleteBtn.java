package com.example.naver.messagechathead.chatBubble;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import com.example.naver.messagechathead.R;
import com.example.naver.messagechathead.utils.ChatBubbleConfig;
import com.example.naver.messagechathead.utils.ChatBubbleHelper;

/**
 * Created by Naver on 16. 8. 12..
 */
public class ChatBubbleDeleteBtn {
	private Context context;
	private WindowManager windowManager;
	private int displayWidth;
	private int displayHeight;
	private View deleteView;
	private ImageView deleteIcon;
	private ChatBubbleAnimator chatBubbleAnimator;

	public ChatBubbleDeleteBtn(Context context, WindowManager windowManager) {
		this.context = context;
		this.windowManager = windowManager;
		chatBubbleAnimator = new ChatBubbleAnimator(context);
	}

	public void init() {
		getDisplaySize();
		int faceIconSize = displayWidth / ChatBubbleConfig.BUBBLE_NUM;

		LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		deleteView = layoutInflater.inflate(R.layout.delete_icon_layout, null);
		deleteIcon = (ImageView)deleteView.findViewById(R.id.deleteImage);
		ChatBubbleHelper chatBubbleHelper = new ChatBubbleHelper(context, windowManager);
		chatBubbleHelper.attachLayout(deleteView, Gravity.BOTTOM | Gravity.CENTER, View.GONE, faceIconSize, faceIconSize,
			WindowManager.LayoutParams.TYPE_PRIORITY_PHONE);
	}

	// 중복 코드
	// util로 사용 불가
	private void getDisplaySize() {
		DisplayMetrics disp = context.getResources().getDisplayMetrics();
		displayWidth = disp.widthPixels * 4/5;
		displayHeight = disp.heightPixels;
	}

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

	public void changeDeleteButtonForNear() {
		deleteIcon.setImageResource(R.drawable.bug);
	}

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
