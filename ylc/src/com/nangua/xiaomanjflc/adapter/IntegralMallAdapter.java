package com.nangua.xiaomanjflc.adapter;

import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import cn.pedant.SweetAlert.SweetAlertDialog;

import com.bumptech.glide.Glide;
import com.louding.frame.KJHttp;
import com.louding.frame.http.HttpCallBack;
import com.louding.frame.http.HttpParams;
import com.nangua.xiaomanjflc.AppConstants;
import com.nangua.xiaomanjflc.AppVariables;
import com.nangua.xiaomanjflc.widget.FontTextView;
import com.nangua.xiaomanjflc.R;
import com.nangua.xiaomanjflc.dialog.CustomDialog;

public class IntegralMallAdapter extends SimpleAdapter {

	private Context context;
	private List<? extends Map<String, ?>> data;
	private int resource;
	private String[] from;
	private int[] to;

	private HttpParams params;
	private KJHttp http;

	public IntegralMallAdapter(Context context,
			List<? extends Map<String, ?>> data, int resource, String[] from,
			int[] to) {
		super(context, data, resource, from, to);
		this.context = context;
		this.data = data;
		this.resource = resource;
		this.from = from;
		this.to = to;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		View v = super.getView(position, convertView, parent);

		ImageView iv_image = (ImageView) v
				.findViewById(R.id.iv_image);
		FontTextView tv_integral = (FontTextView) v
				.findViewById(R.id.tv_integral);
		FontTextView tv_describe = (FontTextView) v
				.findViewById(R.id.tv_describe);

		Glide.with(context).load(AppConstants.HOST_IMAGE
						+ data.get(position).get("img_path").toString()).into(iv_image);
		tv_integral.setText("会员价："
				+ data.get(position).get("cost_point").toString() + "积分");
		tv_describe.setText("("
				+ data.get(position).get("description").toString() + ")");

		Button btn = (Button) v.findViewById(R.id.btn_exchange);
		final int p = position;
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				CustomDialog.Builder builder = new CustomDialog.Builder(context);
				builder.setMessage("您确定使用"
						+ data.get(p).get("cost_point").toString() + "积分兑换"
						+ data.get(p).get("title").toString() + "吗？");
				builder.setPositiveButton("确定兑换",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {

								params = new HttpParams();
								http = new KJHttp();
								dialog.dismiss();
								getData(data.get(p).get("commodity_id")
										.toString(),
										data.get(p).get("red_packet_id")
												.toString(),
										data.get(p).get("cost_point")
												.toString());
							}
						});
				builder.setCancelButton(new android.content.DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
				builder.create().show();
			}
		});
		return v;
	}

	private void getData(String commodity_id, String red_packet_id,
			String cost_point) {
		params.put("sid", AppVariables.sid);
		params.put("commodity_id", commodity_id);
		params.put("red_packet_id", red_packet_id);
		params.put("cost_point", cost_point);
		http.post(AppConstants.MY_INTEGRAL_MALL_EXCHANGE, params, httpCallback);
	}

	private HttpCallBack httpCallback = new HttpCallBack(context) {

		@Override
		public void success(JSONObject ret) {
			super.success(ret);
			try {
				String is_success = ret.getString("is_success");
				String msg = ret.getString("msg");
				CustomDialog.Builder builder;
				if ("success".equals(is_success)) {// 兑换成功
					// builder = new CustomDialog.Builder(context,
					// R.layout.dialog_normal_success_layout);
					// builder.setMessage(msg);
					// builder.setCancelButton(new
					// android.content.DialogInterface.OnClickListener() {
					// public void onClick(DialogInterface dialog, int which) {
					// dialog.dismiss();
					// }
					// });
					// builder.create().show();
					new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
							.setTitleText("恭喜您!").setContentText(msg + "!")
							.show();
				} else {// 兑换失败
					// builder = new CustomDialog.Builder(context,
					// R.layout.dialog_normal_fail_layout);
					// builder.setMessage(msg);
					// builder.setCancelButton(new
					// android.content.DialogInterface.OnClickListener() {
					// public void onClick(DialogInterface dialog, int which) {
					// dialog.dismiss();
					// }
					// });
					// builder.create().show();
					new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
							.setTitleText("兑换失败...").setContentText(msg + "!")
							.show();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public void failure(JSONObject ret) {
			super.failure(ret);
		}
	};

}
