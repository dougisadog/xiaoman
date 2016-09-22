package com.nangua.xiaomanjflc;

import org.json.JSONException;
import org.json.JSONObject;

import com.louding.frame.KJHttp;
import com.louding.frame.http.HttpCallBack;
import com.louding.frame.http.HttpParams;
import com.nangua.xiaomanjflc.bean.Update;
import com.nangua.xiaomanjflc.cache.CacheBean;
import com.nangua.xiaomanjflc.support.ApkInfo;
import com.nangua.xiaomanjflc.support.UpdateManager;
import com.nangua.xiaomanjflc.support.UpdateManager.CheckVersionInterface;
import com.nangua.xiaomanjflc.support.UpdateManager.OnCheckDoneListener;
import com.nangua.xiaomanjflc.utils.ApplicationUtil;
import com.tencent.tmselfupdatesdk.ITMSelfUpdateListener;
import com.tencent.tmselfupdatesdk.TMSelfUpdateManager;
import com.tencent.tmselfupdatesdk.YYBDownloadListener;
import com.tencent.tmselfupdatesdk.model.TMSelfUpdateUpdateInfo;
import com.xiaomi.market.sdk.UpdateResponse;
import com.xiaomi.market.sdk.UpdateStatus;
import com.xiaomi.market.sdk.XiaomiUpdateAgent;
import com.xiaomi.market.sdk.XiaomiUpdateListener;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;

public class AutoUpdateManager {

	private static AutoUpdateManager instance = null;
	
	private KJHttp http;
	private JSONObject versionInfo;
	private UpdateManager updateManager;
	private Update u;
	
	public static AutoUpdateManager getInstance() {
		if (null == instance) {
			instance = new AutoUpdateManager();
			instance.setUpdateCallback(new UpdateCallback(){

				@Override
				public void onNoUpdate() {
				}

				@Override
				public void onUpdated() {
				}

				@Override
				public void onBeforeUpdate() {
				}});
		}
		return instance;
	}
	
	private UpdateCallback updateCallback;
	
	public UpdateCallback getUpdateCallback() {
		return updateCallback;
	}

	public void setUpdateCallback(UpdateCallback updateCallback) {
		this.updateCallback = updateCallback;
	}
	
	/**
	 * 通用更新回调
	 * @author Administrator
	 *
	 */
	public static interface UpdateCallback {
		
		//无更新 暂未使用
		public void onNoUpdate();
		
		//更新完成
		public void onUpdated();	
		
		//更新前 暂未使用
		public void onBeforeUpdate();	
	}
	
	/**
	 * 官方更新流程
	 * @param context
	 */
	public void initLocalUpdate(final Context context) {
		new Handler().postDelayed(new Runnable() {
			public void run() {
				http = new KJHttp();
				updateManager = UpdateManager.getUpdateManager();
				HttpParams params = new HttpParams();

				params.put("sid", AppVariables.sid);
				http.post(AppConstants.UPDATE, params, new HttpCallBack(
						context, false) {

					@Override
					public void failure(JSONObject ret) {
						System.out.println("f");
					}

					@Override
					public void onFailure(Throwable t, int errorNo,
							String strMsg) {
//						updateDone();
						updateCallback.onUpdated();
					};

					public void onSuccess(String t) {
						try {
							versionInfo = new JSONObject(t);
							checkUpdate(context);
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				});
				
			}
		}, 2900);
	}
	
	private void checkUpdate(Context context) {
		updateManager.setOnCheckDoneListener(new OnCheckDoneListener() {
			@Override
			public void onCheckDone() {
				updateCallback.onUpdated();
//				updateDone();
			}
		});
		CheckVersionInterface update = new CheckVersionInterface() {
			
			@Override
			public Update checkVersion() throws Exception {
				try {
					u = new Update(versionInfo);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return u;
			}
		};
		updateManager.checkAppUpdate(context, false, update);
	}

	
	/**
	 * 应用宝注册自动更新  selfUpdateManager.startSelfUpdate(false);启动更新 若为true需强制下载应用宝
		若为 true则需要在activity处增加 onResume处
		try {
	  	 selfUpdateManager.onActivityResume();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	 * @param c
	 */
	public void initYYB(Context c) {
		// 自更新sdk初始化
		TMSelfUpdateManager selfUpdateManager = TMSelfUpdateManager.getInstance();
		try {
			final Context context = c.getApplicationContext();// application的context
			String channelid = "990483"; // 应用宝渠道包的渠道号，申请方法请参见《腾讯应用宝自更新SDK产品介绍》中的产品接入步骤step1
			ITMSelfUpdateListener selfupdateListener = new ITMSelfUpdateListener() {
				@Override
				public void onDownloadAppStateChanged(final int state, final int errorCode, final String errorMsg) {
					// TODO 更新包下载状态变化的处理逻辑
				}

				@Override
				public void onUpdateInfoReceived(TMSelfUpdateUpdateInfo info) {
					// TODO 收到更新信息的处理逻辑
					ApkInfo apkInfo = ApplicationUtil.getApkInfo(context);
					if (apkInfo.versionCode >= info.versioncode) {
						updateCallback.onUpdated();
					}
					else {
						updateCallback.onBeforeUpdate();
					}
				}

				@Override
				public void onDownloadAppProgressChanged(final long arg0, final long arg1) {
					// TODO 更新包下载进度发生变化的处理逻辑
				}
			};
			YYBDownloadListener yybDownloadListener = new YYBDownloadListener() {
				@Override
				public void onDownloadYYBStateChanged(String url, final int state, int errorCode, String errorMsg) {
					// TODO 应用宝下载状态变化的处理逻辑
				}

				@Override
				public void onDownloadYYBProgressChanged(final String url, final long receiveDataLen,
						final long totalDataLen) {
					// TODO 应用宝下载进度变化的处理逻辑
				}

				@Override
				public void onCheckDownloadYYBState(String arg0, int arg1, long arg2, long arg3) {
					// TODO Auto-generated method stub

				}
			};
			Bundle bundle = null;// 附加参数的bundle，一般情况下传空，可以由外部传入场景信息等，具体字段可参考
									// TMSelfUpdateConst. BUNDLE_KEY_* 的定义
			selfUpdateManager.init(context, channelid, selfupdateListener, yybDownloadListener, bundle);

		} catch (Exception e) {
			e.printStackTrace();
		}
		selfUpdateManager.checkSelfUpdate();
		selfUpdateManager.startSelfUpdate(false);
	}
	
	/**
	 * 小米自动更新
	 * @param context
	 * @param debug true沙盒  false 正式
	 */
	public void initXiaomi(Context context, boolean debug) {
		XiaomiUpdateAgent.setCheckUpdateOnlyWifi(true);
		
		XiaomiUpdateAgent.setUpdateAutoPopup(false);
		XiaomiUpdateAgent.setUpdateListener(new XiaomiUpdateListener() {

		    @Override
		    public void onUpdateReturned(int updateStatus, UpdateResponse updateInfo) {
		        switch (updateStatus) {
		            case UpdateStatus.STATUS_UPDATE:
		                 // 有更新， UpdateResponse为本次更新的详细信息
		                 // 其中包含更新信息，下载地址，MD5校验信息等，可自行处理下载安装
		                 // 如果希望 SDK继续接管下载安装事宜，可调用
		            	 updateCallback.onBeforeUpdate();
		                 XiaomiUpdateAgent.arrange();
		                 break;
		             case UpdateStatus.STATUS_NO_UPDATE:
		                // 无更新， UpdateResponse为null
		            	 updateCallback.onUpdated();
		                break;
		            case UpdateStatus.STATUS_NO_WIFI:
		                // 设置了只在WiFi下更新，且WiFi不可用时， UpdateResponse为null
		            	updateCallback.onUpdated();
		                break;
		            case UpdateStatus.STATUS_NO_NET:
		                // 没有网络， UpdateResponse为null
		            	updateCallback.onUpdated();
		                break;
		            case UpdateStatus.STATUS_FAILED:
		                // 检查更新与服务器通讯失败，可稍后再试， UpdateResponse为null
		            	updateCallback.onUpdated();
		                break;
		            case UpdateStatus.STATUS_LOCAL_APP_FAILED:
		                // 检查更新获取本地安装应用信息失败， UpdateResponse为null
		                break;
		            default:
		                break;
		        }
		    }
		});
		
		XiaomiUpdateAgent.update(context, debug);
	}

	/**
	 * 百度自动更新
	 * @param context
	 */
	public void initBaidu(Context context) {
		
//		BDAutoUpdateSDK.uiUpdateAction(context, new UICheckUpdateCallback() {
//
//			@Override
//			public void onCheckComplete() {
//				updateCallback.onUpdated();
//			}
//			
//		});
	}

}
