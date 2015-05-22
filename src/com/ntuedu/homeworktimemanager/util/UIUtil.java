package com.ntuedu.homeworktimemanager.util;

import android.app.Activity;
import android.content.Context;
import android.view.Window;
import android.view.WindowManager;

public class UIUtil {

	// ����ȫ��
	public static void setFullScreen(Context context) {
		int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
		Window window = ((Activity) context).getWindow();
		window.setFlags(flag, flag);
	}

	// �����ޱ�����
	public static void setNoTitle(Context context) {
		((Activity) context).requestWindowFeature(Window.FEATURE_NO_TITLE);
	}

	// ���ⳣ��
	public static void keepScreenOn(Context context) {
		((Activity) context).getWindow().addFlags(
				android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	}

}
