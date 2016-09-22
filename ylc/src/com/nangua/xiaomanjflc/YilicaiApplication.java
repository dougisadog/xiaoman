package com.nangua.xiaomanjflc;

import java.util.ArrayList;
import java.util.List;

import com.nangua.xiaomanjflc.error.ErrLogManager;
//import com.nangua.xiaomanjflc.service.MyPushIntentService;
import com.nangua.xiaomanjflc.support.AppActivityLifecycleCallbacks;
import com.nangua.xiaomanjflc.support.AppInfo;
import com.nangua.xiaomanjflc.support.CrashHandler;
import com.nangua.xiaomanjflc.support.ScreenObserver;
//import com.umeng.message.IUmengRegisterCallback;
//import com.umeng.message.PushAgent;
//import com.umeng.message.UmengNotificationClickHandler;
//import com.umeng.message.entity.UMessage;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.multidex.MultiDex;
import android.widget.Toast;

public class YilicaiApplication extends Application {

	@Override
	protected void attachBaseContext(Context base) {
		super.attachBaseContext(base);
		MultiDex.install(this);
	}


	@Override
	public void onCreate() {
		super.onCreate();
		CrashHandler crashHandler = CrashHandler.getInstance();
		crashHandler.init(getApplicationContext());

		instance = this;
		initAppInfo();
		ErrLogManager.registerHandler();
		if (Build.VERSION.SDK_INT >= 14) {
			registerActivityLifecycleCallbacks(new AppActivityLifecycleCallbacks());
		}

		// 推送消息SDK 初始化
//		UmengManager.getInstance().initPushInfo(this);

		// 监听屏幕
		observer = new ScreenObserver();

	}


	/**
	 * Application
	 * 用于 数据传递，数据共享 等,数据缓存等操作。
	 * 
	 * @author Doug
	 *
	 */
		private static final int SHOW_CUSTOM_ERR_MSG = 0;
		private static final int SHOW_CONNECT_SERVER_ERR_MSG = 10;
		private static final int SHOW_SERVER_ERR_MSG = 11;
		private static final int PUSH_MSG = 20;
		private static final int RESET_TO_LOGIN = 100;

	    private static YilicaiApplication instance = null;
	    
	    /**
	     * app性格信息
	     */
	    private AppInfo appInfo = null;
	    private Activity activity = null;
	    private Activity currentRunningActivity = null;
	    private List<String> stackActivities = new ArrayList<String>();
	    private ScreenObserver observer = null;

	    @SuppressLint("HandlerLeak")
		private Handler uiHandler = new Handler() {
	    	
	    	public void handleMessage(Message msg) {
	    		switch (msg.what) {
				case SHOW_CONNECT_SERVER_ERR_MSG:
					Toast.makeText(YilicaiApplication.getInstance().getActivity(), "connect fail", Toast.LENGTH_SHORT).show();
					break;
				case SHOW_SERVER_ERR_MSG:
					String serverMsg = (String) msg.obj;
					Toast.makeText(YilicaiApplication.getInstance().getActivity(), serverMsg, Toast.LENGTH_SHORT).show();
					break;
				case SHOW_CUSTOM_ERR_MSG:
					String errMsg = (String) msg.obj;
					Toast.makeText(YilicaiApplication.getInstance().getActivity(), errMsg, Toast.LENGTH_SHORT).show();
					break;
				case RESET_TO_LOGIN:
//					M.startAty(YilicaiApplication.getInstance().getActivity());
//					clearStackActivities();
					break;
				}
	    	};
	    	
	    };

	    
	    private void initAppInfo() {
	    	appInfo = new AppInfo();
	        appInfo.getAppInfo(this);
	    }
	    
	    /**
	     * umeng集成注册
	     */
//	    private void initPushInfo() {
//	    	PushAgent mPushAgent = PushAgent.getInstance(this);
//	    	//注册推送服务，每次调用register方法都会回调该接口
//	    	mPushAgent.register(new IUmengRegisterCallback() {
//
//	    	    @Override
//	    	    public void onSuccess(String deviceToken) {
//	    	        //注册成功会返回device token
//	    	    	System.out.println(deviceToken);
//	    	    }
//
//	    	    @Override
//	    	    public void onFailure(String s, String s1) {
//	    	    	System.out.println(s + "####" + s1);
//
//	    	    }
//	    	});
//	    	//完全自定义处理推送
////	    	mPushAgent.setPushIntentServiceClass(MyPushIntentService.class);
//	    	
//	    	UmengNotificationClickHandler notificationClickHandler = new UmengNotificationClickHandler() {
//	    	    @Override
//	    	    public void dealWithCustomAction(Context context, UMessage msg) {
//	    	        Toast.makeText(context, msg.custom, Toast.LENGTH_LONG).show();
//	    	    }
//	    	};
//	    	mPushAgent.setNotificationClickHandler(notificationClickHandler);
//	    }
	    
	    /**********************************************************************************************************************************************************/
	    
	    public void showCustomErrMsg(String errMsg) {
	    	Message msg = new Message();
	    	msg.what = SHOW_CUSTOM_ERR_MSG;
	    	msg.obj = errMsg;
	    	uiHandler.sendMessage(msg);
	    }
	    
	    public void showConnectServerErrMsg() {
	    	Message msg = new Message();
	        msg.what = SHOW_CONNECT_SERVER_ERR_MSG;
	        uiHandler.sendMessage(msg);
		}
	    
	    
	    public void showErrMsg(String errorMsg) {
	    	Message msg = new Message();
	    	msg.what = SHOW_SERVER_ERR_MSG;
	    	msg.obj = errorMsg;
	    	uiHandler.sendMessage(msg);
	    }
	    
	    public void pushMsg() {
	    	Message msg = new Message();
	    	msg.what = PUSH_MSG;
	    	uiHandler.sendMessage(msg);
	    }
	    
	    public void resetToLogin() {
	    	Message msg = new Message();
	    	msg.what = RESET_TO_LOGIN;
	    	uiHandler.sendMessage(msg);
	    }
	    
	    
	    /**********************************************************************************************************************************************************/
	    
	    public static YilicaiApplication getInstance() {
	        return instance;
	    }
	    
		public Activity getActivity() {
	        return activity;
	    }

	    public void setActivity(Activity activity) {
	        this.activity = activity;
	    }

	    public Activity getCurrentRunningActivity() {
	        return currentRunningActivity;
	    }

	    public void setCurrentRunningActivity(Activity currentRunningActivity) {
	        this.currentRunningActivity = currentRunningActivity;
	    }
		
		public Handler getUiHandler() {
			return uiHandler;
		}

		public List<String> getStackActivities() {
			return stackActivities;
		}
		
		public void addStackActivity(Activity activity) {
			stackActivities.add(activity.getClass().getSimpleName());
		}
		
		public void removeStackActivity(Activity activity) {
			String name = activity.getClass().getSimpleName();
			if (stackActivities.contains(name))
				stackActivities.remove(name);
		}
		
	}

