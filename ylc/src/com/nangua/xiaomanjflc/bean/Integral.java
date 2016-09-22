package com.nangua.xiaomanjflc.bean;

/**
 * 积分商城
 * */
public class Integral {

	private String date;// 操作日期
	private String point_type;// 操作类型
	private String point;// 操作类型 已过期
	private String point_description;// 备注

	private String batch_run_time;
	private String befor_point;
	private String delete_point;
	private String result_point;

	private String img_path;
	private String description;
	private String cost_money;
	private String cost_point;
	private String commodity_id;
	private String red_packet_id;

	public String getBatch_run_time() {
		return batch_run_time;
	}

	public void setBatch_run_time(String batch_run_time) {
		this.batch_run_time = batch_run_time;
	}

	public String getBefor_point() {
		return befor_point;
	}

	public void setBefor_point(String befor_point) {
		this.befor_point = befor_point;
	}

	public String getDelete_point() {
		return delete_point;
	}

	public void setDelete_point(String delete_point) {
		this.delete_point = delete_point;
	}

	public String getResult_point() {
		return result_point;
	}

	public void setResult_point(String result_point) {
		this.result_point = result_point;
	}

	public String getImg_path() {
		return img_path;
	}

	public void setImg_path(String img_path) {
		this.img_path = img_path;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCost_money() {
		return cost_money;
	}

	public void setCost_money(String cost_money) {
		this.cost_money = cost_money;
	}

	public String getCost_point() {
		return cost_point;
	}

	public void setCost_point(String cost_point) {
		this.cost_point = cost_point;
	}

	public String getCommodity_id() {
		return commodity_id;
	}

	public void setCommodity_id(String commodity_id) {
		this.commodity_id = commodity_id;
	}

	public String getRed_packet_id() {
		return red_packet_id;
	}

	public void setRed_packet_id(String red_packet_id) {
		this.red_packet_id = red_packet_id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getPoint_description() {
		return point_description;
	}

	public void setPoint_description(String point_description) {
		this.point_description = point_description;
	}

	public String getPoint_type() {
		return point_type;
	}

	public void setPoint_type(String point_type) {
		this.point_type = point_type;
	}

	public String getPoint() {
		return point;
	}

	public void setPoint(String point) {
		this.point = point;
	}

}
