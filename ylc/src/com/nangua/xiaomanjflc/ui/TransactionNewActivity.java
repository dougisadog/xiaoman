package com.nangua.xiaomanjflc.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.nangua.xiaomanjflc.R;
import com.nangua.xiaomanjflc.widget.FontTextView;
import com.yanshang.yilicai.lib.SlidingMenu;
import com.yanshang.yilicai.lib.app.SlidingFragmentActivity;

public class TransactionNewActivity extends SlidingFragmentActivity implements
		OnClickListener {

	private Fragment mContent;

	private ImageView title_right;
	private FontTextView title_left;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// 初始化滑动菜单
		initSlidingMenu(savedInstanceState);

		setContentView(R.layout.content_frame);

		title_right = (ImageView) findViewById(R.id.title_right);
		title_left = (FontTextView) findViewById(R.id.title_left);

		title_right.setOnClickListener(this);
		title_left.setOnClickListener(this);

	}

	/**
	 * 切换Fragment，也是切换视图的内容
	 */
	public void switchContent(Fragment fragment) {

		mContent = fragment;
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.content_frame, fragment).commit();
		getSlidingMenu().showContent();
	}

	/**
	 * 初始化滑动菜单
	 */
	private void initSlidingMenu(Bundle savedInstanceState) {
		// 如果保存的状态不为空则得到ColorFragment，否则实例化ColorFragment
		if (savedInstanceState != null) {
			mContent = getSupportFragmentManager().getFragment(savedInstanceState, "mContent");
		}
		if (mContent == null) {
			mContent = new TransactionFragment("0");
		}

		// 设置主界面视图
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.content_frame, mContent).commit();

		// 设置滑动菜单的视图界面
		setBehindContentView(R.layout.menu_frame);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.menu_frame, new TransactionMenuFragment())
				.commit();

		// 设置滑动菜单的属性值
		getSlidingMenu().setMode(SlidingMenu.RIGHT);// 从右边打开
		getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		getSlidingMenu().setShadowWidthRes(R.dimen.shadow_width);
		getSlidingMenu().setShadowDrawable(R.drawable.shadow);
		getSlidingMenu().setBehindOffsetRes(R.dimen.slidingmenu_offset);
		getSlidingMenu().setFadeDegree(0.35f);
	}

	/**
	 * 点击返回键关闭滑动菜单
	 * */
	@Override
	public void onBackPressed() {
		if (getSlidingMenu().isMenuShowing()) {
			getSlidingMenu().showContent();
		} else {
			super.onBackPressed();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.title_right:
			toggle();
			break;
		case R.id.title_left:
			finish();
			break;
		}
	}

}
