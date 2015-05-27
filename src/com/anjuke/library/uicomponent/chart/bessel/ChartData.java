package com.anjuke.library.uicomponent.chart.bessel;

import java.util.ArrayList;
import java.util.List;

/**
 * ����ͼ�������Լ����������Ϣ
 * 
 * @author tomkeyzhang��qitongzhang@anjuke.com��
 * @date :2014��4��17��
 */
public class ChartData {
	private Marker marker;
	private List<Series> seriesList;
	private List<Label> xLabels;
	private List<Label> yLabels;
	private List<Title> titles;
	private int maxValueY;
	private int minValueY;
	private int maxPointsCount;
	private LabelTransform labelTransform;
	/** ��������ʾ�ı������� */
	private int yLabelCount;

	/** ʹ����һ��series�ĺ���������ʾ�������ı� */
	private int xLabelUsageSeries;

	ChartData() {
		xLabels = new ArrayList<Label>();
		yLabels = new ArrayList<Label>();
		titles = new ArrayList<Title>();
		seriesList = new ArrayList<Series>();
		labelTransform = new LabelTransform() {
			@Override
			public String verticalTransform(int valueY) {
				return String.valueOf(valueY);
			}

			@Override
			public String horizontalTransform(int valueX) {
				return String.valueOf(valueX);
			}

			@Override
			public boolean labelDrawing(int valueX) {
				return true;
			}

		};

		xLabelUsageSeries = 0;// Ĭ�Ϻ���ʹ�õ�һ����������ʾ�ı�
	}

	/** ������������ */
	public void setSeriesList(List<Series> seriesList) {
		this.seriesList.clear();
		if (seriesList != null && seriesList.size() > 0) {
			this.seriesList.addAll(seriesList);
			if (this.seriesList.size() <= xLabelUsageSeries)
				throw new IllegalArgumentException(
						"xLabelUsageSeries should greater than seriesList.size()");
			resetXLabels();
			// resetYLabels();
			titles.clear();

			for (Series series : seriesList) {
				titles.add(series.getTitle());
				if (series.getPoints().size() > maxPointsCount)
					maxPointsCount = series.getPoints().size();
			}
		}
	}

	/** ��������X�������ı� */
	private void resetXLabels() {
		xLabels.clear();
		for (Point point : seriesList.get(xLabelUsageSeries).getPoints()) {
			if (labelTransform.labelDrawing(point.valueX))
				xLabels.add(new Label(point.valueX, labelTransform
						.horizontalTransform(point.valueX)));
		}
	}

	/** ��������Y�������ı� */
	private void resetYLabels() {
		minValueY = 0;
		maxValueY = Integer.MAX_VALUE;
		for (Series series : seriesList) {
			for (Point point : series.getPoints()) {
				if (point.valueY > maxValueY)
					maxValueY = point.valueY;
				if (point.valueY > 0 && point.valueY < minValueY)
					minValueY = point.valueY;
			}
		}

		int step = 60;
		minValueY = 0;
		maxValueY = 240;

		int value = 0;
		for (int i = 0; i < yLabelCount; i++) {
			value = minValueY + step * i;
			yLabels.add(0,
					new Label(value, labelTransform.verticalTransform(value)));
		}

	}

	/** �������,��Сֵ,�Լ���� */
	public void setYLabels(int minValueY, int maxValueY, int step) {
		this.minValueY = minValueY;
		this.maxValueY = maxValueY;

		yLabelCount = maxValueY / step + 1;// Ĭ��������ʾ4���ı�

		int value = 0;
		for (int i = 0; i < yLabelCount; i++) {
			value = minValueY + step * i;
			yLabels.add(0,
					new Label(value, labelTransform.verticalTransform(value)));
		}
	}

	public void setLabelTransform(LabelTransform labelTransform) {
		this.labelTransform = labelTransform;
	}

	public List<Series> getSeriesList() {
		return seriesList;
	}

	public LabelTransform getLabelTransform() {
		return labelTransform;
	}

	public List<Label> getXLabels() {
		return xLabels;
	}

	public List<Label> getYLabels() {
		return yLabels;
	}

	public List<Title> getTitles() {
		return titles;
	}

	public int getMaxValueY() {
		return maxValueY;
	}

	public int getMinValueY() {
		return minValueY;
	}

	public int getyLabelCount() {
		return yLabelCount;
	}

	public void setyLabelCount(int yLabelCount) {
		this.yLabelCount = yLabelCount;
	}

	public int getxLabelUsageSeries() {
		return xLabelUsageSeries;
	}

	public void setxLabelUsageSeries(int xLabelUsageSeries) {
		this.xLabelUsageSeries = xLabelUsageSeries;
	}

	public void setMarker(Marker marker) {
		titles.add(marker);
		this.marker = marker;
	}

	public Marker getMarker() {
		return marker;
	}

	public int getMaxPointsCount() {
		return maxPointsCount;
	}

	public interface LabelTransform {
		/** ��������ʾ���ı� */
		String verticalTransform(int valueY);

		/** ��������ʾ���ı� */
		String horizontalTransform(int valueX);

		/** �Ƿ���ʾָ��λ�õĺ������ı� */
		boolean labelDrawing(int valueX);

	}

	class Label {
		/** �ı���Ӧ������X */
		public float x;
		/** �ı���Ӧ������Y */
		public float y;
		/** �ı���Ӧ�Ļ�������Y */
		public float drawingY;
		/** �ı���Ӧ��ʵ����ֵ */
		public int value;
		/** �ı� */
		public String text;

		public Label(int value, String text) {
			this.value = value;
			this.text = text;
		}
	}
}
