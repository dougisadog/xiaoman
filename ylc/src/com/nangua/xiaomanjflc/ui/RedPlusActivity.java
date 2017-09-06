package com.nangua.xiaomanjflc.ui;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.louding.frame.KJActivity;
import com.louding.frame.ui.BindView;
import com.nangua.xiaomanjflc.R;
import com.nangua.xiaomanjflc.ui.fragment.RedFragment;
import com.nangua.xiaomanjflc.ui.fragment.RedPlusFragment;
import com.nangua.xiaomanjflc.ui.fragment.RedPlusFragment2;
import com.nangua.xiaomanjflc.ui.myabstract.HomeFragment;

public class RedPlusActivity extends KJActivity {

	public static final int TAB = 99;
	
	private ViewPager mViewPager;
	private MyPagerAdapter mPagerAdapter;
	
	@BindView(id = R.id.flleft, click = true)
	private FrameLayout flleft;
	
	@BindView(id = R.id.title_red, click = true)
	private TextView titleRed;
	
	@BindView(id = R.id.title_plus_bonus, click = true)
	private TextView titlePlusBonus;
	
	private static int tabRed = 0;
	
	private static int tabPlusBonus = 1;
	
	private class MyPagerAdapter extends FragmentStatePagerAdapter {

		private List<HomeFragment> fragments;
		public MyPagerAdapter(FragmentManager fm,
				List<HomeFragment> fragments) {
			super(fm);
			this.fragments = fragments;
		}

		@Override
		public HomeFragment getItem(int position) {
			return fragments.get(position);
		}

		@Override
		public int getCount() {
			return fragments.size();
		}
	}
	
	
	@Override
	public void setRootView() {
		setContentView(R.layout.activity_red_v3);
		
	}
	
	@Override
	public void initWidget() {
		super.initWidget();
		mViewPager = (ViewPager) findViewById(R.id.viewpager);

		// 初始化
		List<HomeFragment> fragments = new ArrayList<HomeFragment>();
		fragments.add(new RedFragment());
		fragments.add(new RedPlusFragment());
		fragments.add(new RedPlusFragment2());

		mPagerAdapter = new MyPagerAdapter(getSupportFragmentManager(),
				fragments);
		mViewPager.setAdapter(mPagerAdapter);
		mViewPager.setOnPageChangeListener(mPageChangeListener);

		mViewPager.setOffscreenPageLimit(2);
		changeCurrentTab(0);
	}
	
	
	
	@Override
	public void widgetClick(View v) {
		switch (v.getId()) {
			case R.id.flleft :
				finish();
				break;
			case R.id.title_red :
				changeCurrentTab(tabRed);
				break;
			case R.id.title_plus_bonus :
				changeCurrentTab(tabPlusBonus);
				break;
			default :
				break;
		}
		super.widgetClick(v);
	}



	/**
	 * 切换tab
	 * @param targetTab
	 */
	private void changeCurrentTab(int targetTab) {
		if (targetTab == tabRed) {
			titleRed.setTextColor(getResources().getColor(R.color.orange));
			titlePlusBonus.setTextColor(getResources().getColor(R.color.black));
		}
		if (targetTab == tabPlusBonus) {
			titleRed.setTextColor(getResources().getColor(R.color.black));
			titlePlusBonus.setTextColor(getResources().getColor(R.color.orange));
		}
		mViewPager.setCurrentItem(targetTab);
		
	}
	
	/**
	 * 翻页逻辑
	 */
	private OnPageChangeListener mPageChangeListener = new OnPageChangeListener() {
		@Override
		public void onPageSelected(int current) {
			changeCurrentTab(current);
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		@Override
		public void onPageScrollStateChanged(int arg0) {

		}
	};

}
