
package com.anjuke.library.uicomponent.chart.bessel;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Paint;
import android.graphics.Rect;

import com.anjuke.library.uicomponent.chart.bessel.ChartData.Label;

class BesselCalculator {
    /** �������ı����� */
    public Rect verticalTextRect;
    /** �������ı����� */
    public Rect horizontalTextRect;
    /** ����������ı����� */
    public Rect horizontalTitleRect;
    /** ͼ�εĸ߶� */
    public int height;
    /** ͼ�εĿ�� */
    public int width;
    /** ����Ŀ�� */
    public int yAxisWidth;
    /** ����ĸ߶� */
    public int yAxisHeight;
    /** ����ĸ߶� */
    public int xAxisHeight;
    /** ����ı���ĸ߶� */
    public int xTitleHeight;
    /** ����ĳ��� */
    public int xAxisWidth;
    /** ��ɫ���߶��� */
    public Point[] gridPoints;

    /** ����X���ƽ�ƣ�����ʵ������ͼ�Ĺ���Ч�� */
    private float translateX;

    /** ���ڲ����ı����򳤿�Ļ��� */
    private Paint paint;

    private ChartStyle style;
    private ChartData data;
    /** �⻬���� */
    private float smoothness;

    public BesselCalculator(ChartData data, ChartStyle style) {
        this.data = data;
        this.style = style;
        this.translateX = 0f;
        this.smoothness = 0.33f;
        this.paint = new Paint();
        this.verticalTextRect = new Rect();
        this.horizontalTextRect = new Rect();
        this.horizontalTitleRect = new Rect();
    }

    /**
     * ����ͼ�λ��ƵĲ�����Ϣ
     * 
     * @param width ����ͼ����Ŀ��
     */
    public void compute(int width) {
        this.width = width;
        this.translateX = 0;
        computeVertcalAxisInfo();// �����������
        computeHorizontalAxisInfo();// ����������
        computeTitlesInfo();// ����������
        computeSeriesCoordinate();// �����������
        computeBesselPoints();// ���㱴�������
        computeGridPoints();// �������񶥵�
    }

    /**
     * ƽ�ƻ���
     * 
     * @param distanceX
     * @param velocityX
     */
    public void move(float distanceX) {
        translateX = translateX - distanceX;
    }

    /**
     * ƽ�ƻ���
     * 
     * @param distanceX
     * @param velocityX
     */
    public void moveTo(int x) {
        translateX = x;
    }

    public float getTranslateX() {
        return translateX;
    }

    /***
     * ȷ���������ƶ����ᳬ����Χ
     * 
     * @return true,������Χ��false��δ������Χ
     */
    public boolean ensureTranslation() {
        if (translateX >= 0) {
            translateX = 0;
            return true;
        } else if (translateX < 0) {
            if (yAxisWidth != 0 && translateX < -xAxisWidth / 2) {
                translateX = -xAxisWidth / 2;
                return true;
            }
        }
        return false;
    }

    /** ����������� */
    private void computeVertcalAxisInfo() {
        paint.setTextSize(style.getVerticalLabelTextSize());
        List<Label> yLabels = data.getYLabels();
        int yLabelCount = data.getYLabels().size();
        String maxText = getMaxText(yLabels);
        paint.getTextBounds(maxText, 0, maxText.length(), verticalTextRect);
        float x = verticalTextRect.width() * (0.5f + style.getVerticalLabelTextPaddingRate());
        for (int i = 0; i < yLabelCount; i++) {
            Label label = yLabels.get(i);
            label.x = x;
            label.y = verticalTextRect.height() * (i + 1)
                    + style.getVerticalLabelTextPadding() * (i + 0.5f);
            label.drawingY = label.y + verticalTextRect.height() / 2 - 3;
        }
        yAxisWidth = (int) (verticalTextRect.width() * (1 + style.getVerticalLabelTextPaddingRate() * 2));
        yAxisHeight = verticalTextRect.height() * yLabelCount
                + style.getVerticalLabelTextPadding() * yLabelCount;
    }

    /** ���������� */
    private void computeHorizontalAxisInfo() {
        xAxisWidth = 2 * (width - yAxisWidth);
        paint.setTextSize(style.getHorizontalLabelTextSize());
        List<Label> xLabels = data.getXLabels();
        String measureText = "��";
        paint.getTextBounds(measureText, 0, measureText.length(),
                horizontalTextRect);
        xAxisHeight = horizontalTextRect.height() * 2;
        height = (int) (yAxisHeight + xAxisHeight);// ͼ�εĸ߶ȼ������
        float labelWidth = xAxisWidth / xLabels.size();
        for (int i = 0; i < xLabels.size(); i++) {
            Label label = xLabels.get(i);
            label.x = labelWidth * (i + 0.5f);
            label.y = height - horizontalTextRect.height() * 0.5f;
        }
    }

    /** ��������������Ϣ */
    private void computeTitlesInfo() {
        paint.setTextSize(style.getHorizontalTitleTextSize());
        String titleText = data.getSeriesList().get(0).getTitle().text;
        paint.getTextBounds(titleText, 0, titleText.length(), horizontalTitleRect);
        xTitleHeight = horizontalTitleRect.height() * 2;
        List<Title> titles = data.getTitles();
        int count = titles.size();
        float stepX = (width - style.getHorizontalTitlePaddingLeft() - style.getHorizontalTitlePaddingRight()) / count;
        for (Title title : titles) {
            if (title instanceof Marker) {
                title.radius = 15;
            } else {
                title.radius = 10;
            }
            title.circleTextPadding = 10;
            title.updateTextRect(paint, stepX);
            title.textX = style.getHorizontalTitlePaddingLeft() + (titles.indexOf(title) + 0.5f) * stepX;
            title.textY = xTitleHeight * 0.75f;
            title.circleX = title.textX - title.textRect.width() / 2 - title.circleTextPadding - title.radius;
            title.circleY = title.textY - horizontalTitleRect.height() * 0.5f + 5;
        }
    }

    /** �������е�������Ϣ */
    private void computeSeriesCoordinate() {
        List<Label> yLabels = data.getYLabels();
        float minCoordinateY = yLabels.get(0).y;
        float maxCoordinateY = yLabels.get(yLabels.size() - 1).y;
        int length = 0;
        for (Series series : data.getSeriesList()) {
            if (series.getPoints().size() > length)
                length = series.getPoints().size();
        }
        for (Series series : data.getSeriesList()) {
            List<Point> points = series.getPoints();
            float pointWidth = xAxisWidth / points.size();
            for (int i = 0; i < points.size(); i++) {
                Point point = points.get(i);
                // �������ݵ������
                point.x = pointWidth * (i + 0.5f);
                float ratio = (point.valueY - data.getMinValueY()) / (float) (data.getMaxValueY() - data.getMinValueY());
                point.y = maxCoordinateY - (maxCoordinateY - minCoordinateY) * ratio;
                Marker marker = data.getMarker();
                if (marker != null && marker.getPoint().valueX == point.valueX) {
                    Point markerPoint = marker.getPoint();
                    markerPoint.x = point.x;
                    ratio = (markerPoint.valueY - data.getMinValueY()) / (float) (data.getMaxValueY() - data.getMinValueY());
                    markerPoint.y = maxCoordinateY - (maxCoordinateY - minCoordinateY) * ratio;
                }
            }
        }
    }

    /**
     * ��ȡlabel������ı�
     * 
     * @param labels
     * @return
     */
    private String getMaxText(List<Label> labels) {
        String maxText = "";
        for (Label label : labels) {
            if (label.text.length() > maxText.length())
                maxText = label.text;
        }
        return maxText;
    }

    /** �������񶥵� */
    private void computeGridPoints() {
        gridPoints = new Point[data.getMaxPointsCount()];
        List<Series> seriesList = data.getSeriesList();
        for (Series series : seriesList) {
            for (Point point : series.getPoints()) {
                int index = series.getPoints().indexOf(point);
                if (gridPoints[index] == null || gridPoints[index].valueY < point.valueY) {
                    gridPoints[index] = point;
                }
            }
        }
    }

    /** ���㱴������� */
    private void computeBesselPoints() {
        for (Series series : data.getSeriesList()) {
            List<Point> besselPoints = series.getBesselPoints();
            List<Point> points = new ArrayList<Point>();
            for (Point point : series.getPoints()) {
                if (point.valueY > 0)
                    points.add(point);
            }
            int count = points.size();
            if (count < 2)
                continue;
            besselPoints.clear();
            for (int i = 0; i < count; i++) {
                if (i == 0 || i == count - 1) {
                    computeUnMonotonePoints(i, points, besselPoints);
                } else {
                    Point p0 = points.get(i - 1);
                    Point p1 = points.get(i);
                    Point p2 = points.get(i + 1);
                    if ((p1.y - p0.y) * (p1.y - p2.y) >= 0) {// ��ֵ��
                        computeUnMonotonePoints(i, points, besselPoints);
                    } else {
                        computeMonotonePoints(i, points, besselPoints);
                    }
                }
            }
        }
    }

    /** ����ǵ�������ı�������� */
    private void computeUnMonotonePoints(int i, List<Point> points, List<Point> besselPoints) {
        if (i == 0) {
            Point p1 = points.get(0);
            Point p2 = points.get(1);
            besselPoints.add(p1);
            besselPoints.add(new Point(p1.x + (p2.x - p1.x) * smoothness, p1.y));
        } else if (i == points.size() - 1) {
            Point p0 = points.get(i - 1);
            Point p1 = points.get(i);
            besselPoints.add(new Point(p1.x - (p1.x - p0.x) * smoothness, p1.y));
            besselPoints.add(p1);
        } else {
            Point p0 = points.get(i - 1);
            Point p1 = points.get(i);
            Point p2 = points.get(i + 1);
            besselPoints.add(new Point(p1.x - (p1.x - p0.x) * smoothness, p1.y));
            besselPoints.add(p1);
            besselPoints.add(new Point(p1.x + (p2.x - p1.x) * smoothness, p1.y));
        }
    }

    /**
     * ���㵥������ı��������
     * 
     * @param i
     * @param points
     * @param besselPoints
     */
    private void computeMonotonePoints(int i, List<Point> points, List<Point> besselPoints) {
        Point p0 = points.get(i - 1);
        Point p1 = points.get(i);
        Point p2 = points.get(i + 1);
        float k = (p2.y - p0.y) / (p2.x - p0.x);
        float b = p1.y - k * p1.x;
        Point p01 = new Point();
        p01.x = p1.x - (p1.x - (p0.y - b) / k) * smoothness;
        p01.y = k * p01.x + b;
        besselPoints.add(p01);
        besselPoints.add(p1);
        Point p11 = new Point();
        p11.x = p1.x + (p2.x - p1.x) * smoothness;
        p11.y = k * p11.x + b;
        besselPoints.add(p11);
    }

    public void setSmoothness(float smoothness) {
        this.smoothness = smoothness;
    }
}
