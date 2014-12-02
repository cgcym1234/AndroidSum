//package mymodule.listview.listviewtest;
//
//import android.database.DataSetObserver;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.Filterable;
//import android.widget.ListAdapter;
//import android.widget.ListView;
//import android.widget.WrapperListAdapter;
//
//import java.util.ArrayList;
//
///**
// * Created by Administrator on 2014/10/9.
//在Android开发的时候经常会有一些列表数据分组显示的需求，对于iPhone来说修改下模型数据并设置下风格就可以轻松搞定（如下图）。但对于Android这种需求就会有些痛苦了。因为系统提供的ListAdapter无法提供这种支持，因此只能自定义Adapter。为了实现该功能，Adapter通常就开始充斥很多特殊处理以及各种硬编码。如何用Android的方式设计和实现一个自定义Adapter呢？源码中的HeaderViewListAdapter可能会提供些答案或者启发。
// */
///*如果List需要有标题就会使用该ListAdapter。该ListAdapter包装了另一个ListAdapter并保存着
//标题视图和相关数据对象。该类是作为基类，在代码中可能不需要直接使用。
//*/
//// 类名有点不确切，其实是支持Header和Footter的
//public class headerAdamter implements WrapperListAdapter, Filterable {
//    private final ListAdapter mAdapter;
//    // These two ArrayList are assumed to NOT be null.
//    // They are indeed created when declared in ListView and then shared.
//    ArrayList<ListView.FixedViewInfo> mHeaderViewInfos;
//    ArrayList<ListView.FixedViewInfo> mFooterViewInfos;
//    // Used as a placeholder in case the provided info views are indeed null.
//    // Currently only used by some CTS tests, which may be removed.
//    static final ArrayList<ListView.FixedViewInfo> EMPTY_INFO_LIST =
//            new ArrayList<ListView.FixedViewInfo>();
//    boolean mAreAllFixedViewsSelectable;
//    /// 这是个很有趣的概念，之后我们会一起了解
//    private final boolean mIsFilterable;
//    public HeaderViewListAdapter(ArrayList<ListView.FixedViewInfo> headerViewInfos,
//                                 ArrayList<ListView.FixedViewInfo> footerViewInfos,
//                                 ListAdapter adapter) {
//        mAdapter = adapter;
//        mIsFilterable = adapter instanceof Filterable;
//        /// 很好的处理，这里实际是使用了一种Null对象模式
//        if (headerViewInfos == null) {
//            mHeaderViewInfos = EMPTY_INFO_LIST;
//        } else {
//            mHeaderViewInfos = headerViewInfos;
//        }
//        if (footerViewInfos == null) {
//            mFooterViewInfos = EMPTY_INFO_LIST;
//        } else {
//            mFooterViewInfos = footerViewInfos;
//        }
//        mAreAllFixedViewsSelectable =
//                areAllListInfosSelectable(mHeaderViewInfos)
//                        && areAllListInfosSelectable(mFooterViewInfos);
//    }
//
//    /// 支持多个标题栏哦
//    public int getHeadersCount() {
//        return mHeaderViewInfos.size();
//    }
//
//    /// 支持多个尾注栏哦
//    public int getFootersCount() {
//        return mFooterViewInfos.size();
//    }
//    public boolean isEmpty() {
//        return mAdapter == null || mAdapter.isEmpty();
//    }
//
//    /// FixedViewInfo名字很贴切，这是相对于包装Adapter中的可变数据命名的
//    private boolean areAllListInfosSelectable(ArrayList<ListView.FixedViewInfo> infos) {
//        if (infos != null) {
//            for (ListView.FixedViewInfo info : infos) {
//                if (!info.isSelectable) {
//                    return false;
//                }
//            }
//        }
//        return true;
//    }
//
//    /// 支持标题删减
//    public boolean removeHeader(View v) {
//        for (int i = 0; i < mHeaderViewInfos.size(); i++) {
//            ListView.FixedViewInfo info = mHeaderViewInfos.get(i);
//            if (info.view == v) {
//                mHeaderViewInfos.remove(i);
//                mAreAllFixedViewsSelectable =
//                        areAllListInfosSelectable(mHeaderViewInfos)
//                                && areAllListInfosSelectable(mFooterViewInfos);
//                return true;
//            }
//        }
//        return false;
//    }
//
//    /// 支持尾注删减
//    public boolean removeFooter(View v) {
//        for (int i = 0; i &lt; mFooterViewInfos.size(); i++) {
//            ListView.FixedViewInfo info = mFooterViewInfos.get(i);
//            if (info.view == v) {
//                mFooterViewInfos.remove(i);
//                mAreAllFixedViewsSelectable =
//                        areAllListInfosSelectable(mHeaderViewInfos)
//                                && areAllListInfosSelectable(mFooterViewInfos);
//                return true;
//            }
//        }
//        return false;
//    }
//
//    /// 标题视图、尾注视图和普通的数据视图，一视同仁
//    public int getCount() {
//        if (mAdapter != null) {
//            return getFootersCount() + getHeadersCount() + mAdapter.getCount();
//        } else {
//            return getFootersCount() + getHeadersCount();
//        }
//    }
//    public boolean areAllItemsEnabled() {
//        if (mAdapter != null) {
//            return mAreAllFixedViewsSelectable && mAdapter.areAllItemsEnabled();
//        } else {
//            return true;
//        }
//    }
//    public boolean isEnabled(int position) {
//        // Header (negative positions will throw an ArrayIndexOutOfBoundsException)
//        int numHeaders = getHeadersCount();
//        if (position &lt; numHeaders) {
//            return mHeaderViewInfos.get(position).isSelectable;
//        }
//        // Adapter
//        final int adjPosition = position - numHeaders;
//        int adapterCount = 0;
//        if (mAdapter != null) {
//            adapterCount = mAdapter.getCount();
//            if (adjPosition &lt; adapterCount) {
//                return mAdapter.isEnabled(adjPosition);
//            }
//        }
//        // Footer (off-limits positions will throw an ArrayIndexOutOfBoundsException)
//        return mFooterViewInfos.get(adjPosition - adapterCount).isSelectable;
//    }
//    /// 需要分段处理
//    public Object getItem(int position) {
//        // Header (negative positions will throw an ArrayIndexOutOfBoundsException)
//        int numHeaders = getHeadersCount();
//        if (position &lt; numHeaders) {
//            return mHeaderViewInfos.get(position).data;
//        }
//        // Adapter
//        final int adjPosition = position - numHeaders;
//        int adapterCount = 0;
//        if (mAdapter != null) {
//            adapterCount = mAdapter.getCount();
//            if (adjPosition &lt; adapterCount) {
//                return mAdapter.getItem(adjPosition);
//            }
//        }
//        // Footer (off-limits positions will throw an ArrayIndexOutOfBoundsException)
//        return mFooterViewInfos.get(adjPosition - adapterCount).data;
//    }
//    /// 这可不包括Header和Footer
//    public long getItemId(int position) {
//        int numHeaders = getHeadersCount();
//        if (mAdapter != null && position >= numHeaders) {
//            int adjPosition = position - numHeaders;
//            int adapterCount = mAdapter.getCount();
//            if (adjPosition < adapterCount) {
//                return mAdapter.getItemId(adjPosition);
//            }
//        }
//        return -1;
//    }
//    public boolean hasStableIds() {
//        if (mAdapter != null) {
//            return mAdapter.hasStableIds();
//        }
//        return false;
//    }
//    /// 需要分段处理，其中Header和Footer视图已经事先存储在ListView.FixedViewInfo对象中了
//    public View getView(int position, View convertView, ViewGroup parent) {
//        // Header (negative positions will throw an ArrayIndexOutOfBoundsException)
//        int numHeaders = getHeadersCount();
//        if (position &lt; numHeaders) {
//            return mHeaderViewInfos.get(position).view;
//        }
//        // Adapter
//        final int adjPosition = position - numHeaders;
//        int adapterCount = 0;
//        if (mAdapter != null) {
//            adapterCount = mAdapter.getCount();
//            if (adjPosition &lt; adapterCount) {
//                return mAdapter.getView(adjPosition, convertView, parent);
//            }
//        }
//        // Footer (off-limits positions will throw an ArrayIndexOutOfBoundsException)
//        return mFooterViewInfos.get(adjPosition - adapterCount).view;
//    }
//    /// 支持同一个List中不同类型的视图
//    public int getItemViewType(int position) {
//        int numHeaders = getHeadersCount();
//        if (mAdapter != null && position >= numHeaders) {
//            int adjPosition = position - numHeaders;
//            int adapterCount = mAdapter.getCount();
//            if (adjPosition &lt; adapterCount) {
//                return mAdapter.getItemViewType(adjPosition);
//            }
//        }
//        return AdapterView.ITEM_VIEW_TYPE_HEADER_OR_FOOTER;
//    }
//    public int getViewTypeCount() {
//        if (mAdapter != null) {
//            return mAdapter.getViewTypeCount();
//        }
//        return 1;
//    }
//    public void registerDataSetObserver(DataSetObserver observer) {
//        if (mAdapter != null) {
//            mAdapter.registerDataSetObserver(observer);
//        }
//    }
//    public void unregisterDataSetObserver(DataSetObserver observer) {
//        if (mAdapter != null) {
//            mAdapter.unregisterDataSetObserver(observer);
//        }
//    }
//    /// 这有点意思，有待发掘
//    public Filter getFilter() {
//        if (mIsFilterable) {
//            return ((Filterable) mAdapter).getFilter();
//        }
//        return null;
//    }
//    public ListAdapter getWrappedAdapter() {
//        return mAdapter;
//    }
//}
