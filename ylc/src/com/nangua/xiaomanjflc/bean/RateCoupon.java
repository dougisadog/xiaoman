package com.nangua.xiaomanjflc.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 加息券发放明细Dto类
 * Created by zm on 2017/8/24.
 */
public class RateCoupon {

	private static final long serialVersionUID = 1L;
	
	public static int AVAILABLE = 0; //未使用
	
	public static int USED = 1;//已使用
	
	public static int EXPIRED = 2;//过期 
	
	/** 加息券编号 */
	private String rateCouponId;
	
	private String rateCouponName;
	
    /** 使用状态 (0：未使用，1：已使用；2：已失效；) */
    private String usedFlg;
    
    private String validDays;
    
    private String validFlg;
    
    private long fromDate;
    
    private long usedDate;
    
    /** 使用时间 */
    private long validDate;

    private String sendType;

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
    
    public static String getFormattedDate(long time) {
		String sd = sdf.format(new Date(time));
		return sd;
    }
	/**
	 * 取得加息券编号get的方法。
	 *
	 * @return
	 */
	public String getRateCouponId() {
		return rateCouponId;
	}
	/**
	 * 设置加息券编号set的方法。
	 *
	 * @param rateCouponId
	 */
	public void setRateCouponId(String rateCouponId) {
		this.rateCouponId = rateCouponId;
	}

	/**
	 * 取得使用状态get的方法。
	 *
	 * @return
	 */
	public String getUsedFlg() {
		return usedFlg;
	}
	/**
	 * 设置使用状态set的方法。
	 *
	 * @param usedFlg
	 */
	public void setUsedFlg(String usedFlg) {
		this.usedFlg = usedFlg;
	}
	
	/**
	 * 取得登录者get的方法。
	 *
	 * @return
	 */

	public String getSendType() {
		return sendType;
	}

	public void setSendType(String sendType) {
		this.sendType = sendType;
	}
	public String getRateCouponName() {
		return rateCouponName;
	}
	public void setRateCouponName(String rateCouponName) {
		this.rateCouponName = rateCouponName;
	}
	public String getValidDays() {
		return validDays;
	}
	public void setValidDays(String validDays) {
		this.validDays = validDays;
	}
	public String getValidFlg() {
		return validFlg;
	}
	public void setValidFlg(String validFlg) {
		this.validFlg = validFlg;
	}
	public long getFromDate() {
		return fromDate;
	}
	public void setFromDate(long fromDate) {
		this.fromDate = fromDate;
	}
	public long getUsedDate() {
		return usedDate;
	}
	public void setUsedDate(long usedDate) {
		this.usedDate = usedDate;
	}
	public long getValidDate() {
		return validDate;
	}
	public void setValidDate(long validDate) {
		this.validDate = validDate;
	}


}
