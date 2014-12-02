package mymodule.mymodule.Utils;

import android.content.Context;
import android.view.ActionProvider;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;

import mymodule.mymodule.sum.R;

/**
 * Created by Administrator on 2014/11/9.
 * 这里我们新建了一个PlusActionProvider继承自ActionProvider，
 为了表示这个Action Provider是有子菜单的，需要重写hasSubMenu()方法并返回true，
 然后在onPrepareSubMenu通过调用SubMenu的add()方法添加子菜单。
 */
public class PlusActionProvider extends ActionProvider implements MenuItem.OnMenuItemClickListener{

    private Context context;
    /**
     * Creates a new instance. ActionProvider classes should always implement a
     * constructor that takes a single Context parameter for inflating from menu XML.
     *
     * @param context Context for accessing resources.
     */
    public PlusActionProvider(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public View onCreateActionView() {
        return null;
    }

    /*需要重写hasSubMenu()方法并返回true，*/
    @Override
    public boolean hasSubMenu() {
        return true;
    }

    @Override
    public void onPrepareSubMenu(SubMenu subMenu) {
        subMenu.clear();
        subMenu.add(context.getString(R.string.plus_group_chat)).setIcon(R.drawable.ofm_group_chat_icon).setOnMenuItemClickListener(this);
        subMenu.add(context.getString(R.string.plus_add_friend)).setIcon(R.drawable.ofm_add_icon).setOnMenuItemClickListener(this);
        subMenu.add(context.getString(R.string.plus_video_chat)).setIcon(R.drawable.ofm_video_icon).setOnMenuItemClickListener(this);

    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
        }
        return false;
    }
}
