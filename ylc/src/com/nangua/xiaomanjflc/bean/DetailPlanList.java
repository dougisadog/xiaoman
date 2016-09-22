package com.nangua.xiaomanjflc.bean;

import com.nangua.xiaomanjflc.utils.FormatUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DetailPlanList {

	private List<DetailPlan> list;

	public DetailPlanList(JSONArray array, String startDate)
			throws JSONException {
		super();
		list = new ArrayList<DetailPlan>();
//		array.getString("pdate");
		int len = array.length();
		long data = getTime(startDate);
		for (int i = 0; i < len; i++) {
			DetailPlan a = new DetailPlan();
			JSONObject o = (JSONObject) array.get(i);
			a.setDate(getStrTime(data * 1000));
			String p = o.getString("principal");
			String b = o.getString("interest");
			long ip = Long.parseLong(p);
			long ib = Long.parseLong(b);
			a.setPrincipal(FormatUtils.priceFormat(ip));
			a.setInterest(FormatUtils.priceFormat(ib));
			a.setAmount(FormatUtils.priceFormat(ip + ib));
			list.add(a);
			data = nextData(data);
		}
	}

	private long nextData(long data) {
		String s = getStrTime(data * 1000);
		String[] d = s.split("-");
		int year = Integer.parseInt(d[0]);
		int month = Integer.parseInt(d[1]);
		int day = Integer.parseInt(d[2]);
		if (month == 2) {
			if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
				data += (86400 * 29);
			} else {
				data += (86400 * 28);
			}
		} else if (month == 1) {
			if (day == 31) {
				data += (86400 * 28);
			} else if (day == 30) {
				data += (86400 * 29);
			} else if (day == 29) {
				if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
					data += (86400 * 31);
				} else {
					data += (86400 * 30);
				}
			} else {
				data += (86400 * 31);
			}
		} else if (month == 3 || month == 5 || month == 7 || month == 8
				|| month == 10 || month == 12) {
			if (day > 30) {
				data += (86400 * 30);
			} else {
				data += (86400 * 31);
			}
		} else {
			data += (86400 * 30);
		}
		return data;
	}

	private String getStrTime(long cc_time) {
		String re_StrTime = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// 例如：cc_time=1291778220
		re_StrTime = sdf.format(new Date(cc_time));
		return re_StrTime;
	}

	private long getTime(String user_time) {
		String re_time = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d;
		long l = 0;
		try {
			d = sdf.parse(user_time);
			l = d.getTime();
			System.out.println(l);
			String str = String.valueOf(l);
			re_time = str.substring(0, 10);
			System.out.println(re_time);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return l / 1000;
	}

	public List<DetailPlan> getList() {
		return list;
	}

	public void setList(List<DetailPlan> list) {
		this.list = list;
	}

	@Override
	public String toString() {
		int len = list.size();
		String st = "";
		for (int i = 0; i < len; i++) {
			DetailPlan a = list.get(i);
			st += " /**" + i + a.toString();
		}
		return st;
	}

}
