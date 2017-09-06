package com.nangua.xiaomanjflc.ui.fragment;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import com.louding.frame.KJHttp;
import com.louding.frame.http.HttpCallBack;
import com.louding.frame.http.HttpParams;
import com.louding.frame.utils.DensityUtils;
import com.nangua.xiaomanjflc.AppConstants;
import com.nangua.xiaomanjflc.AppVariables;
import com.nangua.xiaomanjflc.R;
import com.nangua.xiaomanjflc.AppConstants.SendType;
import com.nangua.xiaomanjflc.adapter.CommonAdapter;
import com.nangua.xiaomanjflc.adapter.ViewHolder;
import com.nangua.xiaomanjflc.bean.RateCoupon;
import com.nangua.xiaomanjflc.cache.CacheBean;
import com.nangua.xiaomanjflc.support.UIHelper;
import com.nangua.xiaomanjflc.ui.myabstract.HomeFragment;
import com.nangua.xiaomanjflc.utils.ApplicationUtil;
import com.nangua.xiaomanjflc.utils.FormatUtils;
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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class RedPlusFragment extends HomeFragment {

	private ListView listview;
	
	private TitleTab titleTab;
	
	private TextView empty;
	
	private KJHttp http;
	private HttpParams params;

	private CommonAdapter<RateCoupon> adapter;
	private List<RateCoupon> rateCoupons;

	private int usedFlg = RateCoupon.AVAILABLE;
	
	//返回mainactivity 的resultcode key
	public final static int TAB = 99;
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		http = new KJHttp();
		View v = inflater.inflate(R.layout.fragment_plus_bonus, null);
		rateCoupons = new ArrayList<RateCoupon>();
		http = new KJHttp();
		params = new HttpParams();
		listview = (ListView) v.findViewById(R.id.listview);
		titleTab = (TitleTab) v.findViewById(R.id.mytab);
		empty = (TextView) v.findViewById(R.id.empty);
		initView(v);
		return v;
	}


	private void getData() {
		params.put("usedFlg", usedFlg);
		params.put("sid", AppVariables.sid);
		http.post(AppConstants.RATE_COUPON, params, httpCallback);
	}
	

	private void initView(View v) {
		redBg = getRedBg(R.color.orange);
		redGreyBg = getRedBg(R.color.grey);
		
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
						tv.setTextColor(getResources().getColor(position == i ? R.color.orange : R.color.grey));
				}
				if (titleTab.getCurrentPosition() != position) {
					if (position == 0) {
						usedFlg = RateCoupon.AVAILABLE;
					}
					else if (position == 1) {
						usedFlg = RateCoupon.USED;
					}
					else if (position == 2) {
						usedFlg = RateCoupon.EXPIRED;
					}
					getData();
				}
			}

		});
		
		titleTab.clickItem(0);
		
		adapter = new CommonAdapter<RateCoupon>(getActivity(), R.layout.item_red_plus_v2) {
			@Override
			public void canvas(ViewHolder holder, RateCoupon item) {
				
				ImageView l = holder.getView(R.id.bgimg);
				ImageView t = holder.getView(R.id.cash_check);
				TextView a = holder.getView(R.id.active_time);
				TextView txtRed = holder.getView(R.id.txtRed);
				t.setVisibility(View.GONE);
				
//				if (item.getLock_flg() == 0) {
					l.setImageBitmap(redBg);
					txtRed.setText(item.getRateCouponName());
//				}
//				else {
//					l.setImageBitmap(redGreyBg);
//					txtRed.setText("加息券(已锁定)");
//				}
				holder.setText(R.id.cashId, item.getRateCouponId() + "", false);
				if (usedFlg == RateCoupon.AVAILABLE) {
					holder.setText(R.id.cash_title, "有效时间：", false);
					holder.setText(R.id.get_time, RateCoupon.getFormattedDate(item.getFromDate()) + "至",
							false);
					a.setText(RateCoupon.getFormattedDate(item.getValidDate()));
					a.setVisibility(View.VISIBLE);
				} else if (usedFlg == RateCoupon.USED) { 
					l.setImageBitmap(redGreyBg);
					holder.setText(R.id.cashId, item.getRateCouponId() + "", false);
					holder.setText(R.id.cash_title, "使用时间：", false);
					holder.setText(R.id.get_time, item.getUsedDate()+ " ", false);
//					a.setText(item.getUsed_time()[1]);
//					a.setVisibility(View.VISIBLE);
				} else {
					l.setImageResource(R.drawable.coupon_gray);
					l.setImageBitmap(redGreyBg);
//					t.setText("已过期");
					holder.setText(R.id.cash_title, "有效时间：", false);
					holder.setText(R.id.get_time, RateCoupon.getFormattedDate(item.getValidDate()), false);
					a.setVisibility(View.GONE);
				}
				holder.setText(R.id.cash_price, "仅限于加息产品",
						false);
				String sendType = SendType.getSendTypeByCode(item.getSendType()).getType();
				holder.setText(R.id.cash_from, "来源" + sendType, false);
			}

			@Override
			public void click(int id, List<RateCoupon> list, int position,
					ViewHolder viewHolder) {
			}
		};
		adapter.setList(rateCoupons);
		listview.setAdapter(adapter);
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

	private HttpCallBack httpCallback = new HttpCallBack(getActivity()) {
		public void success(org.json.JSONObject ret) {
			try {
				JSONArray rateCoupon = ret.getJSONArray("rateCoupon");
				List<RateCoupon> rateCoupons = new ArrayList<RateCoupon>();
				for (int i = 0; i < rateCoupon.length(); i++) {
					RateCoupon r = FormatUtils.jsonParse(rateCoupon.get(i).toString(), RateCoupon.class);
					rateCoupons.add(r);
				}
				adapter.setList(rateCoupons);
				if (usedFlg == RateCoupon.AVAILABLE) {
					CacheBean.getInstance().getAccount().setLeftRateCouponNum(rateCoupon.length());
				}
			} catch (Exception e) {
				e.printStackTrace();
				Toast.makeText(getActivity(), R.string.app_data_error,
						Toast.LENGTH_SHORT).show();
			}
		}

	};

	@Override
	public void refreshData() {
		// TODO Auto-generated method stub
		
	}
	
}
