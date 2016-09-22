package com.nangua.xiaomanjflc.ui;

import java.io.UnsupportedEncodingException;

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

public class UsernameActivity extends KJActivity {

	@BindView(id = R.id.username, click = true)
	private FontTextView mUsername;
	@BindView(id = R.id.confirm, click = true)
	private FontTextView mConfirm;

	private String name;

	private KJHttp kjh;

	@Override
	public void setRootView() {
		setContentView(R.layout.activity_username);
		UIHelper.setTitleView(this, "账户中心", "昵称设置");
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
		name = mUsername.getText().toString();
		if (StringUtils.isEmpty(name)) {
			LoudingDialog ld = new LoudingDialog(UsernameActivity.this);
			ld.showConfirmHint("请输入用户名");
			return;
		}
		kjh = new KJHttp();
		HttpParams params = new HttpParams();
		params.put("sid", AppVariables.sid);
		try {
			params.put("name", new String(name.getBytes(), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		System.out.println(params.toJsonString());
		kjh.post(AppConstants.ADDNAME, params, new HttpCallBack(
				UsernameActivity.this) {
			@Override
			public void success(JSONObject ret) {
				super.success(ret);
				LoudingDialog ld = new LoudingDialog(UsernameActivity.this);
				ld.showConfirmHintAndFinish("设置成功。");
			}
		});
	}
}
