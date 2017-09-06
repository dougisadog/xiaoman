package com.nangua.xiaomanjflc.service;

import java.util.Map.Entry;

import org.android.agoo.common.AgooConstants;
import org.json.JSONObject;

import com.louding.frame.KJDB;
import com.nangua.xiaomanjflc.bean.database.UPushMessage;
import com.nangua.xiaomanjflc.cache.CacheBean;
import com.umeng.common.UmLog;
import com.umeng.message.UTrack;
import com.umeng.message.UmengMessageService;
import com.umeng.message.entity.UMessage;

import android.content.Context;
import android.content.Intent;

public class MyPushIntentService extends UmengMessageService {
    private static final String TAG = MyPushIntentService.class.getName();

    @Override
    public void onMessage(Context context, Intent intent) {
        try {
            //可以通过MESSAGE_BODY取得消息体
            String message = intent.getStringExtra(AgooConstants.MESSAGE_BODY);
            UMessage msg = new UMessage(new JSONObject(message));
            
            UmLog.d(TAG, "message=" + message);      //消息体
            UmLog.d(TAG, "custom=" + msg.custom);    //自定义消息的内容
            UmLog.d(TAG, "title=" + msg.title);      //通知标题
            UmLog.d(TAG, "text=" + msg.text);        //通知内容
            
			UPushMessage m = new UPushMessage();
			for (Entry<String, String> entry : msg.extra.entrySet()) {
			    String key = entry.getKey();
			    String value = entry.getValue();
			    if (key.equals("type")) {
			    	m.setType(Integer.parseInt(value));
			    }
			    if (key.equals("url")) {
			    	m.setUrl(value);
			    }
			    if (key.equals("productId")) {
			    	m.setProductId(Integer.parseInt(value));
			    }
			    m.setContent(msg.text);
			    m.setTitle(msg.title);
			    m.setShowed(0);
			    m.setReceiveTime(System.currentTimeMillis());
			    CacheBean.getInstance().setMsg(m);
			    
			    
			  //删除所有存贮umeng推送信息只保存最新获取的
				KJDB kjdb = KJDB.create(context);
				kjdb.deleteByWhere(UPushMessage.class, null);
				kjdb.save(m);
			}
            // code  to handle message here
            // ...

            // 对完全自定义消息的处理方式，点击或者忽略
            boolean isClickOrDismissed = true;
            if (isClickOrDismissed) {
                //完全自定义消息的点击统计
                UTrack.getInstance(getApplicationContext()).trackMsgClick(msg);
            } else {
                //完全自定义消息的忽略统计
                UTrack.getInstance(getApplicationContext()).trackMsgDismissed(msg);
            }

            // 使用完全自定义消息来开启应用服务进程的示例代码
            // 首先需要设置完全自定义消息处理方式
            // mPushAgent.setPushIntentServiceClass(MyPushIntentService.class);
            // code to handle to start/stop service for app process
//            JSONObject json = new JSONObject(msg.custom);
//            String topic = json.getString("topic");
//            UmLog.d(TAG, "topic=" + topic);
//            if (topic != null && topic.equals("appName:startService")) {
//                // 在友盟portal上新建自定义消息，自定义消息文本如下
//                //{"topic":"appName:startService"}
//                if (Helper.isServiceRunning(context, NotificationService.class.getName()))
//                    return;
//                Intent intent1 = new Intent();
//                intent1.setClass(context, NotificationService.class);
//                context.startService(intent1);
//            } else if (topic != null && topic.equals("appName:stopService")) {
//                // 在友盟portal上新建自定义消息，自定义消息文本如下
//                //{"topic":"appName:stopService"}
//                if (!Helper.isServiceRunning(context,NotificationService.class.getName()))
//                    return;
//                Intent intent1 = new Intent();
//                intent1.setClass(context, NotificationService.class);
//                context.stopService(intent1);
//            }
        } catch (Exception e) {
            UmLog.e(TAG, e.getMessage());
        }
    }
}
