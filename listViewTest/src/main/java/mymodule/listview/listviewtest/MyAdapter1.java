package mymodule.listview.listviewtest;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2014/10/9.
 */
public class MyAdapter1 extends BaseAdapter {

    /*Adapter是ListView和数据源之间的桥梁,当每条数据进入可见区时，adapter会调用其getView()方法，
    并返回代表具体数据的视图。滚动的时候频繁调用，支持成千上万的数据。*/

    static class ViewHolder
    {
        private TextView tv;
        private TextView tv2;
        private ImageView iv;
    }

    private LayoutInflater mInflater;
    private Bitmap mIcon1;
    private List<Map<String,Object>> data;
    /*MyAdapter构造器，传入参数*/
    public MyAdapter1(Context context,List<Map<String,Object>> mData){
        //参数初始化
        this.mInflater = LayoutInflater.from(context);
        data = mData;
        mIcon1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.listview1, null);
            holder = new ViewHolder();
            //得到条目中的子组件
            holder.iv = (ImageView)convertView.findViewById(R.id.img);
            holder.tv = (TextView)convertView.findViewById(R.id.title);
            holder.tv2 = (TextView)convertView.findViewById(R.id.info);
            convertView.setTag(holder);
        }

        holder = (ViewHolder) convertView.getTag();


        //从list对象中为子组件赋值
        holder.iv.setImageBitmap(mIcon1);
        holder.tv.setText(data.get(position).get("title").toString());
        holder.tv2.setText(data.get(position).get("info").toString());

        return convertView;
    }

    public interface Adapter1 {
        //注册一个Observer，当Adapter所表示的数据改变时会通知它，DataSetObserver是一个抽象类，定义了两个方法：onChanged与onInvalidated
        void registerDataSetObserver(DataSetObserver observer);
        //取消注册一个Observer
        void unregisterDataSetObserver(DataSetObserver observer);
        //所表示的数据的项数
        int getCount();
        //返回指定位置的数据项
        Object getItem(int position);
        //返回指定位置的数据项的ID
        long getItemId(int position);
        //表示所有数据项的ID是否是稳定的，在BaseAdapter中默认返回了false，假设是不稳定的，在CursorAdapter中返回了true，Cursor中的_ID是不变的
        boolean hasStableIds();
        //为每一个数据项产生相应的视图
        View getView(int position, View convertView, ViewGroup parent);
        //为了避免产生大量的View浪费内存，在Android中，AdapterView中的View是可回收的使用的。比如你有100项数据要显示，而你的屏幕一次只能显示10条数据，则
        //只产生10个View，当往下拖动要显示第11个View时，会把第1个View的引用传递过去，更新里面的数据再显示，也就是说View可重用，只是更新视图中的数据用于显示新
        //的一项，如果一个视图的视图类型是IGNORE_ITEM_VIEW_TYPE的话，则此视图不会被重用
        static final int IGNORE_ITEM_VIEW_TYPE = AdapterView.ITEM_VIEW_TYPE_IGNORE;
        //获得相应位置的这图类型
        int getItemViewType(int position);
        //getView可以返回的View的类型数量。（在HeaderViewListAdapter中可以包含Header和Footer，getView可以返回Header、Footer及Adapter
        //中的视图，但其getViewTypeCount的实现只是调用了内部Adapter的的getViewTypeCount，忽略了Header、Footer中的View Type,不懂。
        int getViewTypeCount();
        static final int NO_SELECTION = Integer.MIN_VALUE;
        boolean isEmpty();
    }
}
