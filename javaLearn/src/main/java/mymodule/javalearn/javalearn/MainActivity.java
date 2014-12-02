package mymodule.javalearn.javalearn;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends Activity {

    /*
* enum与int、String之间的转换
*  对于：

enum Color { RED,BLUE,BLACK YELLOW,GREEN};

       (1)  ordinal()方法: 返回枚举值在枚举类种的顺序。这个顺序根据枚举值声明的顺序而定。
                 Color.RED.ordinal();  //返回结果：0
                 Color.BLUE.ordinal();  //返回结果：1
       (2)  compareTo()方法: Enum实现了java.lang.Comparable接口，因此可以比较象与指定对象的顺序。
       Enum中的compareTo返回的是两个枚举值的顺序之差。当然，前提是两个枚举值必须属于同一个枚举类，否则会抛出ClassCastException()异常。(具体可见源代码)
                 Color.RED.compareTo(Color.BLUE);  //返回结果 -1
       (3)  values()方法： 静态方法，返回一个包含全部枚举值的数组。
                 Color[] colors=Color.values();
                 for(Color c:colors){
                        System.out.print(c+",");
                 }//返回结果：RED,BLUE,BLACK YELLOW,GREEN,
       (4)  toString()方法： 返回枚举常量的名称。
                 Color c=Color.RED;
                 System.out.println(c);//返回结果: RED
       (5)  valueOf()方法： 这个方法和toString方法是相对应的，返回带指定名称的指定枚举类型的枚举常量。
                 Color.valueOf("BLUE");   //返回结果: Color.BLUE
       (6)  equals()方法： 比较两个枚举类对象的引用。

总结：

1.  enum<->int

enum -> int: int i = enumType.value.ordinal();

int -> enum: enumType b= enumType.values()[i];

2.  enum<->String

enum -> String: enumType.name()

String -> enum: enumType.valueOf(name);
* */
    public enum Color {RED,BLUE,BLACK, YELLOW,GREEN}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
