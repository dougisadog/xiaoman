package com.nangua.xiaomanjflc.ui.fragment;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

import com.louding.frame.KJHttp;
import com.louding.frame.http.HttpCallBack;
import com.louding.frame.http.HttpParams;
import com.louding.frame.utils.DensityUtils;
import com.louding.frame.widget.KJListViewFooter;
import com.louding.frame.widget.KJListViewFooter.LoadMoreState;
import com.louding.frame.widget.KJListViewHeader;
import com.louding.frame.widget.KJListViewHeader.RefreshState;
import com.louding.frame.widget.KJSwipeRefreshLayout;
import com.nangua.xiaomanjflc.AppConstants;
import com.nangua.xiaomanjflc.AppVariables;
import com.nangua.xiaomanjflc.R;
import com.nangua.xiaomanjflc.bean.Red;
import com.nangua.xiaomanjflc.bean.RedList;
import com.nangua.xiaomanjflc.cache.CacheBean;
import com.nangua.xiaomanjflc.support.UIHelper;
import com.nangua.xiaomanjflc.ui.adapter.RefreshRecyclerAdapter;
import com.nangua.xiaomanjflc.ui.myabstract.HomeFragment;
import com.nangua.xiaomanjflc.utils.ApplicationUtil;
import com.nangua.xiaomanjflc.widget.TitleTab;
import com.nangua.xiaomanjflc.widget.TitleTab.ItemCallBack;
import com.nangua.xiaomanjflc.widget.v7.HeaderAndFooterWrapper;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.SparseArrayCompat;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class RedPlusFragment2 extends HomeFragment {

	private RecyclerView recyclerView;

	private KJSwipeRefreshLayout swipeRefreshLayout;
	
	private HeaderAndFooterWrapper wrapper;

	private RefreshRecyclerAdapter adapter;
	
	private LinearLayoutManager linearLayoutManager;

	private KJListViewFooter mFooterView;
	
	private KJListViewHeader mHeaderView;

	private TitleTab titleTab;

	private TextView empty;

	private KJHttp http;
	private HttpParams params;

	private List<Red> data;

	private int page = 1;
	private String status = "1";
	private boolean noMoreData;

	// 返回mainactivity 的resultcode key
	public final static int TAB = 99;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container,
			@Nullable Bundle savedInstanceState) {
		http = new KJHttp();
		View v = inflater.inflate(R.layout.fragment_plus_bonus2, null);
		data = new ArrayList<Red>();
		http = new KJHttp();
		params = new HttpParams();
		recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
		swipeRefreshLayout = (KJSwipeRefreshLayout) v
				.findViewById(R.id.swiperefreshlayout);
		titleTab = (TitleTab) v.findViewById(R.id.mytab);
		empty = (TextView) v.findViewById(R.id.empty);

		mFooterView = new KJListViewFooter(getContext());
		mHeaderView = new KJListViewHeader(getContext());
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
		swipeRefreshLayout.setProgressBackgroundColorSchemeResource(
				android.R.color.white);
		swipeRefreshLayout.setColorSchemeResources(
				android.R.color.holo_blue_light, android.R.color.holo_red_light,
				android.R.color.holo_orange_light,
				android.R.color.holo_green_light);
		swipeRefreshLayout.setProgressViewOffset(false, 0,
				(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24,
						getResources().getDisplayMetrics()));
		linearLayoutManager = new LinearLayoutManager(getContext());
		linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
		
		recyclerView.setLayoutManager(linearLayoutManager);

		final List<String> names = new ArrayList<String>();
		names.add("未使用加息券");
		names.add("已使用加息券");
		names.add("已过期加息券");
		titleTab.setDatas(names, new ItemCallBack() {

			@Override
			public void onItemClicked(int position) {

				for (int i = 0; i < titleTab.getChildCount(); i++) {
					TextView tv = titleTab.getTextView(i);
					if (null != tv)
						tv.setTextColor(getResources().getColor(
								position == i ? R.color.orange : R.color.grey));
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

		if (null == redBg) {
			redBg = getRedBg(R.color.orange);
		}
		if (null == redGreyBg) {
			redGreyBg = getRedBg(R.color.grey);
		}
		adapter = new RefreshRecyclerAdapter(getActivity(), redBg, redGreyBg,
				status);
		wrapper = new HeaderAndFooterWrapper(adapter);
		wrapper.addHeaderView(mHeaderView);
		wrapper.addFootView(mFooterView);
		wrapper.addItem(data, status);
		recyclerView.setAdapter(wrapper);

		swipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				getData(1);

			}
		});
		recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
			@Override
			public void onScrollStateChanged(RecyclerView recyclerView,
					int newState) {
				super.onScrollStateChanged(recyclerView, newState);
				if (newState == RecyclerView.SCROLL_STATE_IDLE) {
					if (lastVisibleItem >= adapter.getItemCount() + wrapper.getHeadersCount() + wrapper.getFootersCount()) {
						if (!noMoreData) {
							mFooterView.setState(LoadMoreState.STATE_LOADING);
							getData(page + 1);
						}
					}
					else if (firstVisibleItem == wrapper.getHeadersCount()){
						mHeaderView.setState(RefreshState.STATE_REFRESHING);
						getData(1);
					}
			
				}
			}
			@Override
			public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
				super.onScrolled(recyclerView, dx, dy);
				firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();
				lastVisibleItem = firstVisibleItem + linearLayoutManager.getChildCount();
			}
		});
	}

	private int lastVisibleItem = 0;
	private int firstVisibleItem = 0;

	private Bitmap redBg;
	private Bitmap redGreyBg;

	private Bitmap getRedBg(int colorId) {
		Bitmap bitmap = null;
		SparseArrayCompat<Bitmap> redBgs = CacheBean.getInstance().getRedBgs();
		Context context = getActivity();
		if (null == redBgs.get(colorId)) {
			int w = (ApplicationUtil.getApkInfo(context).width
					- DensityUtils.dip2px(context, 10 + 10)) * 3 / 7;
			int h = DensityUtils.dip2px(context, 80);
			bitmap = UIHelper.makeRedBg2(context, w, h,
					getResources().getColor(colorId));
			redBgs.put(colorId, bitmap);
			CacheBean.getInstance().setRedBgs(redBgs);
		}
		else {
			bitmap = redBgs.get(colorId);
		}
		return bitmap;
	}

	private HttpCallBack httpCallback = new HttpCallBack(getActivity()) {
		public void success(org.json.JSONObject ret) {
			try {
				JSONObject articles = ret.getJSONObject("cash");
				page = articles.getInt("currentPage");
				JSONArray arr = articles.getJSONArray("items");
				if (null == arr || arr.length() == 0) {
					mFooterView.setState(LoadMoreState.STATE_READY); 
					noMoreData = true;
				}
				else {
					mFooterView.setState(LoadMoreState.STATE_NORMAL);
					noMoreData = false;
				}
				if (page < 2) {
					data = new RedList(arr).getList();
				}
				else {
					data = new RedList(data, arr).getList();
				}
				empty.setVisibility(data.size() == 0 ? View.VISIBLE : View.INVISIBLE);
				swipeRefreshLayout.setRefreshing(false);
				wrapper.addItem(data, status);
			} catch (Exception e) {
				e.printStackTrace();
				Toast.makeText(getActivity(), R.string.app_data_error,
						Toast.LENGTH_SHORT).show();
			}
		}

		public void onFinish() {
			super.onFinish();
			swipeRefreshLayout.setRefreshing(false);
		}
	};

	@Override
	public void refreshData() {
		// TODO Auto-generated method stub

	}

}
