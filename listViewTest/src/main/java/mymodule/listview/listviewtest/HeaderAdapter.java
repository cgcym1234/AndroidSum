package mymodule.listview.listviewtest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2014/10/9.
 */
public class HeaderAdapter extends BaseAdapter {

    /*Adapter是ListView和数据源之间的桥梁,当每条数据进入可见区时，adapter会调用其getView()方法，
    并返回代表具体数据的视图。滚动的时候频繁调用，支持成千上万的数据。*/

    static class ViewHolder
    {
        private TextView tv;
        private TextView tv2;
        private ImageView iv;
    }

    static class indexPath {
        int section;
        int row;

        public indexPath(int section, int row){
            this.section = section;
            this.row = row;
        }
    }

    private LayoutInflater mInflater;
    private Bitmap mIcon1;
    private Context context;
    private List<Map<String,Object>> data;
    public final static int TYPE_SECTION_HEADER = 0;

    //每个section的所有items数目，包括header
    private int[] sectionItems;
    int sectionCount = 0;
    int totalCount = 0;
    int curSection = 0;


    /*MyAdapter构造器，传入参数*/
    public HeaderAdapter(Context context, List<Map<String, Object>> mData){
        //参数初始化
        this.mInflater = LayoutInflater.from(context);
        data = mData;
        this.context = context;
        mIcon1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);
        sectionCount = data.size();
        sectionItems = new int[sectionCount];
    }

    @Override
    public boolean areAllItemsEnabled() {
        return super.areAllItemsEnabled();
    }

    private int getSectionCount() {
        return sectionCount = data.size();
    }

    private int getItemCountInSection(int section) {
        ArrayList arr = (ArrayList) data.get(section).get("arr");
        return arr.size();
    }

    private int sectionFromPosition(int position) {
        int total = 0;
        for (int i=0; i < sectionCount; i++) {
            total += sectionItems[i];
            if (total >= position) return i;
        }
        return 0;
    }

    private indexPath indexFromPosition(int position) {
        indexPath indexPath = null;
        int total = 0;
        int prevTotal = 0;
        for (int i=0; i < sectionCount; i++) {
            total += sectionItems[i];
            if (total >= position) {
                int row = position-1-prevTotal;
                indexPath = new indexPath(i, (row < 0 ? 0 : row));
                return indexPath;
            }
            prevTotal = total;
        }
        return indexPath;
    }

    @Override
    public int getCount() {
        totalCount = 0;
        for (int i = 0; i < getSectionCount(); i++) {
            sectionItems[i] = getItemCountInSection(i)+1;
            totalCount += sectionItems[i];
        }

        return totalCount;
    }

    @Override
    public Object getItem(int position) {
        return data.get(sectionFromPosition(position));
    }

    @Override
    public long getItemId(int position) {
        return sectionFromPosition(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        indexPath indexPath = indexFromPosition(position+1);
        android.util.Log.d("getView", "indexPath:"+ indexPath.section + " row:" + indexPath.row);
        ViewHolder holder;
        switch (indexPath.row) {
            case 0:
            {
                if (convertView == null) {
                    convertView = mInflater.inflate(R.layout.listview1, null);

                    holder = new ViewHolder();

                    //得到条目中的子组件
                    holder.iv = (ImageView)convertView.findViewById(R.id.img);
                    holder.tv = (TextView)convertView.findViewById(R.id.title);
                    holder.tv2 = (TextView)convertView.findViewById(R.id.info);

                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag(0);
                    //从list对象中为子组件赋值
                }

                holder.iv.setImageBitmap(mIcon1);
                holder.tv.setText(data.get(indexPath.section).get("title").toString());
                holder.tv2.setText(data.get(indexPath.section).get("info").toString());
            }
                break;
            default:
            {
//                    if (convertView == null) {
//                        convertView = mInflater.inflate(R.layout.listview2, null);
//
//                        holder = new ViewHolder();
//                        //得到条目中的子组件
//                        holder.tv = (TextView)convertView.findViewById(R.id.list_text2);
//                        //convertView.setTag(holder);
//                    } else {
//                        holder = (ViewHolder) convertView.getTag();
//                    }

                convertView = mInflater.inflate(R.layout.listview2, null);
                holder = new ViewHolder();
                //得到条目中的子组件
                holder.tv = (TextView)convertView.findViewById(R.id.list_text2);
                ArrayList<String> arr = (ArrayList<String>) data.get(indexPath.section).get("arr");
                holder.tv.setText(arr.get(indexPath.row-1));
            }
                break;
        }

        return convertView;
    }

}
