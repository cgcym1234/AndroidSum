package com.example.administrator.myapplication;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by Administrator on 2014/10/6.
 * 组合控件
 组合控件的意思就是，我们并不需要自己去绘制视图上显示的内容，而只是用系统原生的控件就好了，
 但我们可以将几个系统原生的控件组合到一起，这样创建出的控件就被称为组合控件。
 */
public class TitleView extends FrameLayout{
    private Button leftButton;
    private TextView titleText;

    public TitleView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        LayoutInflater.from(context).inflate(R.layout.titile, this);
        titleText = (TextView) findViewById(R.id.title_text);
        leftButton = (Button) findViewById(R.id.button_left);
        leftButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Activity)getContext()).finish();
            }
        });
    }

    /*为了让TitleView有更强地扩展性，我们还提供了setTitleText()、setLeftButtonText()、
    setLeftButtonListener()等方法，分别用于设置标题栏上的文字、返回按钮上的文字、
    以及返回按钮的点击事件。*/
    public void setTitleText(String text) {
        titleText.setText(text);
    }

    public void setLeftButtonText(String text) {
        leftButton.setText(text);
    }

    public void setLeftButtonListener(OnClickListener l) {
        leftButton.setOnClickListener(l);
    }

}
