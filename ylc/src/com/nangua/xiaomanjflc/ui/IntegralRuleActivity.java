package com.nangua.xiaomanjflc.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.nangua.xiaomanjflc.R;
import com.nangua.xiaomanjflc.support.UIHelper;

/**
 * 积分使用规则
 * */
public class IntegralRuleActivity extends Activity implements OnClickListener {

	private RelativeLayout rl_text1;
	private RelativeLayout rl_text2;
	private RelativeLayout rl_text3;
	private LinearLayout ll_text1;
	private LinearLayout ll_text2;
	private LinearLayout ll_text3;

	private boolean text1 = true;
	private boolean text2 = true;
	private boolean text3 = true;

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.activity_integral_rule);
		UIHelper.setTitleView(this, "我的积分", "积分使用规则");

		init();

	}

	private void init() {
		rl_text1 = (RelativeLayout) findViewById(R.id.rl_text1);
		rl_text2 = (RelativeLayout) findViewById(R.id.rl_text2);
		rl_text3 = (RelativeLayout) findViewById(R.id.rl_text3);
		ll_text1 = (LinearLayout) findViewById(R.id.ll_text1);
		ll_text2 = (LinearLayout) findViewById(R.id.ll_text2);
		ll_text3 = (LinearLayout) findViewById(R.id.ll_text3);

		rl_text1.setOnClickListener(this);
		rl_text2.setOnClickListener(this);
		rl_text3.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.rl_text1:
			if (text1) {
				ll_text1.setVisibility(View.VISIBLE);
				text1 = false;
			} else {
				ll_text1.setVisibility(View.GONE);
				text1 = true;
			}
			break;
		case R.id.rl_text2:
			if (text2) {
				ll_text2.setVisibility(View.VISIBLE);
				text2 = false;
			} else {
				ll_text2.setVisibility(View.GONE);
				text2 = true;
			}
			break;
		case R.id.rl_text3:
			if (text3) {
				ll_text3.setVisibility(View.VISIBLE);
				text3 = false;
			} else {
				ll_text3.setVisibility(View.GONE);
				text3 = true;
			}
			break;
		}
	}
}
