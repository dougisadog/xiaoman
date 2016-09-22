package com.nangua.xiaomanjflc.ui;

import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.nangua.xiaomanjflc.AppVariables;
import com.nangua.xiaomanjflc.utils.ApplicationUtil;
import com.nangua.xiaomanjflc.R;
import com.nangua.xiaomanjflc.support.UIHelper;

public class FeedbackActivity extends Activity {

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.feedback);
		UIHelper.setTitleView(this, "更多", "意见反馈");
		Intent data = new Intent(Intent.ACTION_SENDTO);
		data.setData(Uri.parse("service@xiaomanjf.com"));
		data.putExtra(Intent.EXTRA_SUBJECT, "这是标题");
		data.putExtra(Intent.EXTRA_TEXT, "这是内容");
		startActivity(Intent.createChooser(data, "请选择邮件类应用"));
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (ApplicationUtil.isNeedGesture(this)) {
			startActivity(new Intent(this, GestureActivity.class));
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		AppVariables.lastTime = new Date().getTime();
	}
}
