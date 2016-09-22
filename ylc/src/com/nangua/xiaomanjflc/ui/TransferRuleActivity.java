package com.nangua.xiaomanjflc.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

import com.nangua.xiaomanjflc.R;
import com.nangua.xiaomanjflc.support.UIHelper;
import com.nangua.xiaomanjflc.widget.FontTextView;

/**
 * 转让规则
 * */
public class TransferRuleActivity extends Activity implements OnClickListener {

	private RelativeLayout rl_agree;
	private FontTextView tv_agree;

	private String products_title;
	private String oid_platform_products_id;
	private String oid_tender_id;
	private String tender_from;
	private String lave_date;
	private String finance_interest_rate;
	private String finance_start_interest_date;
	private String tender_amount;

	private String flag;

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.activity_transfer_rule);
		UIHelper.setTitleView(this, "返回", "转让规则");

		init();

		Bundle extras = getIntent().getExtras();
		flag = extras.getString("flag");
		if ("".equals(flag)) {
			products_title = extras.getString("products_title");
			oid_platform_products_id = extras
					.getString("oid_platform_products_id");
			oid_tender_id = extras.getString("oid_tender_id");
			tender_from = extras.getString("tender_from");
			lave_date = extras.getString("lave_date");
			finance_interest_rate = extras.getString("finance_interest_rate");
			finance_start_interest_date = extras
					.getString("finance_start_interest_date");
			tender_amount = extras.getString("tender_amount");
		} else {
			rl_agree.setVisibility(View.GONE);
		}

	}

	private void init() {
		rl_agree = (RelativeLayout) findViewById(R.id.rl_agree);
		tv_agree = (FontTextView) findViewById(R.id.tv_agree);

		tv_agree.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.tv_agree:
			Intent intent = new Intent(TransferRuleActivity.this,
					TransferActivity.class);
			intent.putExtra("products_title", products_title);
			intent.putExtra("oid_platform_products_id",
					oid_platform_products_id);
			intent.putExtra("oid_tender_id", oid_tender_id);
			intent.putExtra("tender_from", tender_from);
			intent.putExtra("lave_date", lave_date);
			intent.putExtra("finance_interest_rate", finance_interest_rate);
			intent.putExtra("finance_start_interest_date",
					finance_start_interest_date);
			intent.putExtra("tender_amount", tender_amount);
			startActivity(intent);
			TransferRuleActivity.this.finish();
			break;
		}
	}

}
