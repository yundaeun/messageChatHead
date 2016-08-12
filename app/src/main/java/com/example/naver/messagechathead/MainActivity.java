package com.example.naver.messagechathead;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.naver.messagechathead.service.ChatBubbleUIService;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if(Build.VERSION.SDK_INT >= 23) {
			// 권한 체크 (이미 권한을 수락한 경우에는 else문으로 빠진다)
			if (!Settings.canDrawOverlays(this)) {
				Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
					Uri.parse("package:" + getPackageName()));
				startActivityForResult(intent, 1000);
			} else {
				Intent intent = new Intent(MainActivity.this, ChatBubbleUIService.class);
				startService(intent);
			}
		}
		else
		{
			Intent intent = new Intent(MainActivity.this, ChatBubbleUIService.class);
			startService(intent);
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		switch (requestCode) {
			case 1000 :
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
					if (Settings.canDrawOverlays(this)) {
						// Do something with overlay permission
					} else {
						// Show dialog which persuades that we need permission
					}
				}
				break;
		}
	}
}
