package com.example.zhuanpanview;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

//自定义的转盘View
public class zhuanpanView extends View implements Runnable {
	
	private static final String TAG = "zhuanpanView";
	private final int particle = 5; //旋转的粒度
	
	private Bitmap panpic;	// 界面需要的图片

	private Matrix panRotate = new Matrix();	// 旋转矩阵
	
	private int x = 0; //当前的角度
	
	private boolean ifRotate = false; //是否有动画
	
	private boolean ifRunThread = true;//线程停止循环
	
	private int repeatCount = -1; //循环次数，无限循环为-1
	private int duration = 1800; //ms
	private int fromD = 0;
	private int toD = 360;
	private float pivotX = 0.5f;
	private float pivotY = 0.5f;
	private int waitTime = 100; //ms

	private Thread mThread;

	public zhuanpanView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		Resources r = context.getResources();
		
		// 生成图片
		panpic = BitmapFactory.decodeStream(r.openRawResource(R.drawable.music_player_wheel));
				
		stopRotateThread();//prevent memory overflow
		
		// 用线程来刷新界面
		mThread = new Thread(this);
		mThread.start();
	}

	// 重写View类的onDraw()函数
	@Override
	protected void onDraw(Canvas canvas) {
		panRotate.setRotate(x, panpic.getWidth()*pivotX, panpic.getHeight()*pivotY);
		canvas.drawBitmap(panpic, panRotate, null);
	}

	// 重写的run函数，用来控制转盘转动
	public void run() {
		try {
			Log.d(TAG, "run ...cu thread id = "+Thread.currentThread().getId()+",x = "+x);
			while (ifRunThread) {
				if (ifRotate) {
					x = (x+particle)%Math.abs(fromD-toD);
					// 这个函数强制UI线程刷新界面
					postInvalidate();
					Thread.sleep(waitTime);
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	//开始动画
	public void startRotate() {
		waitTime = duration/(Math.abs(fromD-toD)/particle);
		Log.d(TAG, "particle = "+waitTime);
		this.ifRotate = true;
	}

	//停止动画
	public void stopRotate() {
		this.ifRotate = false;
	}
		
	//停止线程
	public void stopRotateThread(){
		if(mThread != null && mThread.isAlive() == true){
			ifRunThread = false;
			mThread.interrupt();
			mThread = null;
		}
	}
	
	public int getRepeatCount() {
		return repeatCount;
	}

	public void setRepeatCount(int repeatCount) {
		this.repeatCount = repeatCount;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public int getFromD() {
		return fromD;
	}

	public void setFromD(int fromD) {
		this.fromD = fromD;
	}

	public int getToD() {
		return toD;
	}

	public void setToD(int toD) {
		this.toD = toD;
	}

	public float getPivotX() {
		return pivotX;
	}

	public void setPivotX(float pivotX) {
		this.pivotX = pivotX;
	}

	public float getPivotY() {
		return pivotY;
	}

	public void setPivotY(float pivotY) {
		this.pivotY = pivotY;
	}
}
