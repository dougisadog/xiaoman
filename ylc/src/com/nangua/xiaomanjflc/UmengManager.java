package com.nangua.xiaomanjflc;

//import com.umeng.message.IUmengRegisterCallback;
//import com.umeng.message.PushAgent;
//import com.umeng.message.UmengNotificationClickHandler;
//import com.umeng.message.entity.UMessage;

import android.content.Context;
import android.widget.Toast;

public class UmengManager {
	
	private static UmengManager instance = null;
	
	public static UmengManager getInstance() {
		if (null == instance) {
			instance = new UmengManager();
		}
		return instance;
	}
	
    public void initPushInfo(Context context) {
//    	PushAgent mPushAgent = PushAgent.getInstance(context);
//    	//注册推送服务，每次调用register方法都会回调该接口
//    	mPushAgent.register(new IUmengRegisterCallback() {
//
//    	    @Override
//    	    public void onSuccess(String deviceToken) {
//    	        //注册成功会返回device token
//    	    	System.out.println(deviceToken);
//    	    }
//
//    	    @Override
//    	    public void onFailure(String s, String s1) {
//    	    	System.out.println(s + "####" + s1);
//
//    	    }
//    	});
//    	//完全自定义处理推送
////    	mPushAgent.setPushIntentServiceClass(MyPushIntentService.class);
//    	mPushAgent.setPushIntentServiceClass(null);
//
//    	//umeng 后台配置自定义信息时的处理
//    	UmengNotificationClickHandler notificationClickHandler = new UmengNotificationClickHandler() {
//    	    @Override
//    	    public void dealWithCustomAction(Context context, UMessage msg) {
//    	    	//自动以notification消息处理
//    	        Toast.makeText(context, msg.custom, Toast.LENGTH_LONG).show();
//    	    }
//    	};
//    	mPushAgent.setNotificationClickHandler(notificationClickHandler);
    }
	

}
