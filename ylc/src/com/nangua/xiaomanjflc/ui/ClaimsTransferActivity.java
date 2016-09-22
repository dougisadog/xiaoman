package com.nangua.xiaomanjflc.ui;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Intent;
import android.view.View;
import android.widget.FrameLayout;
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
import com.nangua.xiaomanjflc.bean.Transfer;
import com.nangua.xiaomanjflc.bean.TransferList;
import com.nangua.xiaomanjflc.support.UIHelper;
import com.nangua.xiaomanjflc.widget.FontTextView;
import com.nangua.xiaomanjflc.R;

public class ClaimsTransferActivity extends KJActivity {

	@BindView(id = R.id.one, click = true)
	private FontTextView one;
	@BindView(id = R.id.two, click = true)
	private FontTextView two;
	@BindView(id = R.id.three, click = true)
	private FontTextView three;
	@BindView(id = R.id.transfer01, click = true)
	private FontTextView transfer1;
	@BindView(id = R.id.transfer02, click = true)
	private FontTextView transfer2;
	@BindView(id = R.id.transfer03, click = true)
	private FontTextView transfer03;
	@BindView(id = R.id.fl_01)
	private FrameLayout fl_01;
	@BindView(id = R.id.fl_02)
	private FrameLayout fl_02;
	@BindView(id = R.id.fl_03)
	private FrameLayout fl_03;

	private KJHttp http;
	private HttpParams params;

	private List<Transfer> transferData;

	@BindView(id = R.id.listview01)
	private KJListView listview01;
	private CommonAdapter<Transfer> adapter01;

	@BindView(id = R.id.listview02)
	private KJListView listview02;
	private CommonAdapter<Transfer> adapter02;

	@BindView(id = R.id.listview03)
	private KJListView listview03;
	private CommonAdapter<Transfer> adapter03;

	private int page = 1;
	private String url;
	private int state;
	private boolean noMoreData;
	private boolean clickable = true;

	@Override
	public void setRootView() {
		setContentView(R.layout.activity_claims_transfer);
		UIHelper.setTitleView(this, "我的账户", "债权转让");
	}

	@Override
	public void initData() {
		super.initData();
		url = AppConstants.TRANSFER_CAN;
		state = 1;
		transferData = new ArrayList<Transfer>();
		http = new KJHttp();
		params = new HttpParams();
//		getData(page);
	}

	private void getData(int page) {
		params.put("sid", AppVariables.sid);
		http.post(url, params, httpCallback);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		getData(page);
	}

	@Override
	public void initWidget() {
		super.initWidget();
		// 可转让
		adapter01 = new CommonAdapter<Transfer>(ClaimsTransferActivity.this,
				R.layout.item_transfer_can) {

			@Override
			public void canvas(ViewHolder holder, Transfer item) {
				holder.addClick(R.id.invest_protocol);

				if(item.getProducts_title() != null)
					holder.setText(R.id.name, item.getProducts_title(), false);
				if(item.getFinance_interest_rate() != null)
					holder.setText(R.id.price, item.getFinance_interest_rate() + "%", false);
				if(item.getTender_amount() != null)
					holder.setText(R.id.rate, item.getTender_amount() + "元", false);
				if(item.getLave_date() != null)
					holder.setText(R.id.lastReturn, item.getLave_date() + "天", false);
				if(item.getFinance_start_interest_date() != null)
					holder.setText(R.id.repayTime, "起息日：" + item.getFinance_start_interest_date(), false);
				if(item.getFinance_end_interest_date() != null)
					holder.setText(R.id.endTime, "到期日：" + item.getFinance_end_interest_date(), false);

			}

			@Override
			public void click(int id, List<Transfer> list, int position,
					ViewHolder viewHolder) {
				switch (id) {
				case R.id.invest_protocol:
					Intent intent = new Intent(ClaimsTransferActivity.this,
							TransferRuleActivity.class);
					intent.putExtra("flag", "");
					intent.putExtra("products_title", list.get(position)
							.getProducts_title());
					intent.putExtra("oid_platform_products_id",
							list.get(position).getOid_platform_products_id());
					intent.putExtra("oid_tender_id", list.get(position)
							.getOid_tender_id());
					intent.putExtra("tender_from", list.get(position)
							.getTender_from());
					intent.putExtra("lave_date", list.get(position)
							.getLave_date());
					intent.putExtra("finance_interest_rate", list.get(position)
							.getFinance_interest_rate());
					intent.putExtra("finance_start_interest_date",
							list.get(position).getFinance_start_interest_date());
					intent.putExtra("tender_amount", list.get(position)
							.getTender_amount());
					startActivity(intent);
					break;
				}
			}
		};
		adapter01.setList(transferData);
		listview01.setAdapter(adapter01);
		listview01.setOnRefreshListener(refreshListener);
		listview01.setEmptyView(findViewById(R.id.empty01));

		// 转让中
		adapter02 = new CommonAdapter<Transfer>(ClaimsTransferActivity.this,
				R.layout.item_transfer_ing) {
			@Override
			public void canvas(ViewHolder holder, Transfer item) {
				if(item.getProducts_title() != null)
					holder.setText(R.id.name, item.getProducts_title(), false);
				if(item.getFinance_interest_rate() != null)
					holder.setText(R.id.price, item.getFinance_interest_rate() + "%", false);
				if(item.getTransfer_capital() != null)
					holder.setText(R.id.rate, item.getTransfer_capital() + "元", false);
				if(item.getFinance_period() != null)
					holder.setText(R.id.lastReturn, item.getFinance_period() + "个月", false);
				if(item.getTransfer_time() != null)
					holder.setText(R.id.repayTime, "转让挂牌日期:" + item.getTransfer_time(), false);
				if(item.getTransfer_period() != null)
					holder.setText(R.id.endTime, "转让下架日期:" + item.getTransfer_period(), false);
			}

			@Override
			public void click(int id, List<Transfer> list, int position,
					ViewHolder viewHolder) {
			}
		};
		adapter02.setList(transferData);
		listview02.setAdapter(adapter02);
		listview02.setOnRefreshListener(refreshListener);
		listview02.setEmptyView(findViewById(R.id.empty02));

		// 已转让
		adapter03 = new CommonAdapter<Transfer>(ClaimsTransferActivity.this,
				R.layout.item_transfer_already) {
			@Override
			public void canvas(ViewHolder holder, Transfer item) {
				if(item.getProducts_title() != null)
					holder.setText(R.id.name, item.getProducts_title(), false);
				if(item.getTransfer_success_time() != null)
					holder.setText(R.id.repayTime, item.getTransfer_success_time(), false);
				if(item.getTransfer_capital_yes() != null)
					holder.setText(R.id.tv_price, item.getTransfer_capital_yes() + "元", false);
			}

			@Override
			public void click(int id, List<Transfer> list, int position,
					ViewHolder viewHolder) {
			}
		};
		adapter03.setList(transferData);
		listview03.setAdapter(adapter03);
		listview03.setOnRefreshListener(refreshListener);
		listview03.setEmptyView(findViewById(R.id.empty03));

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

	private HttpCallBack httpCallback = new HttpCallBack(
			ClaimsTransferActivity.this) {

		@Override
		public void failure(JSONObject ret) {
			super.failure(ret);
		}

		@Override
		public void success(JSONObject ret) {
			super.success(ret);
			try {
				JSONArray items = ret.getJSONArray("orders");

				transferData = new TransferList(items).getTransferList();
				listview01.hideFooter();
				listview02.hideFooter();
				listview03.hideFooter();
				
				adapter01.setList(transferData);
				adapter02.setList(transferData);
				adapter03.setList(transferData);

			} catch (Exception e) {
				e.printStackTrace();
				Toast.makeText(ClaimsTransferActivity.this,
						R.string.app_data_error, Toast.LENGTH_SHORT).show();
			}
		}

		@SuppressWarnings("deprecation")
		public void onFinish() {
			super.onFinish();
			listview01.stopRefreshData();
			listview02.stopRefreshData();
			listview03.stopRefreshData();
			clickable = true;
		}
	};

	public void widgetClick(android.view.View v) {
		Intent intent;

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
			url = AppConstants.TRANSFER_CAN;

			fl_01.setVisibility(View.VISIBLE);
			fl_02.setVisibility(View.GONE);
			fl_03.setVisibility(View.GONE);

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
			url = AppConstants.TRANSFER_ING;

			fl_01.setVisibility(View.GONE);
			fl_02.setVisibility(View.VISIBLE);
			fl_03.setVisibility(View.GONE);

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
			url = AppConstants.TRANSFER_ALREADY;

			fl_01.setVisibility(View.GONE);
			fl_02.setVisibility(View.GONE);
			fl_03.setVisibility(View.VISIBLE);

			state = 3;
			getData(1);
			break;
		case R.id.transfer01:
			intent = new Intent(ClaimsTransferActivity.this,
					TransferRuleActivity.class);
			intent.putExtra("flag", "flag");
			startActivity(intent);
			break;
		case R.id.transfer02:
			intent = new Intent(ClaimsTransferActivity.this,
					TransferRuleActivity.class);
			intent.putExtra("flag", "flag");
			startActivity(intent);
			break;
		case R.id.transfer03:
			intent = new Intent(ClaimsTransferActivity.this,
					TransferRuleActivity.class);
			intent.putExtra("flag", "flag");
			startActivity(intent);
			break;
		}
	};

}
