package com.example.naver.messagechathead;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

/**
 * Created by Naver on 16. 7. 12..
 */
public class ChatBubbleUIService extends Service implements View.OnTouchListener {
	private WindowManager.LayoutParams faceIconParams;
	private WindowManager.LayoutParams deleteParams;
	private WindowManager windowManager;
	LayoutInflater layoutInflater;
	private float START_X, START_Y; //움직이기 위해 터치한 시작 점
	private int PREV_X, PREV_Y; //움직이기 이전에 뷰가 위치한 점
	private int MAX_X = -1, MAX_Y = -1; //뷰의 위치 최대 값
	private int displayWidth, displayHeight;
	private View faceIcon;
	private View deleteView;
	private View chatView;
	private ImageView deleteIcon;

	ImageView faceImage;

	boolean touchorclick;

	@Override
	public void onCreate() {
		super.onCreate();

		getDisplaySize();

		layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		faceIcon = layoutInflater.inflate(R.layout.face_icon_layout, null);

		faceIcon.setOnTouchListener(this);
		faceIcon.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (chatView.getVisibility() == View.VISIBLE) {
					chatView.setVisibility(View.GONE);
				} else if (chatView.getVisibility() == View.GONE) {
					// 우측 상단으로 이동
					faceIconParams.x = MAX_X;
					faceIconParams.y = 0;
					windowManager.updateViewLayout(v, faceIconParams);
					chatView.setVisibility(View.VISIBLE);
				}
			}
		});
		deleteView = layoutInflater.inflate(R.layout.delete_icon_layout, null);
		deleteIcon = (ImageView)deleteView.findViewById(R.id.deleteImage);

		chatView = layoutInflater.inflate(R.layout.chat_view_layout, null);

		// 사이즈 지정
		int faceIconSize = displayWidth / 5; //아이콘 갯수
		int dialogSize = displayHeight - faceIconSize - 70; // 화면 넘게 그려짐

		faceIconParams = attachLayout(faceIcon, Gravity.START | Gravity.TOP, View.VISIBLE, faceIconSize, faceIconSize, WindowManager.LayoutParams.TYPE_PRIORITY_PHONE);
		attachLayout(deleteView, Gravity.BOTTOM | Gravity.CENTER, View.GONE, faceIconSize, faceIconSize, WindowManager.LayoutParams.TYPE_PRIORITY_PHONE);
		attachLayout(chatView, Gravity.BOTTOM, View.GONE, displayWidth - 50, dialogSize, WindowManager.LayoutParams.TYPE_PHONE);

		setMaxPosition();
	}

	@Nullable
	@Override
	public IBinder onBind(Intent intent) {return null;}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {

		// 삭제 버튼 활성화
		boolean serviceFlag = onSelectedDeleteButton(event.getAction());

		if (!serviceFlag) {
			faceIcon.setVisibility(View.GONE);
			deleteView.setVisibility(View.GONE);
			return false;

		} else {
			switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:

					START_X = event.getRawX(); //터치 시작 점
					START_Y = event.getRawY();
					PREV_X = faceIconParams.x; //뷰의 시작 점
					PREV_Y = faceIconParams.y;
					Log.d("BubbleService", "action down start : " + START_X + "," + START_Y);
					Log.d("BubbleService", "action down prev : " + PREV_X + "," + PREV_Y);

					// 삭제 아이콘 등장
					setAnimation(R.anim.slide_up_in, deleteIcon, deleteView, false);
					touchorclick = true;

				case MotionEvent.ACTION_MOVE:

					int x = (int)(event.getRawX() - START_X); //이동한 거리
					int y = (int)(event.getRawY() - START_Y); //이동한 거리

					faceIconParams.x = PREV_X + x;
					faceIconParams.y = PREV_Y + y;

					optimizePosition();

					windowManager.updateViewLayout(v, faceIconParams);

					// 클릭 리스너 받기
					if (y < 10 && x < 10) touchorclick = true;
					else touchorclick = false;

				case MotionEvent.ACTION_UP:
/*
				// 테두리 영역으로 부드럽게 이동
				// 영역 1 or 영역 4
				if (faceIconParams.x < MAX_X/2) {
					faceIconParams.x = 0;
				} else {
					faceIconParams.x = MAX_X;
				}
				windowManager.updateViewLayout(v, faceIconParams);
*/
					setAnimation(R.anim.slide_down_out, deleteIcon, deleteView, true);

					// 클릭 리스너 받기
					if (touchorclick) return false;
					else return true;

			}
		}
		return true;
	}

	/*
	*  삭제 버튼 활성화
	*  motion event가 up일 때, 서비스 종료 (return false)
	*  @param motion : touch event
	* */
	private boolean onSelectedDeleteButton(int motion) {
		int[] location = new int[2];
		deleteIcon.getLocationOnScreen(location);

		int minWidth = location[0] - deleteIcon.getWidth();
		int minHeight = location[1] - deleteIcon.getHeight();
		int maxWidth = location[0] + deleteIcon.getWidth();
		int maxHeight = location[1] + deleteIcon.getHeight();

		if (faceIconParams.x < maxWidth && faceIconParams.x > minWidth && faceIconParams.y < maxHeight
			&& faceIconParams.y > minHeight) {

			deleteIcon.setImageResource(R.drawable.bug);
			if (motion == MotionEvent.ACTION_UP) {
				stopSelf();
				return false;
			}
		} else {
			deleteIcon.setImageResource(R.drawable.image);
		}
		return true;
	}


	/* view inflate
	* @param view : inflate할 view
	* @param visibilty : 처음 실행 시 View의 상태를 결정
	* */
	private WindowManager.LayoutParams attachLayout(View view, int location, int visibilty, int width, int height, int type) {
		WindowManager.LayoutParams params = new WindowManager.LayoutParams(width, height, type,
			WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
			PixelFormat.TRANSLUCENT);
		params.gravity = location;
		windowManager = (WindowManager)getSystemService(WINDOW_SERVICE);
		windowManager.addView(view, params);
		view.setVisibility(visibilty);
		return params;
	}

	/*
	*  애니메이션 설정
	*  @param anim : R.anim 리소스 아이디
	*  @param icon : 움직일 view
	*  @param parentView : icon을 갖고있는 부모 View
	*  @param animationEnd : onAnimationEnd (애니메이션 끝난 후 작업이 있는 경우 true)
	* */
	private void setAnimation(int anim, ImageView icon, final View parentView, final boolean animationEnd) {
		parentView.setVisibility(View.VISIBLE);
		Animation animation = AnimationUtils.loadAnimation(this, anim);
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

	/*
	*  디스플레이 사이즈 측정
	* */
	private void getDisplaySize() {
		DisplayMetrics disp = getApplicationContext().getResources().getDisplayMetrics();
		displayWidth = disp.widthPixels;
		displayHeight = disp.heightPixels;
	}


	/**
	 * 뷰의 위치가 화면 안에 있게 최대값을 설정한다
	 */
	private void setMaxPosition() {
		DisplayMetrics matrix = new DisplayMetrics();
		windowManager.getDefaultDisplay().getMetrics(matrix);
		MAX_X = matrix.widthPixels - faceIcon.getWidth();
		MAX_Y = matrix.heightPixels - faceIcon.getHeight();
	}

	/*
	*  위치 최적화
	* */
	private void optimizePosition() {
		if (faceIconParams.x > MAX_X)
			faceIconParams.x = MAX_X;
		if (faceIconParams.y > MAX_Y)
			faceIconParams.y = MAX_Y;
		if (faceIconParams.x < 0)
			faceIconParams.x = 0;
		if (faceIconParams.y < 0)
			faceIconParams.y = 0;
	}
}
