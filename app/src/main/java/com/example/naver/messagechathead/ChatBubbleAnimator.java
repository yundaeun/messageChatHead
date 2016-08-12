package com.example.naver.messagechathead;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

/**
 * Created by Naver on 16. 8. 11..
 */
public class ChatBubbleAnimator {

	private Context context;

	public ChatBubbleAnimator(Context context) {
		this.context = context;
	}

	/*
	*  애니메이션 설정
	*  @param anim : R.anim 리소스 아이디
	*  @param icon : 움직일 view
	*  @param parentView : icon을 갖고있는 부모 View
	*  @param animationEnd : onAnimationEnd (애니메이션 끝난 후 작업이 있는 경우 true)
	* */
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
