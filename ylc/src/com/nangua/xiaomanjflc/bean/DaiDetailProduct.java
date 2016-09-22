package com.nangua.xiaomanjflc.bean;

import com.louding.frame.utils.StringUtils;
import com.nangua.xiaomanjflc.utils.FormatUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DaiDetailProduct {

    //产品信息部分
    private int id;//商品id
    private String name;
    private int status;
    private int newstatus;
    private String totalInvestment;    //总投资额 单位是分
    private String totalInvestmentunit;    //总投资额
    private String investmentPeriodDesc;    //投资时限
    private String investmentPeriodDescunit;    //投资时限
    private String annualizedGain;    //预计年化收益率
    private String guaranteeModeName;        //保障方式
    private String repaymentMethodName;    //还款方式
    private int investmentProgress;    //百分比
    private String expirationDate;    //投标截止
    private String interestBeginDate;        //计息开始
    private String remainingInvestmentAmount;        //可投 单位分
    private String singlePurchaseLowerLimit;        //起投 单位分
    private String baseLimitAmount;        //（输入此字段的整数倍，单位是分）

    //贷款描述
    private String description;        //贷款描述

    //个人信息
    private String username;        //用户
    private String gender;        //性别
    private String age;        //年龄
    private String maritalStatus;        //是否结婚
    private String permanentAddressCity;        //户籍城市
    private String highestEducation;            //学历

    //个人资产及征信信息
    private String propertyCertificateFile;            //房产
    private String mortgageAmount;            //房贷
    private String vehicleIdentificationFile;    //车产
    private String carloanAmount;        //车贷
    private String annualIncome;    //年收入水平

    //工作信息
    private String companyAddressCity;        // 工作城市
    private String profession;            //职业
    private String companySize;        //单位规模
    private String companyIndustry;        //公司行业
    private String job;        //职务
    //private String annualIncome;		工作起始年份没找到

    private String collateralDescription;            //抵押情况

    private String bondingDesc; //担保方介绍
    private String guarantee; //安全保障

    //审核状况
    private int auditinfo; //auditinfo  从product里取，判断是企业还是个人
    //个人
    private int is_id_card; //身份验证
    private int is_credit; //信用报告
    private int is_organization; //担保机构审核
    private int is_house; //房产证
    private int is_marry; //婚烟证明
    private int is_household; //户口本
    //企业
    private int is_business_num; //营业执照
    private int is_legal_cardid; //法人身份证
    private int is_institution; //机构担保
    private int is_platform; //平台审核

    //借款人信息
    private int origininfo; //origininfo  从product里取，判断是企业还是个人
    //个人
    private String o_real_name; //姓名
    private String o_gender; //性别
    private String o_age; //年龄
    private String o_purpose; //资金用途
    //企业
    private String company_name; //名称
    private String registered_capital; //注册资本
    private String legal_person; //法人代表
    private String business_num; //营业执照
    private String industry; //所属行业
    private String earning; //年经营输入
    private String simple_desc; //简单描述

    private int activityType; //为3 下面有值
    private double extraRate;

    public int getActivityType() {
        return activityType;
    }

    public void setActivityType(int activityType) {
        this.activityType = activityType;
    }

    public double getExtraRate() {
        return extraRate;
    }

    public void setExtraRate(double extraRate) {
        this.extraRate = extraRate;
    }

    public DaiDetailProduct(JSONObject o) throws JSONException {
        super();
        JSONObject product = o.getJSONObject("product");
        //产品信息部分
        id = product.getInt("id");
        name = product.getString("name");
        status = product.getInt("status");
        newstatus = product.getInt("newstatus");
        if(product.has("activityType"))activityType=(product.getString("activityType") == "null" ? 0 : product.getInt("activityType"));
        if(product.has("extraRate"))extraRate=(product.getString("extraRate") =="null"? 0 : product.getDouble("extraRate"));
        totalInvestment = FormatUtils.getNewAmount(product.getString("totalInvestment")).get(0);
        totalInvestmentunit = FormatUtils.getNewAmount(product.getString("totalInvestment")).get(1);
        JSONArray a = product.getJSONArray("investmentPeriodDesc");
        investmentPeriodDesc = a.get(0)+"";
        investmentPeriodDescunit = a.get(1)+"";
        annualizedGain = product.getString("annualizedGain");
        guaranteeModeName = product.getString("guaranteeModeName");
        repaymentMethodName = product.getString("repaymentMethodName");
        investmentProgress = product.getInt("investmentProgress");
        expirationDate = product.getString("expirationDate");
        expirationDate = expirationDate.split(" ")[0];
        interestBeginDate = product.getString("interestBeginDate");
        interestBeginDate = interestBeginDate.split(" ")[0];
        remainingInvestmentAmount = FormatUtils.getAmount(product.getString("remainingInvestmentAmount"));
        singlePurchaseLowerLimit = FormatUtils.getAmount(product.getString("singlePurchaseLowerLimit"));
        baseLimitAmount = product.getString("baseLimitAmount");
        //贷款描述
        description = product.getString("description")=="null"?"":product.getString("description");
        //个人信息
        JSONObject debtor = o.getJSONObject("debtor");
        username = debtor.getString("name");
        gender = debtor.getString("gender");
        age = debtor.getString("age");
        maritalStatus = debtor.getString("maritalStatus");
        permanentAddressCity = debtor.getString("permanentAddressCity");
        highestEducation = debtor.getString("highestEducation");
        //个人资产及征信信息
        JSONObject application = o.getJSONObject("application");
        propertyCertificateFile = application.getString("propertyCertificateFile");//=="null"?"无":application.getString("propertyCertificateFile");
        mortgageAmount = application.getString("mortgageAmount");//=="null"?"无":application.getString("mortgageAmount");
        vehicleIdentificationFile = application.getString("vehicleIdentificationFile");//=="null"?"无":application.getString("vehicleIdentificationFile");
        carloanAmount = application.getString("carloanAmount");//=="null"?"无":application.getString("carloanAmount");
        if (debtor.has("annualIncome"))
            annualIncome = debtor.getString("annualIncome");//=="null"?"无":debtor.getString("annualIncome");
        //工作信息
        companyAddressCity = debtor.getString("companyAddressCity");
        profession = debtor.getString("profession");
        companySize = debtor.getString("companySize");
        companyIndustry = debtor.getString("companyIndustry");
        job = debtor.getString("job");

        collateralDescription = product.getString("collateralDescription");
        //担保方介绍
        bondingDesc = product.getString("bondingDesc")=="null"?"":product.getString("bondingDesc");
        //安全保障
        guarantee = product.getString("guarantee")=="null"?"":product.getString("guarantee");
        //审核状况
        auditinfo = product.getInt("auditinfo");
        if(auditinfo==1){
            JSONObject au = o.getJSONObject("auditinfo");
            is_id_card = au.getInt("is_id_card"); //身份验证
            is_credit = au.getInt("is_credit"); //信用报告
            is_organization = au.getInt("is_organization"); //担保机构审核
            is_house = au.getInt("is_house"); //房产证
            is_marry = au.getInt("is_marry"); //婚烟证明
            is_household = au.getInt("is_household"); //户口本
        }else if(auditinfo==2){
            JSONObject au = o.getJSONObject("auditinfo");
            is_business_num = au.getInt("is_business_num"); //营业执照
            is_legal_cardid = au.getInt("is_legal_cardid"); //法人身份证
            is_institution = au.getInt("is_institution"); //机构担保
            is_platform = au.getInt("is_platform"); //平台审核
        }
        //借款人信息
        origininfo = product.getInt("origininfo");
        if(origininfo==1){
            JSONObject au = o.getJSONObject("origininfo");
            o_real_name = au.getString("real_name");
            o_gender = au.getInt("gender")==0?"男":"女";
            o_age = au.getString("age");
            o_purpose = au.getString("purpose");
        }else if(origininfo==2){
            JSONObject au = o.getJSONObject("origininfo");
            company_name = StringUtils.isJsonEmpty(au.getString("company_name"))?"-":au.getString("company_name");
            registered_capital = StringUtils.isJsonEmpty(au.getString("registered_capital"))?"-":au.getString("registered_capital");
            legal_person = StringUtils.isJsonEmpty(au.getString("legal_person"))?"-":au.getString("legal_person");
            business_num = StringUtils.isJsonEmpty(au.getString("business_num"))?"-":au.getString("business_num");
            industry = StringUtils.isJsonEmpty(au.getString("industry"))?"-":au.getString("industry");
            earning = StringUtils.isJsonEmpty(au.getString("earning"))?"-":au.getString("earning");
            simple_desc = StringUtils.isJsonEmpty(au.getString("simple_desc"))?"-":au.getString("simple_desc");
        }
    }


    //产品信息部分
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getStatus() {
        return status;
    }

    public int getNewstatus() {
        return newstatus;
    }

    public void setNewstatus(int newstatus) {
        this.newstatus = newstatus;
    }

    public String getTotalInvestment() {
        return totalInvestment;
    }

    public String getInvestmentPeriodDesc() {
        return investmentPeriodDesc;
    }

    public String getAnnualizedGain() {
        return annualizedGain;
    }

    public String getGuaranteeModeName() {
        return guaranteeModeName;
    }

    public String getRepaymentMethodName() {
        return repaymentMethodName;
    }

    public int getInvestmentProgress() {
        return investmentProgress;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public String getInterestBeginDate() {
        return interestBeginDate;
    }

    public String getRemainingInvestmentAmount() {
        return remainingInvestmentAmount;
    }

    public String getSinglePurchaseLowerLimit() {
        return singlePurchaseLowerLimit;
    }

    public String getBaseLimitAmount() {
        return baseLimitAmount;
    }

    public String getTotalInvestmentunit() {
        return totalInvestmentunit;
    }

    public void setTotalInvestmentunit(String totalInvestmentunit) {
        this.totalInvestmentunit = totalInvestmentunit;
    }

    public String getInvestmentPeriodDescunit() {
        return investmentPeriodDescunit;
    }

    public void setInvestmentPeriodDescunit(String investmentPeriodDescunit) {
        this.investmentPeriodDescunit = investmentPeriodDescunit;
    }

    //产品描述
    public String getDescription() {
        return description;
    }

    //个人信息
    public String getUsername() {
        return username;
    }

    public String getGender() {
        return gender;
    }

    public String getAge() {
        return age;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public String getPermanentAddressCity() {
        return permanentAddressCity;
    }

    public String getHighestEducation() {
        return highestEducation;
    }

    //个人资产及征信信息
    public String getPropertyCertificateFile() {
        return propertyCertificateFile;
    }

    public String getMortgageAmount() {
        return mortgageAmount;
    }

    public String getVehicleIdentificationFile() {
        return vehicleIdentificationFile;
    }

    public String getCarloanAmount() {
        return carloanAmount;
    }

    public String getAnnualIncome() {
        return annualIncome;
    }
    //工作信息

    public String getCompanyAddressCity() {
        return companyAddressCity;
    }

    public String getProfession() {
        return profession;
    }

    public String getCompanySize() {
        return companySize;
    }

    public String getCompanyIndustry() {
        return companyIndustry;
    }

    public String getJob() {
        return job;
    }

    public String getCollateralDescription() {
        return collateralDescription;
    }

    public String getBondingDesc() {
        return bondingDesc;
    }

    public void setBondingDesc(String bondingDesc) {
        this.bondingDesc = bondingDesc;
    }

    public String getGuarantee() {
        return guarantee;
    }

    public void setGuarantee(String guarantee) {
        this.guarantee = guarantee;
    }

    public int getAuditinfo() {
        return auditinfo;
    }

    public void setAuditinfo(int auditinfo) {
        this.auditinfo = auditinfo;
    }

    public int getIs_id_card() {
        return is_id_card;
    }

    public void setIs_id_card(int is_id_card) {
        this.is_id_card = is_id_card;
    }

    public int getIs_credit() {
        return is_credit;
    }

    public void setIs_credit(int is_credit) {
        this.is_credit = is_credit;
    }

    public int getIs_organization() {
        return is_organization;
    }

    public void setIs_organization(int is_organization) {
        this.is_organization = is_organization;
    }

    public int getIs_house() {
        return is_house;
    }

    public void setIs_house(int is_house) {
        this.is_house = is_house;
    }

    public int getIs_marry() {
        return is_marry;
    }

    public void setIs_marry(int is_marry) {
        this.is_marry = is_marry;
    }

    public int getIs_household() {
        return is_household;
    }

    public void setIs_household(int is_household) {
        this.is_household = is_household;
    }

    public int getIs_platform() {
        return is_platform;
    }

    public void setIs_platform(int is_platform) {
        this.is_platform = is_platform;
    }

    public int getIs_institution() {
        return is_institution;
    }

    public void setIs_institution(int is_institution) {
        this.is_institution = is_institution;
    }

    public int getIs_legal_cardid() {
        return is_legal_cardid;
    }

    public void setIs_legal_cardid(int is_legal_cardid) {
        this.is_legal_cardid = is_legal_cardid;
    }

    public int getIs_business_num() {
        return is_business_num;
    }

    public void setIs_business_num(int is_business_num) {
        this.is_business_num = is_business_num;
    }

    public int getOrigininfo() {
        return origininfo;
    }

    public void setOrigininfo(int origininfo) {
        this.origininfo = origininfo;
    }

    public String getO_purpose() {
        return o_purpose;
    }

    public void setO_purpose(String o_purpose) {
        this.o_purpose = o_purpose;
    }

    public String getO_real_name() {
        return o_real_name;
    }

    public void setO_real_name(String o_real_name) {
        this.o_real_name = o_real_name;
    }

    public String getO_gender() {
        return o_gender;
    }

    public void setO_gender(String o_gender) {
        this.o_gender = o_gender;
    }

    public String getO_age() {
        return o_age;
    }

    public void setO_age(String o_age) {
        this.o_age = o_age;
    }

    public String getSimple_desc() {
        return simple_desc;
    }

    public void setSimple_desc(String simple_desc) {
        this.simple_desc = simple_desc;
    }

    public String getEarning() {
        return earning;
    }

    public void setEarning(String earning) {
        this.earning = earning;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getBusiness_num() {
        return business_num;
    }

    public void setBusiness_num(String business_num) {
        this.business_num = business_num;
    }

    public String getLegal_person() {
        return legal_person;
    }

    public void setLegal_person(String legal_person) {
        this.legal_person = legal_person;
    }

    public String getRegistered_capital() {
        return registered_capital;
    }

    public void setRegistered_capital(String registered_capital) {
        this.registered_capital = registered_capital;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }
}
