package com.nangua.xiaomanjflc.ui;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.ips.p2p.StartPluginTools;
import com.louding.frame.KJActivity;
import com.louding.frame.KJHttp;
import com.louding.frame.http.HttpCallBack;
import com.louding.frame.http.HttpParams;
import com.louding.frame.ui.BindView;
import com.louding.frame.utils.StringUtils;
import com.nangua.xiaomanjflc.AppConstants;
import com.nangua.xiaomanjflc.AppVariables;
import com.nangua.xiaomanjflc.utils.FormatUtils;
import com.nangua.xiaomanjflc.widget.FontTextView;
import com.nangua.xiaomanjflc.widget.LoudingDialog;
import com.nangua.xiaomanjflc.R;
import com.nangua.xiaomanjflc.support.UIHelper;

public class CashActivity extends KJActivity {

	@BindView(id = R.id.cash, click = true)
	private FontTextView mCash;
	@BindView(id = R.id.price)
	private EditText mPrice;
	@BindView(id = R.id.balance)
	private FontTextView mBalance;

	private KJHttp kjh;
	private LoudingDialog ld;

	private int balance;
	private String price;
	private String uri;
	
	private static final String LOGTAG = "CashActivity";

	@Override
	public void setRootView() {
		setContentView(R.layout.activity_cash);
		UIHelper.setTitleView(this, "我的账户", "提现");
	}

	@Override
	public void initData() {
		super.initData();
		kjh = new KJHttp();
		Intent intent = getIntent();
		balance = intent.getIntExtra("balance", 0);
	}

	@Override
	public void initWidget() {
		super.initWidget();
		mBalance.setText(FormatUtils.fmtMicrometer(FormatUtils.priceFormat(balance)) + "元");
		mPrice.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (s.toString().contains(".")) {
					if (s.length() - 1 - s.toString().indexOf(".") > 2) {
						s = s.toString().subSequence(0,
								s.toString().indexOf(".") + 3);
						mPrice.setText(s);
						mPrice.setSelection(s.length());
					}
				}
				if (s.toString().trim().equals(".")) {
					s = "0" + s;
					mPrice.setText(s);
					mPrice.setSelection(2);
				}

				if (s.toString().startsWith("0")
						&& s.toString().trim().length() > 1) {
					if (!s.toString().substring(1, 2).equals(".")) {
						mPrice.setText(s.subSequence(0, 1));
						mPrice.setSelection(1);
						return;
					}
				}
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});
	}

	@Override
	public void widgetClick(View v) {
		super.widgetClick(v);
		switch (v.getId()) {
		case R.id.cash:
			price = mPrice.getText().toString();
			if (StringUtils.isEmpty(price)) {
				ld = new LoudingDialog(CashActivity.this);
				ld.showConfirmHint("请输入金额。");
			} else if ((Double.parseDouble(price)) > balance) {
				ld = new LoudingDialog(CashActivity.this);
				ld.showConfirmHint("余额不足。");
			} else if (Double.parseDouble(price) < 1) {
				ld = new LoudingDialog(CashActivity.this);
				ld.showConfirmHint("提现金额需大于等于1元。");
			} else {
				getFee();
			}
			break;
		}
	}

	private void getFee() {
		HttpParams params = new HttpParams();
		params.put("sid", AppVariables.sid);
		params.put("amount", price);
		kjh.post(AppConstants.FEE, params, new HttpCallBack(CashActivity.this) {
			@Override
			public void success(JSONObject ret) {
				super.success(ret);
				try {
					JSONObject body = ret.getJSONObject("body");
					String actualAmount = body.getString("actualAmount");
					String fee = body.getString("fee");
					final LoudingDialog ld = new LoudingDialog(
							CashActivity.this);
					ld.showOperateMessage("实际提现金额：" + actualAmount + "元\n手续费："
							+ fee + "元\n预计到账：一到两个工作日");
					ld.setTitle("提现信息确认", R.color.dialog_orange);
					ld.setPositiveButton("确定", R.drawable.dialog_positive_btn,
							new OnClickListener() {
								@Override
								public void onClick(View arg0) {
									post();
									ld.dismiss();
								}
							});
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	/**
	 * 开启环迅插件支付
	 * @param ret server返回的支付信息json
	 */
	private void cashAction(JSONObject ret) {
		try {
			Bundle bundle = new Bundle();
			bundle.putString("operationType", ret.getString("operationType"));
			bundle.putString("merchantID", ret.getString("merchantID"));
			bundle.putString("sign", ret.getString("sign"));
			bundle.putString("request", ret.getString("request"));
			StartPluginTools.start_p2p_plugin(StartPluginTools.WITHDRAWAL, CashActivity.this, bundle, 1);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private void post() {
		HttpParams params = new HttpParams();
		params.put("sid", AppVariables.sid);
		params.put("amount", price);
		kjh.post(AppConstants.CASH, params,
				new HttpCallBack(CashActivity.this) {
					@Override
					public void success(JSONObject ret) {
						try {
							if ("ips".equals(ret.getString("pay.provider"))) {
								uri = ret.getString("uri");
								cashAction(ret);
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				});
	}
	
	// 当插件调用完毕后返回时执行该方法
    protected void onNewIntent(Intent intent) {
    	Bundle bundle = intent.getExtras();
    	AppVariables.forceUpdate = true;
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
            
            bundle.getString("response");
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
