package com.nangua.xiaomanjflc.ui;

import java.util.List;

import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;

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
import com.nangua.xiaomanjflc.bean.MessageCenter;
import com.nangua.xiaomanjflc.bean.MessageCenterList;
import com.nangua.xiaomanjflc.support.UIHelper;
import com.nangua.xiaomanjflc.widget.FontTextView;
import com.nangua.xiaomanjflc.R;

/**
 * 消息中心
 * */
@SuppressLint("ResourceAsColor")
public class MessageCenterActivity extends KJActivity {

	@BindView(id = R.id.lv_message_center)
	private KJListView lv_message_center;
	private CommonAdapter<MessageCenter> adapter;

	private List<MessageCenter> messageCenters;

	private HttpParams params;
	private KJHttp http;

	private boolean noMoreData;

	private int page = 1;
	private int maxPage = 1;

	@Override
	public void setRootView() {
		setContentView(R.layout.activity_message_center);
		UIHelper.setTitleView(this, "我的账户", "我的消息");
	}

	@Override
	public void initData() {
		super.initData();

		params = new HttpParams();
		http = new KJHttp();

	}
	
	@Override
	public void onResume() {
		super.onResume();
		getData(page);
	}
	
	private void getData(int page) {
		params.put("sid", AppVariables.sid);
		params.put("page", page);
		http.post(AppConstants.MESSAGE_CENTER, params, httpCallback);
	}

	private HttpCallBack httpCallback = new HttpCallBack(
			MessageCenterActivity.this) {

		@Override
		public void failure(JSONObject ret) {
			super.failure(ret);
		}

		@Override
		public void success(JSONObject ret) {
			super.success(ret);
			try {
				page = ret.getInt("page");
				maxPage = ret.getInt("maxPage");
				if (page >= maxPage) {
					lv_message_center.hideFooter();
					noMoreData = true;
				} else {
					lv_message_center.showFooter();
					noMoreData = false;
				}
				
				if (page < 2) {
					messageCenters = new MessageCenterList(
							ret.getJSONArray("messageList")).getList();
				} else {
					messageCenters = new MessageCenterList(messageCenters,
							ret.getJSONArray("messageList")).getList();
				}
				adapter.setList(messageCenters);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		@SuppressWarnings("deprecation")
		public void onFinish() {
			super.onFinish();
			lv_message_center.stopRefreshData();
		}
	};

	@Override
	public void initWidget() {
		super.initWidget();
		adapter = new CommonAdapter<MessageCenter>(MessageCenterActivity.this,
				R.layout.item_message_center) {

			@Override
			public void canvas(ViewHolder holder, MessageCenter item) {
				holder.addClick(R.id.tv_message_content);

				FontTextView tv_message_title = holder
						.getView(R.id.tv_message_title);
				FontTextView tv_message_time = holder.getView(R.id.tv_message_time);
				if ("0".equals(item.getOpen_flg())) {
					tv_message_title.setTextColor(Color.rgb(122, 167, 224));
					tv_message_time.setTextColor(Color.rgb(122, 167, 224));
				} else {
					tv_message_title.setTextColor(Color.rgb(127, 127, 127));
					tv_message_time.setTextColor(Color.rgb(127, 127, 127));
				}
				holder.setText(R.id.tv_message_title, item.getSubject(), false);
				holder.setText(R.id.tv_message_time, item.getIns_date(), false);
				holder.setText(R.id.tv_message_content, item.getContents(),
						false);

			}

			@Override
			public void click(int id, List<MessageCenter> list, int position,
					ViewHolder viewHolder) {
				Intent intent = new Intent(MessageCenterActivity.this,
						MessageCenterDetailActivity.class);
				intent.putExtra("message_title", list.get(position)
						.getSubject());
				intent.putExtra("message_time", list.get(position)
						.getIns_date());
				intent.putExtra("message_content", list.get(position)
						.getContents());
				intent.putExtra("id", list.get(position).getId());
				startActivity(intent);
			}
		};
		lv_message_center.setAdapter(adapter);
		lv_message_center.setOnRefreshListener(refreshListener);
		lv_message_center.setEmptyView(findViewById(R.id.empty));

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
