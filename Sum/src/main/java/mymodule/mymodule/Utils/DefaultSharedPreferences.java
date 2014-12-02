package mymodule.mymodule.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2014/11/9.
 *
 * SharedPreferences的一个工具类，调用setParam就能保存String, Integer, Boolean, Float, Long类型的参数
 * 同样调用getParam就能获取到保存在手机里面的数据
 *
 */
public class DefaultSharedPreferences {
    /**
     * 保存在手机里面的文件名
     */
    private static final String FileName = "share_date";

    private static final String TypeString = "String";
    private static final String TypeInteger = "Integer";
    private static final String TypeBoolean = "Boolean";
    private static final String TypeFloat = "Float";
    private static final String TypeLong = "Long";

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     * @param context
     * @param key
     * @param object
     */
    public static void setParam(Context context, String key, Object object){
        String type = object.getClass().getSimpleName();
        SharedPreferences preferences = context.getSharedPreferences(FileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        if(TypeString.equals(type)){
            editor.putString(key, (String) object);
        }
        else if(TypeInteger.equals(type)){
            editor.putInt(key, (Integer) object);
        }
        else if(TypeBoolean.equals(type)){
            editor.putBoolean(key, (Boolean) object);
        }
        else if(TypeFloat.equals(type)){
            editor.putFloat(key, (Float) object);
        }
        else if(TypeLong.equals(type)){
            editor.putLong(key, (Long)object);
        }

        editor.commit();
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     * @param context
     * @param key
     * @param defaultObject
     * @return
     */
    public static Object getParam(Context context , String key, Object defaultObject){
        String type = defaultObject.getClass().getSimpleName();
        SharedPreferences sp = context.getSharedPreferences(FileName, Context.MODE_PRIVATE);

        if(TypeString.equals(type)){
            return sp.getString(key, (String)defaultObject);
        }
        else if(TypeInteger.equals(type)){
            return sp.getInt(key, (Integer)defaultObject);
        }
        else if(TypeBoolean.equals(type)){
            return sp.getBoolean(key, (Boolean)defaultObject);
        }
        else if(TypeFloat.equals(type)){
            return sp.getFloat(key, (Float)defaultObject);
        }
        else if(TypeLong.equals(type)){
            return sp.getLong(key, (Long)defaultObject);
        }

        return null;
    }
}