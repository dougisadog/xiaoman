package com.nangua.xiaomanjflc.ui;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
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

/**
 * 换绑手机号
 * */
public class UpdatePhoneActivity extends Activity implements OnClickListener {

	private KJHttp kjh;

	private FontTextView tv_next;
	private EditText et_tel;

	private String tel;
	private String req;
	private String sign;
	private String uri;
	private String status;
	private String msg;

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.activity_update_phone);
		UIHelper.setTitleView(this, "返回", "换绑手机号");

		init();
	}

	public void init() {
		tv_next = (FontTextView) findViewById(R.id.tv_next);
		et_tel = (EditText) findViewById(R.id.et_tel);
		tv_next.setOnClickListener(this);

		kjh = new KJHttp();
		Bundle extras = getIntent().getExtras();
		tel = extras.getString("tel");
	}

	private void post() {

		HttpParams params = new HttpParams();
		params.put("sid", AppVariables.sid);
		params.put("tel", tel);
		params.put("newtel", et_tel.getText().toString().trim());
		kjh.post(AppConstants.update_phone, params, new HttpCallBack(
				UpdatePhoneActivity.this) {

			@Override
			public void success(JSONObject ret) {
				try {
					status = ret.getString("status");
					if ("0".equals(status)) {
						//修改手机号
					} else {
						msg = ret.getString("msg");
						ToastUtil.showToast(UpdatePhoneActivity.this, msg,
								Toast.LENGTH_LONG);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

		});
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.tv_next:
			post();
			break;
		}
	}
}
