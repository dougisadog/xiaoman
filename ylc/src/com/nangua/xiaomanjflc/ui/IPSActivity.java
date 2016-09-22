package com.nangua.xiaomanjflc.ui;

import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.nangua.xiaomanjflc.AppVariables;
import com.nangua.xiaomanjflc.utils.ApplicationUtil;
import com.nangua.xiaomanjflc.R;
import com.nangua.xiaomanjflc.support.UIHelper;

public class IPSActivity extends Activity {

	private WebView webView;

	private String operationType;
	private String merchantID;
	private String sign;
	private String request;
	private String uri;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ips);
		UIHelper.setTitleView(this, "返回", "小满金服");
		Intent intent = getIntent();
		operationType = intent.getStringExtra("operationType");
		merchantID = intent.getStringExtra("merchantID");
		request = intent.getStringExtra("request");
		sign = intent.getStringExtra("sign");
		uri = intent.getStringExtra("uri");
		webView = (WebView) findViewById(R.id.webView);
		WebSettings webSettings = webView.getSettings();
		webSettings.setSaveFormData(false);
		webSettings.setJavaScriptEnabled(true);
		webSettings.setSupportZoom(false);

		webView.setWebViewClient(new MyClient());

		webView.loadUrl("file:///android_asset/IpsWebView.html");
	}

	final class MyClient extends WebViewClient {
		@Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);

			// String req =
			// "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><request platformNo=\"10040011137\"><feeMode>PLATFORM</feeMode><requestNo>1436614</requestNo><platformUserNo>1436574</platformUserNo><amount>1000</amount><callbackUrl>http://www.ylcdev.tk/pay/callback/mobile</callbackUrl><notifyUrl>http://www.ylcdev.tk/pay/notify</notifyUrl></request>";
			// String sign =
			// "MIIFLAYJKoZIhvcNAQcCoIIFHTCCBRkCAQExCzAJBgUrDgMCGgUAMC8GCSqGSIb3DQEHAaAiBCAxZGU2MTdkNjVmNWI5YWIyYzZlYmE1MTdkODI1NDQ5NKCCA+4wggPqMIIDU6ADAgECAhBS+IddC2ORUoU1ta7SVmOlMA0GCSqGSIb3DQEBBQUAMCoxCzAJBgNVBAYTAkNOMRswGQYDVQQKExJDRkNBIE9wZXJhdGlvbiBDQTIwHhcNMTQwOTA0MDY1MjE3WhcNMTcwOTA0MDY1MjE3WjCBhDELMAkGA1UEBhMCQ04xGzAZBgNVBAoTEkNGQ0EgT3BlcmF0aW9uIENBMjEWMBQGA1UECxMNcmEueWVlcGF5LmNvbTEUMBIGA1UECxMLRW50ZXJwcmlzZXMxKjAoBgNVBAMUITA0MUBaMTAwMTI0MTEzMTFAeWlsaWNhaUAwMDAwMDAwMTCBnzANBgkqhkiG9w0BAQEFAAOBjQAwgYkCgYEAoHd6RLOu/PKf+AQ4P69tJULfP3rHQ/I8S7UTiLv+y1Bxyc6JVm9uzB1yhgvoKBzvl4lokb1ta28N3r20BeVY0bN1LOsqyqhRbTt7WuTQ2LZ4+Bu8zdQ3Po7cnsL78Z3a2Q6fnsyw10n9NAogFjhVY6qTRcNP1RZkCuP4Sc36eqkCAwEAAaOCAbQwggGwMB8GA1UdIwQYMBaAFPCN7bNBu/vvCB5VAsMxN+88FE7NMB0GA1UdDgQWBBRVRWzmVmmqIYKP0Ob0z3GBPifuzDALBgNVHQ8EBAMCBPAwDAYDVR0TBAUwAwEBADA7BgNVHSUENDAyBggrBgEFBQcDAQYIKwYBBQUHAwIGCCsGAQUFBwMDBggrBgEFBQcDBAYIKwYBBQUHAwgwgf8GA1UdHwSB9zCB9DBXoFWgU6RRME8xCzAJBgNVBAYTAkNOMRswGQYDVQQKExJDRkNBIE9wZXJhdGlvbiBDQTIxDDAKBgNVBAsTA0NSTDEVMBMGA1UEAxMMY3JsMTA0XzExNzEzMIGYoIGVoIGShoGPbGRhcDovL2NlcnQ4NjMuY2ZjYS5jb20uY246Mzg5L0NOPWNybDEwNF8xMTcxMyxPVT1DUkwsTz1DRkNBIE9wZXJhdGlvbiBDQTIsQz1DTj9jZXJ0aWZpY2F0ZVJldm9jYXRpb25MaXN0P2Jhc2U/b2JqZWN0Y2xhc3M9Y1JMRGlzdHJpYnV0aW9uUG9pbnQwFAYDKlYBBA0TCzEwMDEyNDExMzExMA0GCSqGSIb3DQEBBQUAA4GBAD80zTkYKo3owOHm+qhCygNHXDtFrA2nfUJ1mdBEpo30FDLiYNCbdYnPb9vUoOurHgz9TMuhdIfiCNcoQY50Jc5nRCOyn7SdQ15EblXDAl6Hu+77wgfs8MX5LIXrmbehhKVVOE0lRvoS4xhqFLR+0dMLeEiBpykxQZWaJTjrbAtwMYHjMIHgAgEBMD4wKjELMAkGA1UEBhMCQ04xGzAZBgNVBAoTEkNGQ0EgT3BlcmF0aW9uIENBMgIQUviHXQtjkVKFNbWu0lZjpTAJBgUrDgMCGgUAMA0GCSqGSIb3DQEBAQUABIGABY9oye8fhtAD/0fGSpa14mbZe0y941alMuTc5dEEZQd+/mb9KeMVaiBOFOsYj+I4/DiiROTV6QO5+nj9pAgHGPO4m6lPOrng92RDaJnlF1dj9WnS5mbKHH+LA8YCRUjFxln3PwB3TrXHkRKf/tBTM1dsSl+R6S2jy+2K1bqgVxI=";
			// String uri =
			// "http://119.161.147.110:8088/member/bhawireless/toRecharge";
			webView.loadUrl("javascript:connectIps('" + operationType + "','" + merchantID + "','" +sign + "','" +request + "','" + uri + "')");
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (ApplicationUtil.isNeedGesture(this)) {
			startActivity(new Intent(this, GestureActivity.class));
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		AppVariables.lastTime = new Date().getTime();
	}

}
