package com.nangua.xiaomanjflc.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.nangua.xiaomanjflc.AppConstants;
import com.nangua.xiaomanjflc.AppVariables;
import com.nangua.xiaomanjflc.bean.jsonbean.User;
import com.nangua.xiaomanjflc.cache.CacheBean;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpHelper {

	public Bitmap getCapture(String sid) {
		//暂时屏蔽验证图片请求
//		InputStream is = null;
//		try {
//			// 创建连接
//			URL url = new URL(AppConstants.CAPTCHA);
//			HttpURLConnection connection = (HttpURLConnection) url
//					.openConnection();
//			connection.setDoOutput(true);
//			connection.setDoInput(true);
//			connection.setRequestMethod("POST");
//			connection.setUseCaches(false);
//			connection.setInstanceFollowRedirects(true);
//			connection.setRequestProperty("Content-Type", "application/json");
//			connection.connect();
//			// POST请求
//			DataOutputStream out = new DataOutputStream(
//					connection.getOutputStream());
//			JSONObject obj = new JSONObject();
//			obj.put("sid", sid);
//			out.writeBytes(obj.toString());
//			out.flush();
//			out.close();
//			// 读取响应
//			is = connection.getInputStream();
//			Bitmap bitmap = BitmapFactory.decodeStream(is);
//			// 断开连接
//			connection.disconnect();
//			return bitmap;
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			if (null != is) {
//				try {
//					is.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}
		return null;
	}

//	public Bitmap sendToService(String st) {
//		InputStream is = null;
//		try {
//			// 创建连接
//			URL url = new URL(AppConstants.CAPTCHA);
//			HttpURLConnection connection = (HttpURLConnection) url
//					.openConnection();
//			connection.setDoOutput(true);
//			connection.setDoInput(true);
//			connection.setRequestMethod("POST");
//			connection.setUseCaches(false);
//			connection.setInstanceFollowRedirects(true);
//			connection.setRequestProperty("Content-Type", "application/json");
//			connection.connect();
//			// POST请求
//			DataOutputStream out = new DataOutputStream(
//					connection.getOutputStream());
//			JSONObject obj = new JSONObject();
//			obj.put("log", st);
//			out.writeBytes(obj.toString());
//			out.flush();
//			out.close();
//			// 读取响应
//			is = connection.getInputStream();
//			Bitmap bitmap = BitmapFactory.decodeStream(is);
//			// 断开连接
//			connection.disconnect();
//			return bitmap;
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			if (null != is) {
//				try {
//					is.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//		return null;
//	}

//	public JSONObject getNewVerson(String sid) {
//		InputStream is = null;
//		try {
//			// 创建连接
//			URL url = new URL(AppConstants.CAPTCHA);
//			HttpURLConnection connection = (HttpURLConnection) url
//					.openConnection();
//			connection.setDoOutput(true);
//			connection.setDoInput(true);
//			connection.setRequestMethod("POST");
//			connection.setUseCaches(false);
//			connection.setInstanceFollowRedirects(true);
//			connection.setRequestProperty("Content-Type", "application/json");
//			connection.connect();
//			// POST请求
//			DataOutputStream out = new DataOutputStream(
//					connection.getOutputStream());
//			JSONObject obj = new JSONObject();
//			obj.put("sid", sid);
//			out.writeBytes(obj.toString());
//			out.flush();
//			out.close();
//			// 读取响应
//			BufferedReader reader = null;
//			StringBuilder respond = new StringBuilder();
//			is = connection.getInputStream();
//			reader = new BufferedReader(new InputStreamReader(is));
//			int len = 0;
//			char[] buf = new char[1024];
//			while ((len = reader.read(buf)) != -1) {
//				respond.append(buf, 0, len);
//			}
//			System.out.println(respond.toString());
//			/*
//			 * BufferedReader reader = null; StringBuilder respond = new
//			 * StringBuilder(); reader = new BufferedReader(new
//			 * InputStreamReader(is)); int len = 0; char[] buf = new char[1024];
//			 * while ((len = reader.read(buf)) != -1) { respond.append(buf, 0,
//			 * len); } System.out.println("aaaa");
//			 * System.out.println(respond.toString());
//			 */
//			System.out.println("bbb");
//			String s = inputStream2String(is);
//			System.out.println("sss" + s);
//			System.out.println("ccc");
//			JSONObject jo = new JSONObject(s);
//			// 断开连接
//			connection.disconnect();
//			return jo;
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			if (null != is) {
//				try {
//					is.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//		return null;
//	}

	public JSONObject getSlideView() {
		InputStream is = null;
		try {
			// 创建连接
			URL url = new URL(AppConstants.GET_SLIDE_IMAGE);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.setRequestProperty("Content-Type", "application/json");
			connection.connect();
			// POST请求
			DataOutputStream out = new DataOutputStream(
					connection.getOutputStream());
			JSONObject obj = new JSONObject();
			obj.put("sid", AppVariables.sid);
			out.writeBytes(obj.toString());
			out.flush();
			out.close();
			// 读取响应
			BufferedReader reader = null;
			StringBuilder respond = new StringBuilder();
			is = connection.getInputStream();
			String s = inputStream2String(is);
			JSONObject jo = new JSONObject(s);
			// 断开连接
			connection.disconnect();
			return jo;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != is) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	/**
	 * 将InputStream转换成某种字符编码的String
	 *
	 * @param in
	 * @return
	 * @throws Exception
	 */
	private String InputStreamTOString(InputStream in) throws Exception {

		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] data = new byte[1024];
		int count = -1;
		while ((count = in.read(data, 0, 1024)) != -1)
			outStream.write(data, 0, count);
		data = null;
		return new String(outStream.toByteArray(), "UTF-8");
	}

	public static String inputStream2String(InputStream in) throws IOException {

		StringBuffer out = new StringBuffer();
		byte[] b = new byte[4096];
		int n;
		while ((n = in.read(b)) != -1) {
			out.append(new String(b, 0, n));
		}
		return out.toString();
	}
	
	/**
	 * jsons数据解析成为对应类
	 * @param json
	 * @param clazz 所属class
	 * @return
	 */
	public static <T> T jsonParse(String json, Class<T> clazz) {
		T result;
		try {
			Gson gson = new GsonBuilder().create();
			result = gson.fromJson(json, clazz);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return null;
		}
		return result;
		
	}
	
	/**
	 * 个人信息是否需要更新
	 * @return
	 */
	public static boolean checkNeedUpdate() {
		User user = CacheBean.getInstance().getUser();
		if (null == user || AppVariables.uid != Integer.parseInt(user.getUid())) return true;
		return System.currentTimeMillis()
				- user.getLastModTime() > AppVariables.cacheLiveTime;
	}

}
