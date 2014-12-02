package mymodule.mymodule.actionbartest;

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
import android.util.TypedValue;
import android.view.View;

import java.util.ArrayList;

import astuetz.PagerSlidingTabStrip;


/**
* Created by Administrator on 2014/10/8.
*/
public class UserMyOrdersActivity extends FragmentActivity {

    private ArrayList<ChatFragment> chatFragment;

    private static final String TAG = "UserMyOrders.";

    private DisplayMetrics dm;


    PagerSlidingTabStrip tabs;

    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_my_orders);

        chatFragment = new ArrayList<ChatFragment>();
        for( int i=0;i <5;i++ ) //再增加3个元素
            chatFragment.add(new ChatFragment());
        tabs = (PagerSlidingTabStrip)findViewById(R.id.my_orders_tab);
        viewPager = (ViewPager)findViewById(R.id.my_orders_pager);
        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        tabs.setViewPager(viewPager);
        dm = getResources().getDisplayMetrics();
        setTabsValue();
    }

    public class pagerAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return false;
        }
    }

    public class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        private final String[] titles = {"待付款", "待发货", "已发货", "待评价", "全部"};

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public Fragment getItem(int i) {
            return chatFragment.get(i);
        }
    }

    private void setTabsValue() {
        tabs.setShouldExpand(true);
        tabs.setDividerColor(Color.TRANSPARENT);
        tabs.setUnderlineHeight((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 1, dm));
        tabs.setIndicatorHeight((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 4, dm));
        tabs.setTextSize((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, 14, dm));
        tabs.setIndicatorColor(Color.parseColor("#157efb"));
        tabs.setSelectedTextColor(Color.parseColor("#157efb"));
        tabs.setTabBackground(0);
    }
}
