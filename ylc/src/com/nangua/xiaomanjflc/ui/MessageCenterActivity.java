package com.nangua.xiaomanjflc.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.louding.frame.KJActivity;
import com.louding.frame.KJHttp;
import com.louding.frame.http.HttpCallBack;
import com.louding.frame.http.HttpParams;
import com.louding.frame.ui.BindView;
import com.louding.frame.widget.KJListView;
import com.louding.frame.widget.KJRefreshListener;
import com.nangua.xiaomanjflc.AppConstants;
import com.nangua.xiaomanjflc.AppVariables;
import com.nangua.xiaomanjflc.R;
import com.nangua.xiaomanjflc.adapter.CommonAdapter;
import com.nangua.xiaomanjflc.adapter.ViewHolder;
import com.nangua.xiaomanjflc.bean.MessageCenter;
import com.nangua.xiaomanjflc.bean.MessageCenterList;
import com.nangua.xiaomanjflc.utils.ApplicationUtil;
import com.nangua.xiaomanjflc.utils.DensityUtils;
import com.nangua.xiaomanjflc.widget.FontTextView;

import org.json.JSONObject;

import java.util.List;

/**
 * 消息中心
 */
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
    private ImageView btnRitht;
    private FontTextView title_left;

    @Override
    public void setRootView() {
        setContentView(R.layout.activity_message_center);
        btnRitht = (ImageView) this.findViewById(R.id.title_right);
        btnRitht.setOnClickListener(this);
        title_left = (FontTextView) this.findViewById(R.id.title_left);
        title_left.setOnClickListener(this);
    }

    @Override
    public void initData() {
        super.initData();

        params = new HttpParams();
        http = new KJHttp();


    }


    @Override
    public void widgetClick(View v) {
        if (null != pWindow) {
            pWindow.dismiss();
        }
        switch (v.getId()) {
            case R.id.title_right:
                onPopupButtonClick(v);
//			readAll();
                break;
            case R.id.title_left:
                finish();
                break;
        }
        super.widgetClick(v);
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

    private void readAll() {
        HttpParams p = new HttpParams();
        p.put("sid", AppVariables.sid);
        http.post(AppConstants.MESSAGE_CENTER_READ_ALL, p, new HttpCallBack(MessageCenterActivity.this, false) {

            @Override
            public void success(JSONObject ret) {
                for (int i = 0; i < messageCenters.size(); i++) {
                    messageCenters.get(i).setOpen_flg("1");
                }
                adapter.setList(messageCenters);
                AppVariables.forceUpdate = true;
                super.success(ret);
            }

        });
    }

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


    private PopupWindow pWindow;

    public void onPopupButtonClick(View button) {
        if (null == pWindow) {
            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(R.layout.menu_message_func, null);
            TextView tv = (TextView) view.findViewById(R.id.groupItem);
            tv.setTextSize(14);
            tv.setBackgroundResource(R.color.white);
            tv.setGravity(Gravity.CENTER);
            tv.setTextColor(getResources().getColor(R.color.black));
            tv.setPadding(5,5,5,5);

            // 创建PopupMenu对象
            pWindow = new PopupWindow(view, ApplicationUtil.getApkInfo(this).width / 2, DensityUtils.dip2px(this, 40));
            if (null != tv) {
                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        readAll();
                        pWindow.dismiss();
                    }
                });
            }
            pWindow.setFocusable(true);
            pWindow.setOutsideTouchable(true);  //设置点击屏幕其它地方弹出框消失
            pWindow.setBackgroundDrawable(new BitmapDrawable());
        }

        int xPos = ApplicationUtil.getApkInfo(this).width / 2
                - pWindow.getWidth() / 2;
        pWindow.showAsDropDown(button, xPos, 0);
    }

}
