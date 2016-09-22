package com.nangua.xiaomanjflc.ui;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.louding.frame.KJActivity;
import com.louding.frame.KJHttp;
import com.louding.frame.http.HttpCallBack;
import com.louding.frame.http.HttpParams;
import com.louding.frame.ui.BindView;
import com.nangua.xiaomanjflc.AppConstants;
import com.nangua.xiaomanjflc.AppVariables;
import com.nangua.xiaomanjflc.adapter.CommonAdapter;
import com.nangua.xiaomanjflc.adapter.ViewHolder;
import com.nangua.xiaomanjflc.bean.Red;
import com.nangua.xiaomanjflc.bean.RedList;
import com.nangua.xiaomanjflc.support.UIHelper;
import com.nangua.xiaomanjflc.widget.FontTextView;
import com.nangua.xiaomanjflc.R;

public class UseRedActivity extends KJActivity {

	@BindView(id = R.id.listview)
	private ListView listview;

	@BindView(id = R.id.confirm, click = true)
	private FontTextView confirm;

	@BindView(id = R.id.footer_hint)
	private FontTextView hint;

	private KJHttp http;
	private HttpParams params;

	private CommonAdapter<Red> adapter;
	private List<Red> data;

	private int page = 1;
	private String status = "1";
	private int price = 0;
	private int checkedid = 0;
	private int amount = 0;
	private int productid = 0;

	@Override
	public void setRootView() {
		setContentView(R.layout.activity_usecash);
		UIHelper.setTitleView(this, "投标", "使用现金券");
	}

	@Override
	public void initData() {
		super.initData();
		Intent intent = getIntent();
		amount = intent.getIntExtra("amount", 0);
		productid = intent.getIntExtra("productid", 0);
		data = new ArrayList<Red>();
		http = new KJHttp();
		params = new HttpParams();
		getData(page);
	}

	private void getData(int page) {
		params.put("page", page);
		params.put("status", status);
		params.put("amount", amount);
		params.put("productid", productid);
		params.put("sid", AppVariables.sid);
		http.post(AppConstants.NEWCASH, params, httpCallback);
	}

	@Override
	public void widgetClick(View v) {
		super.widgetClick(v);
		switch (v.getId()) {
		case R.id.confirm:
			Intent intent = new Intent();
			intent.putExtra("cash", checkedid);
			intent.putExtra("price", price);
			setResult(1, intent);
			finish();
			break;
		}
	}

	@Override
	public void initWidget() {
		super.initWidget();
		adapter = new CommonAdapter<Red>(UseRedActivity.this,
				R.layout.item_use_red) {
			@Override
			public void canvas(ViewHolder holder, Red item) {
				holder.addClick(R.id.item);
				ImageView i = holder.getView(R.id.cash_check);
				if (item.isChecked()) {
					i.setImageResource(R.drawable.checkbox);
				} else {
					i.setImageResource(R.drawable.checkbox_none);
				}
				holder.setText(R.id.cash_price, item.getCash_price(), false);
				holder.setText(R.id.cash_desc, item.getCash_desc(), false);
				holder.setText(R.id.cash_span, "有效时间：" + item.getActive_time()
						+ "至" + item.getExpire_time(), false);
			}

			@Override
			public void click(int id, List<Red> list, int position,
					ViewHolder viewHolder) {
				if (checkedid == list.get(position).getId()) {
					Red red = list.get(position);
					red.setChecked(false);
					list.set(position, red);
					checkedid = 0;
					price = 0;
					hint.setText("已选0张，可抵扣0元");
				} else {
					for (int i = 0; i < list.size(); i++) {
						Red red = list.get(i);
						red.setChecked(false);
						list.set(i, red);
					}
					Red red = list.get(position);
					red.setChecked(true);
					list.set(position, red);
					checkedid = list.get(position).getId();
					price = Integer
							.parseInt(list.get(position).getCash_price());
					hint.setText("已选1张，可抵扣" + price + "元");
				}
				adapter.setList(data);
			}
		};
		adapter.setList(data);
		listview.setAdapter(adapter);
		listview.setEmptyView(findViewById(R.id.empty));
	}

	// private KJRefreshListener refreshListener = new KJRefreshListener() {
	// @Override
	// public void onRefresh() {
	// getData(1);
	// }
	//
	// @Override
	// public void onLoadMore() {
	// getData(page + 1);
	// }
	// };

	private HttpCallBack httpCallback = new HttpCallBack(UseRedActivity.this) {
		public void success(org.json.JSONObject ret) {
			try {
				// page = ret.getInt("page");
				// if (page < 2) {
				data = new RedList(ret.getJSONArray("val")).getList();
				// } else {
				// data = new RedList(data, ret.getJSONArray("val")).getList();
				// }
				adapter.setList(data);
			} catch (Exception e) {
				e.printStackTrace();
				Toast.makeText(UseRedActivity.this, R.string.app_data_error,
						Toast.LENGTH_SHORT).show();
			}
		}

		public void onFinish() {
			super.onFinish();
			// listview.stopRefreshData();
		}
	};

	public void onBackPressed() {
		finish();
	}
}
