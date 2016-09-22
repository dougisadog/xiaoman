package com.nangua.xiaomanjflc.ui;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.louding.frame.KJActivity;
import com.louding.frame.KJHttp;
import com.louding.frame.http.HttpCallBack;
import com.louding.frame.http.HttpParams;
import com.louding.frame.ui.BindView;
import com.louding.frame.utils.StringUtils;
import com.nangua.xiaomanjflc.AppConstants;
import com.nangua.xiaomanjflc.utils.ApplicationUtil;
import com.nangua.xiaomanjflc.utils.HttpHelper;
import com.nangua.xiaomanjflc.widget.FontTextView;
import com.nangua.xiaomanjflc.widget.LoudingDialog;
import com.nangua.xiaomanjflc.R;
import com.nangua.xiaomanjflc.support.UIHelper;

public class FindPwdOneActivity extends KJActivity {

	@BindView(id = R.id.tel)
	private EditText mTel;
	@BindView(id = R.id.verification)
	private EditText mVrify;
	@BindView(id = R.id.hint)
	private FontTextView mHint;
	@BindView(id = R.id.pwd)
	private EditText mPwd;
	@BindView(id = R.id.pwdconfirm)
	private EditText mPwdConfirm;
	@BindView(id = R.id.tel_verify)
	private EditText mTelcode;
	@BindView(id = R.id.verifyimage, click = true)
	private ImageView mVrifyImage;
	@BindView(id = R.id.code, click = true)
	private FontTextView mCode;
	@BindView(id = R.id.next, click = true)
	private FontTextView mNext;

	private String tel;
	private String sid;
	private String telcode;
	private String pwd;
	private String pwdc;
	private String captcha;
	private KJHttp http;

	@Override
	public void setRootView() {
		setContentView(R.layout.activity_find_pwd);
	}

	@Override
	public void initWidget() {
		super.initWidget();
		UIHelper.setTitleView(this, "返回", "找回密码");
	}

	@Override
	public void initData() {
		super.initData();
		post();
	}

	@Override
	public void widgetClick(View v) {
		super.widgetClick(v);
		switch (v.getId()) {
		case R.id.next:// 确定
			tel = mTel.getText().toString();
			captcha = mVrify.getText().toString();
			telcode = mTelcode.getText().toString();
			pwd = mPwd.getText().toString();
			pwdc = mPwdConfirm.getText().toString();
//			if (StringUtils.isEmpty(captcha)) {
//				mHint.setVisibility(View.VISIBLE);
//				mHint.setText("请先输入图片验证码。");
//			} else 
			if (StringUtils.isEmpty(tel) || (tel.length() < 11)) {
				mHint.setVisibility(View.VISIBLE);
				mHint.setText(R.string.signup_code);
			} else if (pwd.trim().equals("")) {
				mHint.setVisibility(View.VISIBLE);
				mHint.setText("密码不能为空");
			}
			else if (!pwd.equals(pwdc)) {
				mHint.setVisibility(View.VISIBLE);
				mHint.setText("两次输入密码不一致。");
			}
			else if (!StringUtils.isPasswordStrength(pwd)) {
				mHint.setVisibility(View.VISIBLE);
				mHint.setText("密码格式不正确");
			} 
			else {
				mHint.setVisibility(View.GONE);
				next();
			}
			break;
//		case R.id.verifyimage:
//			getCapture();
//			break;
		case R.id.code:// 获取短信验证码
			tel = mTel.getText().toString();
			captcha = mVrify.getText().toString();
//			if (StringUtils.isEmpty(captcha)) {
//				mHint.setVisibility(View.VISIBLE);
//				mHint.setText("请先输入图片验证码。");
//			} else 
			if (StringUtils.isEmpty(tel) || (tel.length() < 11)) {
				mHint.setVisibility(View.VISIBLE);
				mHint.setText(R.string.signup_code);
			} else {
				mHint.setVisibility(View.GONE);
				getCode();
			}
			break;
		}
	}

	/**
	 * 确定
	 * */
	private void next() {
		http = new KJHttp();
		HttpParams params = new HttpParams();
		params.put("sid", sid);
		params.put("account", tel);
		params.put("phoneCode", telcode);
		params.put("captcha", captcha);
		http.post(AppConstants.VERIFY_CODE, params, new HttpCallBack(
				FindPwdOneActivity.this) {

			@Override
			public void failure(JSONObject ret) {
				if (!ret.isNull("msg")) {
					try {
						String msg = ret.getString("msg");
							if (msg.equals("not login")) {
								ApplicationUtil.restartApplication(FindPwdOneActivity.this);
								// context.startActivity(new Intent(context,
								// SigninActivity.class));
							} else {
								if ("".equals(msg))
									msg = "密码找回失败";
								LoudingDialog ldc = new LoudingDialog(FindPwdOneActivity.this);
								ldc.showConfirmHint(msg);
								mHint.setVisibility(View.VISIBLE);
								mHint.setText(msg);
							}
					} catch (JSONException e) {
						Toast.makeText(FindPwdOneActivity.this, R.string.app_data_error,
								Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(FindPwdOneActivity.this, R.string.app_exception, Toast.LENGTH_SHORT)
							.show();
				}
//				super.failure(ret);
//				String msg = null;
//				try {
//					msg = ret.getString("msg");
//				} catch (JSONException e) {
//					e.printStackTrace();
//				}
			}

			@Override
			public void success(JSONObject ret) {
				super.success(ret);
				confirm();
			}
		});
	}

	private void confirm() {
		http = new KJHttp();
		HttpParams params = new HttpParams();
		params.put("sid", sid);
		params.put("account", tel);
		params.put("password", pwd);
		mNext.setEnabled(false);
		mNext.setBackgroundResource(R.drawable.btn_tender_gray);
		http.post(AppConstants.GET_LOSE, params, new HttpCallBack(
				FindPwdOneActivity.this) {
			
			@Override
			public void onFinish() {
				mNext.setEnabled(true);
				mNext.setBackgroundResource(R.drawable.btn_blue);
				super.onFinish();
			}

			@Override
			public void failure(JSONObject ret) {
				super.failure(ret);
				String msg = null;
				try {
					msg = ret.getString("code");
				} catch (JSONException e) {
					e.printStackTrace();
				}
				mHint.setVisibility(View.VISIBLE);
				mHint.setText(msg);
			}

			@Override
			public void success(JSONObject ret) {
				super.success(ret);
				String status;
				String msg;
				try {
					status = ret.getString("status");
//					msg = ret.getString("msg");
					msg = ret.getString("code");
				} catch (JSONException e) {
					e.printStackTrace();
					return;
				}
				final LoudingDialog ld = new LoudingDialog(FindPwdOneActivity.this);
				if (null != status && Integer.parseInt(status) == 0) {
					
					ld.showOperateMessage("密码修改成功。");
					ld.setPositiveButton("前往登录页", R.drawable.dialog_positive_btn, new OnClickListener() {
						@Override
						public void onClick(View v) {
							FindPwdOneActivity.this.finish();
							ld.dismiss();
						}
					});
				}
				else {
					ld.showConfirmHint(msg);
				}
				
			}
		});
	}

	/**
	 * 获取短信验证码
	 * */
	private void getCode() {
		http = new KJHttp();
		HttpParams params = new HttpParams();
		params.put("sid", sid);
		params.put("phone", tel);
		params.put("captcha", captcha);
		http.post(AppConstants.SENDCODE, params, new HttpCallBack(
				FindPwdOneActivity.this) {

			@Override
			public void failure(JSONObject ret) {
				super.failure(ret);
				String msg = null;
				try {
					msg = ret.getString("msg");
				} catch (JSONException e) {
					e.printStackTrace();
				}
				mHint.setVisibility(View.VISIBLE);
				mHint.setText(msg);
			}

			@Override
			public void success(JSONObject ret) {
				super.success(ret);
				mHint.setVisibility(View.VISIBLE);
				mHint.setText("发送成功。");
				buttonHandle.post(buttonControl);
			}
		});
	}

	/**
	 * 验证是否已登陆
	 * */
	private void post() {
		http = new KJHttp();
		HttpParams params = new HttpParams();
		params.put("sid", "");
		http.post(AppConstants.ISSIGNIN, params, new HttpCallBack(
				FindPwdOneActivity.this) {

			@Override
			public void success(JSONObject ret) {
				super.success(ret);
				try {
					sid = ret.getString("sid");
//					getCapture();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * 获取图片验证码
	 * */
	private void getCapture() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				final Bitmap b = new HttpHelper().getCapture(sid);
				runOnUiThread(new Runnable() {
					public void run() {
						mVrifyImage.setImageBitmap(b);
					}
				});
			}
		}).start();
	}

	Runnable buttonControl = new Runnable() {
		int sec = 60;

		@Override
		public void run() {
			Message msg = buttonHandle.obtainMessage();
			sec -= 1;
			msg.arg1 = sec;
			buttonHandle.sendMessage(msg);
			if (sec == 0) {
				sec = 60;// 读完秒 按下重新获取之后把sec重新设定为60
			}
		}
	};

	@SuppressLint("HandlerLeak")
	Handler buttonHandle = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			mCode.setText(msg.arg1 + "秒后重新获取");
			if (msg.arg1 == 0) {
				buttonHandle.removeCallbacks(buttonControl);
				mCode.setText("点击获取");
				mCode.setClickable(true);
			} else {
				mCode.setClickable(false);
				buttonHandle.postDelayed(buttonControl, 1000);
			}
		};
	};
}
