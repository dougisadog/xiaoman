/*
 * Copyright (c) 2014,KJFrameForAndroid Open Source Project,张涛.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.louding.frame.http;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.louding.frame.utils.KJLoger;
import com.nangua.xiaomanjflc.R;
import com.nangua.xiaomanjflc.utils.ApplicationUtil;
import com.nangua.xiaomanjflc.widget.LoudingDialogIOS;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.Toast;

/**
 * Http请求回调类<br>
 * 
 * <b>创建时间</b> 2014-8-7
 *
 * @author kymjs (http://www.kymjs.com/) .
 * @version 1.4
 */
public abstract class HttpCallBack {
	
	private boolean isStream = false;

	private Context context;
	private LoudingDialogIOS ld;
	private LoudingDialogIOS ldc;
	private boolean loading = true;
	
	public HttpCallBack() {
		super();
	}

	public HttpCallBack(Context context) {
		super();
		this.context = context;
	}
	
	public HttpCallBack(Context context, boolean loading) {
		super();
		this.context = context;
		this.loading = loading;
	}
	
	public HttpCallBack(Context context, boolean loading, boolean isStream) {
		super();
		this.context = context;
		this.loading = loading;
		this.setStream(isStream);
	}

	/**
	 * Http请求开始前回调
	 */
	public void onPreStart() {
		KJLoger.debug("Http请求开始。。。");
		if (context != null && loading) {
			ld = new LoudingDialogIOS(context);
			ld.showLouding(R.string.load_ing);
		}
	}
	
	private void dissmiss() {
		if (ld != null && loading) {
			ld.dismiss();
		}
	}

	;

	/**
	 * 进度回调，仅支持Download时使用
	 *
	 * @param count
	 *            总数
	 * @param current
	 *            当前进度
	 */
	public void onLoading(long count, long current) {
	}
	
	public void onSuccess(InputStream input) {
		KJLoger.debug("Http请求流成功。。。");
	}

	/**
	 * Http请求成功时回调
	 *
	 * @param t
	 */
	public void onSuccess(String t) {
		if (isStream) {
			dissmiss();
			return;
		}
		KJLoger.debug("Http请求成功。。。");
		try {
			JSONObject ret = new JSONObject(t);
			int state = ret.getInt("status");
			if (state != 0) {
				failure(ret);
			} else {
				success(ret);
			}
		} catch (JSONException e) {
			System.out.println("json数据解析错误。");
			if (null != context) {
				Toast.makeText(context, R.string.app_data_error, Toast.LENGTH_SHORT)
				.show();
			}
			dissmiss();
		}
	}

	public void failure(JSONObject ret) {
		if (isStream) {
			dissmiss();
			return;
		}
		if (!ret.isNull("msg")) {
			try {
				String msg = ret.getString("msg");
				if (context != null) {
					if (msg.equals("not login")) {
						ApplicationUtil.restartApplication(context);
					} else {
						ldc = new LoudingDialogIOS(context);
						ldc.showConfirmHint(msg);
					}
				}
			} catch (JSONException e) {
				if (context != null) {
					Toast.makeText(context, R.string.app_data_error,
							Toast.LENGTH_SHORT).show();
				}
				dissmiss();
			}
		} else {
			if (context != null) {
			Toast.makeText(context, R.string.app_exception, Toast.LENGTH_SHORT)
					.show();
			}
		}
		failNext(ret);
	}

	public void failNext(JSONObject ret) {

	}

	public void success(JSONObject ret) {
		KJLoger.debug("Http请求成功。。。");
	}

	/**
	 * Http下载成功时回调
	 */
	public void onSuccess(File f) {
		KJLoger.debug("文件下载成功。。。");
	}
	
	/**
	 * Http请求失败时回调
	 *
	 * @param t
	 * @param errorNo
	 *            错误码
	 * @param strMsg
	 *            错误原因
	 */
	public void onFailure(Throwable t, int errorNo, String strMsg) {
		if (context != null) {
			Toast.makeText(context, "网络错误，请重试。。。", Toast.LENGTH_SHORT).show();
		}
		KJLoger.debug("Http请求失败。。。" + strMsg);
	}

	/**
	 * Http请求结束后回调
	 */
	public void onFinish() {
		KJLoger.debug("Http请求结束。。。");
		dissmiss();
	}

	public boolean isStream() {
		return isStream;
	}

	public void setStream(boolean isStream) {
		this.isStream = isStream;
	}

    /**
     * 注意：本方法将在异步调用。
     * Http异步请求成功时在异步回调,并且仅当本方法执行完成才会继续调用onSuccess()
     *
     * @param t 返回的信息
     */
    public void onSuccessInAsync(byte[] t) {
    }

    /**
     * Http请求成功时回调
     * 
     * @param t
     *            HttpRequest返回信息
     */
    public void onSuccess(byte[] t) {
        if (t != null) {
            onSuccess(new String(t));
        }
    }

    /**
     * Http请求成功时回调
     * 
     * @param headers
     *            HttpRespond头
     * @param t
     *            HttpRequest返回信息
     */
    public void onSuccess(Map<String, String> headers, byte[] t) {
    	if (isStream()) {
    		onSuccess(headers, t);
    	}
    	onSuccess(t);
    }

    /**
     * 仅在KJBitmap中可用，图片加载完成时回调
     * 
     * @param t
     */
    public void onSuccess(Bitmap t) {}

    /**
     * Http请求失败时回调
     * 
     * @param errorNo
     *            错误码
     * @param strMsg
     *            错误原因
     */
    public void onFailure(int errorNo, String strMsg) {}


}
