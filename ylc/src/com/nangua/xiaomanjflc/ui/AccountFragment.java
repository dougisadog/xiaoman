package com.nangua.xiaomanjflc.ui;

import static java.lang.System.out;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.louding.frame.KJHttp;
import com.louding.frame.http.HttpCallBack;
import com.louding.frame.http.HttpParams;
import com.nangua.xiaomanjflc.AppConstants;
import com.nangua.xiaomanjflc.AppVariables;
import com.nangua.xiaomanjflc.utils.FormatUtils;
import com.nangua.xiaomanjflc.utils.HttpHelper;
import com.nangua.xiaomanjflc.widget.FontTextView;
import com.nangua.xiaomanjflc.widget.LoudingDialog;
import com.nangua.xiaomanjflc.R;
import com.nangua.xiaomanjflc.bean.jsonbean.Account;
import com.nangua.xiaomanjflc.bean.jsonbean.User;
import com.nangua.xiaomanjflc.cache.CacheBean;
import com.nangua.xiaomanjflc.support.InfoManager;
import com.nangua.xiaomanjflc.support.InfoManager.TaskCallBack;

public class AccountFragment extends Fragment {
	private View v;
	private FontTextView mTotal;
	private FontTextView mGain;
	private FontTextView mUnrepaid;
	private FontTextView mAmount;
	private FontTextView mBalance;
	private FontTextView mfrozeAmount;
	private RelativeLayout claims_transfer;
	private RelativeLayout mAccount;
	private RelativeLayout mInvest;
	private RelativeLayout mInvite;
	private RelativeLayout mTrans;
	private RelativeLayout mRed;
	private RelativeLayout mIntegral;
	private RelativeLayout mNews;
	private FontTextView register;
	private FontTextView mCharge;
	private FontTextView mCash;
	
	private FontTextView mBind;
	private FontTextView mDate;
	private ImageView iv_red_point;

	private int totalInterest;
	private int unrepaidInterest;
	private int available;
	private int investAmount;
	private int frozeAmount;
	private int noreadmessage;
	
	private int reqType = 0;
	
	private static final int CHARGE = 1;
	private static final int CASH = 2;
	private static final int BIND = 3;

	private KJHttp http;
	private HttpParams params;

	private int idValidated;
	private int status;

	private int attemp = 0; // 个人中心数据拉取尝试次数

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
			@Nullable Bundle savedInstanceState) {
		v = inflater.inflate(R.layout.fragment_account, null);

		http = new KJHttp();
		params = new HttpParams();

		initView();
		getUserAccount(); // 获取用户信息

		return v;
	}


	private void initView() {
		mTotal = (FontTextView) v.findViewById(R.id.total);
		mGain = (FontTextView) v.findViewById(R.id.gain);
		mUnrepaid = (FontTextView) v.findViewById(R.id.unrepaid);
		mAmount = (FontTextView) v.findViewById(R.id.amount);
		mBalance = (FontTextView) v.findViewById(R.id.balance);
		mfrozeAmount = (FontTextView) v.findViewById(R.id.frozeAmount);
		mAccount = (RelativeLayout) v.findViewById(R.id.account);
		mAccount.setOnClickListener(listener);
		mInvest = (RelativeLayout) v.findViewById(R.id.invest);
		mInvest.setOnClickListener(listener);
		mTrans = (RelativeLayout) v.findViewById(R.id.trans);
		mTrans.setOnClickListener(listener);
		mRed = (RelativeLayout) v.findViewById(R.id.red);
		mRed.setOnClickListener(listener);
		mIntegral = (RelativeLayout) v.findViewById(R.id.integral);
		mIntegral.setOnClickListener(listener);
		mNews = (RelativeLayout) v.findViewById(R.id.news);
		mNews.setOnClickListener(listener);
		claims_transfer = (RelativeLayout) v.findViewById(R.id.claims_transfer);
		claims_transfer.setOnClickListener(listener);
		mInvite = (RelativeLayout) v.findViewById(R.id.invite);
		mInvite.setOnClickListener(listener);

		// register = (FontTextView) v.findViewById(R.id.register); // 开户
		// register.setOnClickListener(listener);

		mCharge = (FontTextView) v.findViewById(R.id.charge);
		mCharge.setOnClickListener(listener);
		mCash = (FontTextView) v.findViewById(R.id.cash);
		mCash.setOnClickListener(listener);
		
		mBind = (FontTextView) v.findViewById(R.id.bind);
//		mBind.setOnClickListener(listener);
		mBind.setVisibility(View.GONE);
		
		mDate = (FontTextView) v.findViewById(R.id.date);
		iv_red_point = (ImageView) v.findViewById(R.id.iv_red_point);
	}

	private OnClickListener listener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			out.println("My: 点击 v.getId() => " + v.getId());

			switch (v.getId()) {
			case R.id.account:
				startActivity(new Intent(getActivity(), AccountActivity.class));
				break;
			case R.id.invest:
				startActivity(new Intent(getActivity(), InvestActivity.class));
				break;
			case R.id.trans:
				startActivity(new Intent(getActivity(), TransactionNewActivity.class));
				break;
				//现金券
			case R.id.red:
				startActivity(new Intent(getActivity(), RedActivity.class));
				break;
			case R.id.invite:
				Intent intent = new Intent(getActivity(), NormalInviteActivity.class);
				intent.putExtra("activity", "account");
				startActivity(intent);
				break;
				
			case R.id.bind: //绑卡
				if (idValidated == 1) {
					getIPSData();
				}
				else {
					reqType = BIND;
					getInfo();
				}
				break;
			case R.id.charge: // 充值
				if (idValidated == 1 && status == 2) {
					Intent charge = new Intent(getActivity(), ChargeActivity.class);
					charge.putExtra("balance", available);
					startActivity(charge);
				} else {
					reqType = CHARGE;
					getInfo();
				}
				break;

			case R.id.cash:
				if (idValidated == 1 && status == 2) {
					Intent cash = new Intent(getActivity(), CashActivity.class);
					cash.putExtra("balance", available);
					startActivity(cash);
				} else {
					reqType = CASH;
					getInfo();
				}
				break;
			case R.id.claims_transfer:
				startActivity(new Intent(getActivity(), ClaimsTransferActivity.class));
				break;
			case R.id.integral:
				startActivity(new Intent(getActivity(), IntegralActivity.class));
				break;
			case R.id.news:
				noreadmessage = 0;
				startActivity(new Intent(getActivity(), MessageCenterActivity.class));
				break;
			}
		}
	};
	
	private void getIPSData() {
		params.put("sid", AppVariables.sid);
		http.post(AppConstants.IPS_LOGIN, params, new HttpCallBack(getActivity(), false) {
			@Override
			public void onSuccess(String t) {
					Intent i = new Intent(getActivity() ,BindActivity.class);
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
	
	private void getData() {
		params.put("sid", AppVariables.sid);
		http.post(AppConstants.GAIN, params, new HttpCallBack(getActivity(), true) {

			@Override
			public void onSuccess(String t) {
				try {
					JSONObject ret = new JSONObject(t);
					if (ret.has("totalInterest"))
						totalInterest = ret.getInt("totalInterest");
					if (ret.has("unrepaidInterest"))
						unrepaidInterest = ret.getInt("unrepaidInterest");
					if (ret.has("available"))
						available = ret.getInt("available");
					if (ret.has("investAmount"))
						investAmount = ret.getInt("investAmount");
					if (ret.has("frozeAmount"))
						frozeAmount = ret.getInt("frozeAmount");
					if (ret.has("noreadmessage"))
						noreadmessage = ret.getInt("noreadmessage");
					mTotal.setText(
							FormatUtils.fmtMicrometer(FormatUtils.priceFormat(available + investAmount + frozeAmount)));
					mGain.setText(FormatUtils.fmtMicrometer(FormatUtils.priceFormat(totalInterest)));
					mUnrepaid.setText(FormatUtils.fmtMicrometer(FormatUtils.priceFormat(unrepaidInterest)));
					mAmount.setText(FormatUtils.fmtMicrometer(FormatUtils.priceFormat(investAmount)));
					mBalance.setText(FormatUtils.fmtMicrometer(FormatUtils.priceFormat(available)));
					mfrozeAmount.setText(FormatUtils.fmtMicrometer(FormatUtils.priceFormat(frozeAmount)));
					if (noreadmessage > 0) {
						iv_red_point.setVisibility(View.VISIBLE);
					} else {
						iv_red_point.setVisibility(View.GONE);
					}
					AppVariables.forceUpdate = false;
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * 表单验证
	 */
	private void submitValidate() {
		if (idValidated == 1) {
			if (reqType == BIND) {
				getIPSData();
			}
			else {
				if (status == 0) {
					final LoudingDialog ld = new LoudingDialog(getActivity());
					ld.showOperateMessage("您还没有绑卡，请绑定银行卡。");
					ld.setPositiveButton("前往", R.drawable.dialog_positive_btn, new OnClickListener() {
						@Override
						public void onClick(View v) {
							startActivity(new Intent(getActivity(), AccountActivity.class));
							ld.dismiss();
						}
					});
				} else if (status == 1) {
					final LoudingDialog ld = new LoudingDialog(getActivity());
					ld.showConfirmHint("您的银行卡正在审核中，审核结果将通过短信通知您。");
				} else if (status == 2) {
					Intent i = new Intent();
					switch (reqType) {
					case CHARGE:
						i.setClass(getActivity(), ChargeActivity.class);
						i.putExtra("balance", available);
						startActivity(i);
						break;
					case CASH:
						i.setClass(getActivity(), CashActivity.class);
						i.putExtra("balance", available);
						startActivity(i);
						break;
					default:
						break;
					}
				}
			}
		} else {
			final LoudingDialog ld = new LoudingDialog(getActivity());
			ld.showOperateMessage("请先实名认证。");
			ld.setPositiveButton("前往", R.drawable.dialog_positive_btn, new OnClickListener() {
				@Override
				public void onClick(View v) {
					startActivity(new Intent(getActivity(), AccountActivity.class));
					ld.dismiss();
				}
			});
		}
	}

	/**
	 * 及时拉取服务器基本信息
	 */
	private void getInfo() // 实名认证、绑定银行卡
	{
		InfoManager.getInstance().getInfo(getActivity(), new TaskCallBack() {
			
			@Override
			public void taskSuccess() {
				idValidated = CacheBean.getInstance().getUser().getIdValidated();
				status = CacheBean.getInstance().getAccount().getCardStatus();
				submitValidate();
			}
			
			@Override
			public void taskFail(String err, int type) {
			}
			
			@Override
			public void afterTask() {
			}
		});
	}

	
	public void refreshData() {
		if (HttpHelper.checkNeedUpdate()) {
			getUserAccount(); // 获取用户信息
		}
	}
	
	public void refreshView() {
		((MainActivity) getActivity()).refreshTittleBar();
		getData();
	}
	
	/**
	 * 
	 * @param true 强制更新  false 需要检测
	 */
	public void refreshData(boolean forceUpdate) {
		if (forceUpdate || HttpHelper.checkNeedUpdate()) {
			getUserAccount(); // 获取用户信息
		}
	}

	
	/**
	 * 页面加载时服务器信息拉取
	 */
	private void getUserAccount() // 获取用户信息
	{
		InfoManager.getInstance().getInfo(getActivity(), new TaskCallBack() {
					
					@Override
					public void taskSuccess() {
						User user = CacheBean.getInstance().getUser();
						Account account = CacheBean.getInstance().getAccount();
						//个人 
						if (user.getType() == 1) {
							mCash.setOnClickListener(null);
							mCharge.setOnClickListener(null);
							mBind.setOnClickListener(null);
							mCash.setBackgroundResource(R.color.grey);
							mCharge.setBackgroundResource(R.color.grey);
							mBind.setBackgroundResource(R.color.grey);
						}
						//企业
						else if (user.getType() == 0){
							mCash.setOnClickListener(listener);
							mCharge.setOnClickListener(listener);
							mBind.setOnClickListener(listener);
							mCash.setBackgroundColor(0xFF5581d6);
							mCharge.setBackgroundColor(0xFF5581d6);
							mBind.setBackgroundColor(0xFF5581d6);
						}
						idValidated = user.getIdValidated();
						status = account.getCardStatus();
						refreshView();
					}
					
					@Override
					public void taskFail(String err, int type) {
					}
					
					@Override
					public void afterTask() {
					}
		});
	}
}
