package com.nangua.xiaomanjflc.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.louding.frame.KJActivity;
import com.louding.frame.KJHttp;
import com.louding.frame.http.HttpCallBack;
import com.louding.frame.http.HttpParams;
import com.louding.frame.ui.BindView;
import com.nangua.xiaomanjflc.AppConstants;
import com.nangua.xiaomanjflc.AppVariables;
import com.nangua.xiaomanjflc.adapter.IntegralMallAdapter;
import com.nangua.xiaomanjflc.support.UIHelper;
import com.nangua.xiaomanjflc.widget.FontTextView;
import com.nangua.xiaomanjflc.R;

/**
 * 积分商城
 * */
public class IntegralMallActivity extends KJActivity {

	@BindView(id = R.id.one, click = true)
	private FontTextView one;
	@BindView(id = R.id.two, click = true)
	private FontTextView two;

	private GridView gv_gridview1;

	private SimpleAdapter gridviewAdapter;
	private List<Map<String, Object>> data_list;

	private String type = "";

	private HttpParams params;
	private KJHttp http;

	@Override
	public void setRootView() {
		setContentView(R.layout.activity_integral_mall);
		UIHelper.setTitleView(this, "我的积分", "积分商城");

		gv_gridview1 = (GridView) findViewById(R.id.gv_gridview);

	}

	@Override
	public void initData() {

		params = new HttpParams();
		http = new KJHttp();
		getData();
	}

	private void getData() {
		params.put("sid", AppVariables.sid);
		params.put("type", type);
		http.post(AppConstants.MY_INTEGRAL_MALL, params, httpCallback);
	}

	private HttpCallBack httpCallback = new HttpCallBack(
			IntegralMallActivity.this) {

		@Override
		public void onSuccess(String t) {
			try {
				JSONObject ret = new JSONObject(t);
				JSONArray mallArray = ret.getJSONArray("mallMapList");

				data_list = new ArrayList<Map<String, Object>>();
				for (int i = 0; i < mallArray.length(); i++) {
					JSONObject o = (JSONObject) mallArray.get(i);
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("img_path", o.getString("img_path"));
					map.put("cost_point", o.getString("cost_point"));
					map.put("description", o.getString("description"));
					map.put("title", o.getString("title"));
					map.put("cost_money", o.getString("cost_money"));
					map.put("red_packet_id", o.getString("red_packet_id"));
					map.put("commodity_id", o.getString("commodity_id"));
					data_list.add(map);
				}
				String[] from = { "img_path", "cost_point", "description" };
				int[] to = { R.id.iv_image, R.id.tv_integral, R.id.tv_describe };
				gridviewAdapter = new IntegralMallAdapter(
						IntegralMallActivity.this, data_list,
						R.layout.item_integral_mall, from, to);
				gv_gridview1.setAdapter(gridviewAdapter);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public void onFinish() {
			super.onFinish();
		}
	};

	public void widgetClick(android.view.View v) {
		switch (v.getId()) {
		case R.id.one:
			one.setTextColor(getResources().getColor(R.color.white));
			one.setBackgroundResource(R.drawable.tab_left);
			two.setTextColor(getResources().getColor(R.color.app_blue));
			two.setBackgroundResource(R.drawable.red_two);
			type = "";
			params.put("type", type);
			getData();
			break;
		case R.id.two:
			two.setTextColor(getResources().getColor(R.color.white));
			two.setBackgroundResource(R.drawable.tab_center);
			one.setTextColor(getResources().getColor(R.color.app_blue));
			one.setBackgroundResource(R.drawable.red_one);
			type = "1";
			params.put("type", type);
			getData();
			break;
		}
	}

}
