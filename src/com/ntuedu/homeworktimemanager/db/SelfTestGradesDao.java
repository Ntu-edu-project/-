package com.ntuedu.homeworktimemanager.db;

import java.sql.Date;
import java.util.ArrayList;

import com.ntuedu.homeworktimemanager.model.Grades;

public interface SelfTestGradesDao {

	public ArrayList<Grades> getGradesByMonth(String subject);

	public void addGrades(Grades grades);

	/** �жϽ���subject�����Ƿ��Ѿ��ύ */
	public boolean isNull();

	/** �жϽ���subject�Ƿ��Ѿ��ύ */
	public boolean todayHavePush(Date date, String subject);

	/** ��ȡ����subject�ķ���*/
	public int getTodayGrades(Date date, String subject);
}
