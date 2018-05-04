package cc.ylike.corelibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;



import java.util.Set;

import cc.ylike.corelibrary.CoreLibrary;

/**
 * Created by xsl on 2017/11/13.
 * @version 1.0
 * @author xsl
 * @des 底层 SharedPreferences 存储
 * 1.1.0.4 中增加此功能
 */
public class CelerySpUtils {

    private static SharedPreferences sharedPreferences = null;
    //空间命名
    public static final String spName = "corelibrary";

    private static SharedPreferences getmSharedPreferences(){
        if (sharedPreferences == null){
            sharedPreferences = CoreLibrary.AtContext.getSharedPreferences(spName, Activity.MODE_PRIVATE);
        }
        return sharedPreferences;
    }


    /**
     * 保存boolen类型数据
     * @param context
     * @param key 名称
     * @param bool 值
     */
    public static void putBoolen(Context context,String key,boolean bool){
        SharedPreferences.Editor editor = getmSharedPreferences().edit();
        editor.putBoolean(key, bool);
        editor.commit();
    }

    /**
     * 获boolen类型值
     * @param context
     * @param key 名称
     * @return
     */
    public static boolean getBoolen(Context context,String key){
        return getmSharedPreferences().getBoolean(key, false);
    }


    public static void putString(Context context,String key,String value){
        SharedPreferences.Editor editor = getmSharedPreferences().edit();
        editor.putString(key, value);
        editor.commit();
    }


    public static String getString(Context context,String key){
        return getmSharedPreferences().getString(key,"");
    }


    public static void putInt(Context context,String key, int value){
        SharedPreferences.Editor editor = getmSharedPreferences().edit();
        editor.putInt(key, value);
        editor.commit();
    }


    public static int getInt(Context context,String key){
        return getmSharedPreferences().getInt(key,-1);
    }

    public static void putFloat(Context context,String key, float value){
        SharedPreferences.Editor editor = getmSharedPreferences().edit();
        editor.putFloat(key, value);
        editor.commit();
    }


    public static float getFloat(Context context,String key){
        return getmSharedPreferences().getFloat(key,-1);
    }


    public static void putLong(Context context,String key, long value){
        SharedPreferences.Editor editor = getmSharedPreferences().edit();
        editor.putLong(key, value);
        editor.commit();
    }


    public static float getLong(Context context,String key){
        return getmSharedPreferences().getLong(key,-1);
    }


    public static void putStringSet(Context context,String key,Set<String> values){
        SharedPreferences.Editor editor = getmSharedPreferences().edit();
        editor.putStringSet(key, values);
        editor.commit();
    }


    public static Set<String> getStringSet(Context context,String key){
        return getmSharedPreferences().getStringSet(key,null);
    }



}
