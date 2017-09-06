package com.nangua.xiaomanjflc.ui.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

import com.louding.frame.KJHttp;
import com.louding.frame.http.HttpCallBack;
import com.louding.frame.http.HttpParams;
import com.louding.frame.utils.DensityUtils;
import com.louding.frame.widget.KJListView;
import com.louding.frame.widget.KJRefreshListener;
import com.nangua.xiaomanjflc.AppConstants;
import com.nangua.xiaomanjflc.AppVariables;
import com.nangua.xiaomanjflc.R;
import com.nangua.xiaomanjflc.adapter.CommonAdapter;
import com.nangua.xiaomanjflc.adapter.ViewHolder;
import com.nangua.xiaomanjflc.bean.Red;
import com.nangua.xiaomanjflc.bean.RedList;
import com.nangua.xiaomanjflc.cache.CacheBean;
import com.nangua.xiaomanjflc.support.UIHelper;
import com.nangua.xiaomanjflc.ui.myabstract.HomeFragment;
import com.nangua.xiaomanjflc.utils.ApplicationUtil;
import com.nangua.xiaomanjflc.widget.TitleTab;
import com.nangua.xiaomanjflc.widget.TitleTab.ItemCallBack;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.SparseArrayCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class RedFragment extends HomeFragment {

	private KJListView listview;
	
	private TitleTab titleTab;
	
	private TextView empty;
	
	private KJHttp http;
	private HttpParams params;

	private CommonAdapter<Red> adapter;
	private List<Red> data;

	private int page = 1;
	private String status = "1";
	private boolean noMoreData;
	
	//返回mainactivity 的resultcode key
	public final static int TAB = 99;
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		http = new KJHttp();
		View v = inflater.inflate(R.layout.fragment_red, null);
		data = new ArrayList<Red>();
		http = new KJHttp();
		params = new HttpParams();
		listview = (KJListView) v.findViewById(R.id.listview);
		titleTab = (TitleTab) v.findViewById(R.id.mytab);
		empty = (TextView) v.findViewById(R.id.empty);
		initView(v);
		return v;
	}


	private void getData(int page) {
		params.put("page", page);
		params.put("status", status);
		params.put("sid", AppVariables.sid);
		http.post(AppConstants.RED, params, httpCallback);
	}
	

	private void initView(View v) {
		redBg = getRedBg(R.color.orange);
		redGreyBg = getRedBg(R.color.grey);
		
		final List<String> names = new ArrayList<String>();
		names.add("未使用现金券");
		names.add("已使用现金券");
		names.add("已过期现金券");
		titleTab.setDatas(names, new ItemCallBack() {

			@Override
			public void onItemClicked(int position) {

				for (int i = 0; i < titleTab.getChildCount(); i++) {
					TextView tv = titleTab.getTextView(i);
					if (null != tv)
						tv.setTextColor(getResources().getColor(position == i ? R.color.orange : R.color.grey));
				}
				if (titleTab.getCurrentPosition() != position) {
					if (position == 0) {
						status = "1";
					}
					else if (position == 1) {
						status = "2";
					}
					else if (position == 2) {
						status = "99";
					}
					getData(1);
				}
			}

		});
		
		titleTab.clickItem(0);
		
		adapter = new CommonAdapter<Red>(getActivity(), R.layout.item_red_v2) {
			@Override
			public void canvas(ViewHolder holder, Red item) {
				
				ImageView l = holder.getView(R.id.bgimg);
				ImageView t = holder.getView(R.id.cash_check);
				TextView a = holder.getView(R.id.active_time);
				TextView txtRed = holder.getView(R.id.txtRed);
				t.setVisibility(View.GONE);
				
				if (item.getLock_flg() == 0) {
					l.setImageBitmap(redBg);
					txtRed.setText("现金券");
				}
				else {
					l.setImageBitmap(redGreyBg);
					txtRed.setText("现金券(已锁定)");
				}
				holder.setText(R.id.cashId, item.getId() + "", false);
				if (status.equals("1")) {
					holder.addClick(R.id.item);
					holder.addClick(R.id.cash_check);
					
					holder.setText(R.id.cash_title, "有效时间：", false);
					holder.setText(R.id.get_time, item.getActive_time() + "至",
							false);
					a.setText(item.getExpire_time());
					a.setVisibility(View.VISIBLE);
				} else if (status.equals("2")) {
					l.setImageBitmap(redGreyBg);
//					l.setImageResource(R.drawable.coupon_gray);
//					t.setText("已使用");
					holder.setText(R.id.cashId, item.getId() + "", false);
					holder.setText(R.id.cash_title, "使用时间：", false);
					holder.setText(R.id.get_time, item.getUsed_time()[0] + " ", false);
					a.setText(item.getUsed_time()[1]);
					a.setVisibility(View.VISIBLE);
				} else {
					l.setImageResource(R.drawable.coupon_gray);
					l.setImageBitmap(redGreyBg);
//					t.setText("已过期");
					holder.setText(R.id.cash_title, "有效时间：", false);
					holder.setText(R.id.get_time, item.getExpire_time(), false);
					a.setVisibility(View.GONE);
				}
				holder.setText(R.id.cash_price, item.getCash_price() + "元",
						false);
				holder.setText(R.id.cash_desc, item.getCash_desc(), false);
			}

			@Override
			public void click(int id, List<Red> list, int position,
					ViewHolder viewHolder) {
				if (!status.equals("1") || list.get(position).getLock_flg() != 0) return;
//				Intent intent = new Intent(RedActivity.this, MainActivity.class);
//				intent.putExtra("tab", 1);
//				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//				startActivity(intent);
				
//				setResult(TAB);
//				RedActivity.this.finish();
			}
		};
		adapter.setList(data);
		listview.setAdapter(adapter);
		listview.setOnRefreshListener(refreshListener);
		listview.setEmptyView(empty);
	}
	
	private Bitmap redBg;
	private Bitmap redGreyBg;
	
	private Bitmap getRedBg(int colorId) {
		Bitmap bitmap = null;
		SparseArrayCompat<Bitmap> redBgs = CacheBean.getInstance().getRedBgs();
		Context context = getActivity();
		if (null == redBgs.get(colorId)) {
			int w = (ApplicationUtil.getApkInfo(context).width - DensityUtils.dip2px(context, 10 + 10)) *3/7;
			int h = DensityUtils.dip2px(context, 80);
			bitmap = UIHelper.makeRedBg2(context, w, h, getResources().getColor(colorId));
			redBgs.put(colorId, bitmap);
			CacheBean.getInstance().setRedBgs(redBgs);
		}
		else {
			bitmap = redBgs.get(colorId);
		}
		return bitmap;
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

	private HttpCallBack httpCallback = new HttpCallBack(getActivity()) {
		public void success(org.json.JSONObject ret) {
			try {
				JSONObject articles = ret.getJSONObject("cash");
				page = articles.getInt("currentPage");
//				int maxPage = articles.getJSONObject("pager").getInt("maxPage");
				JSONArray arr = articles.getJSONArray("items");
				if (null == arr || arr.length() == 0) {
					listview.hideFooter();
					noMoreData = true;
				} else {
					listview.showFooter();
					noMoreData = false;
				}
				if (page < 2) {
					data = new RedList(arr).getList();
				} else {
					data = new RedList(data, arr).getList();
				}
				adapter.setList(data);
			} catch (Exception e) {
				e.printStackTrace();
				Toast.makeText(getActivity(), R.string.app_data_error,
						Toast.LENGTH_SHORT).show();
			}
		}

		public void onFinish() {
			super.onFinish();
			listview.stopRefreshData();
		}
	};

	@Override
	public void refreshData() {
		// TODO Auto-generated method stub
		
	}
	
}
