package com.nangua.xiaomanjflc.cache;

import java.util.Date;

import com.nangua.xiaomanjflc.bean.jsonbean.Account;
import com.nangua.xiaomanjflc.bean.jsonbean.User;
import com.nangua.xiaomanjflc.support.ApkInfo;

import android.content.Context;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

public class CacheBean {
	
	private static CacheBean instance = null;
	
	public static CacheBean getInstance() {
		if (null == instance) {
			instance = new CacheBean();
		}
		return instance;
	}
	
	public void clear() {
		user = null;
		account = null;
	}
	
	//清空webcookie数据
	public static void syncCookie(Context context) {
	    CookieSyncManager.createInstance(context);  
	    CookieManager cookieManager = CookieManager.getInstance();  
	    cookieManager.setAcceptCookie(true);  
	    cookieManager.removeSessionCookie();//移除  
	    CookieSyncManager.getInstance().sync();  
	}
	
	private User user;
	
	private Account account;
	
	private ApkInfo apkInfo;
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		user.setLastModTime(new Date().getTime());
		this.user = user;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public ApkInfo getApkInfo() {
		return apkInfo;
	}

	public void setApkInfo(ApkInfo apkInfo) {
		this.apkInfo = apkInfo;
	}

}
