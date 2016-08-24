package com.example.naver.messagechathead.chatBubble;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import com.example.naver.messagechathead.R;

/**
 * Created by Naver on 16. 8. 12..
 */
public class ChatBubbleDeleteBtn {
	private Context context;
	private WindowManager windowManager;
	private View deleteView;
	private ImageView deleteIcon;
	private int bubbleSize;
	ChatBubbleContainer container;

	public ChatBubbleDeleteBtn(Context context, ChatBubbleContainer container) {
		this.context = context;
		this.windowManager = windowManager;
		this.container = container;
	}

	public void init() {
		bubbleSize = container.getBubbleSize();

		LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		deleteView = layoutInflater.inflate(R.layout.delete_icon_layout, null);
		deleteIcon = (ImageView)deleteView.findViewById(R.id.deleteImage);
		container.attachLayout(deleteView, Gravity.BOTTOM | Gravity.CENTER, View.GONE, bubbleSize, bubbleSize,
			WindowManager.LayoutParams.TYPE_PRIORITY_PHONE);
	}

	public boolean isOverlayDeleteArea(WindowManager.LayoutParams faceIconParams) {
		int[] location = new int[2];
		deleteIcon.getLocationOnScreen(location);
		int minWidth = location[0] - deleteIcon.getWidth();
		int maxWidth = location[0] + deleteIcon.getWidth();

		int minHeight = location[1] - (container.getBubbleSize() + deleteIcon.getHeight());
		int maxHeight = location[1] + deleteIcon.getHeight();

		return faceIconParams.x < maxWidth && faceIconParams.x > minWidth && faceIconParams.y < maxHeight && faceIconParams.y > minHeight;
	}

	public void changeDeleteButtonForNear() {
//		setAnimation(R.anim.zoom_in, deleteIcon, deleteView, false);
		deleteIcon.setImageResource(R.drawable.selected_del_icon);
	}

	public void changeDeleteButtonForFar() {
		//setAnimation(R.anim.zoom_out, deleteIcon, deleteView, false);
		deleteIcon.setImageResource(R.drawable.del_icon);
	}

	public void deleteAreaShow() {
		setAnimation(R.anim.slide_up_in, deleteIcon, deleteView, false);
	}

	public void deleteAreaHide() {
		setAnimation(R.anim.slide_down_out, deleteIcon, deleteView, true);
	}

	public void setAnimation(int anim, ImageView icon, final View parentView, final boolean animationEnd) {
		parentView.setVisibility(View.VISIBLE);
		Animation animation = AnimationUtils.loadAnimation(context, anim);

		animation.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}

			public void onAnimationEnd(Animation animation) {
				if (animationEnd) {
					parentView.setVisibility(View.GONE);
				}
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}
		});
		icon.startAnimation(animation);
	}
}
