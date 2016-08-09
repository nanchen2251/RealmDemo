package com.example.nanchen.realmdemo;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * @author nanchen
 * @date 16-8-8 下午5:51
 */
public class RealmUtils {
    private Context context;
    private static RealmUtils mInstance;
    private String realName = "myRealm.realm";

    private RealmUtils(Context context){
        this.context = context;
    }

    public static RealmUtils getInstance(Context context){
        if (mInstance == null){
            synchronized (RealmUtils.class){
                if (mInstance == null){
                    mInstance = new RealmUtils(context);
                }
            }
        }
        return mInstance;
    }

    /**
     * 获得Realm对象
     * @return
     */
    public Realm getRealm(){
        return Realm.getInstance(new RealmConfiguration.Builder(context).name(realName).build());
    }
}
