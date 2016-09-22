package com.nangua.xiaomanjflc.ui;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.view.View;
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
import com.nangua.xiaomanjflc.bean.Transaction;
import com.nangua.xiaomanjflc.bean.TransactionList;
import com.nangua.xiaomanjflc.support.UIHelper;
import com.nangua.xiaomanjflc.utils.FormatUtils;
import com.nangua.xiaomanjflc.widget.FontTextView;
import com.nangua.xiaomanjflc.R;

public class TransactionActivity extends KJActivity {

	@BindView(id = R.id.listview)
	private KJListView listview;

	@BindView(id = R.id.title_right, click = true)
	private FontTextView title_right;

	private KJHttp http;
	private HttpParams params;

	private CommonAdapter<Transaction> adapter;
	private List<Transaction> data;

	private int page = 1;
	private boolean noMoreData;

	@Override
	public void setRootView() {
		setContentView(R.layout.activity_listview);
		UIHelper.setTitleView(this, "我的账户", "交易记录");
	}

	@Override
	public void initData() {
		super.initData();

		title_right.setText("筛选");

		data = new ArrayList<Transaction>();
		http = new KJHttp();
		params = new HttpParams();
		getData(page);
	}

	private void getData(int page) {
		params.put("page", page);
		params.put("sid", AppVariables.sid);
		http.post(AppConstants.TRANSACTION, params, httpCallback);
	}

	@Override
	public void initWidget() {
		super.initWidget();
		adapter = new CommonAdapter<Transaction>(TransactionActivity.this,
				R.layout.item_trans) {
			@Override
			public void canvas(ViewHolder holder, Transaction item) {
				System.out.println(item.toString());
				holder.setText(R.id.transactionId,
						"流水账号：" + item.getTransactionId(), false);
				holder.setText(R.id.currentAsset, FormatUtils.priceFormat(Long
						.parseLong(item.getBeginningBalance())), false);
				holder.setText(R.id.amount, FormatUtils.priceFormat(Long
						.parseLong(item.getOperationAmount())), false);
				holder.setText(R.id.currentAvailable, FormatUtils
						.priceFormat(Long.parseLong(item.getAvailable())),
						false);
				holder.setText(R.id.createTime, "时间：" + item.getCreateTime(),
						false);
				holder.setText(R.id.transactionType,
						"交易类型：" + item.getTransactionType(), false);
			}

			@Override
			public void click(int id, List<Transaction> list, int position,
					ViewHolder viewHolder) {
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
			System.out.println("加载更多============");
			if (!noMoreData) {
				getData(page + 1);
			}
		}
	};

	private HttpCallBack httpCallback = new HttpCallBack(
			TransactionActivity.this) {
		public void success(org.json.JSONObject ret) {
			try {
				JSONObject articles = ret.getJSONObject("balanceLogList");
				page = articles.getInt("currentPage");
				int maxPage = articles.getJSONObject("pager").getInt("maxPage");
				if (page >= maxPage) {
					listview.hideFooter();
					noMoreData = true;
				} else {
					listview.showFooter();
					noMoreData = false;
				}
				System.out.println("当前页面=====>" + page);
				if (page == 1) {
					data = new TransactionList(articles.getJSONArray("items"))
							.getList();
				} else {
					data = new TransactionList(data,
							articles.getJSONArray("items")).getList();
				}
				System.out.println("data=========>" + data);
				adapter.setList(data);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("数据解析错误。");
				Toast.makeText(TransactionActivity.this,
						R.string.app_data_error, Toast.LENGTH_SHORT).show();
			}
		}

		public void onFinish() {
			super.onFinish();
			listview.stopRefreshData();
		}
	};

	@Override
	public void widgetClick(View v) {
		super.widgetClick(v);

		switch (v.getId()) {
		case R.id.title_right:

			break;
		}
	}

}
