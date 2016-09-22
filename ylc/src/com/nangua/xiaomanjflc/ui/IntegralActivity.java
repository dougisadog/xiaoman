package com.nangua.xiaomanjflc.ui;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

import com.louding.frame.KJHttp;
import com.louding.frame.http.HttpCallBack;
import com.louding.frame.http.HttpParams;
import com.nangua.xiaomanjflc.AppConstants;
import com.nangua.xiaomanjflc.AppVariables;
import com.nangua.xiaomanjflc.R;
import com.nangua.xiaomanjflc.support.UIHelper;
import com.nangua.xiaomanjflc.widget.FontTextView;

/**
 * 我的积分
 * */
public class IntegralActivity extends Activity implements OnClickListener {

	private RelativeLayout rl_integral_detail;// 积分明细
	private RelativeLayout rl_integral_income;// 积分收入
	private RelativeLayout rl_integral_expenditure;// 积分支出
	private RelativeLayout rl_integral_expired;// 已过期
	private RelativeLayout rl_integral_mall;// 积分商城
	private FontTextView tv_integral_rule;// 积分使用规则
	private FontTextView tv_usable_point_m;// 可用积分
	private FontTextView tv_froze_usable;// 总积分
	private FontTextView tv_usable_point_m_over;// 即将过期积分

	private String usable_point_m;// 可用积分
	private String froze_usable;// 总积分
	private String usable_point_m_over;// 即将过期积分

	private HttpParams params;
	private KJHttp http;

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.activity_integral);
		UIHelper.setTitleView(this, "我的账户", "我的积分");

		init();

	}

	private void init() {
		rl_integral_detail = (RelativeLayout) findViewById(R.id.rl_integral_detail);
		rl_integral_income = (RelativeLayout) findViewById(R.id.rl_integral_income);
		rl_integral_expenditure = (RelativeLayout) findViewById(R.id.rl_integral_expenditure);
		rl_integral_expired = (RelativeLayout) findViewById(R.id.rl_integral_expired);
		rl_integral_mall = (RelativeLayout) findViewById(R.id.rl_integral_mall);
		tv_integral_rule = (FontTextView) findViewById(R.id.tv_integral_rule);
		tv_usable_point_m = (FontTextView) findViewById(R.id.tv_usable_point_m);
		tv_froze_usable = (FontTextView) findViewById(R.id.tv_froze_usable);
		tv_usable_point_m_over = (FontTextView) findViewById(R.id.tv_usable_point_m_over);

		rl_integral_detail.setOnClickListener(this);
		rl_integral_income.setOnClickListener(this);
		rl_integral_expenditure.setOnClickListener(this);
		rl_integral_expired.setOnClickListener(this);
		rl_integral_mall.setOnClickListener(this);
		tv_integral_rule.setOnClickListener(this);

		params = new HttpParams();
		http = new KJHttp();

		getData();
	}

	private void getData() {
		params.put("sid", AppVariables.sid);
		http.post(AppConstants.MY_INTEGRAL, params, httpCallback);
	}

	private HttpCallBack httpCallback = new HttpCallBack(IntegralActivity.this) {

		@Override
		public void failure(JSONObject ret) {
			super.failure(ret);
		}

		@Override
		public void success(JSONObject ret) {
			super.success(ret);
			try {
				usable_point_m = ret.getString("usable_point_m");
				usable_point_m_over = ret.getString("usable_point_m_over");
				froze_usable = ret.getString("froze_usable");

				tv_usable_point_m.setText(usable_point_m);
				tv_usable_point_m_over.setText(usable_point_m_over);
				tv_froze_usable.setText(froze_usable);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public void onFinish() {
			super.onFinish();
		}
	};

	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.rl_integral_detail:
			intent = new Intent(IntegralActivity.this,
					IntegralDetailActivity.class);
			intent.putExtra("type", "1");// 积分明细
			startActivity(intent);
			break;
		case R.id.rl_integral_income:
			intent = new Intent(IntegralActivity.this,
					IntegralDetailActivity.class);
			intent.putExtra("type", "2");// 积分收入
			startActivity(intent);
			break;
		case R.id.rl_integral_expenditure:
			intent = new Intent(IntegralActivity.this,
					IntegralDetailActivity.class);
			intent.putExtra("type", "3");// 积分支出
			startActivity(intent);
			break;
		case R.id.rl_integral_expired:
			intent = new Intent(IntegralActivity.this,
					IntegralDetailActivity.class);
			intent.putExtra("type", "4");// 已过期
			startActivity(intent);
			break;
		case R.id.rl_integral_mall:
			intent = new Intent(IntegralActivity.this,
					IntegralMallActivity.class);
			startActivity(intent);// 积分商城
			break;
		case R.id.tv_integral_rule:
			intent = new Intent(IntegralActivity.this,
					IntegralRuleActivity.class);// 积分规则
			startActivity(intent);
			break;
		}
	}

}
