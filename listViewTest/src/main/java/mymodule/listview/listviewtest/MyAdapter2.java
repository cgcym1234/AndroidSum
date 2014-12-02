package mymodule.listview.listviewtest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2014/10/9.
 */
public class MyAdapter2 extends BaseAdapter {

    /*Adapter是ListView和数据源之间的桥梁,当每条数据进入可见区时，adapter会调用其getView()方法，
    并返回代表具体数据的视图。滚动的时候频繁调用，支持成千上万的数据。*/

    static class ViewHolder
    {
        private TextView tv;
        private TextView tv2;
        private ImageView iv;
    }

    int i = 0;
    private LayoutInflater mInflater;
    private Bitmap mIcon1;
    private Context context;
    private List<Map<String,Object>> data;
    /*MyAdapter构造器，传入参数*/
    public MyAdapter2(Context context, List<Map<String, Object>> mData){
        //参数初始化
        this.mInflater = LayoutInflater.from(context);
        data = mData;
        this.context = context;
        mIcon1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);
    }

    /*重写Baseadapter时，我们知道需要重写以下四个方法：
    getCount，getItem(int position)，getItemId(int position)，getView方法，
getCount决定了listview一共有多少个item，而getView返回了每个item项所显示的view。
可是getItem(int position)，getItemId(int position)有什么作用呢？该怎么重写呢？

首先看 getItem：
官方解释是Get the data item associated with the specified position in the data set.
即获得相应数据集合中特定位置的数据项。那么该方法是在哪里被调用呢？什么时候被调用呢？
通过查看源代码发现，getItem方法不是在Baseadapter类中被调用的，而是在Adapterview中被调用的。

那么getItemAtPosition(position) 又是什么时候被调用？答案：它也不会被自动调用，它是用来在我们设置
setOnItemClickListener、setOnItemLongClickListener、setOnItemSelectedListener
的点击选择处理事件中方便地调用来获取当前行数据的。
官方解释Impelmenters can call getItemAtPosition(position) if they need to access the data

associated with the selected item.*/
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
            LinearLayout linearLayout = new LinearLayout(context);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            View view1 = mInflater.inflate(R.layout.listview1, null);
            View view2 = mInflater.inflate(R.layout.listview2, null);
            linearLayout.addView(view1);
            linearLayout.addView(view2);

            if (position%3 == 0) {
                View view3 = mInflater.inflate(R.layout.listview3, null);
                linearLayout.addView(view3);
            }

            convertView = linearLayout;
            holder = new ViewHolder();
            //得到条目中的子组件
            holder.iv = (ImageView)convertView.findViewById(R.id.img);
            holder.tv = (TextView)convertView.findViewById(R.id.title);
            holder.tv2 = (TextView)convertView.findViewById(R.id.info);
            convertView.setTag(holder);
            android.util.Log.d("getView", "count:" + ++i);
        }

        holder = (ViewHolder) convertView.getTag();


        //从list对象中为子组件赋值
        holder.iv.setImageBitmap(mIcon1);
        holder.tv.setText(data.get(position).get("title").toString());
        holder.tv2.setText(data.get(position).get("info").toString());

        return convertView;
    }

}
