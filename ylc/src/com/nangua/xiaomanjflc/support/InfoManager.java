package com.nangua.xiaomanjflc.support;

import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.kymjs.kjframe.KJDB;

import com.louding.frame.KJHttp;
import com.louding.frame.http.HttpCallBack;
import com.louding.frame.http.HttpParams;
import com.nangua.xiaomanjflc.AppConfig;
import com.nangua.xiaomanjflc.AppConstants;
import com.nangua.xiaomanjflc.AppVariables;
import com.nangua.xiaomanjflc.bean.jsonbean.Account;
import com.nangua.xiaomanjflc.bean.jsonbean.User;
import com.nangua.xiaomanjflc.bean.jsonbean.UserConfig;
import com.nangua.xiaomanjflc.cache.CacheBean;
import com.nangua.xiaomanjflc.utils.ApplicationUtil;
import com.nangua.xiaomanjflc.utils.HttpHelper;

import android.content.Context;

/**
 * 处理登录  个人信息拉取
 * @author Doug
 *
 */
public class InfoManager {

	private static InfoManager instance = null;
	
	public static InfoManager getInstance() {
		if (null == instance) {
			instance = new InfoManager();
		}
		return instance;
	}
	
	public static interface TaskCallBack {
		
		public static int JSON = 0;
		public static int TXT = 1;
		
		public void taskSuccess();
		
		public void taskFail(String err, int type);
		
		public void afterTask();
		
	}
	
	/**
	 * 注册请求
	 * @param context
	 * @param account 账号
	 * @param pwd	密码
	 * @param taskCallBack 回调
	 */
	public void loginNormal(final Context context, final String account , final String pwd, 
			final TaskCallBack taskCallBack) {
		// 注册成功后登录
			KJHttp kjh = new KJHttp();
			HttpParams params = new HttpParams();
			params.put("sid", "");
			params.put("account", account);
			params.put("passwd", pwd);
			params.put("captcha", "");
			String verionName = ApplicationUtil.getApkInfo(context).versionName;
			params.put("loginVersionName", "Android" + verionName);
			kjh.post(AppConstants.SIGNIN, params, new HttpCallBack(context) {
				@Override
				public void success(JSONObject ret) {
					super.success(ret);
					try {
						JSONObject o = ret.getJSONObject("body");
						
						//基础参数更新
						String sid = o.getString("sid");
						int uid = o.getInt("uid");
						AppConfig.getAppConfig(context).set("sid", sid);
						AppConfig.getAppConfig(context).set("tel", account);
						AppConfig.getAppConfig(context).set("uid", Integer.toString(uid));
						AppVariables.uid = uid;
						AppVariables.sid = sid;
						AppVariables.tel = account;
						AppVariables.isSignin = true;
						
						//账号数据本地数据库存储
						KJDB kjdb = KJDB.create(context);
						List<UserConfig> userConfigs = kjdb.findAllByWhere(UserConfig.class, "uid=" + AppVariables.uid);
						UserConfig userConfig = null;
						if (userConfigs.size() > 0) {
							userConfig = userConfigs.get(0);
							userConfig.setLastGestureCheckTime(new Date().getTime());
							kjdb.update(userConfig);
							AppVariables.needGesture = userConfig.isNeedGesture();
						}
						if (userConfig == null) {
							userConfig = new UserConfig();
							userConfig.setUid(uid);
							userConfig.setNeedGesture(false);
							userConfig.setLastGestureCheckTime(new Date().getTime());
							kjdb.save(userConfig);
						}
						
						//清空webview的cookie
						CacheBean.syncCookie(context);
						AppVariables.forceUpdate = true;
						taskCallBack.taskSuccess();
//						getInfo(context, taskCallBack);
					} catch (JSONException e) {
						this.onFinish();
						taskCallBack.taskFail(e.toString(), TaskCallBack.TXT);
						e.printStackTrace();
					}
				}

				@Override
				public void onFinish() {
					taskCallBack.afterTask();
					super.onFinish();
				}

				@Override
				public void failure(JSONObject ret) {
					taskCallBack.taskFail(ret.toString(), TaskCallBack.JSON);
					super.failure(ret);
				}
				
			});
		}
	
	/**
	 * 及时拉取服务器基本信息
	 */
	
	public void getInfo(Context context, final TaskCallBack taskCallBack) {
		getInfo(context, taskCallBack, true);
	}
	
	
	/**
	 * 个人信息请求
	 * @param context
	 * @param taskCallBack 回调
	 * @param needLoading  显示loading
	 */
	public void getInfo(Context context, final TaskCallBack taskCallBack, boolean needLoading) // 实名认证、绑定银行卡
	{
		if (!AppVariables.isSignin) return;
		HttpParams params = new HttpParams();
		params.put("sid", AppVariables.sid);
		KJHttp kjh = new KJHttp();
		kjh.post(AppConstants.BASICINFO, params, new HttpCallBack(context, needLoading) {
			
			@Override
			public void success(JSONObject ret) {
				super.success(ret);
				try {
					JSONObject user = ret.getJSONObject("user");
					JSONObject account = ret.getJSONObject("account");
					
					//储存在缓存中
					User u = HttpHelper.jsonParse(user.toString(), User.class);
					CacheBean.getInstance().setUser(u);
					Account a = HttpHelper.jsonParse(account.toString(), Account.class);
					CacheBean.getInstance().setAccount(a);
					taskCallBack.taskSuccess();
				} catch (JSONException e) {
					taskCallBack.taskFail(e.toString(), TaskCallBack.TXT);
					e.printStackTrace();
				}
			}

			@Override
			public void failure(JSONObject ret) {
				taskCallBack.taskFail(ret.toString(), TaskCallBack.JSON);
				super.failure(ret);
			}

			@Override
			public void onFinish() {
				taskCallBack.afterTask();
				super.onFinish();
			}
			
			
		});
	}
	
	/**
	 * 清空用户数据 和缓存
	 * @param context
	 */
	public void clearinfo(Context context) {
		AppConfig.getAppConfig(context).set("sid", "");
		AppConfig.getAppConfig(context).set("tel", "");
		AppConfig.getAppConfig(context).set("uid", "");
		AppConfig.getAppConfig(context).set("gesturetel", "");
		AppConfig.getAppConfig(context).set("gesture", "");
		AppVariables.clear();
	}
}
