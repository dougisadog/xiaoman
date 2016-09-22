package com.nangua.xiaomanjflc.bean;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Arrays;

public class Red {

	private int id;
	private String cash_price; // 现金券金额
	private String expire_time; // 过期时间
	private String active_time; // 激活时间
	private String[] used_time; // 使用时间
	private String cash_desc; // 现金券描述
	private String valid_days; // 有效天数
	private boolean checked; // 是否被选中

	public String getValid_days() {
		return valid_days;
	}

	public void setValid_days(String valid_days) {
		this.valid_days = valid_days;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCash_price() {
		return cash_price;
	}

	public void setCash_price(int cash_price) {
		this.cash_price = cash_price / 100 + "";
	}

	public String getExpire_time() {
		return expire_time;
	}
	public void setExpire_time(String expireTime) {
		this.expire_time = expireTime;
	}
	public void setExpire_time(long active_time, int day) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
		String sd = sdf.format(new Date((active_time + day * 86400 - 1) * 1000));
		this.expire_time = sd;
	}

	public String getActive_time() {
		return active_time;
	}

	public void setActive_time(long active_time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
		String sd = sdf.format(new Date(active_time * 1000));
		this.active_time = sd;
	}

	public String[] getUsed_time() {
		return used_time;
	}

	public void setUsed_time(long used_time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
		String sd = sdf.format(new Date(used_time * 1000));
		this.used_time = sd.split(" ");
	}

	public String getCash_desc() {
		return cash_desc;
	}

	public void setCach_Desc(String cash_desc) {
		this.cash_desc = cash_desc;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	@Override
	public String toString() {
		return "Red{" + "id=" + id + ", cash_price='" + cash_price + '\''
				+ ", expire_time='" + expire_time + '\'' + ", active_time='"
				+ active_time + '\'' + ", used_time="
				+ Arrays.toString(used_time) + ", cash_desc='" + cash_desc
				+ '\'' + '}';
	}
}
