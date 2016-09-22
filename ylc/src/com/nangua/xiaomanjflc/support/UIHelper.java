package com.nangua.xiaomanjflc.support;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.nangua.xiaomanjflc.R;
import com.nangua.xiaomanjflc.widget.FontTextView;

public class UIHelper {

	/**
	 * 设置titleView的通用方法
	 *
	 * @param atv
	 *            activity对象
	 * @param textContent
	 *            中间TextView的文字
	 */
	public static void setTitleView(final Activity atv, String back,
			String textContent) {
		FontTextView btnLeft = null;
		FontTextView titleTv = null;
		ImageView titleImage = null;
		btnLeft = (FontTextView) atv.findViewById(R.id.title_left);
		titleTv = (FontTextView) atv.findViewById(R.id.title_center);
		titleImage = (ImageView) atv.findViewById(R.id.title_image);
		btnLeft.setText(back);
		titleImage.setVisibility(View.GONE);
		btnLeft.setVisibility(View.VISIBLE);
		titleTv.setVisibility(View.VISIBLE);
		titleTv.setText(textContent);
		btnLeft.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				atv.finish();
			}
		});
	}

}
