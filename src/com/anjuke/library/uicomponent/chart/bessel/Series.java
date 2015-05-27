
package com.anjuke.library.uicomponent.chart.bessel;

import java.util.ArrayList;
import java.util.List;

public class Series {
    /** �������ߵı��� */
    private Title title;
    /** �������ߵ���ɫ */
    private int color;
    /** ���е㼯�� */
    private List<Point> points;
    /** ���������ߵ� */
    private List<Point> besselPoints;

    /**
     * @param color ���ߵ���ɫ
     * @param points �㼯��
     */
    public Series(String title,int color, List<Point> points) {
        this.title=new Title(title, color);
    	this.color = color;
        this.points = points;
        this.besselPoints = new ArrayList<Point>();
    }
    public Title getTitle() {
		return title;
	}
    public int getColor() {
        return color;
    }

    public List<Point> getPoints() {
        return points;
    }

    public List<Point> getBesselPoints() {
        return besselPoints;
    }

}
