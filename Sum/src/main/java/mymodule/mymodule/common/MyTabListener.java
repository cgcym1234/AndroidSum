package mymodule.mymodule.common;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;

/**
 * Created by Administrator on 2014/11/2.
 * 一个实现ActionBar.TabListener接口的类
 */
public class MyTabListener<T extends Fragment> implements ActionBar.TabListener{

    private Fragment mFragment;
    private final Activity mActivity;
    private final String mTag;
    private final Class<T> mClass;

    public MyTabListener(Activity activity, String tag, Class<T> clz){
        mActivity = activity;
        mTag = tag;
        mClass = clz;
    }

    /*当Tab被选中的时候会调用onTabSelected()方法，在这里我们先判断mFragment是否为空，
    如果为空的话就创建Fragment的实例并调用FragmentTransaction的add()方法，
    如果不会空的话就调用FragmentTransaction的attach()方法。*/
    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        if (mFragment == null){
            mFragment = Fragment.instantiate(mActivity, mClass.getName());
            ft.add(android.R.id.content, mFragment, mTag);
        } else {
            ft.attach(mFragment);
        }
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
        if (mFragment != null){
            ft.detach(mFragment);
        }
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

}
