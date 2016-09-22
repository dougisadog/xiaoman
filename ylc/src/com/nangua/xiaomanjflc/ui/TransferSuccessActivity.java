package com.nangua.xiaomanjflc.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.nangua.xiaomanjflc.R;
import com.nangua.xiaomanjflc.support.UIHelper;

/**
 * 转让发布成功
 * */
public class TransferSuccessActivity extends Activity implements
		OnClickListener {

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.activity_transfer_success);
		UIHelper.setTitleView(this, "返回", "");

		init();

	}

	private void init() {
	}

	@Override
	public void onClick(View v) {

	}

}
