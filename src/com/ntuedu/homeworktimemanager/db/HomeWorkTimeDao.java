package com.ntuedu.homeworktimemanager.db;

import java.sql.Date;

import com.ntuedu.homeworktimemanager.model.HomeWorkTime;

public interface HomeWorkTimeDao {

	public void addTime(HomeWorkTime homeWorkTime);

	/** �жϽ���subject�Ƿ��Ѿ��ύ */
	public boolean todayHavePush(Date date, String subject);

	/** ��ȡ����subject��ʱ�� */
	public int getTodayTime(Date date, String subject);
}
