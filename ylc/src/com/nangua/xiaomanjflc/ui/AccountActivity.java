package com.nangua.xiaomanjflc.ui;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.kymjs.kjframe.KJDB;

import com.louding.frame.KJActivity;
import com.louding.frame.KJHttp;
import com.louding.frame.http.HttpCallBack;
import com.louding.frame.http.HttpParams;
import com.louding.frame.ui.BindView;
import com.nangua.xiaomanjflc.AppConfig;
import com.nangua.xiaomanjflc.AppConstants;
import com.nangua.xiaomanjflc.AppVariables;
import com.nangua.xiaomanjflc.R;
import com.nangua.xiaomanjflc.bean.jsonbean.Account;
import com.nangua.xiaomanjflc.bean.jsonbean.User;
import com.nangua.xiaomanjflc.bean.jsonbean.UserConfig;
import com.nangua.xiaomanjflc.cache.CacheBean;
import com.nangua.xiaomanjflc.support.InfoManager;
import com.nangua.xiaomanjflc.support.InfoManager.TaskCallBack;
import com.nangua.xiaomanjflc.support.UIHelper;
import com.nangua.xiaomanjflc.utils.HttpHelper;
import com.nangua.xiaomanjflc.utils.ToastUtil;
import com.nangua.xiaomanjflc.widget.FontTextView;
import com.nangua.xiaomanjflc.widget.LoudingDialog;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class AccountActivity extends KJActivity {

	@BindView(id = R.id.rl_username, click = true)
	private RelativeLayout rl_username;
	@BindView(id = R.id.username)
	private FontTextView mUsername;
	@BindView(id = R.id.rl_email, click = true)
	private RelativeLayout rl_email;
	@BindView(id = R.id.email)
	private FontTextView mEmail;
	@BindView(id = R.id.rl_tel, click = true)
	private RelativeLayout rl_tel;
	@BindView(id = R.id.tel)
	private FontTextView mTel;
	@BindView(id = R.id.rl_idcard, click = true)
	private RelativeLayout rl_idcard;
	@BindView(id = R.id.idcard)
	private FontTextView mIdcard;
	@BindView(id = R.id.rl_bankcard, click = true)
	private RelativeLayout rl_bankcard;
	@BindView(id = R.id.bankcard)
	private FontTextView mBankcard;
	@BindView(id = R.id.handimg, click = true)
	private ImageView mHandimg;
	@BindView(id = R.id.hand)
	private FontTextView mHand;
	@BindView(id = R.id.rl_pwd, click = true)
	private RelativeLayout rl_pwd;
	@BindView(id = R.id.pwd)
	private FontTextView mPwd;

	@BindView(id = R.id.usernameimg)
	private ImageView mUsernameImg;
	@BindView(id = R.id.emailimg)
	private ImageView mEmailImg;
	@BindView(id = R.id.idcardimg)
	private ImageView mIdcardImg;
	@BindView(id = R.id.bankcardimg)
	private ImageView mBankcardImg;

	private String name;
	private String phone;
	private String email;
	private String realName;
	private String idCard;
	private String bankCard;
	private int emailValidated;
	private int idValidated;
	private int nameValidated;
	private int bankValidated;
	private int accountStatus;

	private boolean opened;

	private KJHttp kjh;
	private KJDB kjdb;
	
	@Override
	public void setRootView() {
		setContentView(R.layout.activity_account);
		UIHelper.setTitleView(this, "我的账户", "账户中心");
	}
	
	private void refreshHandView() {
		boolean needGesture = false;
		List<UserConfig> userConfigs = kjdb.findAllByWhere(UserConfig.class, "uid=" + AppVariables.uid);
		UserConfig userConfig = null;
		if (userConfigs.size() > 0) {
			userConfig = userConfigs.get(0);
		}
		if (null != userConfig) {
			needGesture = userConfig.isNeedGesture();
		}
		if (!needGesture) {
			mHand.setText("启用手势密码");
			mHandimg.setImageResource(R.drawable.gesture_close);
			opened = false;
		} else {
			mHand.setText("关闭手势密码");
			mHandimg.setImageResource(R.drawable.gesture_open);
			opened = true;
		}
	}

	@Override
	public void initData() {
		super.initData();
		kjh = new KJHttp();
		kjdb = KJDB.create(this);
//		refreshHandView();
	}
	
	private void refresh() {
		if (AppVariables.forceUpdate || HttpHelper.checkNeedUpdate()) {
			getInfo();
		}
		else {
			bindData();
			initView();
		}
	}

	/**
	 * 绑定私有变量
	 */
	private void bindData() {
		User u = CacheBean.getInstance().getUser();
		Account a = CacheBean.getInstance().getAccount();
		emailValidated = Integer.parseInt(u.getEmailValidated());
		idValidated = u.getIdValidated();
		nameValidated = u.getNameValidated();
		name = u.getName();
		email = u.getEmail();
		phone = u.getPhone();

		if (idValidated == 1 || !"".equals(a.getIdCard())) {
			realName = a.getRealName();
			idCard = a.getIdCard();
			bankValidated = a.getCardStatus();
			accountStatus = a.getAccountStatus();
			if (bankValidated == 2) {
				bankCard = a.getBankAccount();
			}
		}

	}
	
	/**
	 * 请求个人信息
	 */
	private void getInfo() {
		InfoManager.getInstance().getInfo(this, new TaskCallBack() {

			@Override
			public void taskSuccess() {
				idValidated = CacheBean.getInstance().getUser().getIdValidated();
				bindData();
				initView();
			}

			@Override
			public void taskFail(String err, int type) {
			}

			@Override
			public void afterTask() {
			}
		});
	}

	/**
	 * 刷新UI
	 */
	private void initView() {
		if (nameValidated == 1) {
			mUsername.setText(TextUtils.isEmpty(realName) ? phone : realName);
			mUsernameImg.setVisibility(View.GONE);
		}

		if (emailValidated == 1) {
			mEmail.setText(email);
			mEmailImg.setVisibility(View.GONE);
		}
		if (idValidated == 1) {
			if (accountStatus == 0) {
				mIdcard.setText("审核中");
				mIdcardImg.setVisibility(View.GONE);
			} else {
				mIdcard.setText(idCard);
				mIdcardImg.setVisibility(View.GONE);
			}
		}
		if (bankValidated == 1) {
			mBankcard.setText("审核中");
		} else if (bankValidated == 2) {
			mBankcard.setText(bankCard);
//			mBankcardImg.setVisibility(View.GONE);
		}
		StringBuffer b = new StringBuffer(phone);
		AppVariables.tel = phone;
		b = b.replace(3, 7, "****");
		mTel.setText(b);
	}

	@Override
	public void widgetClick(View v) {
		Intent intent;
		super.widgetClick(v);
		switch (v.getId()) {
		case R.id.rl_username:
			if (nameValidated != 1) {
				showActivity(AccountActivity.this, UsernameActivity.class);
			}
			break;
		case R.id.rl_email:
			if (emailValidated != 1) {
				if (nameValidated != 1) {
					LoudingDialog ld = new LoudingDialog(AccountActivity.this);
					ld.showConfirmHint("请先设置用户名。");
				} else {
					showActivity(AccountActivity.this, EmailActivity.class);
				}
			}
			break;
		case R.id.rl_tel:
			if ("".equals(AppConfig.getAppConfig(AccountActivity.this).get(
					"tel"))) {
				ToastUtil.showToast(AccountActivity.this, "您还没有绑定手机号",
						Toast.LENGTH_LONG);
			} else {
				intent = new Intent(AccountActivity.this,
						UpdatePhoneActivity.class);
				intent.putExtra("tel",
						AppConfig.getAppConfig(AccountActivity.this).get("tel"));
				startActivity(intent);
			}
			break;
		case R.id.rl_idcard:
			if (idValidated != 1) {
				showActivity(AccountActivity.this, IdcardActivity.class);
			}
			break;
		case R.id.rl_bankcard:
//			if (bankValidated == 0) {
				if (idValidated != 1) {
					LoudingDialog ld = new LoudingDialog(AccountActivity.this);
					ld.showConfirmHint("请先实名认证。");
				} else {
					getIPSData();
				}
//			}
			break;
		case R.id.handimg:
			if (opened) {
				intent = new Intent(AccountActivity.this,
						GestureCloseActivity.class);
				startActivity(intent);
			} else {
				intent = new Intent(AccountActivity.this, GestureActivity.class);
				intent.putExtra("isSet", true);
				startActivity(intent);
			}
			break;
		case R.id.rl_pwd:
			showActivity(AccountActivity.this, PasswordActivity.class);
			break;
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		refreshHandView();
		refresh();
	}
	
	private void getIPSData() {
		HttpParams params = new HttpParams();
		params.put("sid", AppVariables.sid);
		kjh.post(AppConstants.IPS_LOGIN, params, new HttpCallBack(AccountActivity.this, false) {
			@Override
			public void onSuccess(String t) {
					Intent i = new Intent(AccountActivity.this ,BindActivity.class);
					try {
						JSONObject ret = new JSONObject(t);
						if (ret.has("userName"))
							i.putExtra("userName", ret.getString("userName"));
						if (ret.has("url"))
							i.putExtra("url", ret.getString("url"));
						if (ret.has("merchantId")) 
							i.putExtra("merchantId", ret.getString("merchantId"));
						startActivity(i);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		});
	}

}
