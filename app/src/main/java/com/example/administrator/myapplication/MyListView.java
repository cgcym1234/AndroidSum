package com.example.administrator.myapplication;

import android.view.GestureDetector;
import android.view.View;
import android.widget.ListView;

/**
 * Created by Administrator on 2014/10/6.
 * 继承控件
 继承控件的意思就是，我们并不需要自己重头去实现一个控件，只需要去继承一个现有的控件，
 然后在这个控件上增加一些新的功能，就可以形成一个自定义的控件了。

 下面我们再来编写一个新的继承控件。在ListView上滑动就可以显示出一个删除按钮，
 点击按钮就会删除相应数据的功能。

 这里在MyListView的构造方法中创建了一个GestureDetector的实例用于监听手势，
 然后给MyListView注册了touch监听事件。然后在onTouch()方法中进行判断，
 如果删除按钮已经显示了，就将它移除掉，如果删除按钮没有显示，
 就使用GestureDetector来处理当前手势。
 当手指按下时，会调用OnGestureListener的onDown()方法，
 在这里通过pointToPosition()方法来判断出当前选中的是ListView的哪一行。
 当手指快速滑动时，会调用onFling()方法，在这里会去加载delete_button.xml这个布局，
 然后将删除按钮添加到当前选中的那一行item上。注意，我们还给删除按钮添加了一个点击事件，
 当点击了删除按钮时就会回调onDeleteListener的onDelete()方法，在回调方法中应该去处理具体的删除操作。
 */
/*public class MyListView extends ListView implements View.OnTouchListener, GestureDetector.OnGestureListener {
    private GestureDetector gestureDetector;
    //private OnDeleteListener listener;
}*/
