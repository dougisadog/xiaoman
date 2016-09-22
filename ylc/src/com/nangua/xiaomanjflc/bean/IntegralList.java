package com.nangua.xiaomanjflc.bean;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class IntegralList {

	private List<Integral> integrals;

	public IntegralList(JSONArray array) throws JSONException {
		super();
		integrals = new ArrayList<Integral>();
		int len = array.length();
		for (int i = 0; i < len; i++) {
			Integral a = new Integral();
			JSONObject o = (JSONObject) array.get(i);
			if (o.has("ins_date"))
				a.setDate(o.getString("ins_date"));
			if (o.has("point_type"))
				a.setPoint_type(o.getString("point_type"));
			if (o.has("point_description"))
				a.setPoint_description(o.getString("point_description"));
			if (o.has("point"))
				a.setPoint(o.getString("point"));
			if (o.has("batch_run_time"))
				a.setBatch_run_time(o.getString("batch_run_time"));
			if (o.has("befor_point"))
				a.setBefor_point(o.getString("befor_point"));
			if (o.has("delete_point"))
				a.setDelete_point(o.getString("delete_point"));
			if (o.has("result_point"))
				a.setResult_point(o.getString("result_point"));
			integrals.add(a);
		}
	}

	public IntegralList(List<Integral> integrals, JSONArray array)
			throws JSONException {
		super();
		this.integrals = integrals;
		int len = array.length();
		for (int i = 0; i < len; i++) {
			Integral a = new Integral();
			JSONObject o = (JSONObject) array.get(i);
			if (o.has("ins_date"))
				a.setDate(o.getString("ins_date"));
			if (o.has("point_type"))
				a.setPoint_type(o.getString("point_type"));
			if (o.has("point_description"))
				a.setPoint_description(o.getString("point_description"));
			if (o.has("point"))
				a.setPoint(o.getString("point"));
			if (o.has("batch_run_time"))
				a.setBatch_run_time(o.getString("batch_run_time"));
			if (o.has("befor_point"))
				a.setBefor_point(o.getString("befor_point"));
			if (o.has("delete_point"))
				a.setDelete_point(o.getString("delete_point"));
			if (o.has("result_point"))
				a.setResult_point(o.getString("result_point"));
			this.integrals.add(a);
		}
	}

	public List<Integral> getIntegrals() {
		return integrals;
	}

}
