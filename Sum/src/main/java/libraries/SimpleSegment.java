package libraries;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * Created by Administrator on 2014/10/8
 * 二、视图重绘
 虽然视图会在Activity加载完成之后自动绘制到屏幕上，但是我们完全有理由在与Activity进行交互的时候要求动态更新视图，
 比如改变视图的状态、以及显示或隐藏某个控件等。那在这个时候，之前绘制出的视图其实就已经过期了，此时我们就应该对视图进行重绘。
 调用视图的setVisibility()、setEnabled()、setSelected()等方法时都会导致视图重绘，
 而如果我们想要手动地强制让视图进行重绘，可以调用invalidate()方法来实现。
 当然了，setVisibility()、setEnabled()、setSelected()等方法的内部其实也是通过调用invalidate()方法来实现的
 *
 *
 * Android使用AttributeSet自定义控件的方法

 在这种方法中,大概的步骤是这样的

 1.我们的自定义控件和其他的控件一样,应该写成一个类,而这个类的属性是是有自己来决定的.

 2.我们要在res/values目录下建立一个attrs.xml的文件,并在此文件中增加对控件的属性的定义.

 3.使用AttributeSet来完成控件类的构造函数,并在构造函数中将自定义控件类中变量与attrs.xml中的属性连接起来.

 4.在自定义控件类中使用这些已经连接的属性变量.

 5.将自定义的控件类定义到布局用的xml文件中去.

 6.在界面中生成此自定义控件类对象,并加以使用.

 */
public class SimpleSegment extends LinearLayout {

    static String attr_titles               = "titles";
    static String attr_titles_split         = ",";

    static String attr_textSize             = "textSize";
    static String attr_textColor            = "textColor";
    static String attr_textColorSelected    = "textColorSelected";
    static String attr_indicatorWidth       = "indicatorWidth";




    public interface SimpleSegmentListener{
        void didChanged(int fromIdx, int toIdx);
    }

    private SimpleSegmentListener simpleSegmentListener;

    private ArrayList<Button> mButtons;
    private String[] mTitles = {"待付款", "待发货", "已发货", "待评价", "全部"};

    private View indicator;
    int indicatorWidth = 60;
    private int textSize = 14;
    private int textColor = Color.BLACK;
    private int selectedTextColor = Color.parseColor("#157efb");
    int curIndex = 0;
    int prevIndex = 0;

    public void setSimpleSegmentListener(SimpleSegmentListener simpleSegmentListener) {
        this.simpleSegmentListener = simpleSegmentListener;
    }

    public SimpleSegment(Context context) {
        super(context);
    }

    public SimpleSegment(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        attrInit(context, attributeSet);

        setOrientation(VERTICAL);
        addButton(mTitles);
        addIndicator();
    }

    public SimpleSegment(Context context, AttributeSet attributeSet, int defStyle) {
        super(context, attributeSet, defStyle);
    }

    private void attrInit(Context context, AttributeSet attr) {
//        android.util.Log.d("attrInit", "AttributeSet:"+ attr.toString());
        int resouceId = -1;
        resouceId = attr.getAttributeResourceValue(null, attr_titles, 0);
        if (resouceId > 0) {
            String titles = context.getResources().getText(resouceId).toString();
            mTitles = titles.split(attr_titles_split);
        }

        resouceId = attr.getAttributeResourceValue(null, attr_textSize, 0);
        if (resouceId > 0) {
            textSize = context.getResources().getDimensionPixelSize(resouceId);
        }

        resouceId = attr.getAttributeResourceValue(null, attr_textColor, 0);
        if (resouceId > 0) {
            textColor = context.getResources().getColor(resouceId);
        }

        resouceId = attr.getAttributeResourceValue(null, attr_textColorSelected, 0);
        if (resouceId > 0) {
            selectedTextColor = context.getResources().getColor(resouceId);
        }

        resouceId = attr.getAttributeResourceValue(null, attr_indicatorWidth, 0);
        if (resouceId > 0) {
            indicatorWidth = context.getResources().getInteger(resouceId);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        setButtonState(prevIndex, curIndex);
    }

    private void addButton(String[] titles) {
        int count = titles.length;
        mButtons = new ArrayList<Button>(count);
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(HORIZONTAL);
        linearLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 0, 11));

        LayoutParams params = new LayoutParams(0, LayoutParams.MATCH_PARENT, 1);
        for (int i = 0; i < count; i++) {
            Button button = new Button(getContext());
            button.setId(i);
            button.setText(titles[i]);
            button.setTextSize(textSize);
            button.setTextColor(textColor);
            button.setGravity(Gravity.CENTER);
            button.setSingleLine();

            //padding：Button里面的文字与Button边界的间隔
            button.setPadding(0,0,0,0);
            button.setBackgroundColor(Color.alpha(0));
            button.setHighlightColor(selectedTextColor);
            button.setLayoutParams(params);
            button.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    prevIndex = curIndex;
                    curIndex = view.getId();
                    setButtonState(prevIndex, curIndex);
                    if (simpleSegmentListener != null) {
                        simpleSegmentListener.didChanged(prevIndex, curIndex);
                    }
                }
            });
            linearLayout.addView(button);
            mButtons.add(button);
        }
        addView(linearLayout);
    }

    private void addIndicator() {
        View view = new View(getContext());
        view.setBackgroundColor(selectedTextColor);
        view.setLayoutParams(new LayoutParams(indicatorWidth, 0, 1));
        addView(view);
        indicator = view;

        View line = new View(getContext());
        line.setBackgroundColor(Color.parseColor("#c8c8c8"));
        line.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 1));
        addView(line);
    }

    private void setButtonState(int prev, int cur) {
        Button button = mButtons.get(prev);
        button.setEnabled(true);
        button.setTextColor(textColor);
        button = mButtons.get(cur);
        button.setEnabled(false);
        button.setTextColor(selectedTextColor);
        setIndicatorLocation(button);
    }

    private void setIndicatorLocation(Button button) {
        //float oldx = indicator.getX();
        int newx = button.getRight()-button.getWidth()/2 - indicator.getWidth()/2;
        indicator.layout(newx, indicator.getTop(), newx+indicator.getWidth(), indicator.getBottom());
        //indicator.setX(newx);

        //android.util.Log.d("button", "getX:" + button.getX() + " getY:" + button.getY() + " getLeft:" + button.getLeft() + " getRight:" + button.getRight() + " getTop:" + button.getTop() +" getBottom:" + button.getBottom());
        //android.util.Log.d("indicator", "getX:" + indicator.getX() + " getY:" + indicator.getY() + " getLeft:" + indicator.getLeft() + " getRight:" + indicator.getRight() + " getTop:" + indicator.getTop() +" getBottom:" + indicator.getBottom());
    }
}
