package com.nangua.xiaomanjflc.ui;

import org.json.JSONObject;

import android.view.View;

import com.louding.frame.KJActivity;
import com.louding.frame.KJHttp;
import com.louding.frame.http.HttpCallBack;
import com.louding.frame.http.HttpParams;
import com.louding.frame.ui.BindView;
import com.louding.frame.utils.StringUtils;
import com.nangua.xiaomanjflc.AppConstants;
import com.nangua.xiaomanjflc.AppVariables;
import com.nangua.xiaomanjflc.widget.FontTextView;
import com.nangua.xiaomanjflc.widget.LoudingDialog;
import com.nangua.xiaomanjflc.R;
import com.nangua.xiaomanjflc.support.UIHelper;

public class EmailActivity extends KJActivity {

	@BindView(id = R.id.email, click = true)
	private FontTextView mEmail;
	@BindView(id = R.id.confirm, click = true)
	private FontTextView mConfirm;

	private String email;

	private KJHttp kjh;

	@Override
	public void setRootView() {
		setContentView(R.layout.activity_email);
		UIHelper.setTitleView(this, "账户中心", "邮箱绑定");
	}

	@Override
	public void widgetClick(View v) {
		super.widgetClick(v);
		switch (v.getId()) {
		case R.id.confirm:
			comfirm();
			break;
		}
	}

	private void comfirm() {
		email = mEmail.getText().toString();
		if (!StringUtils.isEmail(email)) {
			LoudingDialog ld = new LoudingDialog(EmailActivity.this);
			ld.showConfirmHint("请输入正确邮箱");
			return;
		}
		kjh = new KJHttp();
		HttpParams params = new HttpParams();
		params.put("sid", AppVariables.sid);
		params.put("email", email);
		System.out.println(params.toJsonString());
		kjh.post(AppConstants.BINDEMAIL, params, new HttpCallBack(
				EmailActivity.this) {
			@Override
			public void success(JSONObject ret) {
				super.success(ret);
				LoudingDialog ld = new LoudingDialog(EmailActivity.this);
				ld.showConfirmHintAndFinish("发送成功，请至邮箱绑定。");
			}
		});
	}

}
