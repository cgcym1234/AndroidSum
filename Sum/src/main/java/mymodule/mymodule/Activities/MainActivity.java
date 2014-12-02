package mymodule.mymodule.Activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.ShareActionProvider;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import mymodule.mymodule.Fragments.TestFragment;
import mymodule.mymodule.common.MyTabListener;
import mymodule.mymodule.sum.R;

/**
 添加导航Tabs
 而Android官方更加推荐使用ActionBar中提供的Tabs功能，因为它更加的智能，可以自动适配各种屏幕的大小。
 比如说，在平板上屏幕的空间非常充足，Tabs会和Action按钮在同一行显示，如下图所示：

 而如果是在手机上，屏幕的空间不够大的话，Tabs和Action按钮则会分为两行显示，如下图所示：

 下面我们就来看一下如何使用ActionBar提供的Tab功能，大致可以分为以下几步：
 1. 实现ActionBar.TabListener接口，这个接口提供了Tab事件的各种回调，比如当用户点击了一个Tab时，你就可以进行切换Tab的操作。

 2.为每一个你想添加的Tab创建一个ActionBar.Tab的实例，并且调用setTabListener()方法来设置ActionBar.TabListener。
 除此之外，还需要调用setText()方法来给当前Tab设置标题。

 3.最后调用ActionBar的addTab()方法将创建好的Tab添加到ActionBar中。
 */
public class MainActivity extends Activity {


    private static final String TAG = "MainActivity.";

    private String[] mTitles = {"待付款", "待发货", "已发货", "待评价", "全部"};

    Button buttonBarCode;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        actionBarInit();
        barCodeTest();

    }

    void actionBarInit()
    {
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            /*启用ActionBar图标导航功能*/
            actionBar.setDisplayHomeAsUpEnabled(true);

            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        }

        /*始终显示Overflow*/
        setOverflowShowingAlways();
    }

    void barCodeTest(){
        buttonBarCode = (Button) findViewById(R.id.button_bar_code);
        buttonBarCode.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,BarCodeDemo.class);
                startActivity(intent);
            }
        });
    }

    Intent getDefaultShareIntent(){
        Intent intent = new Intent(Intent.ACTION_SEND);
        //Intent intent = new Intent();
        //表示会将所有可以共享图片的程度都列出来
        intent.setType("image/*");
        return intent;
    }

    /*添加Action按钮
   ActionBar还可以根据应用程序当前的功能来提供与其相关的Action按钮，这些按钮都会以图标或文字的形式直接显示在ActionBar上。
   当然，如果按钮过多，ActionBar上显示不完，多出的一些按钮可以隐藏在overflow里面（最右边的三个点就是overflow按钮），
   点击一下overflow按钮就可以看到全部的Action按钮了。
   当Activity启动的时候，系统会调用Activity的onCreateOptionsMenu()方法来取出所有的Action按钮，
   我们只需要在这个方法中去加载一个menu资源，并把所有的Action按钮都定义在资源文件里面就可以了。*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        /*如果你还希望在代码中对SearchView的属性进行配置（比如添加监听事件等），完全没有问题，
        只需要在onCreateOptionsMenu()方法中获取该ActionView的实例就可以了，代码如下所示：*/
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setClickable();

        /*除此之外，有些程序可能还希望在ActionView展开和合并的时候显示不同的界面，
        其实我们只需要去注册一个ActionView的监听器就能实现这样的功能了，代码如下所示：*/
//        searchItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
//            /*当SearchView展开的时候*/
//            @Override
//            public boolean onMenuItemActionExpand(MenuItem item) {
//                Log.d(TAG, "onMenuItemActionExpand");
//                return false;
//            }
//
//            /*当SearchView合并的时候*/
//            @Override
//            public boolean onMenuItemActionCollapse(MenuItem item) {
//                Log.d(TAG, "onMenuItemActionCollapse");
//                return false;
//            }
//        });


        /*ShareActionProvider会自己处理它的显示和事件，但我们仍然要记得给它添加一个title，以防止它会在overflow当中出现。
接着剩下的事情就是通过Intent来定义出你想分享哪些东西了，我们只需要在onCreateOptionsMenu()中调用MenuItem的getActionProvider()
方法来得到该ShareActionProvider对象，再通过setShareIntent()方法去选择构建出什么样的一个Intent就可以了。代码如下所示：*/
        MenuItem shareItem = menu.findItem(R.id.action_share);
        ShareActionProvider shareActionProvider = (ShareActionProvider) shareItem.getActionProvider();
        shareActionProvider.setShareIntent(getDefaultShareIntent());

        return super.onCreateOptionsMenu(menu);
    }

    /*响应Action按钮的点击事件
当用户点击Action按钮的时候，系统会调用Activity的onOptionsItemSelected()方法，
通过方法传入的MenuItem参数，我们可以调用它的getItemId()方法和menu资源中的id进行比较，
从而辨别出用户点击的是哪一个Action按钮*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_settings:{
                break;
            }
            case R.id.action_collection:{
                break;
            }
            case R.id.action_search:{
                Log.d(TAG, "onOptionsItemSelected");
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }


    /*让Overflow中的选项显示图标
如果你点击一下overflow按钮去查看隐藏的Action按钮，你会发现这部分Action按钮都是只显示文字不显示图标的：
这是官方的默认效果，Google认为隐藏在overflow中的Action按钮都应该只显示文字。当然，如果你认为这样不够美观，
希望在overflow中的Action按钮也可以显示图标，我们仍然可以想办法来改变这一默认行为。
其实，overflow中的Action按钮应不应该显示图标，是由MenuBuilder这个类的setOptionalIconsVisible方法来决定的，
如果我们在overflow被展开的时候给这个方法传入true，那么里面的每一个Action按钮对应的图标就都会显示出来了。
调用的方法当然仍然是用反射了，代码如下所示*/
    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        String menuBuilderName = "MenuBuilder";
        if (featureId == Window.FEATURE_ACTION_BAR && menu != null){
            if (menu.getClass().getSimpleName().equals(menuBuilderName)){
                try {
                    Method method = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    method.setAccessible(true);
                    method.invoke(menu, true);
                } catch (Exception ignored) {

                }
            }
        }
        return super.onMenuOpened(featureId, menu);
    }

    /*Overflow按钮不显示的情况
虽然现在我们已经掌握了不少ActionBar的用法，但是当你真正去使用它的时候还是可能会遇到各种各样的问题，
比如很多人都会碰到overflow按钮不显示的情况。明明是同样的一份代码，overflow按钮在有些手机上会显示，
而在有些手机上偏偏就不显示，这是为什么呢？后来我总结了一下，overflow按钮的显示情况和手机的硬件情况是有关系的，
如果手机没有物理Menu键的话，overflow按钮就可以显示，如果有物理Menu键的话，overflow按钮就不会显示出来。*/
    protected void setOverflowShowingAlways() {
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class
                    .getDeclaredField("sHasPermanentMenuKey");
            menuKeyField.setAccessible(true);
            menuKeyField.setBoolean(config, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
