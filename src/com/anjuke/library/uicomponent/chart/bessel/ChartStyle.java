
package com.anjuke.library.uicomponent.chart.bessel;

import android.graphics.Color;

/**
 * ����ͼ�������ʽ
 * 
 * @author tomkeyzhang��qitongzhang@anjuke.com��
 * @date :2014��4��17��
 */
public class ChartStyle {
    /** ��������ɫ */
    private int gridColor;
    /** ������ָ��߿�� */
    private int axisLineWidth;
    
    /** �������ı���С */
    private float horizontalLabelTextSize;
    /** �������ı���ɫ */
    private int horizontalLabelTextColor;
    /** ����������ı���С */
    private float horizontalTitleTextSize;
    /** ����������ı���ɫ */
    private int horizontalTitleTextColor;
    /** ����������ı����� */
    private int horizontalTitlePaddingLeft;
    /** ����������ı��Ҽ�� */
    private int horizontalTitlePaddingRight;
    
    /** �������ı���С */
    private float verticalLabelTextSize;
    /** �������ı����¼�� */
    private int verticalLabelTextPadding;
    /** �������ı����Ҽ������ı��ı��� */
    private float verticalLabelTextPaddingRate;
    /** �������ı���ɫ */
    private int verticalLabelTextColor;

    public ChartStyle() {
        gridColor=Color.LTGRAY;
        horizontalTitleTextSize=34;
        horizontalTitleTextColor=Color.GRAY;
    	horizontalLabelTextSize=30;
    	horizontalLabelTextColor=Color.GRAY;
        verticalLabelTextSize = 34;
        verticalLabelTextPadding = 60;
        verticalLabelTextColor = Color.GRAY;
        verticalLabelTextPaddingRate=0.2f;
        axisLineWidth=2;
        horizontalTitlePaddingLeft = 20;
        horizontalTitlePaddingRight = 10;
    }

    public float getVerticalLabelTextSize() {
        return verticalLabelTextSize;
    }

    public void setVerticalLabelTextSize(float verticalLabelTextSize) {
        this.verticalLabelTextSize = verticalLabelTextSize;
    }

    public int getVerticalLabelTextPadding() {
        return verticalLabelTextPadding;
    }

    public int getVerticalLabelTextColor() {
        return verticalLabelTextColor;
    }

    public void setVerticalLabelTextPadding(int verticalLabelTextPadding) {
        this.verticalLabelTextPadding = verticalLabelTextPadding;
    }

    public void setVerticalLabelTextColor(int verticalLabelTextColor) {
        this.verticalLabelTextColor = verticalLabelTextColor;
    }

	public float getHorizontalLabelTextSize() {
		return horizontalLabelTextSize;
	}

	public void setHorizontalLabelTextSize(float horizontalLabelTextSize) {
		this.horizontalLabelTextSize = horizontalLabelTextSize;
	}

	public int getHorizontalLabelTextColor() {
		return horizontalLabelTextColor;
	}

	public void setHorizontalLabelTextColor(int horizontalLabelTextColor) {
		this.horizontalLabelTextColor = horizontalLabelTextColor;
	}

    public int getGridColor() {
        return gridColor;
    }

    public void setGridColor(int gridColor) {
        this.gridColor = gridColor;
    }

	public float getHorizontalTitleTextSize() {
		return horizontalTitleTextSize;
	}

	public void setHorizontalTitleTextSize(float horizontalTitleTextSize) {
		this.horizontalTitleTextSize = horizontalTitleTextSize;
	}

	public int getHorizontalTitleTextColor() {
		return horizontalTitleTextColor;
	}

	public void setHorizontalTitleTextColor(int horizontalTitleTextColor) {
		this.horizontalTitleTextColor = horizontalTitleTextColor;
	}

    public float getVerticalLabelTextPaddingRate() {
        return verticalLabelTextPaddingRate;
    }

    public void setVerticalLabelTextPaddingRate(float verticalLabelTextPaddingRate) {
        this.verticalLabelTextPaddingRate = verticalLabelTextPaddingRate;
    }
    public int getAxisLineWidth() {
        return axisLineWidth;
    }
    public void setAxisLineWidth(int axisLineWidth) {
        this.axisLineWidth = axisLineWidth;
    }

    public int getHorizontalTitlePaddingLeft() {
        return horizontalTitlePaddingLeft;
    }

    public int getHorizontalTitlePaddingRight() {
        return horizontalTitlePaddingRight;
    }

    public void setHorizontalTitlePaddingLeft(int horizontalTitlePaddingLeft) {
        this.horizontalTitlePaddingLeft = horizontalTitlePaddingLeft;
    }

    public void setHorizontalTitlePaddingRight(int horizontalTitlePaddingRight) {
        this.horizontalTitlePaddingRight = horizontalTitlePaddingRight;
    }

}
