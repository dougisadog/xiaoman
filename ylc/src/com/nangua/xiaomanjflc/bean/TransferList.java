package com.nangua.xiaomanjflc.bean;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TransferList {

	List<Transfer> transferList = new ArrayList<Transfer>();

	public TransferList(JSONArray items) throws JSONException {
		for (int i = 0; i < items.length(); i++) {

			JSONObject order = (JSONObject) items.get(i);

			Transfer transfer = new Transfer();
			if (order.has("products_title")) {
				transfer.setProducts_title(order.getString("products_title"));
			}
			if (order.has("transfer_products_title")) {
				transfer.setTransfer_products_title(order
						.getString("transfer_products_title"));
			}
			if (order.has("oid_platform_products_id")) {
				transfer.setOid_platform_products_id(order
						.getString("oid_platform_products_id"));
			}
			if (order.has("tender_amount")) {
				transfer.setTender_amount(order.getString("tender_amount"));
			}
			if (order.has("finance_interest_rate")) {
				transfer.setFinance_interest_rate(order
						.getString("finance_interest_rate"));
			}
			if (order.has("finance_period")) {
				transfer.setFinance_period(order.getString("finance_period"));
			}
			if (order.has("finance_start_interest_date")) {
				transfer.setFinance_start_interest_date(order
						.getString("finance_start_interest_date"));
			}
			if (order.has("finance_end_interest_date")) {
				transfer.setFinance_end_interest_date(order
						.getString("finance_end_interest_date"));
			}
			if (order.has("lave_date")) {
				transfer.setLave_date(order.getString("lave_date"));
			}
			if (order.has("oid_tender_id")) {
				transfer.setOid_tender_id(order.getString("oid_tender_id"));
			}
			if (order.has("oid_user_id")) {
				transfer.setOid_user_id(order.getString("oid_user_id"));
			}
			if (order.has("min_tender_amount")) {
				transfer.setMin_tender_amount(order
						.getString("min_tender_amount"));
			}
			if (order.has("ins_date")) {
				transfer.setIns_date(order.getString("ins_date"));
			}
			if (order.has("tender_from")) {
				transfer.setTender_from(order.getString("tender_from"));
			}
			if (order.has("transfer_capital")) {
				transfer.setTransfer_capital(order
						.getString("transfer_capital"));
			}
			if (order.has("transfer_capital_yes")) {
				transfer.setTransfer_capital_yes(order
						.getString("transfer_capital_yes"));
			}
			if (order.has("transfer_time")) {
				transfer.setTransfer_time(order.getString("transfer_time"));
			}
			if (order.has("transfer_success_time")) {
				transfer.setTransfer_success_time(order
						.getString("transfer_success_time"));
			}
			if (order.has("transfer_period")) {
				transfer.setTransfer_period(order.getString("transfer_period"));
			}
			transferList.add(transfer);
		}
	}

	public List<Transfer> getTransferList() {
		return transferList;
	}

	public void setTransferList(List<Transfer> transferList) {
		this.transferList = transferList;
	}
}
