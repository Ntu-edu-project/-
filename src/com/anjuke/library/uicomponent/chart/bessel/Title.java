package com.anjuke.library.uicomponent.chart.bessel;

import android.graphics.Paint;
import android.graphics.Rect;
/**
 * �ڲ�ʹ��
 * @author tomkeyzhang��qitongzhang@anjuke.com��
 * @date :2014��4��23��
 */
class Title {
    /**�ı���Ӧ������X*/
    public float textX;
    /**�ı���Ӧ������Y*/
    public float textY;
    /**�ı�*/
    public String text;
    /**Բ���Ӧ������X*/
    public float circleX;
    /**Բ���Ӧ������Y*/
    public float circleY;
    /**��ɫ*/
    public int color;
    /**Բ��İ뾶*/
    public int radius;
    /**ͼ�α�ע���ı��ļ��*/
    public int circleTextPadding;
    /**�ı�����*/
    public Rect textRect=new Rect();
    public Title(String text, int color) {
        this.text = text;
        this.color = color;
    }
    public void updateTextRect(Paint paint,float maxWidth){
        int textWidth=textCircleWidth(paint);
        if(textWidth<=maxWidth)
            return;
        while(textWidth>maxWidth){
//            Log.d("zqt", "text="+text);
            text=text.substring(0, text.length()-1);
            textWidth=textCircleWidth(paint);
        }
        text=text.substring(0, text.length()-1)+"...";
        textCircleWidth(paint);
    }
    private int textCircleWidth(Paint paint){
        paint.getTextBounds(text, 0, text.length(), textRect);
        return textRect.width() + (radius + circleTextPadding) * 2;
    }
}
