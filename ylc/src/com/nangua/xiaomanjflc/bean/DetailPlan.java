package com.nangua.xiaomanjflc.bean;

public class DetailPlan {

    private String date;
    private String principal;
    private String interest;
    private String amount;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "DetailPlan [date=" + date + ", principal=" + principal
                + ", interest=" + interest + ", amount=" + amount + "]";
    }

}
