package com.nangua.xiaomanjflc.bean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProductList {

	private List<Product> products;

	public ProductList(JSONArray array) throws JSONException {
		super();
		products = new ArrayList<Product>();
		Product pnull = new Product();
		products.add(pnull);// 为了后来特殊需求加的
		int len = array.length();
		for (int i = 0; i < len; i++) {
			Product p = new Product();
			JSONObject o = (JSONObject) array.get(i);
			p.setType(o.getInt("productType"));
			p.setUrl(o.getString("productUrl"));
			p.setConfine(o.getInt("confine"));
			if (o.has("guaranteeModeName")) {
				p.setGuaranteeModeName(o.getString("guaranteeModeName"));
			}
			p.setSinglePurchaseLowerLimit(o.getInt("singlePurchaseLowerLimit"));
			p.setNameInfo(o.getString("activityTab"));
			p.setRemainingInvestmentAmount(o
					.getString("remainingInvestmentAmount"));
			p.setTotalInvestment(o.getString("totalInvestment"));
			p.setGain(o.getString("annualizedGain"));
			p.setActivity(o.getInt("activity"));
			if (o.has("activityType"))
				p.setActivityType(o.getString("activityType") == "null" ? 0 : o
						.getInt("activityType"));
			if (o.has("extraRate"))
				p.setExtraRate(o.getString("extraRate") == "null" ? 0 : o
						.getDouble("extraRate"));
			JSONArray a = o.getJSONArray("investmentPeriodDesc");
			p.setId(o.getInt("id"));
			p.setDeadline(a.get(0) + "");
			p.setDeadlinedesc(a.getString(1));
			p.setName(o.getString("name"));
			p.setPercentage(o.getInt("investmentProgress"));
			p.setRepayMethod(o.getString("repaymentMethodName"));
			p.setStatus(o.getInt("status"));
			p.setNewstatus(o.getInt("newstatus"));
			products.add(p);
		}
	}

	public ProductList(List<Product> ps, JSONArray array) throws JSONException {
		super();
		products = ps;
		int len = array.length();
		for (int i = 0; i < len; i++) {
			Product p = new Product();
			JSONObject o = (JSONObject) array.get(i);
			p.setType(o.getInt("productType"));
			p.setUrl(o.getString("productUrl"));
			p.setConfine(o.getInt("confine"));
			p.setGuaranteeModeName(o.getString("guaranteeModeName"));
			p.setSinglePurchaseLowerLimit(o.getInt("singlePurchaseLowerLimit"));
			p.setNameInfo(o.getString("activityTab"));
			p.setRemainingInvestmentAmount(o
					.getString("remainingInvestmentAmount"));
			p.setTotalInvestment(o.getString("totalInvestment"));
			p.setGain(o.getString("annualizedGain"));
			p.setActivity(o.getInt("activity"));
			if (o.has("activityType"))
				p.setActivityType(o.getString("activityType") == "null" ? 0 : o
						.getInt("activityType"));
			if (o.has("extraRate"))
				p.setExtraRate(o.getString("extraRate") == "null" ? 0 : o
						.getInt("extraRate"));
			JSONArray a = o.getJSONArray("investmentPeriodDesc");
			p.setId(o.getInt("id"));
			p.setDeadline(a.get(0) + "");
			p.setDeadlinedesc(a.getString(1));
			p.setName(o.getString("name"));
			p.setPercentage(o.getInt("investmentProgress"));
			p.setRepayMethod(o.getString("repaymentMethodName"));
			p.setStatus(o.getInt("status"));
			p.setNewstatus(o.getInt("newstatus"));
			products.add(p);
		}
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	@Override
	public String toString() {
		int len = products.size();
		String st = "";
		for (int i = 0; i < len; i++) {
			Product p = products.get(i);
			st += " /**" + i + p.toString();
		}
		return st;
	}

}
