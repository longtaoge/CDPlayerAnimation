package com.example.zhuanpanview;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;

public class zhuanpanActivity extends Activity {
	private zhuanpanView panView;
	private ImageView systemImageView;
	private Animation mMusicPlayerWheelAnim;
	/** Called when the activity is first created. */
	private void findViewAndButton() {
		// 自定义的View
		panView = (zhuanpanView) this
				.findViewById(R.id.zhuanpanV);
		panView.setDuration(900);
		// 开始旋转的按钮
		Button startButton = (Button) this.findViewById(R.id.startButton);
		startButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				panView.startRotate();
				
				if (mMusicPlayerWheelAnim == null) {
                    mMusicPlayerWheelAnim =
                            AnimationUtils.loadAnimation(zhuanpanActivity.this, R.anim.music_player_rotate);
                    mMusicPlayerWheelAnim.setInterpolator(new LinearInterpolator());
                }
				systemImageView.startAnimation(mMusicPlayerWheelAnim);
			}
		});
		// 停止旋转的按钮
		Button stopButton = (Button) this.findViewById(R.id.stopButton);
		stopButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				panView.stopRotate();
				if (mMusicPlayerWheelAnim != null) {
					systemImageView.clearAnimation();
		        }
			}
		});
		systemImageView = (ImageView) this
				.findViewById(R.id.roatateView);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.zhuanpan_main);
		findViewAndButton();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		panView.stopRotateThread();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
	
}
