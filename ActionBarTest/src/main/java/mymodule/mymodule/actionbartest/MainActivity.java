package mymodule.mymodule.actionbartest;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.widget.Button;
import android.widget.ShareActionProvider;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


public class MainActivity extends Activity {

    /*通过Action Bar图标进行导航
启用ActionBar图标导航的功能，可以允许用户根据当前应用的位置来在不同界面之间切换。
比如，A界面展示了一个列表，点击某一项之后进入了B界面，这时B界面就应该启用ActionBar图标导航功能，
这样就可以回到A界面。
我们可以通过调用setDisplayHomeAsUpEnabled()方法来启用ActionBar图标导航功能*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Overflow按钮不显示的情况
虽然现在我们已经掌握了不少ActionBar的用法，但是当你真正去使用它的时候还是可能会遇到各种各样的问题，
比如很多人都会碰到overflow按钮不显示的情况。明明是同样的一份代码，overflow按钮在有些手机上会显示，
而在有些手机上偏偏就不显示，这是为什么呢？后来我总结了一下，overflow按钮的显示情况和手机的硬件情况是有关系的，
如果手机没有物理Menu键的话，overflow按钮就可以显示，如果有物理Menu键的话，overflow按钮就不会显示出来。*/
        setOverflowShowingAlways();

        /*ActionBar的添加非常简单，只需要在AndroidManifest.xml中
        指定Application或Activity的theme是Theme.Holo或其子类就可以了*/
        /*而如果想要移除ActionBar的话通常有两种方式，一是将theme指定成Theme.Holo.NoActionBar，
        表示使用一个不包含ActionBar的主题，二是在Activity中调用以下方法：

        ActionBar导航的设计初衷并不是这样的，它和Back键的功能还是有一些区别的
        实现标准的ActionBar导航功能只需三步走。
第一步我们已经实现了，就是调用setDisplayHomeAsUpEnabled()方法，并传入true。
第二步需要在AndroidManifest.xml中配置父Activity，如下所示：
<activity
    android:name="com.example.actionbartest.MainActivity"
    android:logo="@drawable/weather" >
    <meta-data
        android:name="android.support.PARENT_ACTIVITY"
        android:value="com.example.actionbartest.LaunchActivity" />
</activity>
可以看到，这里通过meta-data标签指定了MainActivity的父Activity是LaunchActivity，在Android 4.1版本之后，也可以直接使用android:parentActivityName这个属性来进行指定，如下所示：
<activity
    android:name="com.example.actionbartest.MainActivity"
    android:logo="@drawable/weather"
    android:parentActivityName="com.example.actionbartest.LaunchActivity" >
</activity>
第三步则需要对android.R.id.home这个事件进行一些特殊处理，如下所示：
@Override
public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
    case android.R.id.home:
        Intent upIntent = NavUtils.getParentActivityIntent(this);
        if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
            TaskStackBuilder.create(this)
                    .addNextIntentWithParentStack(upIntent)
                    .startActivities();
        } else {
            upIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            NavUtils.navigateUpTo(this, upIntent);
        }
        return true;
        ......
    }
}
其中，调用NavUtils.getParentActivityIntent()方法可以获取到跳转至父Activity的Intent，
然后如果父Activity和当前Activity是在同一个Task中的，则直接调用navigateUpTo()方法进行跳转，
如果不是在同一个Task中的，则需要借助TaskStackBuilder来创建一个新的Task。
这样，就按照标准的规范成功实现ActionBar导航的功能了。*/
        ActionBar action = getActionBar();
        //action.hide();
        action.setDisplayHomeAsUpEnabled(true);


        Button button = (Button) findViewById(R.id.button_to_next);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoNavActivity(1);
            }
        });

        /*修改Action Bar的图标和标题
默认情况下，系统会使用<application>或者<activity>中icon属性指定的图片来作为ActionBar的图标，
但是我们也可以改变这一默认行为。如果我们想要使用另外一张图片来作为ActionBar的图标，
可以在<application>                    所有activity生效
或者<activity>中通过logo属性来进行指定。 当前activity生效*/

        /*标题中的内容该怎样修改呢？其实也很简单，使用label属性来指定一个字符串就可以了，如下所示：
<activity
    android:name="com.example.actionbartest.MainActivity"
    android:label="天气"
    android:logo="@drawable/weather" >
</activity>  */
    }

    private void gotoNavActivity(int type) {
//        Intent intent = new Intent(this, UserMyOrdersActivity.class);
        Intent intent = new Intent(this, ActionBarTabActivity.class);
        intent.putExtra("fragmentName", type);
        startActivity(intent);
    }
    /*添加Action按钮
ActionBar还可以根据应用程序当前的功能来提供与其相关的Action按钮，这些按钮都会以图标或文字的形式直接显示在ActionBar上。
当然，如果按钮过多，ActionBar上显示不完，多出的一些按钮可以隐藏在overflow里面（最右边的三个点就是overflow按钮），
点击一下overflow按钮就可以看到全部的Action按钮了。
当Activity启动的时候，系统会调用Activity的onCreateOptionsMenu()方法来取出所有的Action按钮，
我们只需要在这个方法中去加载一个menu资源，并把所有的Action按钮都定义在资源文件里面就可以了。*/

    /*添加Action Provider
和Action View有点类似，Action Provider也可以将一个Action按钮替换成一个自定义的布局。
但不同的是，Action Provider能够完全控制事件的所有行为，并且还可以在点击的时候显示子菜单。

为了添加一个Action Provider，我们需要在<item>标签中指定一个actionViewClass属性，
在里面填入Action Provider的完整类名。我们可以通过继承ActionProvider类的方式来创建一个自己的Action Provider，
同时，Android也提供好了几个内置的Action Provider，比如说ShareActionProvider。

由于每个Action Provider都可以自由地控制事件响应，所以它们不需要在onOptionsItemSelected()方法中再去监听点击事件，
而是应该在onPerformDefaultAction()方法中去执行相应的逻辑。*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        /*接着剩下的事情就是通过Intent来定义出你想分享哪些东西了，
        我们只需要在onCreateOptionsMenu()中调用MenuItem的getActionProvider()方法来得到该ShareActionProvider对象，
        再通过setShareIntent()方法去选择构建出什么样的一个Intent就可以了。*/
        MenuItem shareItem = menu.findItem(R.id.action_share);
        ShareActionProvider provider = (ShareActionProvider)shareItem.getActionProvider();
        provider.setShareIntent(getDefaultIntent());
        return true;
    }

    /*过getDefaultIntent()方法来构建了一个Intent，该Intent表示会将所有可以共享图片的程度都列出来*/
    private Intent getDefaultIntent() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/*");
        return intent;
    }

    /*让Overflow中的选项显示图标
如果你点击一下overflow按钮去查看隐藏的Action按钮，你会发现这部分Action按钮都是只显示文字不显示图标的，如下图所示：

这是官方的默认效果，Google认为隐藏在overflow中的Action按钮都应该只显示文字。当然，如果你认为这样不够美观，
希望在overflow中的Action按钮也可以显示图标，我们仍然可以想办法来改变这一默认行为。
其实，overflow中的Action按钮应不应该显示图标，是由MenuBuilder这个类的setOptionalIconsVisible方法来决定的，
如果我们在overflow被展开的时候给这个方法传入true，那么里面的每一个Action按钮对应的图标就都会显示出来了。
调用的方法当然仍然是用反射了，代码如下所示*/
    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (featureId == Window.FEATURE_ACTION_BAR && menu != null) {
            if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
                try {
                    Method m = menu.getClass().getDeclaredMethod(
                            "setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (Exception e) {
                }
            }
        }
        return super.onMenuOpened(featureId, menu);
    }

    /*响应Action按钮的点击事件
当用户点击Action按钮的时候，系统会调用Activity的onOptionsItemSelected()方法，
通过方法传入的MenuItem参数，我们可以调用它的getItemId()方法和menu资源中的id进行比较，
从而辨别出用户点击的是哪一个Action按钮*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_card:
                Toast.makeText(this, "action_card", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_collection:
                Toast.makeText(this, "action_collection", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_settings:
                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
                return true;
            /*当点击ActionBar图标的时候，系统同样会调用onOptionsItemSelected()方法，
            并且此时的itemId是android.R.id.home，所以finish()方法也就是加在这里的了。*/
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setOverflowShowingAlways() {
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
