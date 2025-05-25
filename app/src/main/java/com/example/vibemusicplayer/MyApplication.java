package com.example.vibemusicplayer;

import android.app.Application;

import org.xutils.x;

public class MyApplication extends Application {
    public String URL = "http://172.17.98.139/";
    public String insertUrl = URL + "MusicPlayer/insert.php";
    public String imageBaseUrl = URL + "MusicPlayer/images/";

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        //x.Ext.setDebug(BuildConfig.DEBUG); // 是否输出debug日志, 开启debug会影响性能.
    }
}