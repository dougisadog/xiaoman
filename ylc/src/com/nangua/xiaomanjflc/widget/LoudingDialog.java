package com.nangua.xiaomanjflc.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;

import com.nangua.xiaomanjflc.R;

public class LoudingDialog {
	Context context;
	android.app.AlertDialog ad;
	FontTextView titleView;
	FontTextView messageView;
	LinearLayout buttonLayout;
	RelativeLayout loudingLayout;
	FontTextView loudingtext;

	public LoudingDialog(Context context) {
		this.context = context;
		ad = new android.app.AlertDialog.Builder(context).create();
		ad.show();
		// 关键在下面的两行,使用window.setContentView,替换整个对话框窗口的布局
		Window window = ad.getWindow();
		window.setContentView(R.layout.loudingdialog);
		titleView = (FontTextView) window.findViewById(R.id.title);
		messageView = (FontTextView) window.findViewById(R.id.message);
		buttonLayout = (LinearLayout) window.findViewById(R.id.buttonLayout);
		loudingLayout = (RelativeLayout) window.findViewById(R.id.louding);
		loudingtext = (FontTextView) window.findViewById(R.id.loudtext);
	}

	public void setTitle(int resId, int resColor) {
		titleView.setText(resId);
		titleView.setTextColor(resColor);
	}

	public void setTitle(String resId, int resColor) {
		titleView.setText(resId);
		titleView.setTextColor(resColor);
	}

	public void setMessage(int resId, int resColor) {
		messageView.setText(resId);
		messageView.setTextColor(resColor);
	}

	public void setMessage(String resId, int resColor) {
		messageView.setText(resId);
		messageView.setTextColor(resColor);
	}

	public void setMessageicon(int resId) {
		Drawable icon;
		Resources res = context.getResources();
		icon = res.getDrawable(resId);
		// 调用setCompoundDrawables时，必须调用Drawable.setBounds()方法,否则图片不显示
		icon.setBounds(0, 0, icon.getMinimumWidth(), icon.getMinimumHeight());
		messageView.setCompoundDrawables(icon, null, null, null); // 设置左图标
		messageView.setCompoundDrawablePadding(10);
	}

	/**
	 * 设置按钮
	 *
	 * @param text
	 * @param listener
	 */
	public void setPositiveButton(String text, int bg, final View.OnClickListener listener) {
		Button button = new Button(context);
		LinearLayout.LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		button.setLayoutParams(params);
		button.setBackgroundResource(bg);
		button.setText(text);
		button.setTextColor(context.getResources().getColor(R.color.white));
		button.setTextSize(20);
		button.setOnClickListener(listener);
		if (buttonLayout.getChildCount() > 0) {
			params.setMargins(20, 0, 0, 0);
			button.setLayoutParams(params);
			buttonLayout.addView(button, 1);
		} else {
			button.setLayoutParams(params);
			buttonLayout.addView(button);
		}
	}

	/**
	 * 设置按钮
	 *
	 * @param text
	 * @param listener
	 */
	public void setNegativeButton(String text,
			final View.OnClickListener listener) {
		Button button = new Button(context);
		LinearLayout.LayoutParams params = new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		button.setLayoutParams(params);
		button.setBackgroundResource(R.drawable.dialog_negative_btn);
		button.setText(text);
		button.setTextColor(context.getResources().getColor(R.color.app_blue));
		button.setTextSize(20);
		button.setOnClickListener(listener);
		if (buttonLayout.getChildCount() > 0) {
			params.setMargins(20, 0, 0, 0);
			button.setLayoutParams(params);
			buttonLayout.addView(button, 1);
		} else {
			button.setLayoutParams(params);
			buttonLayout.addView(button);
		}

	}

	/**
	 * 关闭对话框
	 */
	public void dismiss() {
		ad.dismiss();
	}

	/**
	 * 显示温馨提示对话框,带确定
	 */
	public void showConfirmHint(int textId) {
		setTitle(R.string.dialog_title, R.color.dialog_orange);
		setMessage(textId, R.color.dialog_orange);
		setPositiveButton(
				context.getResources().getString(R.string.dialog_confirm),
				R.drawable.dialog_positive_btn_long, new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						dismiss();
					}
				});
	}

	public void showConfirmHint(String textId) {
		setTitle(R.string.dialog_title, R.color.dialog_orange);
		setMessage(textId, R.color.dialog_orange);
		setPositiveButton(
				context.getResources().getString(R.string.dialog_confirm),
				R.drawable.dialog_positive_btn_long, new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						dismiss();
					}
				});
	}

	/**
	 * 显示温馨提示对话框,带确定
	 */
	public void showConfirmHintAndFinish(int textId) {
		setTitle(R.string.dialog_title, R.color.dialog_orange);
		setMessage(textId, R.color.dialog_orange);
		setPositiveButton(
				context.getResources().getString(R.string.dialog_confirm),
				R.drawable.dialog_positive_btn_long, new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						dismiss();
						Activity activity = (Activity) context;
						activity.finish();
					}
				});
	}

	public void showConfirmHintAndFinish(String textId) {
		setTitle(R.string.dialog_title, R.color.dialog_orange);
		setMessage(textId, R.color.dialog_orange);
		setPositiveButton(
				context.getResources().getString(R.string.dialog_confirm),
				R.drawable.dialog_positive_btn_long, new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						dismiss();
						Activity activity = (Activity) context;
						activity.finish();
					}
				});
	}

	/**
	 * 显示操作对话框,带确定
	 */
	public void showOperateMessage(int textId) {
		setTitle(R.string.dialog_title, R.color.dialog_orange);
		setMessage(textId, R.color.dialog_orange);
		setNegativeButton("取消", new OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
	}

	public void showOperateMessage(String textId) {
		setTitle(R.string.dialog_title, R.color.dialog_orange);
		setMessage(textId, R.color.dialog_orange);
		setNegativeButton("取消", new OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
	}

	/**
	 * 显示温馨提示对话框，带icon
	 */
	public void showIconHint(int textId) {
		setTitle(R.string.dialog_title, R.color.dialog_orange);
		setMessage(textId, R.color.dialog_green);
		setMessageicon(R.drawable.dialog_icon_confirm);
	}

	public void showIconHint(String textId) {
		setTitle(R.string.dialog_title, R.color.dialog_orange);
		setMessage(textId, R.color.dialog_green);
		setMessageicon(R.drawable.dialog_icon_confirm);
	}

	/**
	 * 显示正在加载对话框,带小菊花
	 */
	public void showLouding(int textId) {
		titleView.setVisibility(View.GONE);
		messageView.setVisibility(View.GONE);
		buttonLayout.setVisibility(View.GONE);
		loudingtext.setText(textId);
		loudingLayout.setVisibility(View.VISIBLE);
	}

	public void showLouding(String textId) {
		titleView.setVisibility(View.GONE);
		messageView.setVisibility(View.GONE);
		buttonLayout.setVisibility(View.GONE);
		loudingtext.setText(textId);
		loudingLayout.setVisibility(View.VISIBLE);
	}

}
