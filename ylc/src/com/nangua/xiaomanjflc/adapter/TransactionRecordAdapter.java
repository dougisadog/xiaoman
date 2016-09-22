package com.nangua.xiaomanjflc.adapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.nangua.xiaomanjflc.bean.Transaction;
import com.nangua.xiaomanjflc.widget.FontTextView;
import com.nangua.xiaomanjflc.R;

public class TransactionRecordAdapter extends BaseAdapter {

	private LayoutInflater layoutInflater;

	private List<Transaction> transactions;

	public TransactionRecordAdapter(Context context,
			List<Transaction> transactions) {
		this.layoutInflater = LayoutInflater.from(context);
		this.transactions = transactions;
	}

	@Override
	public int getCount() {
		return transactions.size();
	}

	@Override
	public Object getItem(int position) {
		return transactions.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		holder = new ViewHolder();
		if (transactions.get(position).getDateflag() != null) {
			convertView = layoutInflater.inflate(
					R.layout.item_transaction_record_date, null);

			holder.tv_date = (FontTextView) convertView
					.findViewById(R.id.tv_date);

			holder.tv_date.setText(transactions.get(position).getDateflag());
		} else {
			convertView = layoutInflater.inflate(
					R.layout.item_transaction_record, null);
			holder.transactionId = (FontTextView) convertView
					.findViewById(R.id.transactionId);
			holder.amount = (FontTextView) convertView
					.findViewById(R.id.amount);
			holder.createTime = (FontTextView) convertView
					.findViewById(R.id.createTime);
			holder.transactionType = (FontTextView) convertView
					.findViewById(R.id.transactionType);

			holder.transactionId.setText("交易号："
					+ transactions.get(position).getTransactionId());
			if (transactions.get(position).getOperationAmount().startsWith("+")) {
				holder.amount.setTextColor(Color.rgb(61, 145, 64));
			} else if (transactions.get(position).getOperationAmount()
					.startsWith("-")) {
				holder.amount.setTextColor(Color.rgb(253, 153, 18));
			} else {
				holder.amount.setTextColor(Color.rgb(61, 145, 64));
			}
			holder.amount.setText(transactions.get(position)
					.getOperationAmount());
			try {
				SimpleDateFormat sdfold = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				Date date = sdfold.parse(transactions.get(position)
						.getCreateTime());
				SimpleDateFormat sdfnew = new SimpleDateFormat("MM-dd HH:mm");
				holder.createTime.setText(sdfnew.format(date));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			holder.transactionType.setText(transactions.get(position)
					.getTransactionType());

		}

		return convertView;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	public static class ViewHolder {
		public FontTextView transactionId;
		public FontTextView amount;
		public FontTextView createTime;
		public FontTextView transactionType;
		public FontTextView tv_date;
	}

}