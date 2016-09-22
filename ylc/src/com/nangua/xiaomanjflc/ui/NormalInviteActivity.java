package com.nangua.xiaomanjflc.ui;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
//import cn.sharesdk.framework.ShareSDK;
//import cn.sharesdk.onekeyshare.OnekeyShare;

import com.louding.frame.KJActivity;
import com.louding.frame.KJHttp;
import com.louding.frame.http.HttpCallBack;
import com.louding.frame.http.HttpParams;
import com.louding.frame.ui.BindView;
import com.nangua.xiaomanjflc.AppConstants;
import com.nangua.xiaomanjflc.AppVariables;
import com.nangua.xiaomanjflc.utils.ApplicationUtil;
import com.nangua.xiaomanjflc.utils.FormatUtils;
import com.nangua.xiaomanjflc.widget.FontTextView;
import com.nangua.xiaomanjflc.R;
import com.nangua.xiaomanjflc.support.UIHelper;

public class NormalInviteActivity extends KJActivity {

	@BindView(id = R.id.count, click = true)
	private FontTextView mCount;
	@BindView(id = R.id.cashReturned, click = true)
	private FontTextView mCashReturned;
	@BindView(id = R.id.invite, click = true)
	private FontTextView mInvite;
	@BindView(id = R.id.scan, click = true)
	private RelativeLayout mScan;
//	@BindView(id = R.id.refCode, click = true)
//	private FontTextView mRefCode;

	private KJHttp http;
	private HttpParams params;

	private String invitationCount;
	private int cashReturned;
	private String refCode;
	private String shareUrl;
	
	private WebView webView;

	@Override
	public void setRootView() {
		setContentView(R.layout.activity_normal_invite);

		Intent intent = getIntent();
		if ("account".equals(intent.getStringExtra("activity"))) {
			UIHelper.setTitleView(this, "我的账户", "邀请好友");
		} else {
			UIHelper.setTitleView(this, "返回", "邀请好友");
		}
		
		
		webView = (WebView) findViewById(R.id.webView);
		WebSettings webSettings = webView.getSettings();
		webSettings.setSaveFormData(false);
		webSettings.setJavaScriptEnabled(true);
		webSettings.setSupportZoom(true);
		webSettings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		webSettings.setUseWideViewPort(true);

		webView.setWebViewClient(new MyClient());

	}
	
	final class MyClient extends WebViewClient {
		@Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);

//			int width = ApplicationUtil.getApkInfo(NormalInviteActivity.this).width;
//			String baseUrl = shareUrl;
//			webView.loadUrl("javascript:doPicCreate('" + baseUrl + "','" + refCode + "','" + width + "')");
		}
	}

	@Override
	public void widgetClick(View v) {
		super.widgetClick(v);
		switch (v.getId()) {
		case R.id.invite:
			showShare();
			break;
		case R.id.scan:
			showActivity(NormalInviteActivity.this, InviteListActivity.class);
			break;
		}
	}

	@Override
	public void initData() {
		super.initData();
		http = new KJHttp();
		params = new HttpParams();
		getInfo();
	}

	private void getInfo() {
		params.put("sid", AppVariables.sid);
		http.post(AppConstants.INVITE, params, new HttpCallBack(
				NormalInviteActivity.this) {
			@Override
			public void onSuccess(String t) {
				try {
					JSONObject ret = new JSONObject(t);
					//分享地址
					shareUrl = ret.getString("recommendationUrl");
					//邀请人数
					invitationCount = ret.getString("invitationCount");
					//返还现金
					cashReturned = ret.getInt("couponCashSum");
					//邀请码
					refCode = ret.getString("refCode");
					mCount.setText(invitationCount);
					mCashReturned.setText(FormatUtils.priceFormat(cashReturned));
//					mRefCode.setText(refCode);
					
					int width = ApplicationUtil.getApkInfo(NormalInviteActivity.this).width;
					String url = AppConstants.SPECIALHOST + "/useryaoqingweixin.html?refcode= " + refCode 
							+ "&baseUrl=" + shareUrl + "&width=" + width;
//					webView.loadUrl("file:///android_asset/UserYaoqingWeixin.html");
					webView.loadUrl(url);
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void showShare() {
//		ShareSDK.initSDK(this);
//		OnekeyShare oks = new OnekeyShare();
//		// 关闭sso授权
//		oks.disableSSOWhenAuthorize();
//
//		// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
//		oks.setTitle(getString(R.string.share));
//		// titleUrl是标题的网络链接，仅在人人网和QQ空间使用
//		oks.setTitleUrl("http://www.xiaomanjf.com");
//		// text是分享文本，所有平台都需要这个字段
//		oks.setText("亲,我在互联网金融平台小满金服投资啦，7%-15%年化收益率，本息担保。现在加入立得10元现金券，快猛戳链接领钱吧"
//				+ shareUrl);
//		// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//		// oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
//		// url仅在微信（包括好友和朋友圈）中使用
//		oks.setUrl("http://www.xiaomanjf.com");
//		// comment是我对这条分享的评论，仅在人人网和QQ空间使用
//		// oks.setComment("我是测试评论文本");
//		// site是分享此内容的网站名称，仅在QQ空间使用
//		oks.setSite(getString(R.string.app_name));
//		// siteUrl是分享此内容的网站地址，仅在QQ空间使用
//		oks.setSiteUrl("http://www.xiaomanjf.com");
//
//		// 启动分享GUI
//		oks.show(this);
	}

}
