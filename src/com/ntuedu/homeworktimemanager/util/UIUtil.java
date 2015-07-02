package com.ntuedu.homeworktimemanager.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import android.app.Activity;
import android.content.Context;
import android.os.SystemProperties;
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

	public static void setStatusBarTextColor(Activity context, int type) {
		if (!isMiUIV6()) {

			return;
		}

		Window window = context.getWindow();
		Class clazz = window.getClass();
		try {
			int tranceFlag = 0;
			int darkModeFlag = 0;
			Class layoutParams = Class
					.forName("android.view.MiuiWindowManager$LayoutParams");
			Field field = layoutParams
					.getField("EXTRA_FLAG_STATUS_BAR_TRANSPARENT");
			tranceFlag = field.getInt(layoutParams);
			field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
			darkModeFlag = field.getInt(layoutParams);
			Method extraFlagField = clazz.getMethod("setExtraFlags", int.class,
					int.class);
			if (type == 0) {
				extraFlagField.invoke(window, tranceFlag, tranceFlag);// ֻ��Ҫ״̬��͸��
			} else if (type == 1) {
				extraFlagField.invoke(window, tranceFlag | darkModeFlag,
						tranceFlag | darkModeFlag);// ״̬��͸���Һ�ɫ����
			} else {
				extraFlagField.invoke(window, 0, darkModeFlag);// �����ɫ����
			}
		} catch (Exception e) {

		}
	}

	private static final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
	private static boolean isMiUIV6() {
		String name = SystemProperties.get(KEY_MIUI_VERSION_NAME, "");
		if ("V6".equals(name)) {
			return true;
		} else {
			return false;
		}
	}
}
