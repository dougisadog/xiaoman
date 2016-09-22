package com.nangua.xiaomanjflc.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.nangua.xiaomanjflc.adapter.ViewHolder.MyClickHandler;
import com.nangua.xiaomanjflc.utils.ApplicationUtil;
import com.nangua.xiaomanjflc.widget.SlideShowView;
import com.nangua.xiaomanjflc.AppConstants;
import com.nangua.xiaomanjflc.R;

public abstract class SlideAdapter<T> extends BaseAdapter {

	protected List<T> list;
	protected Context context;
	protected ListView listview;
	private LayoutInflater inflater;
	protected final int itemLayoutId;

	private View slide;

	public SlideAdapter(Context context, List<T> mDatas, int itemLayoutId) {
		this.context = context;
		this.inflater = LayoutInflater.from(context);
		this.list = mDatas;
		this.itemLayoutId = itemLayoutId;
	}

	public SlideAdapter(Context context, int itemLayoutId) {
		this.context = context;
		this.inflater = LayoutInflater.from(context);
		this.itemLayoutId = itemLayoutId;
	}

	@Override
	public int getCount() {
		return list == null ? 0 : list.size();
	}

	@Override
	public T getItem(int position) {
		return list == null ? null : list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		Log.d("itemposition", position + "");
		if (position == 0) {
			if (slide == null) {
				slide = inflater.inflate(R.layout.item_slide, parent, false);
				SlideShowView v = (SlideShowView) slide.findViewById(R.id.banner);
				int width = ApplicationUtil.getApkInfo(context).width;
				RelativeLayout.LayoutParams params = new   RelativeLayout.LayoutParams(
						RelativeLayout.LayoutParams.MATCH_PARENT,
						(int) (width /AppConstants.BANNER_SCALE));
				v.setLayoutParams(params);
			}
			return slide;
		} else {
			final ViewHolder viewHolder = getViewHolder(position, convertView,
					parent);
			viewHolder.setHandler(new MyClickHandler() {
				@Override
				public void viewClick(int id) {
					click(id, list, position, viewHolder);
				}
			});
			canvas(viewHolder, getItem(position));
			return viewHolder.getConvertView();
		}
	}

	public abstract void canvas(ViewHolder holder, T item);

	public abstract void click(int id, List<T> list, int position,
			ViewHolder viewHolder);

	private ViewHolder getViewHolder(int position, View convertView,
			ViewGroup parent) {
		return ViewHolder.get(context, convertView, parent, itemLayoutId,
				position);
	}

	public void setList(List<T> list) {
		this.list = list;
		notifyDataSetChanged();
	}

	public void setList(T[] list) {
		ArrayList<T> arrayList = new ArrayList<T>(list.length);
		for (T t : list) {
			arrayList.add(t);
		}
		setList(arrayList);
	}

	public List<T> getList() {
		return list;
	}

}
