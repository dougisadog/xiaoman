package com.nangua.xiaomanjflc.ui;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.louding.frame.KJActivity;
import com.louding.frame.KJHttp;
import com.louding.frame.http.HttpCallBack;
import com.louding.frame.http.HttpParams;
import com.louding.frame.ui.BindView;
import com.louding.frame.widget.KJListView;
import com.louding.frame.widget.KJRefreshListener;
import com.nangua.xiaomanjflc.AppConstants;
import com.nangua.xiaomanjflc.AppVariables;
import com.nangua.xiaomanjflc.adapter.CommonAdapter;
import com.nangua.xiaomanjflc.adapter.ViewHolder;
import com.nangua.xiaomanjflc.bean.Invest;
import com.nangua.xiaomanjflc.bean.InvestList;
import com.nangua.xiaomanjflc.support.UIHelper;
import com.nangua.xiaomanjflc.widget.FontTextView;
import com.nangua.xiaomanjflc.R;

public class InvestActivity extends KJActivity {

	@BindView(id = R.id.listview)
	private KJListView listview;
	@BindView(id = R.id.one, click = true)
	private FontTextView one;
	@BindView(id = R.id.two, click = true)
	private FontTextView two;
	@BindView(id = R.id.three, click = true)
	private FontTextView three;
	@BindView(id = R.id.four, click = true)
	private FontTextView four;

	private KJHttp http;
	private HttpParams params;

	private CommonAdapter<Invest> adapter;
	private List<Invest> data;

	private int page = 1;
	private String url;
	private int state;
	private boolean noMoreData;
	private boolean clickable = true;

	@Override
	public void setRootView() {
		setContentView(R.layout.activity_invest);
		UIHelper.setTitleView(this, "我的账户", "投资记录");
	}

	@Override
	public void initData() {
		super.initData();
		url = AppConstants.INVEST_ORDER;
		state = 1;
		data = new ArrayList<Invest>();
		http = new KJHttp();
		params = new HttpParams();
		getData(page);
	}

	private void getData(int page) {
		params.put("page", page);
		params.put("sid", AppVariables.sid);
		http.post(url, params, httpCallback);
	}

	@Override
	public void initWidget() {
		super.initWidget();
		adapter = new CommonAdapter<Invest>(InvestActivity.this,
				R.layout.item_invest) {
			@Override
			public void canvas(ViewHolder holder, Invest item) {
				holder.addClick(R.id.invest_protocol);
				FontTextView t = holder.getView(R.id.statusText);
				FontTextView t1 = holder.getView(R.id.statusText1);
				LinearLayout ll = holder.getView(R.id.coupon);
				if (item.isHasCoupon()) {
					ll.setVisibility(View.VISIBLE);
				} else {
					ll.setVisibility(View.GONE);
				}
				holder.setText(R.id.name, item.getName(), false);
				holder.setText(R.id.couponname, item.getCouponName(), false);
				holder.setText(R.id.price, item.getPrice() + "元", false);
				holder.setText(R.id.price1, item.getCouponPrice() + "元", false);
				holder.setText(R.id.rate, item.getRate() + "%", false);
				holder.setText(R.id.rate1, item.getRate() + "%", false);
				Resources res = context.getResources();
				Drawable icon;
				switch (state) {
				case 1:
					holder.setText(R.id.txthint, "回款本息", false);
					holder.setText(R.id.lastReturn,
							item.getPrincipalAndInterest() + "元", false);
					holder.setText(R.id.repayTime,
							"下期回款日：" + item.getRepayTime(), false);
					holder.setText(R.id.txthint1, "回款本息", false);
					holder.setText(R.id.lastReturn1, item.getCouponLastReturn()
							+ "元", false);
					holder.setText(R.id.repayTime1,
							"下期回款日：" + item.getRepayTime(), false);
					t.setVisibility(View.VISIBLE);
					t.setText("期数：" + item.getStatusText());
					icon = res.getDrawable(R.drawable.invest_status);
					icon.setBounds(0, 0, icon.getMinimumWidth(),
							icon.getMinimumHeight());
					t.setCompoundDrawables(icon, null, null, null); // 设置左图标
					t.setCompoundDrawablePadding(5);
					t1.setVisibility(View.VISIBLE);
					t1.setText("期数：" + item.getStatusText());
					t1.setCompoundDrawables(icon, null, null, null); // 设置左图标
					t1.setCompoundDrawablePadding(5);
					break;
				case 2:
					holder.setText(R.id.txthint, "状态", false);
					holder.setText(R.id.lastReturn, item.getStatusText(), false);
					holder.setText(R.id.repayTime,
							"投资日期：" + item.getCreateDate(), false);
					holder.setText(R.id.txthint1, "状态", false);
					holder.setText(R.id.lastReturn1, item.getStatusText(),
							false);
					holder.setText(R.id.repayTime1,
							"投资日期：" + item.getCreateDate(), false);
					t.setVisibility(View.VISIBLE);
					t.setText("起息日期：" + item.getInterestBeginDate());
					icon = res.getDrawable(R.drawable.invest_start);
					icon.setBounds(0, 0, icon.getMinimumWidth(),
							icon.getMinimumHeight());
					t.setCompoundDrawables(icon, null, null, null); // 设置左图标
					t.setCompoundDrawablePadding(5);
					t1.setVisibility(View.VISIBLE);
					t1.setText("起息日期：" + item.getInterestBeginDate());
					t1.setCompoundDrawables(icon, null, null, null); // 设置左图标
					t1.setCompoundDrawablePadding(5);
					break;
				case 3:
					holder.setText(R.id.txthint, "回款总额", false);
					holder.setText(R.id.lastReturn, item.getLastReturn() + "元", false);
					holder.setText(R.id.repayTime,
							"起息日期：" + item.getInterestBeginDate(), false);
					holder.setText(R.id.txthint1, "回款总额", false);
					holder.setText(R.id.lastReturn1,
							item.getCouponLastReturn() + "元", false);
					holder.setText(R.id.repayTime1,
							"起息日期：" + item.getInterestBeginDate(), false);
					t.setVisibility(View.VISIBLE);
					t.setText("结清日期：" + item.getEndDate());
					icon = res.getDrawable(R.drawable.invest_start);
					icon.setBounds(0, 0, icon.getMinimumWidth(),
							icon.getMinimumHeight());
					t.setCompoundDrawables(icon, null, null, null); // 设置左图标
					t.setCompoundDrawablePadding(5);
					t1.setVisibility(View.VISIBLE);
					t1.setText("结清日期：" + item.getEndDate());
					t1.setCompoundDrawables(icon, null, null, null); // 设置左图标
					t1.setCompoundDrawablePadding(5);
					break;
				case 4:
					holder.setText(R.id.txthint, "状态", false);
					holder.setText(R.id.lastReturn, item.getStatusText(), false);
					holder.setText(R.id.repayTime,
							"投资日期：" + item.getCreateDate(), false);
					t.setVisibility(View.GONE);
					break;

				}
			}

			@Override
			public void click(int id, List<Invest> list, int position,
					ViewHolder viewHolder) {
				switch (id) {
				case R.id.invest_protocol:
					Intent intent = new Intent(InvestActivity.this,
							InvestProtocolActivity.class);
					intent.putExtra("id", list.get(position).getId());
					startActivity(intent);
					break;
				}
			}
		};
		adapter.setList(data);
		listview.setAdapter(adapter);
		listview.setOnRefreshListener(refreshListener);
		listview.setEmptyView(findViewById(R.id.empty));
	}

	private KJRefreshListener refreshListener = new KJRefreshListener() {
		@Override
		public void onRefresh() {
			getData(1);
		}

		@Override
		public void onLoadMore() {
			if (!noMoreData) {
				getData(page + 1);
			}
		}
	};

	private HttpCallBack httpCallback = new HttpCallBack(InvestActivity.this) {
		public void success(org.json.JSONObject ret) {
			try {
				JSONObject articles = ret.getJSONObject("orders");
				page = articles.getInt("currentPage");
				int maxPage = articles.getJSONObject("pager").getInt("maxPage");
				if (page >= maxPage) {
					listview.hideFooter();
					noMoreData = true;
				} else {
					listview.showFooter();
					noMoreData = false;
				}
				if (page < 2) {
					data = new InvestList(articles.getJSONArray("items"))
							.getInvests();
				} else {
					data = new InvestList(data, articles.getJSONArray("items"))
							.getInvests();
				}
				adapter.setList(data);
			} catch (Exception e) {
				if (page > 0) {
					page = page - 1;
				}
				e.printStackTrace();
				Toast.makeText(InvestActivity.this, R.string.app_data_error,
						Toast.LENGTH_SHORT).show();
			}
		}

		public void onFinish() {
			super.onFinish();
			listview.stopRefreshData();
			clickable = true;
		}
	};

	public void widgetClick(android.view.View v) {
		switch (v.getId()) {
		case R.id.one:
			if (!clickable) {
				break;
			}
			clickable = false;
			one.setTextColor(getResources().getColor(R.color.white));
			one.setBackgroundResource(R.drawable.tab_center);
			two.setTextColor(getResources().getColor(R.color.app_blue));
			two.setBackgroundResource(R.drawable.invest_two);
			three.setTextColor(getResources().getColor(R.color.app_blue));
			three.setBackgroundResource(R.drawable.invest_three);
			four.setTextColor(getResources().getColor(R.color.app_blue));
			four.setBackgroundResource(R.drawable.invest_four);
			url = AppConstants.INVEST_ORDER;
			state = 1;
			getData(1);
			break;
		case R.id.two:
			if (!clickable) {
				break;
			}
			clickable = false;
			one.setTextColor(getResources().getColor(R.color.app_blue));
			one.setBackgroundResource(R.drawable.invest_one);
			two.setTextColor(getResources().getColor(R.color.white));
			two.setBackgroundResource(R.drawable.tab_center);
			three.setTextColor(getResources().getColor(R.color.app_blue));
			three.setBackgroundResource(R.drawable.invest_three);
			four.setTextColor(getResources().getColor(R.color.app_blue));
			four.setBackgroundResource(R.drawable.invest_four);
			url = AppConstants.INVEST_PENDING;
			state = 2;
			getData(1);
			break;
		case R.id.three:
			if (!clickable) {
				break;
			}
			clickable = false;
			one.setTextColor(getResources().getColor(R.color.app_blue));
			one.setBackgroundResource(R.drawable.invest_one);
			two.setTextColor(getResources().getColor(R.color.app_blue));
			two.setBackgroundResource(R.drawable.invest_two);
			three.setTextColor(getResources().getColor(R.color.white));
			three.setBackgroundResource(R.drawable.tab_center);
			four.setTextColor(getResources().getColor(R.color.app_blue));
			four.setBackgroundResource(R.drawable.invest_four);
			url = AppConstants.INVEST_CLOSED;
			state = 3;
			getData(1);
			break;
		case R.id.four:
			if (!clickable) {
				break;
			}
			clickable = false;
			one.setTextColor(getResources().getColor(R.color.app_blue));
			one.setBackgroundResource(R.drawable.invest_one);
			two.setTextColor(getResources().getColor(R.color.app_blue));
			two.setBackgroundResource(R.drawable.invest_two);
			three.setTextColor(getResources().getColor(R.color.app_blue));
			three.setBackgroundResource(R.drawable.invest_three);
			four.setTextColor(getResources().getColor(R.color.white));
			four.setBackgroundResource(R.drawable.tab_right);
			url = AppConstants.INVEST_ABORTED;
			state = 4;
			getData(1);
			break;
		}
	}
}
