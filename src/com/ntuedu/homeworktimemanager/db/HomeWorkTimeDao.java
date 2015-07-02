package com.ntuedu.homeworktimemanager.db;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.SliceValue;

import com.ntuedu.homeworktimemanager.model.HomeWorkTime;

public interface HomeWorkTimeDao {

	public ArrayList<HomeWorkTime> getTimeByMonth(String subject);

	public void addTime(HomeWorkTime homeWorkTime);

	/** �жϽ���subject�Ƿ��Ѿ��ύ */
	public boolean isNull();

	/** �жϽ���subject�Ƿ��Ѿ��ύ */
	public boolean todayHavePush(Date date, String subject);

	/** ��ȡ����subject��ʱ�� */
	public int getTodayTime(Date date, String subject);

	public List<SliceValue> getPieList();

}
