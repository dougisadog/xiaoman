package com.nangua.xiaomanjflc;

import com.nangua.xiaomanjflc.cache.CacheBean;

public class AppVariables {
	
	public static Long cacheLiveTime = (long) (1 * 60 * 1000); //10分钟

    public static int uid = 0; //主键

    public static String sid = ""; //通信登录id sid

    public static boolean isSignin = false; //已登录

    public static String tel = ""; //电话

    public static long lastTime = 0; //上次登录时间
    
    //开启强制更新
    public static boolean  forceUpdate = false;

    //当前是否强制弹出手势验证
    public static boolean needGesture = true;

    /**
     * 清空缓存
     */
    public static void clear() {
        uid = 0;
        sid = "";
        isSignin = false;
        tel = "";
        CacheBean.getInstance().clear();
    }

}
