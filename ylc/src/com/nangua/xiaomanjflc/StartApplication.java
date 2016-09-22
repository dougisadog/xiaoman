package com.nangua.xiaomanjflc;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.view.View;
import android.view.View.OnClickListener;

import com.louding.frame.KJActivity;
import com.nangua.xiaomanjflc.ui.MainActivity;
import com.nangua.xiaomanjflc.widget.LoudingDialog;
import com.nangua.xiaomanjflc.AutoUpdateManager.UpdateCallback;
import com.nangua.xiaomanjflc.R;

public class StartApplication extends KJActivity {


	@Override
	public void setRootView() {
		setContentView(R.layout.activity_start);
	}

	@Override
	public void initData() {
		super.initData();
		
		AutoUpdateManager.getInstance().setUpdateCallback(new UpdateCallback() {
			
			@Override
			public void onUpdated() {
				updateDone();
			}
			
			@Override
			public void onNoUpdate() {
			}
			
			@Override
			public void onBeforeUpdate() {
			}
		});
		
		parseChannel();
	}
	
	/**
	 * 渠道解析 读取manifest的 channel来执行对应自动更新
	 */
	private void parseChannel() {
		try {
			ApplicationInfo appInfo = getPackageManager().getApplicationInfo(getPackageName(),
					PackageManager.GET_META_DATA);
			String channel = appInfo.metaData.getString("UMENG_CHANNEL", "").toUpperCase();
			if ("BAIDU".equals(channel)) {
				AutoUpdateManager.getInstance().initBaidu(this);
			} else if ("XIAOMI".equals(channel)) {
				AutoUpdateManager.getInstance().initXiaomi(this, false);
			} else if ("YYB".equals(channel)) {
				AutoUpdateManager.getInstance().initYYB(this);
			}
		} catch (Exception e) {
		}
		AutoUpdateManager.getInstance().initLocalUpdate(this);
	}

	/**
	 * 无更新或者 完成更新的正常进入流程
	 */
	private void updateDone() {
		AppVariables.needGesture = true;
		Intent mainIntent = null;
		// mainIntent = new Intent(StartApplication.this,
		// GuideActivity.class);// 引导页
		mainIntent = new Intent(StartApplication.this,
				MainActivity.class);
		mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		StartApplication.this.startActivity(mainIntent);
		StartApplication.this.finish();
	}
	
}
