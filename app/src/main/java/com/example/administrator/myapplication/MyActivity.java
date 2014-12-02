package com.example.administrator.myapplication;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.LinearLayout;

public class MyActivity extends Activity {

    private LinearLayout mainLayout;
    /*
    * 相信接触Android久一点的朋友对于LayoutInflater一定不会陌生，都会知道它主要是用于加载布局的。
    * 而刚接触Android的朋友可能对LayoutInflater不怎么熟悉，因为加载布局的任务通常都是在Activity中调用setContentView()方法来完成的。
    * 其实setContentView()方法的内部也是使用LayoutInflater来加载布局的，只不过这部分源码是internal的，不太容易查看到。
    * 那么今天我们就来把LayoutInflater的工作流程仔细地剖析一遍，也许还能解决掉某些困扰你心头多年的疑惑。
先来看一下LayoutInflater的基本用法吧，它的用法非常简单，首先需要获取到LayoutInflater的实例，有两种方法可以获取到，第一种写法如下：
1 LayoutInflater layoutInflater = LayoutInflater.from(context);
2 LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
其实第一种就是第二种的简单写法，只是Android给我们做了一下封装而已。
* */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*说到这里，虽然setContentView()方法大家都会用，但实际上Android界面显示的原理要比我们所看到的东西复杂得多。
        任何一个Activity中显示的界面其实主要都由两部分组成，标题栏和内容布局。
        标题栏就是在很多界面顶部显示的那部分内容，比如刚刚我们的那个例子当中就有标题栏，
        可以在代码中控制让它是否显示。而内容布局就是一个FrameLayout，这个布局的id叫作content，
        我们调用setContentView()方法时所传入的布局其实就是放到这个FrameLayout中的，
        这也是为什么这个方法名叫作setContentView()，而不是叫setView()。*/
        setContentView(R.layout.activity_my);

        /*ActionBar的添加非常简单，只需要在AndroidManifest.xml中
        指定Application或Activity的theme是Theme.Holo或其子类就可以了*/
        /*而如果想要移除ActionBar的话通常有两种方式，一是将theme指定成Theme.Holo.NoActionBar，
        表示使用一个不包含ActionBar的主题，二是在Activity中调用以下方法：*/
        ActionBar action = getActionBar();
        //action.hide();


        mainLayout = (LinearLayout)findViewById(R.id.main_layout);
        LayoutInflater layoutInflater = LayoutInflater.from(this);

        /*得到了LayoutInflater的实例之后就可以调用它的inflate()方法来加载布局了
        inflate()方法一般接收两个参数，第一个参数就是要加载的布局id，
        第二个参数是指给该布局的外部再嵌套一层父布局，如果不需要就直接传null。
        */
        View buttonLayout = layoutInflater.inflate(R.layout.button, null);
       /* <Button xmlns:android="http://schemas.android.com/apk/res/android"
        android:layout_width="300dp"
        android:layout_height="80dp"
        android:text="Button" >

        </Button>
       这里不管你将Button的layout_width和layout_height的值修改成多少，都不会有任何效果的，
       因为这两个值现在已经完全失去了作用。平时我们经常使用layout_width和layout_height来设置View的大小，
       并且一直都能正常工作，就好像这两个属性确实是用于设置View的大小的。
       而实际上则不然，它们其实是用于设置View在布局中的大小的，也就是说，首先View必须存在于一个布局中，
       之后如果将layout_width设置成match_parent表示让View的宽度填充满布局，
       如果设置成wrap_content表示让View的宽度刚好可以包含其内容，如果设置成具体的数值则View的宽度会变成相应的数值。
       这也是为什么这两个属性叫作layout_width和layout_height，而不是width和height。

       很明显Button这个控件目前不存在于任何布局当中，所以layout_width和layout_height这两个属性理所当然没有任何作用。
       那么怎样修改才能让按钮的大小改变呢？解决方法其实有很多种，最简单的方式就是在Button的外面再嵌套一层布局

       <RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent" >

    <Button
        android:layout_width="300dp"
        android:layout_height="80dp"
        android:text="Button" >
    </Button>

</RelativeLayout>
        */

       /* 看到这里，也许有些朋友心中会有一个巨大的疑惑。不对呀！平时在Activity中指定布局文件的时候，
        最外层的那个布局是可以指定大小的呀，layout_width和layout_height都是有作用的。
        确实，这主要是因为，在setContentView()方法中，Android会自动在布局文件的最外层再嵌套一个FrameLayout，
        所以layout_width和layout_height属性才会有效果。
        验证:*/
        ViewParent parent = mainLayout.getParent();
        Log.d("TAG", "the parent of mainLayout is " + parent);

        mainLayout.addView(buttonLayout);

        /*这里先是获取到了LayoutInflater的实例，然后调用它的inflate()方法来加载button_layout这个布局，
        最后调用LinearLayout的addView()方法将它添加到LinearLayout中。*/
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
