package com.nangua.xiaomanjflc.ui;

import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.kymjs.kjframe.KJDB;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources.NotFoundException;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

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
import com.nangua.xiaomanjflc.utils.HttpHelper;
import com.nangua.xiaomanjflc.widget.FontTextView;
import com.nangua.xiaomanjflc.widget.LoudingDialog;
import com.nangua.xiaomanjflc.R;
import com.nangua.xiaomanjflc.bean.jsonbean.UserConfig;
import com.nangua.xiaomanjflc.cache.CacheBean;
import com.nangua.xiaomanjflc.support.InfoManager;
import com.nangua.xiaomanjflc.support.InfoManager.TaskCallBack;
import com.nangua.xiaomanjflc.support.UIHelper;

public class SigninActivity extends KJActivity {

	@BindView(id = R.id.tel)
	private EditText mTel;
	@BindView(id = R.id.pwd)
	private EditText mPwd;
	@BindView(id = R.id.verification)
	private EditText mVrify;
	@BindView(id = R.id.verifyimage, click = true)
	private ImageView mVrifyImage;
	@BindView(id = R.id.signin, click = true)
	private FontTextView mSignin;
	@BindView(id = R.id.signup, click = true)
	private FontTextView mSignup;
	@BindView(id = R.id.verify1)
	private LinearLayout mVrify1;
	@BindView(id = R.id.verify2)
	private LinearLayout mVrify2;
	@BindView(id = R.id.hint)
	private FontTextView mHint;
	@BindView(id = R.id.losepwd, click = true)
	private FontTextView mLose;

	private FontTextView titleRight;

	private String tel;
	private String pwd;
	private String code;
	private String sid;
	private int uid;

	private KJHttp kjh;
	private KJDB kjdb;

	@Override
	public void setRootView() {
		setContentView(R.layout.activity_signin);
	}

	@Override
	public void initWidget() {
		super.initWidget();
		UIHelper.setTitleView(this, "关闭", "登录");
	}

	@Override
	public void initData() {
		super.initData();
		sid = "";
		kjdb = KJDB.create(this);
	}

	@Override
	public void widgetClick(View v) {
		super.widgetClick(v);
		switch (v.getId()) {
		case R.id.signin:
			tel = mTel.getText().toString();
			pwd = mPwd.getText().toString();
			code = mVrify.getText().toString();
			if (StringUtils.isEmpty(tel) || StringUtils.isEmpty(pwd)) {
				mHint.setVisibility(View.VISIBLE);
				mHint.setText(R.string.sign_hint);
			} else {
				InfoManager.getInstance().loginNormal(SigninActivity.this, tel, pwd, new TaskCallBack() {
					
					@Override
					public void taskSuccess() {
						setResult(AppConstants.SUCCESS);
						SigninActivity.this.finish();
					}
					
					@Override
					public void taskFail(String err, int type) {
					}
					
					@Override
					public void afterTask() {
						// TODO Auto-generated method stub
						
					}
				});
			}
			break;
//		case R.id.verifyimage:
//			getCapture();
//			break;
		case R.id.losepwd:
			showActivity(SigninActivity.this, FindPwdOneActivity.class);
			break;
		case R.id.signup:
			showActivity(SigninActivity.this, SignupActivity.class);
			finish();
			break;
		}
	}


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

}
