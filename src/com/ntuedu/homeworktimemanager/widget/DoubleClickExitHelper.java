package com.ntuedu.homeworktimemanager.widget;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.view.KeyEvent;
import android.widget.Toast;

/**
 * ˫���˳� �������� 2014-05-12
 * 
 * @author ���� (http://my.oschina.net/LittleDY)
 * 
 */
public class DoubleClickExitHelper {

	private final Activity mActivity;

	private boolean isOnKeyBacking = false;
	private Handler mHandler;
	private Toast mBackToast;

	public DoubleClickExitHelper(Activity activity) {
		mActivity = activity;
		mHandler = new Handler(Looper.getMainLooper());
		mBackToast = new Toast(activity);
	}

	/**
	 * Activity onKeyDown�¼�
	 * */
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode != KeyEvent.KEYCODE_BACK) {
			return false;
		}
		if (isOnKeyBacking) {
			mHandler.removeCallbacks(onBackTimeRunnable);
			if (mBackToast != null) {
				mBackToast.cancel();
			}
			mActivity.finish();
			return true;
		} else {
			isOnKeyBacking = true;
			if (mBackToast != null) {
				mBackToast.makeText(mActivity, "�ٰ�һ���˳�", 2000).show();
			}
			mHandler.postDelayed(onBackTimeRunnable, 2000);
			return true;
		}
	}

	private Runnable onBackTimeRunnable = new Runnable() {

		@Override
		public void run() {
			isOnKeyBacking = false;
			if (mBackToast != null) {
				mBackToast.cancel();
			}
		}
	};
}
