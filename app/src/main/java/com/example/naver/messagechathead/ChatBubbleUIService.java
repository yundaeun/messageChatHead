package com.example.naver.messagechathead;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

/**
 * Created by DAEUN on 16. 7. 12..
 */
public class ChatBubbleUIService extends Service {
	private WindowManager.LayoutParams deleteParams;
	private WindowManager windowManager;
	LayoutInflater layoutInflater;
	private int displayWidth, displayHeight;
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


		ChatBubbleCreator chatBubbleCreator = new ChatBubbleCreator(getApplicationContext(), windowManager);
		ChatBubbleDeleter chatBubbleDeleter = new ChatBubbleDeleter(getApplicationContext(), windowManager);
		chatBubbleDeleter.init();


		// 사이즈 지정
		int faceIconSize = displayWidth / 5; //아이콘 갯수
		int dialogSize = displayHeight - faceIconSize - 65; // 화면 넘게 그려짐

		chatView = layoutInflater.inflate(R.layout.chat_view_layout, null);
		attachLayout(chatView, Gravity.BOTTOM, View.GONE, displayWidth - 50, dialogSize, WindowManager.LayoutParams.TYPE_PHONE);
	}

	@Nullable
	@Override
	public IBinder onBind(Intent intent) {return null;}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

//	@Override
//	public boolean onTouch(View v, MotionEvent event) {
//
//		// 삭제 버튼 활성화
//		boolean serviceFlag = onSelectedDeleteButton(event.getAction());
//
//		if (!serviceFlag) {
//		//	faceIcon.setVisibility(View.GONE);
//			deleteView.setVisibility(View.GONE);
//			return false;
//
//		} else {
//			switch (event.getAction()) {
//				case MotionEvent.ACTION_DOWN:
//
//					START_X = event.getRawX(); //터치 시작 점
//					START_Y = event.getRawY();
//					PREV_X = faceIconParams.x; //뷰의 시작 점
//					PREV_Y = faceIconParams.y;
//					Log.d("BubbleService", "action down start : " + START_X + "," + START_Y);
//					Log.d("BubbleService", "action down prev : " + PREV_X + "," + PREV_Y);
//
//					// 삭제 아이콘 등장
//					setAnimation(R.anim.slide_up_in, deleteIcon, deleteView, false);
//					touchorclick = true;
//
//				case MotionEvent.ACTION_MOVE:
//
//					int x = (int)(event.getRawX() - START_X); //이동한 거리
//					int y = (int)(event.getRawY() - START_Y); //이동한 거리
//
//					faceIconParams.x = PREV_X + x;
//					faceIconParams.y = PREV_Y + y;
//
//					optimizePosition();
//
//					windowManager.updateViewLayout(v, faceIconParams);
//
//					// 클릭 리스너 받기
//					if (y < 10 && x < 10) touchorclick = true;
//					else touchorclick = false;
//
//				case MotionEvent.ACTION_UP:
///*
//				// 테두리 영역으로 부드럽게 이동
//				// 영역 1 or 영역 4
//				if (faceIconParams.x < MAX_X/2) {
//					faceIconParams.x = 0;
//				} else {
//					faceIconParams.x = MAX_X;
//				}
//				windowManager.updateViewLayout(v, faceIconParams);
//*/
//					setAnimation(R.anim.slide_down_out, deleteIcon, deleteView, true);
//
//					// 클릭 리스너 받기
//					if (touchorclick) return false;
//					else return true;
//
//			}
//		}
//		return true;
//	}


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
	*  디스플레이 사이즈 측정
	* */
	private void getDisplaySize() {
		DisplayMetrics disp = getApplicationContext().getResources().getDisplayMetrics();
		displayWidth = disp.widthPixels;
		displayHeight = disp.heightPixels;
	}
}
