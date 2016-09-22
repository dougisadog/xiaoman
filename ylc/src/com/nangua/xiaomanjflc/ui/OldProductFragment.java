package com.nangua.xiaomanjflc.ui;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.louding.frame.KJHttp;
import com.louding.frame.http.HttpCallBack;
import com.louding.frame.http.HttpParams;
import com.louding.frame.widget.KJListView;
import com.louding.frame.widget.KJRefreshListener;
import com.nangua.xiaomanjflc.AppConstants;
import com.nangua.xiaomanjflc.adapter.SlideAdapter;
import com.nangua.xiaomanjflc.adapter.ViewHolder;
import com.nangua.xiaomanjflc.bean.oldProduct;
import com.nangua.xiaomanjflc.bean.oldProductList;
import com.nangua.xiaomanjflc.widget.CircleProgressBar;
import com.nangua.xiaomanjflc.widget.FontTextView;
import com.nangua.xiaomanjflc.R;

public class OldProductFragment extends Fragment {

	private FontTextView tabone;
	private FontTextView tabtwo;

	private KJListView listview;
	private KJHttp http;
	private HttpParams params;

	private SlideAdapter<oldProduct> adapter;
	private List<oldProduct> data;

	private int page = 1;
	private int temp = 0;
	private int state = 1;
	private boolean noMoreData;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		data = new ArrayList<oldProduct>();
		http = new KJHttp();
		params = new HttpParams();
		getData(1);
		View v = inflater.inflate(R.layout.fragment_product, null);
		initView(v);
		listview = (KJListView) v.findViewById(R.id.listview);
		adapter = new SlideAdapter<oldProduct>(getActivity(),
				R.layout.item_oldproduct) {
			@Override
			public void canvas(ViewHolder holder, oldProduct item) {
				holder.addClick(R.id.item);
				holder.setText(R.id.name, item.getName(), false);
				holder.setText(R.id.gain, item.getGain() + "%", false);
				holder.setText(R.id.deadline, item.getDeadline(), false);
				holder.setText(R.id.method, item.getRepayMethod(), false);
				String st = "";
				switch (item.getNewstatus()) {
				case 1:
					st = "还款中";
					break;
				case 2:
					st = "已售罄";
					break;
				case 3:
					st = "预约";
					break;
				case 4:
					st = "已结束";
					break;
				case 5:
					st = "正在售卖";
					break;
				case 6:
					st = "已还款";
					break;
				}
				holder.setText(R.id.status, st, false);
				if (item.getPercentage() == 100) {
					holder.setText(R.id.percentage, "已满标", false);
					holder.setText(R.id.percentagetxt, "", false);
				} else {
					holder.setText(R.id.percentage, "", false);
					holder.setText(R.id.percentagetxt, item.getPercentage()
							+ "%", false);
				}
				CircleProgressBar pb = holder.getView(R.id.percentagepb);
				pb.setProgress(item.getPercentage());
			}

			@Override
			public void click(int id, List<oldProduct> list, int position,
					ViewHolder viewHolder) {
				Intent intent = new Intent(getActivity(), DetailActivity.class);
				intent.putExtra("id", list.get(position).getId());
				startActivity(intent);
			}
		};
		adapter.setList(data);
		listview.setAdapter(adapter);
		listview.setOnRefreshListener(refreshListener);
		return v;
	}

	private void initView(View v) {
		tabone = (FontTextView) v.findViewById(R.id.one);
		tabtwo = (FontTextView) v.findViewById(R.id.two);

		tabone.setOnClickListener(listener);
		tabtwo.setOnClickListener(listener);
	}

	private View.OnClickListener listener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			switch (view.getId()) {
			case R.id.one:
				tabone.setTextColor(getResources().getColor(R.color.white));
				tabone.setBackgroundResource(R.drawable.tab_left);
				tabtwo.setTextColor(getResources().getColor(R.color.app_blue));
				tabtwo.setBackgroundResource(R.drawable.invest_four);
				state = 1;
				break;
			case R.id.two:
				tabone.setTextColor(getResources().getColor(R.color.blue));
				tabone.setBackgroundResource(R.drawable.invest_one);
				tabtwo.setTextColor(getResources().getColor(R.color.white));
				tabtwo.setBackgroundResource(R.drawable.tab_right);
				state = 2;
				break;
			}
		}
	};

	private void getData(int page) {
		params.put("page", page);
		params.put("sid", "");
		http.post(AppConstants.DIRECT, params, httpCallback);
	}

	private KJRefreshListener refreshListener = new KJRefreshListener() {
		@Override
		public void onRefresh() {
			getData(1);
		}

		@Override
		public void onLoadMore() {
			System.out.println("加载更多============>");
			if (!noMoreData) {
				getData(page + 1);
			}
		}
	};

	private HttpCallBack httpCallback = new HttpCallBack(getActivity()) {
		public void onSuccess(String t) {
			Log.d("product", t);
			System.out.println("product=====>" + t);
			try {
				JSONObject ret = new JSONObject(t);
				int state = ret.getInt("status");
				if (state != 0) {
					listview.hideFooter();
					noMoreData = true;
				} else {
					listview.showFooter();
					noMoreData = false;
					page = ret.getInt("current_page");
					System.out.println("当前页面=====>" + page);
					if (page == 1) {
						// 因为后来要求改变，所以需要数据第一位为空。在构造方法里面做处理。
						data = new oldProductList(
								ret.getJSONArray("product_newb_list"))
								.getOldProducts();
					}
					data = new oldProductList(data,
							ret.getJSONArray("product_list")).getOldProducts();
					System.out.println("data=========>" + data);
					adapter.setList(data);
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("数据解析错误。");
				Toast.makeText(getActivity(), R.string.app_data_error,
						Toast.LENGTH_SHORT).show();
			}
		};

		public void onFinish() {
			listview.stopRefreshData();
		};
	};

}
