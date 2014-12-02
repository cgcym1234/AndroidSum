package mymodule.mymodule.actionbartest;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

import java.util.ArrayList;


/**
 添加导航Tabs
 Tabs的应用可以算是非常广泛了，它可以使得用户非常轻松地在你的应用程序中切换不同的视图。
 而Android官方更加推荐使用ActionBar中提供的Tabs功能，因为它更加的智能，可以自动适配各种屏幕的大小。
 比如说，在平板上屏幕的空间非常充足，Tabs会和Action按钮在同一行显示，如下图所示：

 而如果是在手机上，屏幕的空间不够大的话，Tabs和Action按钮则会分为两行显示，如下图所示：

 下面我们就来看一下如何使用ActionBar提供的Tab功能，大致可以分为以下几步：

 1. 实现ActionBar.TabListener接口，这个接口提供了Tab事件的各种回调，比如当用户点击了一个Tab时，你就可以进行切换Tab的操作。

 2.为每一个你想添加的Tab创建一个ActionBar.Tab的实例，并且调用setTabListener()方法来设置ActionBar.TabListener。
 除此之外，还需要调用setText()方法来给当前Tab设置标题。

 3.最后调用ActionBar的addTab()方法将创建好的Tab添加到ActionBar中。

 看起来并不复杂，总共就只有三步，那么我们现在就来尝试一下吧。首先第一步需要创建一个实现ActionBar.TabListener接口的类，
 */
public class ActionBarTabActivity extends FragmentActivity {

    private ArrayList<ChatFragment> fragments = new ArrayList<ChatFragment>();

    private static final String TAG = "UserMyOrders.";

    private String[] mTitles = {"待付款", "待发货", "已发货", "待评价", "全部"};



    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actionbar_tab_activity);


        setTitle("test");
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        for(int i = 0; i < mTitles.length; i++){
            fragments.add(new ChatFragment());
            ActionBar.Tab tab = actionBar.newTab().setText(mTitles[i]).setTabListener(new MyTabListener<ChatFragment>(this, "one1", ChatFragment.class));
            actionBar.addTab(tab);
        }
    }

    void viewPagerInit(){
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager(), mTitles, fragments));
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

   private class MyPagerAdapter extends FragmentPagerAdapter{

       private String[] titles = null;
       private ArrayList<ChatFragment> fragments;
       public MyPagerAdapter(FragmentManager fm, String[] titles, ArrayList<ChatFragment> fragments) {
           super(fm);
           this.titles = titles;
           this.fragments = fragments;
       }



       @Override
       public Fragment getItem(int position) {
           return fragments.get(position);
       }

       @Override
       public int getCount() {
           return fragments.size();
       }

       @Override
       public CharSequence getPageTitle(int position) {
           return titles[position];
       }
   }

}
