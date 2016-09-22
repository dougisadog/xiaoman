package com.nangua.xiaomanjflc.ui;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
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
import com.nangua.xiaomanjflc.bean.Red;
import com.nangua.xiaomanjflc.bean.RedList;
import com.nangua.xiaomanjflc.support.UIHelper;
import com.nangua.xiaomanjflc.widget.FontTextView;
import com.nangua.xiaomanjflc.R;

public class RedActivity extends KJActivity {

	@BindView(id = R.id.listview)
	private KJListView listview;
	@BindView(id = R.id.one, click = true)
	private FontTextView one;
	@BindView(id = R.id.two, click = true)
	private FontTextView two;
	@BindView(id = R.id.three, click = true)
	private FontTextView three;

	private KJHttp http;
	private HttpParams params;

	private CommonAdapter<Red> adapter;
	private List<Red> data;

	private int page = 1;
	private String status = "1";
	private boolean noMoreData;

	@Override
	public void setRootView() {
		setContentView(R.layout.activity_red);
		UIHelper.setTitleView(this, "我的账户", "现金券管理");
	}

	@Override
	public void initData() {
		super.initData();
		data = new ArrayList<Red>();
		http = new KJHttp();
		params = new HttpParams();
		getData(page);
	}

	private void getData(int page) {
		params.put("page", page);
		params.put("status", status);
		params.put("sid", AppVariables.sid);
		http.post(AppConstants.RED, params, httpCallback);
	}

	@Override
	public void initWidget() {
		super.initWidget();
		adapter = new CommonAdapter<Red>(RedActivity.this, R.layout.item_red) {
			@Override
			public void canvas(ViewHolder holder, Red item) {
				if (status.equals("1")) {
					holder.addClick(R.id.item);
					holder.addClick(R.id.status);
					ImageView l = holder.getView(R.id.bgimg);
					l.setImageResource(R.drawable.coupon_red);
					FontTextView t = holder.getView(R.id.status);
					t.setTextColor(getResources().getColor(R.color.red));
					t.setText("立即使用>");
					holder.setText(R.id.cash_title, "有效时间：", false);
					holder.setText(R.id.get_time, item.getActive_time() + "至",
							false);
					FontTextView a = holder.getView(R.id.active_time);
					a.setText(item.getExpire_time());
					a.setVisibility(View.VISIBLE);
				} else if (status.equals("2")) {
					ImageView l = holder.getView(R.id.bgimg);
					l.setImageResource(R.drawable.coupon_gray);
					FontTextView t = holder.getView(R.id.status);
					t.setTextColor(getResources().getColor(R.color.font_gray));
					t.setText("已使用");
					holder.setText(R.id.cash_title, "使用时间：", false);
					holder.setText(R.id.get_time, item.getUsed_time()[0], false);
					FontTextView a = holder.getView(R.id.active_time);
					a.setText(item.getUsed_time()[1]);
					a.setVisibility(View.VISIBLE);
				} else {
					ImageView l = holder.getView(R.id.bgimg);
					l.setImageResource(R.drawable.coupon_gray);
					FontTextView t = holder.getView(R.id.status);
					t.setTextColor(getResources().getColor(R.color.font_gray));
					t.setText("已过期");
					holder.setText(R.id.cash_title, "过期时间：", false);
					holder.setText(R.id.get_time, item.getExpire_time(), false);
					FontTextView a = holder.getView(R.id.active_time);
					a.setVisibility(View.GONE);
				}
				holder.setText(R.id.cash_price, "￥" + item.getCash_price(),
						false);
				holder.setText(R.id.cash_desc, item.getCash_desc(), false);
			}

			@Override
			public void click(int id, List<Red> list, int position,
					ViewHolder viewHolder) {
				if (!status.equals("1")) return;
				Intent intent = new Intent(RedActivity.this, MainActivity.class);
				intent.putExtra("tab", 0);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				RedActivity.this.finish();
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

	private HttpCallBack httpCallback = new HttpCallBack(RedActivity.this) {
		public void success(org.json.JSONObject ret) {
			try {
				JSONObject articles = ret.getJSONObject("cash");
				page = articles.getInt("currentPage");
				int maxPage = articles.getJSONObject("pager").getInt("maxPage");
				JSONArray arr = articles.getJSONArray("items");
				if (null == arr || arr.length() == 0) {
					listview.hideFooter();
					noMoreData = true;
				} else {
					listview.showFooter();
					noMoreData = false;
				}
				if (page < 2) {
					data = new RedList(arr)
							.getList();
				} else {
					data = new RedList(data, arr)
							.getList();
				}
				adapter.setList(data);
			} catch (Exception e) {
				e.printStackTrace();
				Toast.makeText(RedActivity.this, R.string.app_data_error,
						Toast.LENGTH_SHORT).show();
			}
		}

		public void onFinish() {
			super.onFinish();
			listview.stopRefreshData();
		}
	};

	public void widgetClick(android.view.View v) {
		switch (v.getId()) {
		case R.id.one:
			one.setTextColor(getResources().getColor(R.color.white));
			one.setBackgroundResource(R.drawable.tab_left);
			two.setTextColor(getResources().getColor(R.color.app_blue));
			two.setBackgroundResource(R.drawable.red_two);
			three.setTextColor(getResources().getColor(R.color.app_blue));
			three.setBackgroundResource(R.drawable.red_three);
			status = "1";
			getData(1);
			break;
		case R.id.two:
			two.setTextColor(getResources().getColor(R.color.white));
			two.setBackgroundResource(R.drawable.tab_center);
			one.setTextColor(getResources().getColor(R.color.app_blue));
			one.setBackgroundResource(R.drawable.red_one);
			three.setTextColor(getResources().getColor(R.color.app_blue));
			three.setBackgroundResource(R.drawable.red_three);
			status = "2";
			getData(1);
			break;
		case R.id.three:
			three.setTextColor(getResources().getColor(R.color.white));
			three.setBackgroundResource(R.drawable.tab_right);
			two.setTextColor(getResources().getColor(R.color.app_blue));
			two.setBackgroundResource(R.drawable.red_two);
			one.setTextColor(getResources().getColor(R.color.app_blue));
			one.setBackgroundResource(R.drawable.red_one);
			status = "99";
			getData(1);
			break;
		}
	}

}
