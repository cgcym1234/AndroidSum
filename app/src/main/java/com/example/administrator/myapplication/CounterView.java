package com.example.administrator.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import java.util.jar.Attributes;

/**
 * Created by Administrator on 2014/10/6.
 * 自定义一个计数器View，这个View可以响应用户的点击事件，
 * 并自动记录一共点击了多少次。新建一个CounterView继承自View
 */

/*
    * Android中的任何一个布局、任何一个控件其实都是直接或间接继承自View的，
    * 如TextView、Button、ImageView、ListView等。这些控件虽然是Android系统本身就提供好的，
    * 我们只需要拿过来使用就可以了，但你知道它们是怎样被绘制到屏幕上的吗？
    * 多知道一些总是没有坏处的，那么我们赶快进入到本篇文章的正题内容吧。

要知道，任何一个视图都不可能凭空突然出现在屏幕上，它们都是要经过非常科学的绘制流程后才能显示出来的。
每一个视图的绘制过程都必须经历三个最主要的阶段，即onMeasure()、onLayout()和onDraw()，
    * */
public class CounterView extends View implements View.OnClickListener {
    private Paint mPaint;
    private Rect mBounds;
    private int mCount;

    /*
    * 在CounterView的构造函数中初始化了一些数据，并给这个View的本身注册了点击事件，
    * 这样当CounterView被点击的时候，onClick()方法就会得到调用。
    * */
    public CounterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBounds = new Rect();
        setOnClickListener(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /*将Paint画笔设置为蓝色，然后调用Canvas的drawRect()方法绘制了一个矩形，
        这个矩形也就可以当作是CounterView的背景图吧*/
        mPaint.setColor(Color.BLUE);
        canvas.drawRect(0,0,getWidth(),getHeight(),mPaint);


        /*接着将画笔设置为黄色，准备在背景上面绘制当前的计数，
        注意这里先是调用了getTextBounds()方法来获取到文字的宽度和高度，
        然后调用了drawText()方法去进行绘制就可以了。*/
        mPaint.setColor(Color.YELLOW);
        mPaint.setTextSize(30);
        String text = String.valueOf(mCount);
        mPaint.getTextBounds(text,0,text.length(),mBounds);
        float textWidth = mBounds.width();
        float textHeight = mBounds.height();
        canvas.drawText(text,getWidth()/2-textWidth/2,getHeight()/2+textHeight/2,mPaint);
    }

    /*这样，一个自定义的View就已经完成了，并且目前这个CounterView是具备自动计数功能的。
    那么剩下的问题就是如何让这个View在界面上显示出来了，其实这也非常简单，
    我们只需要像使用普通的控件一样来使用CounterView就可以了。比如在布局文件中加入如下代码：
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent" >

//自定义的View在使用的时候一定要写出完整的包名，不然系统将无法找到这个View。
    <com.example.customview.CounterView
        android:layout_width="100dp"
        android:layout_height="100dp"
        android:layout_centerInParent="true" />

</RelativeLayout>  */


    @Override
    public void onClick(View view) {
        mCount++;
        /*调用invalidate()方法会导致视图进行重绘，因此onDraw()方法在稍后就将会得到调用。*/
        invalidate();
    }
}

