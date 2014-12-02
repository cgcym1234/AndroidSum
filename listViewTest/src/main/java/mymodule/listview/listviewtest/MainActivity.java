package mymodule.listview.listviewtest;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.HeaderViewListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;


import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class MainActivity extends Activity {

    public PullToRefreshListView listView;

    SimpleAdapter adapter1;
    MyAdapter1 myAdapter1;
    MyAdapter2 myAdapter2;

    void init1() {
        List<Map<String,Object>> list = new ArrayList<Map<String, Object>>(20);
        for (int i = 0; i < 20; i++) {
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("title", ":"+i);
            map.put("info", "这是数据项一" + i);
            map.put("img", R.drawable.ic_actionbar_logo);

            ArrayList<String> array = new ArrayList<String>();
            for (int j = 0; j < i+1; j++) {
                array.add("sub:"+j);
                if (j==3) break;
            }

            map.put("arr", array);

            list.add(map);
        }
        adapter1 = new SimpleAdapter(this,list,R.layout.listview1,new String[]{"title","info","img"},new int[]{R.id.title,R.id.info,R.id.img});
        myAdapter1 = new MyAdapter1(this,list);
        myAdapter2 = new MyAdapter2(this,list);

        HeaderAdapter headerAdapter = new HeaderAdapter(this, list);

        listView.setAdapter(headerAdapter);
    }


//list:数据集合

    private List<String> list = new ArrayList<String>();

//listTag:Tag集合，其中Tag是分类的分割标签，每个分组的header
    private List<String> listTag = new ArrayList<String>();

    public void setData(){
        list.add("A");

        listTag.add("A");

        for(int i=0;i<3;i++){

            list.add("阿凡达"+i);

        }

        list.add("B");

        listTag.add("B");

        for(int i=0;i<3;i++){

            list.add("比特风暴"+i);

        }

        list.add("C");

        listTag.add("C");

        for(int i=0;i<30;i++){

            list.add("查理风云"+i);

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (PullToRefreshListView) findViewById(R.id.listView);
        listView.setMode(PullToRefreshBase.Mode.BOTH);
        init1();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

}
