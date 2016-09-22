package com.nangua.xiaomanjflc.bean;

public class oldProduct {

    private int id;

    private String name; //产品名称 name

    private String gain; //预期年化收益 annualizedGain

    private String deadline; //投资时限 investmentPeriodDesc

    private int percentage; //百分比 investmentProgress

    private String repayMethod; //到期还本付息(还款方式) repaymentMethodName

    private int status; //产品状态

    private int newstatus;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getNewstatus() {
        return newstatus;
    }

    public void setNewstatus(int newstatus) {
        this.newstatus = newstatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGain() {
        return gain;
    }

    public void setGain(String gain) {
        this.gain = gain;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    public String getRepayMethod() {
        return repayMethod;
    }

    public void setRepayMethod(String repayMethod) {
        this.repayMethod = repayMethod;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return "Product [name=" + name + ", gain=" + gain + ", deadline="
                + deadline + ", percentage=" + percentage + ", repayMethod="
                + repayMethod + "]";
    }

}
