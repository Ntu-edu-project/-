package com.anjuke.library.uicomponent.chart.bessel;
/**
 * ���
 * @author tomkeyzhang��qitongzhang@anjuke.com��
 * @date :2014��4��17��
 */
public class Point {
    /**�Ƿ���ͼ���л��Ƴ��˽��*/
    public boolean willDrawing;
    /** ��canvas�е�X���� */
    public float x;
    /** ��canvas�е�Y���� */
    public float y;
    /** ʵ�ʵ�X��ֵ */
    public int valueX;
    /** ʵ�ʵ�Y��ֵ */
    public int valueY;
    public Point() {
	}
    public Point(int valueX, int valueY,boolean willDrawing) {
        this.valueX = valueX;
        this.valueY = valueY;
        this.willDrawing=willDrawing;
    }

    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }
    
}
