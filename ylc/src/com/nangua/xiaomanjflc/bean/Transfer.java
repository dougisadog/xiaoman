package com.nangua.xiaomanjflc.bean;

/**
 * 债权
 * */
public class Transfer {
	private String products_title;// 产品标题
	private String transfer_products_title;// 转让产品标题
	private String oid_platform_products_id;// 平台产品ID
	private String tender_amount;// 本金
	private String finance_interest_rate;// 预期收益率
	private String finance_period;// 期限
	private String finance_start_interest_date;// 起息日
	private String finance_end_interest_date;// 到息日
	private String lave_date;// 剩余天数
	private String oid_tender_id;
	private String oid_user_id;// 转让人ID
	private String min_tender_amount;// 最小投标金额
	private String ins_date;// 作成日
	private String tender_from;// 转让来源（1：借款标；2：债权标）
	private String transfer_capital;// 转让价格
	private String transfer_capital_yes;// 成功转让价格
	private String transfer_time;// 转让时间
	private String transfer_success_time;// 转让成功时间
	private String transfer_period;// 转让周期

	public String getTransfer_products_title() {
		return transfer_products_title;
	}

	public void setTransfer_products_title(String transfer_products_title) {
		this.transfer_products_title = transfer_products_title;
	}

	public String getOid_platform_products_id() {
		return oid_platform_products_id;
	}

	public void setOid_platform_products_id(String oid_platform_products_id) {
		this.oid_platform_products_id = oid_platform_products_id;
	}

	public String getOid_tender_id() {
		return oid_tender_id;
	}

	public void setOid_tender_id(String oid_tender_id) {
		this.oid_tender_id = oid_tender_id;
	}

	public String getOid_user_id() {
		return oid_user_id;
	}

	public void setOid_user_id(String oid_user_id) {
		this.oid_user_id = oid_user_id;
	}

	public String getMin_tender_amount() {
		return min_tender_amount;
	}

	public void setMin_tender_amount(String min_tender_amount) {
		this.min_tender_amount = min_tender_amount;
	}

	public String getIns_date() {
		return ins_date;
	}

	public void setIns_date(String ins_date) {
		this.ins_date = ins_date;
	}

	public String getTender_from() {
		return tender_from;
	}

	public void setTender_from(String tender_from) {
		this.tender_from = tender_from;
	}

	public String getTransfer_success_time() {
		return transfer_success_time;
	}

	public void setTransfer_success_time(String transfer_success_time) {
		this.transfer_success_time = transfer_success_time;
	}

	public String getTransfer_capital_yes() {
		return transfer_capital_yes;
	}

	public void setTransfer_capital_yes(String transfer_capital_yes) {
		this.transfer_capital_yes = transfer_capital_yes;
	}

	public String getTransfer_time() {
		return transfer_time;
	}

	public void setTransfer_time(String transfer_time) {
		this.transfer_time = transfer_time;
	}

	public String getTransfer_period() {
		return transfer_period;
	}

	public void setTransfer_period(String transfer_period) {
		this.transfer_period = transfer_period;
	}

	public String getTransfer_capital() {
		return transfer_capital;
	}

	public void setTransfer_capital(String transfer_capital) {
		this.transfer_capital = transfer_capital;
	}

	public String getProducts_title() {
		return products_title;
	}

	public void setProducts_title(String products_title) {
		this.products_title = products_title;
	}

	public String getTender_amount() {
		return tender_amount;
	}

	public void setTender_amount(String tender_amount) {
		this.tender_amount = tender_amount;
	}

	public String getFinance_interest_rate() {
		return finance_interest_rate;
	}

	public void setFinance_interest_rate(String finance_interest_rate) {
		this.finance_interest_rate = finance_interest_rate;
	}

	public String getFinance_period() {
		return finance_period;
	}

	public void setFinance_period(String finance_period) {
		this.finance_period = finance_period;
	}

	public String getFinance_start_interest_date() {
		return finance_start_interest_date;
	}

	public void setFinance_start_interest_date(
			String finance_start_interest_date) {
		this.finance_start_interest_date = finance_start_interest_date;
	}

	public String getFinance_end_interest_date() {
		return finance_end_interest_date;
	}

	public void setFinance_end_interest_date(String finance_end_interest_date) {
		this.finance_end_interest_date = finance_end_interest_date;
	}

	public String getLave_date() {
		return lave_date;
	}

	public void setLave_date(String lave_date) {
		this.lave_date = lave_date;
	}

}
