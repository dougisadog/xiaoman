package com.nangua.xiaomanjflc.bean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class oldProductList {

    private List<oldProduct> oldProducts;

    public oldProductList(JSONArray array) throws JSONException {
        super();
        oldProducts = new ArrayList<oldProduct>();
        oldProduct pnull = new oldProduct();
        oldProducts.add(pnull);//为了后来特殊需求加的
        int len = array.length();
        for (int i = 0; i < len; i++) {
            oldProduct p = new oldProduct();
            JSONObject o = (JSONObject) array.get(i);
            p.setGain(o.getString("annualizedGain"));
            JSONArray a = o.getJSONArray("investmentPeriodDesc");
            p.setId(o.getInt("id"));
            p.setDeadline(a.get(0) + a.getString(1));
            p.setName(o.getString("name"));
            p.setPercentage(o.getInt("investmentProgress"));
            p.setRepayMethod(o.getString("repaymentMethodName"));
            p.setStatus(o.getInt("status"));
            p.setNewstatus(o.getInt("newStatus"));
            oldProducts.add(p);
        }
    }

    public oldProductList(List<oldProduct> ps, JSONArray array) throws JSONException {
        super();
        oldProducts = ps;
        int len = array.length();
        for (int i = 0; i < len; i++) {
            oldProduct p = new oldProduct();
            JSONObject o = (JSONObject) array.get(i);
            p.setGain(o.getString("annualizedGain"));
            JSONArray a = o.getJSONArray("investmentPeriodDesc");
            p.setId(o.getInt("id"));
            p.setDeadline(a.get(0) + a.getString(1));
            p.setName(o.getString("name"));
            p.setPercentage(o.getInt("investmentProgress"));
            p.setRepayMethod(o.getString("repaymentMethodName"));
            p.setStatus(o.getInt("status"));
            p.setNewstatus(o.getInt("newstatus"));
            oldProducts.add(p);
        }
    }

    public List<oldProduct> getOldProducts() {
        return oldProducts;
    }

    public void setOldProducts(List<oldProduct> oldProducts) {
        this.oldProducts = oldProducts;
    }

    @Override
    public String toString() {
        int len = oldProducts.size();
        String st = "";
        for (int i = 0; i < len; i++) {
            oldProduct p = oldProducts.get(i);
            st += " /**" + i + p.toString();
        }
        return st;
    }

}
