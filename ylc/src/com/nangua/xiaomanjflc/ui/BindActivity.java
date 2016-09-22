package com.nangua.xiaomanjflc.ui;

import java.util.Date;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.louding.frame.KJActivity;
import com.nangua.xiaomanjflc.AppVariables;
import com.nangua.xiaomanjflc.R;
import com.nangua.xiaomanjflc.support.UIHelper;

//关于一理财页面
public class BindActivity extends KJActivity {
	private WebView webView;
	
	private String userName;
	private String requestUrl;
	private String merchantId;

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.activity_bind);
		UIHelper.setTitleView(this, "返回", "环迅管理");
		
		
		userName = getIntent().getStringExtra("userName");
		requestUrl = getIntent().getStringExtra("url");
		merchantId = getIntent().getStringExtra("merchantId");
		
		
		webView = (WebView) findViewById(R.id.bindWeb);
		WebSettings ws = webView.getSettings();
		ws.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
		// 设置支持缩放
		ws.setSupportZoom(true);
		ws.setUseWideViewPort(true);
		ws.setJavaScriptEnabled(true);
		ws.setLoadWithOverviewMode(true);
		webView.setWebViewClient(new MyClient());
		webView.loadUrl("file:///android_asset/IpsWebView.html");
	}

	final class MyClient extends WebViewClient {
		@Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
			webView.loadUrl("javascript:connectIps('" + userName + "','" + merchantId + "','" + requestUrl + "')");
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		AppVariables.lastTime = new Date().getTime();
	}

	@Override
	public void setRootView() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onDestroy() {
		AppVariables.forceUpdate = true;
		super.onDestroy();
	}
	
}
