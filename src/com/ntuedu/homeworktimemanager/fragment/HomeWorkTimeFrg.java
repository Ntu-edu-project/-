package com.ntuedu.homeworktimemanager.fragment;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.anjuke.library.uicomponent.chart.bessel.BesselChart;
import com.anjuke.library.uicomponent.chart.bessel.ChartData.LabelTransform;
import com.anjuke.library.uicomponent.chart.bessel.Point;
import com.anjuke.library.uicomponent.chart.bessel.Series;
import com.ntuedu.homeworktimemanager.Constant;
import com.ntuedu.homeworktimemanager.R;
import com.ntuedu.homeworktimemanager.db.HomeWorkTimeDao;
import com.ntuedu.homeworktimemanager.db.HomeWorkTimeDaoImpl;
import com.ntuedu.homeworktimemanager.model.HomeWorkTime;
import com.ntuedu.homeworktimemanager.util.MyHttpClient;

@SuppressWarnings("deprecation")
@SuppressLint({"ShowToast", "ClickableViewAccessibility"})
public class HomeWorkTimeFrg extends Fragment {

	// �ύ��ؿؼ�
	private EditText etHourChinese;
	private EditText etMinuteChinese;
	private CheckBox cbChinese;
	private Button btChinese;

	private EditText etHourMath;
	private EditText etMinuteMath;
	private CheckBox cbMath;
	private Button btMath;

	private EditText etHourEnglish;
	private EditText etMinuteEnglish;
	private CheckBox cbEnglish;
	private Button btEnglish;

	// ������ؿؼ�
	private TextView tv_chinese_thisweek_time_no;
	private TextView tv_chinese_lastweek_time_no;
	private TextView tv_math_thisweek_time_no;
	private TextView tv_math_lastweek_time_no;
	private TextView tv_english_thisweek_time_no;
	private TextView tv_english_lastweek_time_no;

	// ����ˢ�¿ؼ�
	private SwipeRefreshLayout swipeRefreshLayout;

	// ���ڱ���������Ϣ������
	private SharedPreferences preferences;
	private Editor editor;

	public static final String PER_NAME = "time_no";

	private String sno;

	// ��ͼ���ͼ
	private BesselChart line_chart;
	private PieChartView pie_chart;

	private ViewPager mViewPager;

	private Context ct;

	private HomeWorkTimeDao homeWorkTimeDao;

	// Ĭ�ϻ�ȡȫ����Ϣ
	private String serviceFlag = "getAllTime";

	private String subject;
	private int time;
	private String showName;
	private String message;

	Handler handler = new Handler();

	public HomeWorkTimeFrg(ViewPager mViewPager, String sno) {
		// TODO Auto-generated constructor stub
		// ��ȡmViewPager������
		this.mViewPager = mViewPager;
		this.sno = sno;
	}

	public HomeWorkTimeFrg() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		View view = inflater.inflate(R.layout.homework_time_frg, container,
				false);

		ct = getActivity();

		homeWorkTimeDao = new HomeWorkTimeDaoImpl(ct);
		preferences = ct.getSharedPreferences(PER_NAME, ct.MODE_PRIVATE);
		editor = preferences.edit();

		initView(view);

		// ���ν����ȡ�������ϵ�ʱ��
		if (homeWorkTimeDao.isNull()) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					serviceFlag = "getAllTime";
					getAllTime();
					handler.post(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							refreshPushUI();
							refreshLineChart();
							refreshPieChart();
						}
					});

				}
			}).start();

		} else {
			refreshPushUI();
			refreshLineChart();
			refreshPieChart();
		}

		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				getAllLastWeekTimeNo();
				getAllThisWeekTimeNo();

				handler.post(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						refreshTimeRakingView();
					}
				});

			}
		}).start();

		return view;

	}

	private void initView(View v) {

		etHourChinese = (EditText) v.findViewById(R.id.et_hour_chinese);
		etMinuteChinese = (EditText) v.findViewById(R.id.et_minute_chinese);
		cbChinese = (CheckBox) v.findViewById(R.id.cb_chinese);
		btChinese = (Button) v.findViewById(R.id.bt_chinese);

		etHourMath = (EditText) v.findViewById(R.id.et_hour_math);
		etMinuteMath = (EditText) v.findViewById(R.id.et_minute_math);
		cbMath = (CheckBox) v.findViewById(R.id.cb_math);
		btMath = (Button) v.findViewById(R.id.bt_math);

		etHourEnglish = (EditText) v.findViewById(R.id.et_hour_english);
		etMinuteEnglish = (EditText) v.findViewById(R.id.et_minute_english);
		cbEnglish = (CheckBox) v.findViewById(R.id.cb_english);
		btEnglish = (Button) v.findViewById(R.id.bt_english);

		tv_chinese_thisweek_time_no = (TextView) v
				.findViewById(R.id.chinese_thisweek_time_no);
		tv_chinese_lastweek_time_no = (TextView) v
				.findViewById(R.id.chinese_lastweek_time_no);
		tv_math_thisweek_time_no = (TextView) v
				.findViewById(R.id.math_thisweek_time_no);
		tv_math_lastweek_time_no = (TextView) v
				.findViewById(R.id.math_lastweek_time_no);
		tv_english_thisweek_time_no = (TextView) v
				.findViewById(R.id.english_thisweek_time_no);
		tv_english_lastweek_time_no = (TextView) v
				.findViewById(R.id.english_lastweek_time_no);

		swipeRefreshLayout = (SwipeRefreshLayout) v
				.findViewById(R.id.time_RefreshLayout);

		btChinese.setOnClickListener(listener);
		btMath.setOnClickListener(listener);
		btEnglish.setOnClickListener(listener);

		swipeRefreshLayout.setColorSchemeResources(
				android.R.color.holo_blue_light,
				android.R.color.holo_red_light,
				android.R.color.holo_orange_light,
				android.R.color.holo_green_light);

		// ����ˢ�¼���
		swipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {

				// TODO Auto-generated method stub

				new Thread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub

						getAllLastWeekTimeNo();
						getAllThisWeekTimeNo();

						handler.post(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								refreshTimeRakingView();
								refreshPieChart();
								swipeRefreshLayout.setRefreshing(false);
							}
						});

					}
				}).start();

			}
		});

		pie_chart = (PieChartView) v.findViewById(R.id.time_pie_chart);

		line_chart = (BesselChart) v.findViewById(R.id.chart);
		setLineChartConf();

		// ����mViewPager�Ļ����¼�
		LinearLayout layout = (LinearLayout) v.findViewById(R.id.front_layout);
		layout.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				switch (event.getAction()) {
					case MotionEvent.ACTION_CANCEL :
						mViewPager.requestDisallowInterceptTouchEvent(false);
						break;
					default :
						mViewPager.requestDisallowInterceptTouchEvent(true);
				}

				return false;
			}
		});

	}

	// ��������ͼ
	private void setLineChartConf() {

		line_chart.setSmoothness(0);
		line_chart.getData().setLabelTransform(new LabelTransform() {

			@Override
			public String verticalTransform(int valueY) {
				return String.format("%.1fʱ", (valueY * 1.0f / 60));
			}

			@Override
			public String horizontalTransform(int valueX) {
				return String.format("%s��", valueX);
			}

			@Override
			public boolean labelDrawing(int valueX) {
				return true;
			}

		});
		line_chart.getData().setYLabels(0, 240, 60);
	}

	// ��ȡ����ͼ������
	private Series getSeries(String subject, int color) {
		List<Point> points = new ArrayList<Point>();

		ArrayList<HomeWorkTime> arrayList = homeWorkTimeDao
				.getTimeByMonth(subject);

		if (arrayList == null) {
			points.add(new Point(1, 0, true));
		} else {
			for (int i = 0; i < arrayList.size(); i++) {

				points.add(new Point(i + 1, arrayList.get(i).getTime(), true));

			}
		}

		return new Series(subject, color, points);
	}

	public int getHomeWorkTime(EditText h, EditText m) {

		int hour = Integer.parseInt(h.getText().toString().isEmpty() ? "0" : h
				.getText().toString());
		int minute = Integer.parseInt(m.getText().toString().isEmpty()
				? "0"
				: m.getText().toString());

		return hour * 60 + minute;

	}

	String formatTime(int time) {
		return (time / 60) + "ʱ" + (time % 60) + "��";
	}

	// dialogȷ����ʾ
	@SuppressLint("InflateParams")
	public void showConfirmDialog(String subject, int time, String showName) {

		AlertDialog.Builder alert = new AlertDialog.Builder(ct);

		LayoutInflater layoutInflater = LayoutInflater.from(ct);
		View myDialog = layoutInflater.inflate(R.layout.mydialog, null);

		TextView tv = (TextView) myDialog.findViewById(R.id.mydialog_tv);
		final EditText et = (EditText) myDialog.findViewById(R.id.mydialog_et);

		tv.setText("�γ�:"
				+ subject
				+ "\nʱ��:"
				+ formatTime(time)
				+ "\n����:"
				+ (showName.equals("1")
						? getString(R.string.show_name)
						: getString(R.string.no_name)));

		alert.setView(myDialog);
		alert.setPositiveButton(getString(android.R.string.ok),
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						message = et.getText().toString();

						new TimeServices().execute();
					}
				});
		alert.setNegativeButton(getString(android.R.string.cancel), null);
		alert.show();

	}

	View.OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

			serviceFlag = "pushTime";
			if (v == btChinese) {
				subject = "����";
				time = getHomeWorkTime(etHourChinese, etMinuteChinese);
				showName = cbChinese.isChecked() ? "0" : "1";
			}
			if (v == btMath) {
				subject = "��ѧ";
				time = getHomeWorkTime(etHourMath, etMinuteMath);
				showName = cbMath.isChecked() ? "0" : "1";
			}
			if (v == btEnglish) {
				subject = "Ӣ��";
				time = getHomeWorkTime(etHourEnglish, etMinuteEnglish);
				showName = cbEnglish.isChecked() ? "0" : "1";
			}
			showConfirmDialog(subject, time, showName);

		}
	};

	// �ύʱ�亯��
	@SuppressWarnings("deprecation")
	public int pushTime() {
		String result = "0";
		try {
			HttpPost httpRequest = new HttpPost(Constant.SERVER_URL);
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("flag", serviceFlag));
			params.add(new BasicNameValuePair("sNo", sno));
			params.add(new BasicNameValuePair("message", message));
			params.add(new BasicNameValuePair("subject", subject));
			params.add(new BasicNameValuePair("time", String.valueOf(time)));
			params.add(new BasicNameValuePair("showname", showName));

			httpRequest.setEntity(new UrlEncodedFormEntity(params, "utf-8"));
			HttpResponse httpResponse = new MyHttpClient().getHttpClient(2000,
					4000).execute(httpRequest);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				result = EntityUtils.toString(httpResponse.getEntity());

			}

			return result.equals("0") ? 0 : 1;
		} catch (Exception e) {
			return 2;
		}
	}

	// ��ȡ������ȫ��ʱ�亯��
	@SuppressWarnings("deprecation")
	public int getAllTime() {
		String result = "0";
		try {
			HttpPost httpRequest = new HttpPost(Constant.SERVER_URL);
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("flag", serviceFlag));
			params.add(new BasicNameValuePair("sNo", sno));

			httpRequest.setEntity(new UrlEncodedFormEntity(params, "utf-8"));
			HttpResponse httpResponse = new MyHttpClient().getHttpClient(2000,
					2000).execute(httpRequest);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				result = EntityUtils.toString(httpResponse.getEntity());
			}

			if (!result.equals("]")) {
				doTimeJson(result);
			}

			return result.isEmpty() ? 0 : 1;
		} catch (Exception e) {
			return 2;
		}
	}

	// ��ȡ������������
	@SuppressWarnings("deprecation")
	public void getAllThisWeekTimeNo() {
		String result = "0";
		serviceFlag = "getAllThisWeekTimeNo";
		try {
			HttpPost httpRequest = new HttpPost(Constant.SERVER_URL);
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("flag", serviceFlag));
			params.add(new BasicNameValuePair("sNo", sno));

			httpRequest.setEntity(new UrlEncodedFormEntity(params, "utf-8"));
			HttpResponse httpResponse = new MyHttpClient().getHttpClient(2000,
					2000).execute(httpRequest);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				result = EntityUtils.toString(httpResponse.getEntity());
			}

			JSONArray array = new JSONArray(result);
			JSONObject jsonObject = array.getJSONObject(0);

			editor.putString(
					"chinese_thisweek_time_no",
					jsonObject.getString("����").equals("-1") ? "-" : jsonObject
							.getString("����"));
			editor.putString(
					"math_thisweek_time_no",
					jsonObject.getString("��ѧ").equals("-1") ? "-" : jsonObject
							.getString("��ѧ"));
			editor.putString(
					"english_thisweek_time_no",
					jsonObject.getString("Ӣ��").equals("-1") ? "-" : jsonObject
							.getString("Ӣ��"));
			editor.commit();

		} catch (Exception e) {

		}
	}

	// ��ȡ��������
	@SuppressWarnings("deprecation")
	public void getAllLastWeekTimeNo() {
		String result = "0";
		serviceFlag = "getAllLastWeekTimeNo";
		try {
			HttpPost httpRequest = new HttpPost(Constant.SERVER_URL);
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("flag", serviceFlag));
			params.add(new BasicNameValuePair("sNo", sno));

			httpRequest.setEntity(new UrlEncodedFormEntity(params, "utf-8"));
			HttpResponse httpResponse = new MyHttpClient().getHttpClient(2000,
					2000).execute(httpRequest);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				result = EntityUtils.toString(httpResponse.getEntity());
			}
			JSONArray array = new JSONArray(result);
			JSONObject jsonObject = array.getJSONObject(0);

			editor.putString("chinese_lastweek_time_no",
					jsonObject.getString("����"));
			editor.putString("math_lastweek_time_no",
					jsonObject.getString("��ѧ"));
			editor.putString("english_lastweek_time_no",
					jsonObject.getString("Ӣ��"));
			editor.commit();

		} catch (Exception e) {

		}
	}

	// json��������ʱ��
	private void doTimeJson(String result) {
		// TODO Auto-generated method stub
		try {
			JSONArray array = new JSONArray(result);

			for (int i = 0; i < array.length(); i++) {

				JSONObject jsonObject = array.getJSONObject(i);
				homeWorkTimeDao.addTime(new HomeWorkTime(Date
						.valueOf(jsonObject.getString("Date")), Integer
						.parseInt(jsonObject.getString("Htime")), jsonObject
						.getString("Subject")));
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

		}

	}

	/** AsyncTask�����ύʱ�� */
	private class TimeServices extends AsyncTask<Object, Integer, Integer> {
		@Override
		protected void onPreExecute() {

		}

		@Override
		protected Integer doInBackground(Object... arg0) {
			// TODO Auto-generated method stub

			return pushTime();

		}

		@Override
		protected void onPostExecute(Integer result) {

			switch (result) {
				case 0 :
					Toast.makeText(ct, getString(R.string.have_submit), 1000)
							.show();

					break;
				case 1 :
					HomeWorkTime homeWorkTime = new HomeWorkTime(
							Date.valueOf(getFormatDate()), time, subject);
					homeWorkTimeDao.addTime(homeWorkTime);
					refreshPushUI();
					refreshLineChart();
					refreshPieChart();
					new Thread(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub

							getAllLastWeekTimeNo();
							getAllThisWeekTimeNo();

							handler.post(new Runnable() {

								@Override
								public void run() {
									// TODO Auto-generated method stub
									refreshTimeRakingView();
								}
							});

						}
					}).start();
					Toast.makeText(ct, getString(R.string.submit_success), 1000)
							.show();
					break;
				default :
					Toast.makeText(ct, getString(R.string.net_error), 1000)
							.show();
					break;
			}

		}

		@Override
		protected void onProgressUpdate(Integer... values) {

		}

	}

	/** ����������ͼ */
	private void refreshTimeRakingView() {

		tv_chinese_thisweek_time_no.setText(preferences.getString(
				"chinese_thisweek_time_no", "-"));
		tv_chinese_lastweek_time_no.setText(preferences.getString(
				"chinese_lastweek_time_no", "-"));
		tv_math_thisweek_time_no.setText(preferences.getString(
				"math_thisweek_time_no", "-"));
		tv_math_lastweek_time_no.setText(preferences.getString(
				"math_lastweek_time_no", "-"));
		tv_english_thisweek_time_no.setText(preferences.getString(
				"english_thisweek_time_no", "-"));
		tv_english_lastweek_time_no.setText(preferences.getString(
				"english_lastweek_time_no", "-"));

	}

	/** ˢ���ύ�ؼ�����������Ѿ��ύ���ÿؼ�������״̬ */
	private void refreshPushUI() {

		boolean isChinesePush = homeWorkTimeDao.todayHavePush(
				Date.valueOf(getFormatDate()), "����");
		boolean isMathPush = homeWorkTimeDao.todayHavePush(
				Date.valueOf(getFormatDate()), "��ѧ");
		boolean isEnglishPush = homeWorkTimeDao.todayHavePush(
				Date.valueOf(getFormatDate()), "Ӣ��");

		int chineseTime = isChinesePush ? homeWorkTimeDao.getTodayTime(
				Date.valueOf(getFormatDate()), "����") : 0;
		int mathTime = isMathPush ? homeWorkTimeDao.getTodayTime(
				Date.valueOf(getFormatDate()), "��ѧ") : 0;
		int englishTime = isEnglishPush ? homeWorkTimeDao.getTodayTime(
				Date.valueOf(getFormatDate()), "Ӣ��") : 0;

		btChinese.setEnabled(!isChinesePush);
		cbChinese.setEnabled(!isChinesePush);
		etHourChinese.setText(chineseTime / 60 + "");
		etMinuteChinese.setText(chineseTime % 60 + "");
		etHourChinese.setEnabled(!isChinesePush);
		etMinuteChinese.setEnabled(!isChinesePush);

		btMath.setEnabled(!isMathPush);
		cbMath.setEnabled(!isMathPush);
		etHourMath.setText(mathTime / 60 + "");
		etMinuteMath.setText(mathTime % 60 + "");
		etHourMath.setEnabled(!isMathPush);
		etMinuteMath.setEnabled(!isMathPush);

		btEnglish.setEnabled(!isEnglishPush);
		cbEnglish.setEnabled(!isEnglishPush);
		etHourEnglish.setText(englishTime / 60 + "");
		etMinuteEnglish.setText(englishTime % 60 + "");
		etHourEnglish.setEnabled(!isEnglishPush);
		etMinuteEnglish.setEnabled(!isEnglishPush);

	}

	/** ˢ�±�ͼ */
	private void refreshPieChart() {
		List<SliceValue> values = homeWorkTimeDao.getPieList();

		PieChartData data = new PieChartData(values);

		data.setHasLabels(true);
		pie_chart.setPieChartData(data);
	}

	/** ˢ������ͼ */
	private void refreshLineChart() {
		List<Series> seriess = new ArrayList<Series>();
		seriess.add(getSeries("����", Color.parseColor("#FF7F24")));
		seriess.add(getSeries("��ѧ", Color.parseColor("#6495ED")));
		seriess.add(getSeries("Ӣ��", Color.parseColor("#5F9EA0")));
		line_chart.getData().setSeriesList(seriess);
		line_chart.refresh(true);

	}

	@SuppressLint("SimpleDateFormat")
	String getFormatDate() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(cal.getTime());
	}
}
