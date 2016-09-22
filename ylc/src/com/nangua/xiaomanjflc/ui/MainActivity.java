package com.nangua.xiaomanjflc.ui;

import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.kymjs.kjframe.KJDB;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabWidget;
import android.widget.Toast;

import com.louding.frame.KJHttp;
import com.louding.frame.http.HttpCallBack;
import com.louding.frame.http.HttpParams;
import com.louding.frame.utils.StringUtils;
import com.nangua.xiaomanjflc.AppConfig;
import com.nangua.xiaomanjflc.AppConstants;
import com.nangua.xiaomanjflc.AppVariables;
import com.nangua.xiaomanjflc.receiver.AppReceiver;
import com.nangua.xiaomanjflc.support.InfoManager;
import com.nangua.xiaomanjflc.support.InfoManager.TaskCallBack;
import com.nangua.xiaomanjflc.utils.ApplicationUtil;
import com.nangua.xiaomanjflc.widget.FontTextView;
//import com.umeng.message.PushAgent;
import com.nangua.xiaomanjflc.R;
import com.nangua.xiaomanjflc.YilicaiApplication;
import com.nangua.xiaomanjflc.bean.jsonbean.Account;
import com.nangua.xiaomanjflc.bean.jsonbean.User;
import com.nangua.xiaomanjflc.bean.jsonbean.UserConfig;
import com.nangua.xiaomanjflc.cache.CacheBean;

public class MainActivity extends FragmentActivity {

	private ViewPager mViewPager;
	private FragmentStatePagerAdapter mPagerAdapter;
	private TabWidget mTabWidget;
	private FontTextView ProductTab;
	private FontTextView AccountTab;
	private FontTextView MoreTab;
	private ProductFragment ProductFrag;
	private AccountFragment AccountFrag;
	private MoreFragment MoreFrag;
	private FontTextView tel;
	private LinearLayout center;
	private ImageView img;

	private KJHttp kjh;
	private AppReceiver receiver;
	// 再按一次退出程序
	private long firstTime = 0;
	private String name = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		PushAgent.getInstance(this).onAppStart();
		
		YilicaiApplication.getInstance().setActivity(this);
        YilicaiApplication.getInstance().addStackActivity(this);
        
		setContentView(R.layout.activity_main);
		
		receiver = new AppReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction("android.intent.action.SCREEN_OFF");
		filter.addAction("android.intent.action.SCREEN_ON");
		registerReceiver(receiver, filter);
		Intent intent = getIntent();
		int tab = intent.getIntExtra("tab", 0);
		String sid = AppConfig.getAppConfig(this).get("sid");
		if (null != sid) {
			AppVariables.sid = sid;
		}
		if (!StringUtils.isEmpty(AppVariables.sid)) {
			isSignin();
		} else {
			AppVariables.isSignin = false;
		}
		tel = (FontTextView) findViewById(R.id.tel_center);
		center = (LinearLayout) findViewById(R.id.center);
		img = (ImageView) findViewById(R.id.title_image);
		mTabWidget = (TabWidget) findViewById(R.id.tabwidget);
		ProductTab = (FontTextView) getTvTab(R.string.product, R.drawable.tab_product_selecter);
		mTabWidget.addView(ProductTab);
		/*
		 * Listener必须在mTabWidget.addView()之后再加入，用于覆盖默认的Listener，
		 * mTabWidget.addView()中默认的Listener没有NullPointer检测。
		 */
		ProductTab.setOnClickListener(mTabClickListener);

		AccountTab = (FontTextView) getTvTab(R.string.account,
				R.drawable.tab_account_selecter);
		mTabWidget.addView(AccountTab);
		AccountTab.setOnClickListener(mTabClickListener);

		MoreTab = (FontTextView) getTvTab(R.string.more,
				R.drawable.tab_more_selecter);
		mTabWidget.addView(MoreTab);
		MoreTab.setOnClickListener(mTabClickListener);

		mViewPager = (ViewPager) findViewById(R.id.viewpager);
		mPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
		mViewPager.setAdapter(mPagerAdapter);
		mViewPager.setOnPageChangeListener(mPageChangeListener);

		mTabWidget.setCurrentTab(tab);
		mViewPager.setCurrentItem(tab);
		mViewPager.setOffscreenPageLimit(2);
	}

	private void isSignin() {
		kjh = new KJHttp();
		HttpParams params = new HttpParams();
		params.put("sid", AppVariables.sid);
		kjh.post(AppConstants.ISSIGNIN, params, new HttpCallBack(
				MainActivity.this) {
			@Override
			public void onSuccess(String t) {
				try {
					JSONObject ret = new JSONObject(t);
					JSONObject o = ret.getJSONObject("body");
					AppVariables.isSignin = o.getBoolean("isLogin");
					if (AppVariables.isSignin) {
						AppVariables.tel = AppConfig.getAppConfig(
								MainActivity.this).get("tel");
						AppVariables.sid = AppConfig.getAppConfig(
								MainActivity.this).get("sid");
						String uid = AppConfig.getAppConfig(MainActivity.this)
								.get("uid");
						if (!StringUtils.isEmpty(uid)) {
							AppVariables.needGesture = true;
							AppVariables.uid = Integer.parseInt(uid);
							KJDB kjdb = KJDB.create(MainActivity.this);
							List<UserConfig> userConfigs = kjdb.findAllByWhere(UserConfig.class, "uid=" + AppVariables.uid);
							UserConfig userConfig = null;
							if (userConfigs.size() > 0) {
								userConfig = userConfigs.get(0);
								userConfig.setLastGestureCheckTime(new Date().getTime());
								kjdb.update(userConfig);
							}
							else {
								userConfig = new UserConfig();
								userConfig.setUid(Integer.parseInt(uid));
								userConfig.setNeedGesture(false);
								userConfig.setLastGestureCheckTime(new Date().getTime());
								kjdb.save(userConfig);
							}
						}
						notifyViewPagerDataSetChanged();
						getName();
						CacheBean.syncCookie(MainActivity.this);
						if (ApplicationUtil.isNeedGesture(MainActivity.this)) {
							startActivity(new Intent(MainActivity.this, GestureActivity.class));
						}
					}
				} catch (JSONException e) {
					this.onFinish();
					Toast.makeText(MainActivity.this, "数据解析错误", Toast.LENGTH_LONG).show();
					e.printStackTrace();
				}
			}
		});
	}
	

	/**
	 * 刷新标题头
	 */
	public void refreshTittleBar() {
		User user = CacheBean.getInstance().getUser();
		Account account = CacheBean.getInstance().getAccount();
		if (null != user)
			//通过验证
		if (user.getIdValidated() == 1 && account != null && !account.getRealName().trim().equals("")) {
			tel.setText(account.getRealName() + "的账户");
		} else {
			tel.setText(user.getPhone() + "的账户");
		}
	}
	
	/**
	 * 获取基本信息 刷新标题
	 */
	private void getName() {
		User user = CacheBean.getInstance().getUser();
		InfoManager.getInstance().getInfo(this, new TaskCallBack() {

			@Override
			public void taskSuccess() {
				refreshTittleBar();
			}

			@Override
			public void taskFail(String err, int type) {
			}

			@Override
			public void afterTask() {
			}
		}, null == user || StringUtils.isEmpty(user.getUid())); //当内存没有user 则开始loading 否则关闭
		
	}

	private OnClickListener mTabClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if (v == ProductTab) {
				mViewPager.setCurrentItem(0);
			} else if (v == AccountTab) {
				if (!AppVariables.isSignin) {
					Intent intent = new Intent(MainActivity.this,
							SigninActivity.class);
//					startActivity(intent);
					startActivityForResult(intent, 10001);
				} else {
					mViewPager.setCurrentItem(1);
				}
			} else if (v == MoreTab) {
				mViewPager.setCurrentItem(2);
			}
		}
	};

	private OnPageChangeListener mPageChangeListener = new OnPageChangeListener() {
		@Override
		public void onPageSelected(int arg0) {
			img.setVisibility(View.VISIBLE);
			center.setVisibility(View.GONE);
			if (arg0 == 1) {
				if (!AppVariables.isSignin) {
//					mViewPager.setCurrentItem(0);
//					mTabWidget.setCurrentTab(0);
					startActivityForResult(new Intent(MainActivity.this,
							SigninActivity.class), 10001);
//					startActivity(new Intent(MainActivity.this,
//							SigninActivity.class));
				} else {
					img.setVisibility(View.GONE);
					center.setVisibility(View.VISIBLE);
					
					if (AccountFrag == null)
						AccountFrag = new AccountFragment();
					else {
						AccountFrag.refreshData(AppVariables.forceUpdate);
					}
					mTabWidget.setCurrentTab(arg0);
				}
			} 
			else if (arg0 == 0) {
				if (ProductFrag == null)
					ProductFrag = new ProductFragment();
				else {
					ProductFrag.refreshData();
				}
				mTabWidget.setCurrentTab(arg0);
			} 
			else if (arg0 == 2) {
				if (MoreFrag == null)
					MoreFrag = new MoreFragment();
				else {
					MoreFrag.refreshData();
				}
				mTabWidget.setCurrentTab(arg0);
			}
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		@Override
		public void onPageScrollStateChanged(int arg0) {

		}
	};

	private FontTextView getTvTab(int txtId, int resId) {
		FontTextView tv = new FontTextView(this);
		tv.setFocusable(true);
		tv.setText(getString(txtId));
		tv.setTextColor(getResources().getColorStateList(
				R.drawable.tab_font_selecter));
		tv.setTextSize(14);
		Drawable icon = getResources().getDrawable(resId);
		// 调用setCompoundDrawables时，必须调用Drawable.setBounds()方法,否则图片不显示
		icon.setBounds(0, 0, icon.getMinimumWidth(), icon.getMinimumHeight());
		tv.setCompoundDrawables(null, icon, null, null); // 设置上图标
		tv.setCompoundDrawablePadding(5);
		tv.setGravity(Gravity.CENTER);
		return tv;
	}

	private class MyPagerAdapter extends FragmentStatePagerAdapter {
		public MyPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			switch (position) {
			case 0:
				if (ProductFrag == null)
					ProductFrag = new ProductFragment();
					
					return ProductFrag;
			case 1:
				if (AccountFrag == null)
					AccountFrag =  new AccountFragment();
				
					return AccountFrag;
			case 2:
				if (MoreFrag == null)
					MoreFrag =  new MoreFragment();
				
					return MoreFrag;
			}
			return null;
		}

		@Override
		public int getCount() {
			return 3;
		}
	}
	private void notifyViewPagerDataSetChanged() {

		mPagerAdapter.notifyDataSetChanged();
    }
	@Override
	protected void onResume() {
		super.onResume();
		YilicaiApplication.getInstance().setCurrentRunningActivity(this);
		if (ApplicationUtil.isNeedGesture(this)) {
			startActivity(new Intent(this, GestureActivity.class));
		}
		if (mViewPager.getCurrentItem() == 0) {
			if (ProductFrag == null)
				ProductFrag = new ProductFragment();
			else {
				ProductFrag.refreshData();
			}
		}
		if (mViewPager.getCurrentItem() == 1) {
			if (AccountFrag == null)
				AccountFrag = new AccountFragment();
			else {
//				AccountFrag.refreshData(forceUpdate);
				AccountFrag.refreshData(AppVariables.forceUpdate);
			}
		}
		if (mViewPager.getCurrentItem() == 2) {
			if (MoreFrag == null)
				MoreFrag = new MoreFragment();
			else {
				MoreFrag.refreshData();
			}
		}
			
	}

	@Override
	protected void onPause() {
		super.onPause();
		 if (YilicaiApplication.getInstance().getCurrentRunningActivity().equals(this)) {
	            YilicaiApplication.getInstance().setCurrentRunningActivity(null);
	        }
		AppVariables.lastTime = new Date().getTime();
	}

	// 再按一次退出程序
	@Override
	public void onBackPressed() {
		long secondTime = System.currentTimeMillis();
		if (secondTime - firstTime > 2000) { // 如果两次按键时间间隔大于2秒，则不退出
			Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
			firstTime = secondTime;// 更新firstTime
		} else { // 两次按键小于2秒时，退出应用
			finish();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(receiver);
	}

	@Override
	public void finish() {
		super.finish();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (resultCode) {
		case AppConstants.SUCCESS:
			mViewPager.setCurrentItem(1);
			break;
		case AppConstants.FAILED:
			break;
		default:
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	

}
