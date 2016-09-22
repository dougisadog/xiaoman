package com.nangua.xiaomanjflc.ui;

import static java.lang.System.out;

import org.json.JSONException;
import org.json.JSONObject;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ips.commons.security.MD5;
import com.louding.frame.KJActivity;
import com.louding.frame.KJHttp;
import com.louding.frame.http.HttpCallBack;
import com.louding.frame.http.HttpParams;
import com.louding.frame.ui.BindView;
import com.louding.frame.utils.StringUtils;
import com.nangua.xiaomanjflc.AppConfig;
import com.nangua.xiaomanjflc.AppConstants;
import com.nangua.xiaomanjflc.AppVariables;
import com.nangua.xiaomanjflc.utils.ApplicationUtil;
import com.nangua.xiaomanjflc.widget.FontTextView;
import com.nangua.xiaomanjflc.widget.LoudingDialog;
import com.nangua.xiaomanjflc.R;
import com.nangua.xiaomanjflc.support.UIHelper;

public class PasswordActivity extends KJActivity {

	@BindView(id = R.id.pwd_old, click = true)
	private EditText mPwd_old;
	@BindView(id = R.id.pwd_new, click = true)
	private EditText mPwd_new;
	@BindView(id = R.id.pwd_confirm, click = true)
	private EditText mPwd_confirm;
	@BindView(id = R.id.confirm, click = true)
	private FontTextView mConfirm;

	private String pwd_old;
	private String pwd_new;
	private String pwd_confirm;

	private KJHttp kjh;

	@Override
	public void setRootView() {
		setContentView(R.layout.activity_password);
		UIHelper.setTitleView(this, "账户中心", "修改密码");
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
		pwd_old = mPwd_old.getText().toString();
		pwd_new = mPwd_new.getText().toString();
		pwd_confirm = mPwd_confirm.getText().toString();
		if (StringUtils.isEmpty(pwd_old) || StringUtils.isEmpty(pwd_new)) {
			LoudingDialog ld = new LoudingDialog(PasswordActivity.this);
			ld.showConfirmHint("请输入完整信息。");
			return;
		}
		if (pwd_new.length() < 8 || pwd_new.length() > 20) {
			LoudingDialog ld = new LoudingDialog(PasswordActivity.this);
			ld.showConfirmHint("密码长度必须大于等于8位小于等于20位");
			return;
		}
		if (!pwd_new.equals(pwd_confirm)) {
			LoudingDialog ld = new LoudingDialog(PasswordActivity.this);
			ld.showConfirmHint("两次输入密码不一致");
			return;
		}
		if (pwd_new.trim().equals(pwd_old.trim())) {
			LoudingDialog ld = new LoudingDialog(PasswordActivity.this);
			ld.showConfirmHint("新旧密码不能相同");
			return;
		}
		kjh = new KJHttp();
		HttpParams params = new HttpParams();
		params.put("sid", AppVariables.sid);
		params.put("originPassword", pwd_old);
		params.put("newPassword", pwd_new);
		params.put("confirmNewPassword", pwd_confirm);
		kjh.post(AppConstants.CHANGEPWD, params, new HttpCallBack(
				PasswordActivity.this) {
			
			
			@Override
			public void failure(JSONObject ret) {
//					super.failure(ret);
				if (!ret.isNull("msg")) {
					try {
						String msg = ret.getString("msg");
							if (msg.equals("not login")) {
								ApplicationUtil.restartApplication(PasswordActivity.this);
								// context.startActivity(new Intent(context,
								// SigninActivity.class));
							} else {
								if ("".equals(msg))
									msg = "密码修改失败";
								LoudingDialog ldc = new LoudingDialog(PasswordActivity.this);
								ldc.showConfirmHint(msg);
							}
					} catch (JSONException e) {
						Toast.makeText(PasswordActivity.this, R.string.app_data_error,
								Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(PasswordActivity.this, R.string.app_exception, Toast.LENGTH_SHORT)
							.show();
				}
			}


			@Override
			public void success(JSONObject ret) {
				super.success(ret);
				LoudingDialog ld = new LoudingDialog(PasswordActivity.this);
				ld.showConfirmHintAndFinish("设置成功。");
				//修改成功后强制登出
				clearinfo();
				
			}
		});
	}
	
	private void clearinfo() {
		AppConfig.getAppConfig(this).set("sid", "");
		AppConfig.getAppConfig(this).set("tel", "");
		AppConfig.getAppConfig(this).set("uid", "");
		AppConfig.getAppConfig(this).set("gesturetel", "");
		AppConfig.getAppConfig(this).set("gesture", "");
		AppVariables.clear();
	}
	
}
