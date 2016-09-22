package com.nangua.xiaomanjflc.ui;

import m.framework.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ips.commons.security.DES;
import com.ips.p2p.StartPluginTools;
import com.louding.frame.KJActivity;
import com.louding.frame.KJHttp;
import com.louding.frame.http.HttpCallBack;
import com.louding.frame.http.HttpParams;
import com.louding.frame.ui.BindView;
import com.louding.frame.utils.KJLoger;
import com.louding.frame.utils.StringUtils;
import com.nangua.xiaomanjflc.AppConstants;
import com.nangua.xiaomanjflc.AppVariables;
import com.nangua.xiaomanjflc.bean.DetailProduct;
import com.nangua.xiaomanjflc.bean.jsonbean.Account;
import com.nangua.xiaomanjflc.bean.jsonbean.User;
import com.nangua.xiaomanjflc.cache.CacheBean;
import com.nangua.xiaomanjflc.support.InfoManager;
import com.nangua.xiaomanjflc.support.UIHelper;
import com.nangua.xiaomanjflc.support.InfoManager.TaskCallBack;
import com.nangua.xiaomanjflc.utils.FormatUtils;
import com.nangua.xiaomanjflc.widget.CircleProgressBar;
import com.nangua.xiaomanjflc.widget.FontTextView;
import com.nangua.xiaomanjflc.widget.LoudingDialog;
import com.nangua.xiaomanjflc.R;

public class TenderActivity extends KJActivity {

	// 产品详情
	@BindView(id = R.id.name)
	private FontTextView name;
	@BindView(id = R.id.totalInvestment)
	private FontTextView totalInvestment;
	@BindView(id = R.id.totalInvestmentunit)
	private FontTextView totalInvestmentunit;
	@BindView(id = R.id.investmentPeriodDesc)
	private FontTextView investmentPeriodDesc;
	@BindView(id = R.id.investmentPeriodDescunit)
	private FontTextView investmentPeriodDescunit;
	@BindView(id = R.id.annualizedGain)
	private FontTextView annualizedGain;
	@BindView(id = R.id.guaranteeModeName)
	private FontTextView guaranteeModeName;
	@BindView(id = R.id.repaymentMethodName)
	private FontTextView repaymentMethodName;
	@BindView(id = R.id.expirationDate)
	private FontTextView expirationDate;
	@BindView(id = R.id.interestBeginDate)
	private FontTextView interestBeginDate;
	@BindView(id = R.id.remainingInvestmentAmount)
	private FontTextView remainingInvestmentAmount;
	@BindView(id = R.id.singlePurchaseLowerLimit)
	private FontTextView singlePurchaseLowerLimit;
	@BindView(id = R.id.percentagetxt)
	private FontTextView percentagetxt;
	@BindView(id = R.id.percentage)
	private FontTextView percentage;
	@BindView(id = R.id.percentagepb)
	private CircleProgressBar percentagepb;

	@BindView(id = R.id.tender_cash, click = true)
	private FontTextView tender_cash;
	@BindView(id = R.id.price)
	private EditText mPrice;
	@BindView(id = R.id.cash, click = true)
	private FontTextView cash;
	@BindView(id = R.id.cash_state, click = true)
	private FontTextView cash_state;
	@BindView(id = R.id.buy, click = true)
	private FontTextView buy;
	@BindView(id = R.id.protocol, click = true)
	private FontTextView protocol;
	@BindView(id = R.id.cash_use, click = true)
	private FontTextView cash_use;
	@BindView(id = R.id.available)
	private FontTextView mAvaliable;
	@BindView(id = R.id.cash_discount)
	private FontTextView cash_discount;
	@BindView(id = R.id.pay)
	private FontTextView pay;
	@BindView(id = R.id.checkbox, click = true)
	private ImageView mCheckbox;

	@BindView(id = R.id.add_v)
	private LinearLayout add_v;
	@BindView(id = R.id.add)
	private FontTextView add;

	private KJHttp http;
	private HttpParams params;
	private DetailProduct product;
	private int id;
	private int mul;
	private int max;
	private int min;
	private int available = 0;
	private int price;
	private int cashid;
	private int cash_price;
	private int cash_count;
	private int cash_sum;
	private String url;
	private String agreement;// 我同意内容
	private String products_type;// 产品类型 01:融资产品,02:债权产品
	private boolean checkbox = true;

	private int idValidated;
	private int status;
	private boolean isCharge;

	private String tenderAward;

	private static final String LOGTAG = "TenderActivity";

	@Override
	public void setRootView() {
		setContentView(R.layout.activity_tender);
		UIHelper.setTitleView(this, "产品详情", "投标");
	}

	@Override
	protected void onResume() {
		super.onResume();
		idValidated = 0;
	}

	@Override
	public void initData() {
		super.initData();
		Intent intent = getIntent();
		tenderAward = intent.getStringExtra("tenderAward");// 加息
		id = intent.getIntExtra("id", 0);
		price = intent.getIntExtra("price", 0);
		http = new KJHttp();
		params = new HttpParams();
		getData();
		mPrice.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
			}

			/**
			 * 限制输入长度
			 */
			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
				Editable editable = mPrice.getText();
				// 输入长度
				int len = editable.length();
				// 可用金钱长度
				int maxLen = (available / 100 + "").length();
				// 输入字符
				String str = editable.toString();
				if (Utils.isNullOrEmpty(str)) {
					pay.setText((0L - (long) cash_price) + "元");
					cash_discount.setText(cash_price + "元");
					return;
				}
				if (str.startsWith("0")) {
					LoudingDialog ld = new LoudingDialog(TenderActivity.this);
					ld.showConfirmHint("请输入至少100元");
					mPrice.setText("");
					return;
				}
				// 截取新字符串
				long p = 0L;
				p = Long.parseLong(str);
				if (p > available / 100) {
					str = str.substring(0, Math.max(len, maxLen) - 1);
					LoudingDialog ld = new LoudingDialog(TenderActivity.this);
					ld.showConfirmHint("抱歉，您的账户可用余额不足，请充值后再进行投资");
					mPrice.setText(str);
				}
				pay.setText((Utils.isNullOrEmpty(str) ? 0L : (Long.parseLong(str) - (long) cash_price)) + "元");
				cash_discount.setText(cash_price + "元");
				editable = mPrice.getText();

				// 新字符串的长度
				int newLen = editable.length();
				// 设置新光标所在的位置
				Selection.setSelection(editable, newLen);
			}

			@Override
			public void afterTextChanged(Editable editable) {
			}
		});
	}

	private void getData() {
		params.put("sid", AppVariables.sid);
		params.put("id", id);
		http.post(AppConstants.DETAIL_PRODUCT + id, params, httpCallback);
	}

	private HttpCallBack httpCallback = new HttpCallBack(TenderActivity.this) {
		public void success(org.json.JSONObject ret) {
			try {
				product = new DetailProduct(ret);
				cash_count = ret.getInt("cashCount");
				cash_sum = ret.getInt("cashSum");
				JSONObject p = ret.getJSONObject("product");
				agreement = p.getString("agreement");
				products_type = p.getString("products_type");
				mul = (p.getInt("baseLimitAmount")) / 100;
				max = Integer.parseInt(p.getString("remainingInvestmentAmount")) / 100;
				min = Integer.parseInt(p.getString("singlePurchaseLowerLimit")) / 100;
				initView();
				getAvailable();
			} catch (Exception e) {
				e.printStackTrace();
				Toast.makeText(TenderActivity.this, R.string.app_data_error, Toast.LENGTH_SHORT).show();
			}
		}
	};

	private void initView() {
		protocol.setText(agreement);

		// 产品详情
		name.setText(product.getName());
		totalInvestment.setText(product.getTotalInvestment());
		totalInvestmentunit.setText(product.getTotalInvestmentunit());
		investmentPeriodDesc.setText(product.getInvestmentPeriodDesc());
		investmentPeriodDescunit.setText(product.getInvestmentPeriodDescunit());
		annualizedGain.setText(product.getAnnualizedGain() + "");
		if (!"".equals(tenderAward)) {
			if (null != add_v)
				add_v.setVisibility(View.VISIBLE);
			if (null != add)
				add.setText(tenderAward);
		}

		guaranteeModeName.setText(product.getGuaranteeModeName());
		repaymentMethodName.setText(product.getRepaymentMethodName());
		expirationDate.setText("剩余投资时间");
		interestBeginDate.setText(product.getExpirationDate());
		remainingInvestmentAmount.setText(product.getRemainingInvestmentAmount());
		singlePurchaseLowerLimit.setText(product.getSinglePurchaseLowerLimit());
		if (product.getInvestmentProgress() == 100) {
			percentage.setText("已满标");
			percentagetxt.setText("");
		} else {
			percentage.setText("");
			percentagetxt.setText(product.getInvestmentProgress() + "");
		}
		percentagepb.setProgress(product.getInvestmentProgress());
		mCheckbox.setImageResource(R.drawable.checkbox);

		mPrice.setHint("输入金额为" + mul + "的整数倍");

		if (cash_count > 0) {
			cash_state.setText("现金券使用");
			cash_use.setText("使用");
			cash_use.setTextColor(getResources().getColor(R.color.app_blue));
		} else {
			cash_state.setText("现金券使用（无可用）");
			cash_use.setText("使用");
			cash_use.setTextColor(getResources().getColor(R.color.app_bg));
		}
		mCheckbox.setImageResource(R.drawable.checkbox);
	}

	@Override
	public void widgetClick(View v) {
		super.widgetClick(v);
		switch (v.getId()) {
		case R.id.tender_cash:
			if (idValidated == 1 && status == 2) {
				Intent charge = new Intent(TenderActivity.this, ChargeActivity.class);
				charge.putExtra("balance", available);
				startActivity(charge);
			} else {
				isCharge = true;
				getInfo1();
			}
			break;
		case R.id.cash_use:
			if (cash_count < 1) {
				break;
			}
			String p = mPrice.getText().toString();
			if (StringUtils.isEmpty(p)) {
				LoudingDialog ld = new LoudingDialog(TenderActivity.this);
				ld.showConfirmHint("请输入金额。");
				break;
			}
			if (Integer.parseInt(p) < 100) {
				LoudingDialog ld = new LoudingDialog(TenderActivity.this);
				ld.showConfirmHint("只有大于100元的投资才能使用现金券。");
				break;
			}
			if (Integer.parseInt(p) % mul > 0) {
				LoudingDialog ld = new LoudingDialog(TenderActivity.this);
				ld.showConfirmHint("请输入" + mul + "的整数倍");
				break;
			}
			Intent intent = new Intent(TenderActivity.this, UseRedActivity.class);
			intent.putExtra("amount", Integer.parseInt(p));
			intent.putExtra("productid", id);
			startActivityForResult(intent, 1);
			break;
		case R.id.buy:
			if (!AppVariables.isSignin) {
				startActivity(new Intent(TenderActivity.this, SigninActivity.class));
				break;
			} else {
				getInfo();
			}
			break;
		case R.id.protocol:
			Intent protocol = new Intent(TenderActivity.this, TenderProtocolActivity.class);
			protocol.putExtra("pid", id);
			protocol.putExtra("products_type", products_type);
			String amt = mPrice.getText().toString();
			if (StringUtils.isEmpty(amt)) {
				final LoudingDialog ld = new LoudingDialog(TenderActivity.this);
				ld.showConfirmHint("请输入金额。");
				break;
			}
			protocol.putExtra("amt", Integer.parseInt(amt));
			startActivity(protocol);
			break;
		case R.id.checkbox:
			if (checkbox) {
				checkbox = false;
				mCheckbox.setImageResource(R.drawable.checkbox_none);
			} else {
				checkbox = true;
				mCheckbox.setImageResource(R.drawable.checkbox);
			}
			break;
		}
	}

	private void getInfo() {
		params.put("sid", AppVariables.sid);
		http.post(AppConstants.GAIN, params, new HttpCallBack(TenderActivity.this) {
			@Override
			public void onSuccess(String t) {
				KJLoger.debug(t);
				try {
					JSONObject ret = new JSONObject(t);
					available = ret.getInt("available");
					final LoudingDialog ld = new LoudingDialog(TenderActivity.this);
					String p = mPrice.getText().toString();
					if (!checkbox) {
						ld.showConfirmHint("请先同意相关协议。");
						return;
					}
					if (StringUtils.isEmpty(p)) {
						ld.showConfirmHint("请输入金额");
						return;
					}
					int price = Integer.parseInt(p);
					if (price > available) {
						ld.showConfirmHint("可用余额不足,请先充值。");
						return;
					} else if (price > max) {
						ld.showConfirmHint("输入的金额超过可投金额，请重新输入！");
						return;
					} else if (price < min) {
						ld.showConfirmHint("请大于最小投资金额");
						return;
					} else if ((price % mul) > 0) {
						ld.showConfirmHint("请输入" + mul + "的整数倍");
						return;
					}
					ld.dismiss();
					params.put("sid", AppVariables.sid);
					params.put("id", id);
					params.put("amount", price / mul);
					params.put("cash", cashid);
					http.post(AppConstants.BUY + id + "/order/pay", params, tenderCallback);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}

	;

	/**
	 * 购买回调
	 */
	private HttpCallBack tenderCallback = new HttpCallBack(TenderActivity.this) {
		@Override
		public void success(JSONObject ret) {
			try {
				url = ret.getString("url");
				String[] as = url.split("/");
				for (int i = 0; i < as.length; i++) {
					KJLoger.debug(i + "===>" + as[i]);
				}
				params = new HttpParams();
				params.put("sid", AppVariables.sid);
				params.put("id", as[2]);
				http.post(AppConstants.HOST + url, params, transCallback);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	};

	/**
	 * 开启环迅插件支付
	 * 
	 * @param ret server返回的支付信息json
	 */
	private void biddingAction(JSONObject ret) {
		try {
			Bundle bundle = new Bundle();
			bundle.putString("operationType", ret.getString("operationType"));
			bundle.putString("merchantID", ret.getString("merchantID"));
			bundle.putString("sign", ret.getString("sign"));
			bundle.putString("request", ret.getString("request"));
			StartPluginTools.start_p2p_plugin(StartPluginTools.BIDDING, TenderActivity.this, bundle, 1);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private HttpCallBack transCallback = new HttpCallBack(TenderActivity.this) {
		@Override
		public void success(JSONObject ret) {
			try {
				if ("ips".equals(ret.getString("pay.provider"))) {
					biddingAction(ret);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	};

	private void getAvailable() {
		params.put("sid", AppVariables.sid);
		http.post(AppConstants.GAIN, params, new HttpCallBack(TenderActivity.this) {
			@Override
			public void onSuccess(String t) {
				try {
					JSONObject ret = new JSONObject(t);
					available = ret.getInt("available");
					mAvaliable.setText(FormatUtils.fmtMicrometer(available / 100 + "") + "." + available % 100 / 10
							+ available % 10 + "元");
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void getInfo1() {

		InfoManager.getInstance().getInfo(this, new TaskCallBack() {

			@Override
			public void taskSuccess() {
				User user = CacheBean.getInstance().getUser();

				idValidated = user.getIdValidated();
				if (idValidated == 1) {
					if (isCharge) {
						Intent charge = new Intent(TenderActivity.this, ChargeActivity.class);
						charge.putExtra("balance", available);
						startActivity(charge);
					} else {
						Account account = CacheBean.getInstance().getAccount();
						status = account.getCardStatus();
						if (status == 0) {
							final LoudingDialog ld = new LoudingDialog(TenderActivity.this);
							ld.showConfirmHint("您还没有绑卡，请到电脑上绑定银行卡。");
						} else if (status == 1) {
							final LoudingDialog ld = new LoudingDialog(TenderActivity.this);
							ld.showConfirmHint("您的银行卡正在审核中，审核结果将通过短信通知您。");
						} else if (status == 2) {
							Intent cash = new Intent(TenderActivity.this, CashActivity.class);
							cash.putExtra("balance", available);
							startActivity(cash);
						}
					}
				} else {
					final LoudingDialog ld = new LoudingDialog(TenderActivity.this);
					ld.showOperateMessage("请先实名认证。");
					ld.setPositiveButton("前往", R.drawable.dialog_positive_btn, new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							startActivity(new Intent(TenderActivity.this, AccountActivity.class));
							ld.dismiss();
						}
					});
				}
			}

			@Override
			public void taskFail(String err, int type) {
			}

			@Override
			public void afterTask() {
			}
		});

	}

	// 当插件调用完毕后返回时执行该方法
	protected void onNewIntent(Intent intent) {
		AppVariables.forceUpdate = true;
		Bundle bundle = intent.getExtras();
		if (bundle != null) {
			// 更新数据
			getData();

			printExtras(bundle);
			String resultCode = bundle.getString("resultCode");
			String resultMsg = bundle.getString("resultMsg");
			String merchantID = bundle.getString("merchantID");
			String sign = bundle.getString("sign");

			Log.e(LOGTAG, "resultCode" + ":" + resultCode);
			Log.e(LOGTAG, "resultMsg" + ":" + resultMsg);
			Log.e(LOGTAG, "merchantID" + ":" + merchantID);
			Log.e(LOGTAG, "sign" + ":" + sign);

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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 1 && resultCode == 1) {
			if (data != null) {
				cashid = data.getIntExtra("cash", 0);
				cash_price = data.getIntExtra("price", 0);
			} else {
				cashid = 0;
				cash_price = 0;
			}
			if (cashid == 0) {
				cash_discount.setText("0元");
				int p = 0;
				String sp = mPrice.getText().toString();
				if (!Utils.isNullOrEmpty(sp)) {
					p = Integer.parseInt(sp);
				}
				pay.setText(p + "元");
			} else {
				cash_discount.setText(cash_price + "元");
				int p = 0;
				String sp = mPrice.getText().toString();
				if (!Utils.isNullOrEmpty(sp)) {
					p = Integer.parseInt(sp);
				}
				pay.setText((p - cash_price) + "元");
			}
		}
	}
}
