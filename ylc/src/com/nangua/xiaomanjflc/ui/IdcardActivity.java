package com.nangua.xiaomanjflc.ui;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ips.p2p.StartPluginTools;
import com.louding.frame.KJActivity;
import com.louding.frame.KJHttp;
import com.louding.frame.http.HttpCallBack;
import com.louding.frame.http.HttpParams;
import com.louding.frame.ui.BindView;
import com.louding.frame.utils.StringUtils;
import com.nangua.xiaomanjflc.AppConstants;
import com.nangua.xiaomanjflc.AppVariables;
import com.nangua.xiaomanjflc.widget.LoudingDialog;
import com.nangua.xiaomanjflc.R;
import com.nangua.xiaomanjflc.support.UIHelper;

public class IdcardActivity extends KJActivity {

	private static final String LOGTAG = "IdcardActivity";
	
	@BindView(id = R.id.post, click = true)
	private TextView mPost;
	@BindView(id = R.id.name)
	private EditText mName;
	@BindView(id = R.id.id)
	private EditText mId;

	private KJHttp kjh;
	private LoudingDialog ld;

	private String idcard;
	private String realName;

	@Override
	public void setRootView() {
		setContentView(R.layout.activity_idcard);
		UIHelper.setTitleView(this, "账户中心", "实名认证");
		kjh = new KJHttp();
	}

	@Override
	public void widgetClick(View v) {
		super.widgetClick(v);
		switch (v.getId()) {
		case R.id.post:
			realName = mName.getText().toString();
			idcard = mId.getText().toString();
			if (StringUtils.isEmpty(realName) || StringUtils.isEmpty(idcard)) {
				ld = new LoudingDialog(IdcardActivity.this);
				ld.showConfirmHint("请填写完整信息");
			} else {
				post();
			}
			break;
		}
	}
	
	
	/**
	 * 开启环迅插件 开户
	 * @param server返回的支付信息json
	 */
	private void createAccountAction(JSONObject ret) {
		try {
			Bundle bundle = new Bundle();
			bundle.putString("operationType", ret.getString("operationType"));
			bundle.putString("merchantID", ret.getString("merchantID"));
			bundle.putString("sign", ret.getString("sign"));
			bundle.putString("request", ret.getString("request"));
			StartPluginTools.start_p2p_plugin(StartPluginTools.CREATE_ACCT, IdcardActivity.this, bundle, 1);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private void post() {
		HttpParams params = new HttpParams();
		params.put("sid", AppVariables.sid);
		params.put("idCard", idcard);
		params.put("realName", realName);
		kjh.post(AppConstants.IDCARD, params, new HttpCallBack(
				IdcardActivity.this) {
			@Override
			public void success(JSONObject ret) {
				try {
					if ("ips".equals(ret.getString("pay.provider"))) {
						createAccountAction(ret);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	// 当插件调用完毕后返回时执行该方法
    protected void onNewIntent(Intent intent) {
    	AppVariables.forceUpdate = true;
    	Bundle bundle = intent.getExtras();
    	if (bundle != null) {
    		printExtras(bundle);
    		String resultCode= bundle.getString("resultCode");
            String resultMsg= bundle.getString("resultMsg");
            String merchantID= bundle.getString("merchantID");
            String sign= bundle.getString("sign");
            
            Log.e(LOGTAG, "resultCode"+":"+resultCode);
			Log.e(LOGTAG, "resultMsg"+":"+resultMsg);
			Log.e(LOGTAG, "merchantID"+":"+merchantID);
			Log.e(LOGTAG, "sign"+":"+sign);
    	}
    	finish();
    }
	
    protected void printExtras(Bundle extras) {
        if (extras != null) {
            Log.e(LOGTAG, "╔*************************打印开始*************************╗");
            for (String key : extras.keySet()) {
                Log.i(LOGTAG, key + ": " + extras.get(key));
            }
            Log.e(LOGTAG, "╚═════════════════════════打印结束═════════════════════════╝");
        } else {
            Log.w(LOGTAG, "Extras is null");
        }
    }

}
