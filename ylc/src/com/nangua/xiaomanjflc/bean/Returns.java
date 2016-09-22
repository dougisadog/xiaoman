package com.nangua.xiaomanjflc.bean;

import android.R.string;

import com.google.gson.annotations.SerializedName;

public class Returns<T> {
	
	@SerializedName("status")
	private int status;		//返回状态码
	
	@SerializedName("sid")
	private string sid; 	//返回身份标识
	
	@SerializedName("code")
	private string code; 	//
	
	@SerializedName("msg")
	private string msg; 	//返回错误信息说明

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public string getSid() {
		return sid;
	}

	public void setSid(string sid) {
		this.sid = sid;
	}

	public string getCode() {
		return code;
	}

	public void setCode(string code) {
		this.code = code;
	}

	public string getMsg() {
		return msg;
	}

	public void setMsg(string msg) {
		this.msg = msg;
	}
	
}
