package com.nangua.xiaomanjflc.ui;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.louding.frame.KJHttp;
import com.louding.frame.http.HttpCallBack;
import com.louding.frame.http.HttpParams;
import com.nangua.xiaomanjflc.AppConfig;
import com.nangua.xiaomanjflc.AppConstants;
import com.nangua.xiaomanjflc.AppVariables;
import com.nangua.xiaomanjflc.bean.Update;
import com.nangua.xiaomanjflc.support.UpdateManager;
import com.nangua.xiaomanjflc.support.UpdateManager.CheckVersionInterface;
import com.nangua.xiaomanjflc.support.UpdateManager.OnCheckDoneListener;
import com.nangua.xiaomanjflc.utils.ApplicationUtil;
import com.nangua.xiaomanjflc.widget.FontTextView;
import com.nangua.xiaomanjflc.widget.LoudingDialog;
import com.nangua.xiaomanjflc.R;

public class MoreFragment extends Fragment implements CheckVersionInterface {

	private FontTextView signout;
	private FontTextView version;
	private RelativeLayout anno;
	private RelativeLayout share;
	private RelativeLayout update;
	private RelativeLayout aboutus;
	private RelativeLayout help;
	private RelativeLayout comment;
	private View v;

	private KJHttp http;
	private UpdateManager updateManager;
	private Update u;
	private JSONObject versionInfo;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		v = inflater.inflate(R.layout.fragment_more, null);
		updateManager = UpdateManager.getUpdateManager();
		http = new KJHttp();
		initView();
		initData();
		return v;
	}

	private void initView() {
		signout = (FontTextView) v.findViewById(R.id.signout);
		signout.setOnClickListener(listener);
		version = (FontTextView) v.findViewById(R.id.version);
		if (!AppVariables.isSignin) {
			signout.setVisibility(View.GONE);
		}
		anno = (RelativeLayout) v.findViewById(R.id.anno);
		anno.setOnClickListener(listener);
		share = (RelativeLayout) v.findViewById(R.id.share);
		share.setOnClickListener(listener);
		update = (RelativeLayout) v.findViewById(R.id.update);
		update.setOnClickListener(listener);
		aboutus = (RelativeLayout) v.findViewById(R.id.aboutus);
		aboutus.setOnClickListener(listener);
		help = (RelativeLayout) v.findViewById(R.id.help);
		help.setOnClickListener(listener);
		comment = (RelativeLayout) v.findViewById(R.id.comment);
		comment.setOnClickListener(listener);
	}

	private OnClickListener listener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.signout:
				final LoudingDialog ldsignout = new LoudingDialog(getActivity());
				ldsignout.showOperateMessage("确定退出登录？");
				ldsignout.setPositiveButton("确定",
						R.drawable.dialog_positive_btn, new OnClickListener() {
							@Override
							public void onClick(View v) {
								AppConfig.getAppConfig(getActivity()).set(
										"sid", "");
								AppConfig.getAppConfig(getActivity()).set(
										"uid", "");
								AppConfig.getAppConfig(getActivity()).set(
										"tel", "");
								AppConfig.getAppConfig(getActivity()).set(
										"gesturetel", "");
								AppConfig.getAppConfig(getActivity()).set(
										"gesture", "");
								AppVariables.clear();
								signout.setVisibility(View.GONE);
								AppVariables.isSignin = false;
								ldsignout.dismiss();
							}
						});
				break;
			case R.id.anno:
				startActivity(new Intent(getActivity(), AnnoActivity.class));
				break;
			case R.id.share:
				if (!AppVariables.isSignin) {
					startActivity(new Intent(getActivity(),
							SigninActivity.class));
				} else {
					Intent intent = new Intent(getActivity(),
							NormalInviteActivity.class);
					intent.putExtra("activity", "more");
					startActivity(intent);
				}
				break;
			case R.id.update:
				http = new KJHttp();
				HttpParams params = new HttpParams();
				params.put("sid", AppVariables.sid);
				http.post(AppConstants.UPDATE, params, new HttpCallBack(
						getActivity()) {
					public void onPreStart() {
					};

					public void onFinish() {
					};

					public void onSuccess(String t) {
						try {
							versionInfo = new JSONObject(t);
							checkUpdate();
						} catch (JSONException e) {
							e.printStackTrace();
						}
					};
				});
				break;
			case R.id.aboutus:
				startActivity(new Intent(getActivity(), AboutActivity.class));
				break;
			case R.id.help:
				startActivity(new Intent(getActivity(),
						HelpCenterActivity.class));
				break;
			case R.id.comment:
				final LoudingDialog ldcall = new LoudingDialog(getActivity());
				ldcall.showOperateMessage("确定拨打电话400-178-5558？");
				ldcall.setPositiveButton("确定", R.drawable.dialog_positive_btn,
						new OnClickListener() {
							@Override
							public void onClick(View v) {
								Intent intent = new Intent(Intent.ACTION_CALL,
										Uri.parse("tel:4001785558"));
								startActivity(intent);
								ldcall.dismiss();
							}
						});
				break;
			}
		}
	};

	private void checkUpdate() {
		updateManager.checkAppUpdate(getActivity(), true, this);
		updateManager.setOnCheckDoneListener(new OnCheckDoneListener() {
			@Override
			public void onCheckDone() {
			}
		});

	}

	private void initData() {
		version.setText(ApplicationUtil.getApkInfo(getActivity()).versionName);
	}



	@Override
	public Update checkVersion() throws Exception {
		try {
			u = new Update(versionInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return u;
	}

	public void refreshData() {
		if (AppVariables.isSignin) {
			signout.setVisibility(View.VISIBLE);
		} else {
			signout.setVisibility(View.GONE);
		}
	}

}
