package com.nangua.xiaomanjflc.ui;

import java.util.List;

import org.json.JSONObject;

import android.content.Intent;
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
import com.nangua.xiaomanjflc.bean.Integral;
import com.nangua.xiaomanjflc.bean.IntegralList;
import com.nangua.xiaomanjflc.support.UIHelper;
import com.nangua.xiaomanjflc.R;

/**
 * 积分明细、收入、支出、已过期
 * */
public class IntegralDetailActivity extends KJActivity {

	@BindView(id = R.id.listview)
	private KJListView listview;

	private String type;

	private int page = 1;

	private HttpParams params;
	private KJHttp http;

	private boolean noMoreData;

	private List<Integral> data;
	private CommonAdapter<Integral> adapter;

	@Override
	public void setRootView() {
		setContentView(R.layout.activity_integral_detail);

		Intent intent = getIntent();
		type = intent.getStringExtra("type");

		if ( type.equals("1") ) 
		{
			UIHelper.setTitleView(this, "我的积分", "积分明细");
		}
		else if ( type.equals("2") ) 
		{
			UIHelper.setTitleView(this, "我的积分", "积分收入");
		}
		else if ( type.equals("3") )
		{
			UIHelper.setTitleView(this, "我的积分", "积分支出");
		}
		else if ( type.equals("4") ) 
		{
			UIHelper.setTitleView(this, "我的积分", "已过期");
		}
	}

	@Override
	public void initData() {
		super.initData();
		params = new HttpParams();
		http = new KJHttp();

		getData(page);
	}

	private void getData(int page) {
		params.put("sid", AppVariables.sid);
		params.put("page", page);
		params.put("type", type);
		http.post(AppConstants.MY_INTEGRAL_DETAIL, params, httpCallback);
	}

	private HttpCallBack httpCallback = new HttpCallBack(
			IntegralDetailActivity.this) {

		@Override
		public void onSuccess(String t) {
			try {
				JSONObject ret = new JSONObject(t);
				JSONObject invitations = ret.getJSONObject("pager");
				page = invitations.getInt("page");
				int maxPage = invitations.getInt("maxPage");
				if (page >= maxPage) {
					listview.hideFooter();
					noMoreData = true;
				} else {
					listview.showFooter();
					noMoreData = false;
				}
				if (page < 2) {
					data = new IntegralList(ret.getJSONArray("integralMapList"))
							.getIntegrals();
				} else {
					data = new IntegralList(data,
							ret.getJSONArray("integralMapList")).getIntegrals();
				}
				adapter.setList(data);
			} catch (Exception e) {
				e.printStackTrace();
				Toast.makeText(IntegralDetailActivity.this,
						R.string.app_data_error, Toast.LENGTH_SHORT).show();
			}
		}

		public void onFinish() {
			super.onFinish();
			listview.stopRefreshData();
		}
	};

	@Override
	public void initWidget() {
		super.initWidget();
		adapter = new CommonAdapter<Integral>(IntegralDetailActivity.this,
				R.layout.item_integral_detail) {

			@Override
			public void canvas(ViewHolder holder, Integral item) {
				if (item.getDate() != null) {
					holder.setText(R.id.tv_date, item.getDate(), false);
					holder.setText(R.id.tv_integral, item.getPoint(), false);
					holder.setText(R.id.tv_type, item.getPoint_description(),
							false);
				} else {
					holder.setText(R.id.tv_date, item.getBatch_run_time(), false);
					holder.setText(R.id.tv_integral, item.getBefor_point(), false);
					holder.setText(R.id.tv_type, "已过期", false);
				}
			}

			@Override
			public void click(int id, List<Integral> list, int position,
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
			if (!noMoreData) {
				getData(page + 1);
			}
		}
	};

}
