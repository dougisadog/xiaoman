package com.nangua.xiaomanjflc.ui;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.louding.frame.KJHttp;
import com.louding.frame.http.HttpCallBack;
import com.louding.frame.http.HttpParams;
import com.louding.frame.widget.KJListView;
import com.louding.frame.widget.KJRefreshListener;
import com.nangua.xiaomanjflc.AppConstants;
import com.nangua.xiaomanjflc.AppVariables;
import com.nangua.xiaomanjflc.adapter.SlideAdapter;
import com.nangua.xiaomanjflc.adapter.ViewHolder;
import com.nangua.xiaomanjflc.bean.Product;
import com.nangua.xiaomanjflc.bean.ProductList;
import com.nangua.xiaomanjflc.utils.FormatUtils;
import com.nangua.xiaomanjflc.utils.ToastUtil;
import com.nangua.xiaomanjflc.widget.FontTextView;
import com.nangua.xiaomanjflc.R;

public class ProductFragment extends Fragment {

	private FontTextView tabone;
	private FontTextView tabtwo;

	private KJListView listview;
	private KJHttp http;
	private HttpParams params;

	private SlideAdapter<Product> adapter;
	private List<Product> data;

	private int page = 1;
	private int temp = 0;
	private int state = 1;
	private int new_hand = 0;
	private boolean noMoreData;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		data = new ArrayList<Product>();
		http = new KJHttp();
		params = new HttpParams();
		getData(1);
		View v = inflater.inflate(R.layout.fragment_product, null);
		initView(v);
		listview = (KJListView) v.findViewById(R.id.listview);
		adapter = new SlideAdapter<Product>(getActivity(),
				R.layout.item_product) {
			@Override
			public void canvas(ViewHolder holder, Product item) {
				holder.addClick(R.id.item);
				holder.setText(R.id.name, item.getName(), false);
				FontTextView ni = holder.getView(R.id.nameInfo);
				holder.setText(R.id.gain, item.getGain(), false);
				LinearLayout l = holder.getView(R.id.add_v);
				if (item.getActivity() == 0) {
					ni.setVisibility(View.GONE);
					l.setVisibility(View.GONE);
				} else {
					if (item.getActivityType() == 3) {
						holder.setText(R.id.gain, (FormatUtils.numFormat(Float
								.parseFloat(item.getGain())
								- (float) item.getExtraRate())), false);
						l.setVisibility(View.VISIBLE);
						holder.setText(
								R.id.add,
								FormatUtils.numFormat((float) item
										.getExtraRate()) + "", false);
					} else {
						l.setVisibility(View.GONE);
					}
					ni.setVisibility(View.VISIBLE);
					ni.setText(item.getNameInfo());
				}
				holder.setText(R.id.deadline, item.getDeadline(), false);
				holder.setText(R.id.deadlinedesc, item.getDeadlinedesc(), false);
				holder.setText(R.id.repayMethod, item.getRepayMethod(), false);
				holder.setText(R.id.singlePurchaseLowerLimit,
						item.getSinglePurchaseLowerLimit() + "元起投", false);
				holder.setText(R.id.guaranteeModeName,
						item.getGuaranteeModeName(), false);
				String remainInvestmentAmount = FormatUtils.getAmount(item.getRemainingInvestmentAmount());
				holder.setText(
						R.id.percnt,
						"剩 "
								+ remainInvestmentAmount
								+ "/"
								+ FormatUtils.getAmount(item
										.getTotalInvestment()), false);
				int i = item.getNewstatus();
				FontTextView status = holder.getView(R.id.status);
				
				String[] arr = getResources().getStringArray(R.array.product_status);
				status.setText(arr[i]);
				if (i == 5) { // 正在售卖
					status.setBackgroundResource(R.drawable.saling);
				} else {
					status.setBackgroundResource(R.drawable.saled);
				}
				holder.setText(R.id.percentage, item.getPercentage() + "%",
						false);
				ProgressBar pb = holder.getView(R.id.percentagepb);
				pb.setProgress(item.getPercentage());
				ImageView confine = holder.getView(R.id.confine);
				switch (item.getConfine()) {
				case 0:
					confine.setImageResource(R.drawable.confine0);
					break;
				case 1:
					confine.setImageResource(R.drawable.confine1);
					if (new_hand != 0 || !AppVariables.isSignin) {
						status.setBackgroundResource(R.drawable.saled);
					}
					break;
				case 2:
					confine.setImageResource(R.drawable.confine2);
					break;
				case 3:
					confine.setImageResource(R.drawable.confine4);
					break;
				case 4:
					confine.setImageResource(R.drawable.confine4);
					break;
				case 5:
					confine.setImageResource(R.drawable.confine5);
					break;
				case 6:
					confine.setImageResource(R.drawable.confine6);
					if (new_hand != 0 || !AppVariables.isSignin) {
						status.setBackgroundResource(R.drawable.saled);
					}
					break;
				case 7:
					confine.setImageResource(R.drawable.confine7);
					break;
				case 8:
					confine.setImageResource(R.drawable.confine8);
					break;
			}


			}

			@Override
			public void click(int id, List<Product> list, int position,
					ViewHolder viewHolder) {
				int i = list.get(position).getType();
				int confine = list.get(position).getConfine();
				if ((confine == 1 || confine == 6 ) && new_hand != 0) {
					if (!AppVariables.isSignin) {
						ToastUtil.showToast(getActivity(), "请先登录",
								Toast.LENGTH_SHORT);
					} else {
						ToastUtil.showToast(getActivity(), "您已不是新手",
								Toast.LENGTH_SHORT);
					}
				} else {
					Intent intent = new Intent(getActivity(),
							DetailActivity.class);
					intent.putExtra("id", list.get(position).getId());
					startActivity(intent);
				}
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
		if (null == params) {
			params = new HttpParams();
		}
		params.put("page", page);
		params.put("sid", AppVariables.sid);
		params.put("loginVersionName", "Android"
				+ getAppVersionName(getActivity()));
		http.post(AppConstants.PRODUCTS, params, httpCallback);
	}
	
	public void refreshData() {
		getData(1);
	}

	/**
	 * 返回当前程序版本名
	 */
	public static String getAppVersionName(Context context) {
		String versionName = "";
		try {
			PackageManager pm = context.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
			versionName = pi.versionName;
			if (versionName == null || versionName.length() <= 0) {
				return "";
			}
		} catch (Exception e) {
			Log.e("LAG", "Exception", e);
		}
		return versionName;
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
		public void onSuccess(String t) {
			try {
				JSONObject ret = new JSONObject(t);
				int state = ret.getInt("status");
				new_hand = ret.getInt("new_hand");
				if (state != 0) {
					listview.hideFooter();
					noMoreData = true;
				} else {
					listview.showFooter();
					noMoreData = false;
					page = ret.getInt("current_page");
					if (page == 1) {
						// 因为后来要求改变，所以需要数据第一位为空，显示轮播图。在构造方法里面做处理。
						data = new ProductList(ret.getJSONArray("product_list"))
								.getProducts();
					} else {
						data = new ProductList(data,
								ret.getJSONArray("product_list")).getProducts();
					}
					adapter.setList(data);
				}
			} catch (Exception e) {
				e.printStackTrace();
				Toast.makeText(getActivity(), R.string.app_data_error,
						Toast.LENGTH_SHORT).show();
			}
		};

		public void onFinish() {
			listview.stopRefreshData();
		};
	};

}
