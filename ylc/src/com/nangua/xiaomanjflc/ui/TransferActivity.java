package com.nangua.xiaomanjflc.ui;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.louding.frame.KJHttp;
import com.louding.frame.http.HttpCallBack;
import com.louding.frame.http.HttpParams;
import com.nangua.xiaomanjflc.AppConstants;
import com.nangua.xiaomanjflc.AppVariables;
import com.nangua.xiaomanjflc.utils.ToastUtil;
import com.nangua.xiaomanjflc.widget.FontTextView;
import com.nangua.xiaomanjflc.R;
import com.nangua.xiaomanjflc.support.UIHelper;

@SuppressLint("SimpleDateFormat")
public class TransferActivity extends Activity implements OnClickListener {

	private WindowManager wm;
	private int width;// 屏幕宽度
	private int height;// 屏幕高度

	// 手指按下的点为(x1, y1)手指离开屏幕的点为(x2, y2)
	private float x1 = 0;
	private float x2 = 0;
	private float y1 = 0;
	private float y2 = 0;

	private HttpParams params;
	private KJHttp http;
	private String url;

	private LinearLayout headLinear;
	private FontTextView tv_products_title;
	private FontTextView tv_lave_date;
	private FontTextView tv_finance_interest_rate;
	private FontTextView tv_off_shelf_time;
	private FontTextView tv_yield;
	private FontTextView tv_interest;
	private FontTextView tv_transfer_publish;
	private FontTextView tv_counter_fee;
	private FontTextView tv_transfer_price;
	private FontTextView tv_agreement;
	private FontTextView tv_prompt;

	private ImageView iv_problem1;
	private ImageView iv_problem2;
	private ImageView iv_problem3;
	private ImageView iv_problem4;
	private ImageView iv_problem5;
	private ImageView iv_problem6;

	private EditText et_transfer_capital;
	private EditText et_discount_amount;

	private String products_title;// 产品标题
	private String oid_platform_products_id;// 平台产品ID
	private String oid_tender_id;
	private String tender_from;// 转让来源（1：借款标；2：债权标）
	private String lave_date;// 剩余天数
	private String finance_interest_rate;// 预期收益率
	private String finance_start_interest_date;// 起息日
	private String tender_amount;// 项目本金

	private double interest = 0;// 利息金额

	private String transfer_capital;// 输入的金额
	private String discount_amount;// 输入的折让利息金额
	private boolean transfer_capitalFlag = false;
	private boolean discount_amountFlag = false;

	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_transfer);
		UIHelper.setTitleView(this, "", "申请转让");

		Bundle extras = getIntent().getExtras();
		products_title = extras.getString("products_title");
		oid_platform_products_id = extras.getString("oid_platform_products_id");
		oid_tender_id = extras.getString("oid_tender_id");
		tender_from = extras.getString("tender_from");
		lave_date = extras.getString("lave_date");
		finance_interest_rate = extras.getString("finance_interest_rate");
		finance_start_interest_date = extras
				.getString("finance_start_interest_date");
		tender_amount = extras.getString("tender_amount").replaceAll(",", "");

		init();

		tv_products_title.setText(products_title);
		tv_lave_date.setText(lave_date + "天");
		tv_finance_interest_rate.setText(finance_interest_rate + "%");
		tv_yield.setText(finance_interest_rate + "%");

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.HOUR_OF_DAY, 24);// 加24小时制
		tv_off_shelf_time.setText(format.format(cal.getTime()));

	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if (ev.getAction() == MotionEvent.ACTION_DOWN) {
			// 当手指按下的时候
			x1 = ev.getX();
			y1 = ev.getY();

			View v = getCurrentFocus();
			if (isClickView(v, ev)) {
				hideKeyboard(v.getWindowToken());
				tv_prompt.setVisibility(View.GONE);

				transfer_capital = et_transfer_capital.getText().toString();// 输入的金额
				discount_amount = et_discount_amount.getText().toString();// 输入的折让利息金额
				if ("".equals(transfer_capital)) {
					transfer_capital = "0";
				}
				if ("".equals(discount_amount)) {
					discount_amount = "0";
				}
				if (Double.parseDouble(tender_amount) < 10000
						&& Double.parseDouble(tender_amount) == Double
								.parseDouble(transfer_capital)) {
					try {
						SimpleDateFormat sdf = new SimpleDateFormat(
								"yyyy-MM-dd");
						Date dateStart = sdf.parse(finance_start_interest_date
								.toString());// 起息日期
						int datenum = daysBetween(dateStart, new Date());// 天数
						interest = getInterest(
								new BigDecimal(transfer_capital), datenum);// 预期所得利息
						tv_interest.setText(interest + "元");
						tv_counter_fee.setText(counterFee(datenum,
								Double.parseDouble(transfer_capital))
								+ "");
						transfer_capitalFlag = true;
					} catch (ParseException e) {
						e.printStackTrace();
					}
				} else if (Double.parseDouble(tender_amount) > 10000
						&& Double.parseDouble(transfer_capital) % 10000 == 0) {
					try {
						SimpleDateFormat sdf = new SimpleDateFormat(
								"yyyy-MM-dd");
						Date dateStart = sdf.parse(finance_start_interest_date
								.toString());// 起息日期
						int datenum = daysBetween(dateStart, new Date());// 天数
						interest = getInterest(
								new BigDecimal(transfer_capital), datenum);// 预期所得利息
						tv_interest.setText(interest + "元");
						tv_counter_fee.setText(counterFee(datenum,
								Double.parseDouble(transfer_capital))
								+ "");
						transfer_capitalFlag = true;
					} catch (ParseException e) {
						e.printStackTrace();
					}
				} else {
					int[] location = new int[2];
					iv_problem1.getLocationOnScreen(location);
					tv_prompt.setX(location[0] - 10);
					tv_prompt.setY(location[1] - headLinear.getMeasuredHeight()
							- 10);
					tv_prompt.setText("全部转让或转让本金为10000元的整数倍");
					tv_prompt.setWidth(width - location[0] - 10);
					tv_prompt.setVisibility(View.VISIBLE);
					transfer_capitalFlag = false;
				}

				if (Double.parseDouble(discount_amount) <= interest) {
					tv_transfer_price.setText(Double
							.parseDouble(transfer_capital)
							+ interest
							- Double.parseDouble(discount_amount) + "元");
					discount_amountFlag = true;
				} else {
					int[] location = new int[2];
					iv_problem3.getLocationOnScreen(location);
					tv_prompt.setX(location[0] - 10);
					tv_prompt.setY(location[1] - headLinear.getMeasuredHeight()
							- 10);
					tv_prompt.setText("折让利息金额不可超过预期所得利息");
					tv_prompt.setWidth(width - location[0] - 10);
					tv_prompt.setVisibility(View.VISIBLE);
					discount_amountFlag = false;
				}
			}
		}

		if (ev.getAction() == MotionEvent.ACTION_UP) {
			// 当手指离开的时候
			x2 = ev.getX();
			y2 = ev.getY();
			if (y1 - y2 > 20) {
				tv_prompt.setVisibility(View.GONE);
			} else if (y2 - y1 > 20) {
				tv_prompt.setVisibility(View.GONE);
			} else if (x1 - x2 > 20) {
				tv_prompt.setVisibility(View.GONE);
			} else if (x2 - x1 > 20) {
				tv_prompt.setVisibility(View.GONE);
			}
		}
		return super.dispatchTouchEvent(ev);
	}

	/**
	 * 手续费
	 * */
	private double counterFee(int datenum, double transferCapital) {
		if (datenum >= 0 && datenum < 30) {
			return transferCapital * 0.002;
		} else if (datenum >= 30 && datenum < 60) {
			return transferCapital * 0.002;
		} else if (datenum >= 60 && datenum < 90) {
			return transferCapital * 0.001;
		} else {
			return 0;
		}
	}

	/**
	 * 所得利息
	 * */
	private double getInterest(BigDecimal money, int dateNum) {
		BigDecimal interest = new BigDecimal(0);
		BigDecimal decimal = new BigDecimal(100);
		BigDecimal interestRate = new BigDecimal(finance_interest_rate).divide(
				decimal, 20, BigDecimal.ROUND_HALF_DOWN);// 利率
		double ratioDay = dateNum / 365.0;// 天数与一年的比值
		interest = money.multiply(interestRate).multiply(
				new BigDecimal(ratioDay));// 利息
		return interest.setScale(2, BigDecimal.ROUND_HALF_DOWN).doubleValue();
	}

	/**
	 * 计算两个日期之间相差的天数
	 */
	private int daysBetween(Date smdate, Date bdate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		smdate = sdf.parse(sdf.format(smdate));
		bdate = sdf.parse(sdf.format(bdate));
		Calendar cal = Calendar.getInstance();
		cal.setTime(smdate);
		long time1 = cal.getTimeInMillis();
		cal.setTime(bdate);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);
		return Integer.parseInt(String.valueOf(between_days));
	}

	/**
	 * 根据控件所在坐标和用户点击的坐标相对比，来判断是否点击控件
	 *
	 * @param v
	 * @param event
	 * @return
	 */
	private boolean isClickView(View v, MotionEvent event) {
		if (v != null && (v instanceof EditText)) {
			int[] l = { 0, 0 };
			v.getLocationInWindow(l);
			int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
					+ v.getWidth();
			if (event.getX() > left && event.getX() < right
					&& event.getY() > top && event.getY() < bottom) {
				// 点击EditText的事件，忽略它。
				return false;
			} else {
				return true;
			}
		}
		// 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
		return false;
	}

	/**
	 * 获取InputMethodManager，隐藏软键盘
	 * 
	 * @param token
	 */
	private void hideKeyboard(IBinder token) {
		if (token != null) {
			InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			im.hideSoftInputFromWindow(token,
					InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}

	private void init() {
		headLinear = (LinearLayout) findViewById(R.id.headLinear);
		tv_products_title = (FontTextView) findViewById(R.id.tv_products_title);
		tv_lave_date = (FontTextView) findViewById(R.id.tv_lave_date);
		tv_finance_interest_rate = (FontTextView) findViewById(R.id.tv_finance_interest_rate);
		tv_off_shelf_time = (FontTextView) findViewById(R.id.tv_off_shelf_time);
		tv_yield = (FontTextView) findViewById(R.id.tv_yield);
		tv_interest = (FontTextView) findViewById(R.id.tv_interest);
		tv_transfer_publish = (FontTextView) findViewById(R.id.tv_transfer_publish);
		tv_counter_fee = (FontTextView) findViewById(R.id.tv_counter_fee);
		tv_transfer_price = (FontTextView) findViewById(R.id.tv_transfer_price);
		tv_agreement = (FontTextView) findViewById(R.id.tv_agreement);
		tv_prompt = (FontTextView) findViewById(R.id.tv_prompt);

		et_transfer_capital = (EditText) findViewById(R.id.et_transfer_capital);
		et_discount_amount = (EditText) findViewById(R.id.et_discount_amount);
		iv_problem1 = (ImageView) findViewById(R.id.iv_problem1);
		iv_problem2 = (ImageView) findViewById(R.id.iv_problem2);
		iv_problem3 = (ImageView) findViewById(R.id.iv_problem3);
		iv_problem4 = (ImageView) findViewById(R.id.iv_problem4);
		iv_problem5 = (ImageView) findViewById(R.id.iv_problem5);
		iv_problem6 = (ImageView) findViewById(R.id.iv_problem6);

		tv_transfer_publish.setOnClickListener(this);
		iv_problem1.setOnClickListener(this);
		iv_problem2.setOnClickListener(this);
		iv_problem3.setOnClickListener(this);
		iv_problem4.setOnClickListener(this);
		iv_problem5.setOnClickListener(this);
		iv_problem6.setOnClickListener(this);
		tv_agreement.setOnClickListener(this);

		wm = this.getWindowManager();
		width = wm.getDefaultDisplay().getWidth();// 屏幕宽度
		height = wm.getDefaultDisplay().getHeight();// 屏幕高度
	}

	@Override
	public void onClick(View v) {
		int[] location = new int[2];
		switch (v.getId()) {
		case R.id.tv_transfer_publish:

			if (transfer_capitalFlag && discount_amountFlag) {
				getData();
			} else {
				if (!discount_amountFlag) {
					iv_problem3.getLocationOnScreen(location);
					tv_prompt.setX(location[0] - 10);
					tv_prompt.setY(location[1] - headLinear.getMeasuredHeight()
							- 10);
					tv_prompt.setText("折让利息金额不可超过预期所得利息");
					tv_prompt.setWidth(width - location[0] - 10);
					tv_prompt.setVisibility(View.VISIBLE);
				}
				if (!transfer_capitalFlag) {
					iv_problem1.getLocationOnScreen(location);
					tv_prompt.setX(location[0] - 10);
					tv_prompt.setY(location[1] - headLinear.getMeasuredHeight()
							- 10);
					tv_prompt.setText("全部转让或转让本金为10000元的整数倍");
					tv_prompt.setWidth(width - location[0] - 10);
					tv_prompt.setVisibility(View.VISIBLE);
				}
			}

			break;
		case R.id.iv_problem1:
			iv_problem1.getLocationOnScreen(location);
			tv_prompt.setX(location[0] - 10);
			tv_prompt.setY(location[1] - headLinear.getMeasuredHeight() - 10);
			tv_prompt.setText("全部转让或转让本金为10000元的整数倍");
			tv_prompt.setWidth(width - location[0] - 10);
			tv_prompt.setVisibility(View.VISIBLE);
			break;
		case R.id.iv_problem2:
			iv_problem2.getLocationOnScreen(location);
			tv_prompt.setX(location[0] - 10);
			tv_prompt.setY(location[1] - headLinear.getMeasuredHeight() - 10);
			tv_prompt.setText("从起息日至转让发布日当天，为计息天数，此处为默认全部转出所得利息，仅供参考");
			tv_prompt.setWidth(width - location[0] - 10);
			tv_prompt.setVisibility(View.VISIBLE);
			break;
		case R.id.iv_problem3:
			iv_problem3.getLocationOnScreen(location);
			tv_prompt.setX(location[0] - 10);
			tv_prompt.setY(location[1] - headLinear.getMeasuredHeight() - 10);
			tv_prompt.setText("折让利息金额不可超过预期所得利息");
			tv_prompt.setWidth(width - location[0] - 10);
			tv_prompt.setVisibility(View.VISIBLE);
			break;
		case R.id.iv_problem4:
			iv_problem4.getLocationOnScreen(location);
			tv_prompt.setX(location[0] - 10);
			tv_prompt.setY(location[1] - headLinear.getMeasuredHeight() - 10);
			tv_prompt.setText("根据平台运营标准，预计将收取转让本金0-0.3%的平台服务费，具体以实际产生费用为准");
			tv_prompt.setWidth(width - location[0] - 10);
			tv_prompt.setVisibility(View.VISIBLE);
			break;
		case R.id.iv_problem5:
			iv_problem5.getLocationOnScreen(location);
			tv_prompt.setX(location[0] - 10);
			tv_prompt.setY(location[1] - headLinear.getMeasuredHeight() - 10);
			tv_prompt.setText("转让本金+预期所得利息-折让利息金额");
			tv_prompt.setWidth(width - location[0] - 10);
			tv_prompt.setVisibility(View.VISIBLE);
			break;
		case R.id.iv_problem6:
			iv_problem6.getLocationOnScreen(location);
			tv_prompt.setX(location[0] - 10);
			tv_prompt.setY(location[1] - headLinear.getMeasuredHeight() - 10);
			tv_prompt.setText("债权转让筹款期限为24小时，筹款完成即转让成功，24小时仍未筹款完成即转让失败");
			tv_prompt.setWidth(width - location[0] - 10);
			tv_prompt.setVisibility(View.VISIBLE);
			break;

		case R.id.tv_agreement:
			ToastUtil.showToast(TransferActivity.this, "债权转让协议",
					Toast.LENGTH_LONG);
			break;
		}

	}

	private void getData() {
		params = new HttpParams();
		params.put("sid", AppVariables.sid);
		params.put("products_title", products_title);
		params.put("oid_platform_products_id", oid_platform_products_id);
		params.put("oid_tender_id", oid_tender_id);
		params.put("tender_from", tender_from);
		params.put("transfer_capital", transfer_capital);
		params.put("discount_amount", discount_amount);
		http = new KJHttp();
		url = AppConstants.TRANSFER_PUBLISH;
		http.post(url, params, httpCallback);
	}

	private HttpCallBack httpCallback = new HttpCallBack(TransferActivity.this) {

		@Override
		public void failure(JSONObject ret) {
			super.failure(ret);
		}

		@Override
		public void success(JSONObject ret) {
			super.success(ret);
			try {
				if ("1".equals(ret.getString("transfer_tag"))) {
					Intent intent = new Intent(TransferActivity.this,
							TransferSuccessActivity.class);
					startActivity(intent);
					TransferActivity.this.finish();
				} else if ("0".equals(ret.getString("transfer_tag"))) {
					ToastUtil.showToast(TransferActivity.this, "发布失败",
							Toast.LENGTH_LONG);
				}
			} catch (Exception e) {
				e.printStackTrace();
				Toast.makeText(TransferActivity.this, R.string.app_data_error,
						Toast.LENGTH_SHORT).show();
			}
		}

		public void onFinish() {
			super.onFinish();
		}
	};

}
