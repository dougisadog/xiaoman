package com.nangua.xiaomanjflc.ui;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.louding.frame.KJActivity;
import com.louding.frame.KJHttp;
import com.louding.frame.http.HttpCallBack;
import com.louding.frame.http.HttpParams;
import com.louding.frame.ui.BindView;
import com.louding.frame.utils.StringUtils;
import com.louding.frame.widget.KJListView;
import com.louding.frame.widget.KJRefreshListener;
import com.nangua.xiaomanjflc.AppConstants;
import com.nangua.xiaomanjflc.AppVariables;
import com.nangua.xiaomanjflc.adapter.CommonAdapter;
import com.nangua.xiaomanjflc.adapter.ViewHolder;
import com.nangua.xiaomanjflc.bean.DaiDetailProduct;
import com.nangua.xiaomanjflc.bean.DetailPlan;
import com.nangua.xiaomanjflc.bean.DetailPlanList;
import com.nangua.xiaomanjflc.bean.DetailRecord;
import com.nangua.xiaomanjflc.bean.DetailRecordList;
import com.nangua.xiaomanjflc.support.UIHelper;
import com.nangua.xiaomanjflc.utils.FormatUtils;
import com.nangua.xiaomanjflc.widget.CircleProgressBar;
import com.nangua.xiaomanjflc.widget.FontTextView;
import com.nangua.xiaomanjflc.widget.LoudingDialog;
import com.nangua.xiaomanjflc.R;

public class DaiDetailActivity extends KJActivity {

	@BindView(id = R.id.one, click = true)
	private FontTextView one;
	@BindView(id = R.id.two, click = true)
	private FontTextView two;
	@BindView(id = R.id.three, click = true)
	private FontTextView three;
	@BindView(id = R.id.product)
	private LinearLayout mProduct;
	@BindView(id = R.id.plan)
	private LinearLayout mPlan;
	@BindView(id = R.id.record)
	private LinearLayout mRecord;
	@BindView(id = R.id.project_profile)
	private LinearLayout project_profile;

	// 产品详情
	@BindView(id = R.id.name)
	private FontTextView name;
	@BindView(id = R.id.status)
	private FontTextView status;
	@BindView(id = R.id.totalInvestment)
	private FontTextView totalInvestment;
	@BindView(id = R.id.totalInvestmentunit)
	private FontTextView totalInvestmentunit;
	@BindView(id = R.id.investmentPeriodDesc)
	private FontTextView investmentPeriodDesc;
	@BindView(id = R.id.investmentPeriodDescunit)
	private FontTextView investmentPeriodDescunit;
	@BindView(id = R.id.annualizedGain)
	private FontTextView annualizedGain;
	@BindView(id = R.id.guaranteeModeName)
	private FontTextView guaranteeModeName;
	@BindView(id = R.id.repaymentMethodName)
	private FontTextView repaymentMethodName;
	@BindView(id = R.id.expirationDate)
	private FontTextView expirationDate;
	@BindView(id = R.id.interestBeginDate)
	private FontTextView interestBeginDate;
	@BindView(id = R.id.remainingInvestmentAmount)
	private FontTextView remainingInvestmentAmount;
	@BindView(id = R.id.singlePurchaseLowerLimit)
	private FontTextView singlePurchaseLowerLimit;
	@BindView(id = R.id.percentagetxt)
	private FontTextView percentagetxt;
	@BindView(id = R.id.percentage)
	private FontTextView percentage;
	@BindView(id = R.id.p)
	private FontTextView p;
	@BindView(id = R.id.percentagepb)
	private CircleProgressBar percentagepb;
	// 贷款描述
	@BindView(id = R.id.description)
	private FontTextView description;
	// 个人信息
	@BindView(id = R.id.username)
	private FontTextView username;
	@BindView(id = R.id.gender)
	private FontTextView gender;
	@BindView(id = R.id.age)
	private FontTextView age;
	@BindView(id = R.id.maritalStatus)
	private FontTextView maritalStatus;
	@BindView(id = R.id.permanentAddressCity)
	private FontTextView permanentAddressCity;
	@BindView(id = R.id.highestEducation)
	private FontTextView highestEducation;
	// 个人资产及征信信息
	@BindView(id = R.id.propertyCertificateFile)
	private FontTextView propertyCertificateFile;
	@BindView(id = R.id.mortgageAmount)
	private FontTextView mortgageAmount;
	@BindView(id = R.id.vehicleIdentificationFile)
	private FontTextView vehicleIdentificationFile;
	@BindView(id = R.id.carloanAmount)
	private FontTextView carloanAmount;
	@BindView(id = R.id.annualIncome)
	private FontTextView annualIncome;
	// 工作信息
	@BindView(id = R.id.companyAddressCity)
	private FontTextView companyAddressCity;
	@BindView(id = R.id.profession)
	private FontTextView profession;
	@BindView(id = R.id.companySize)
	private FontTextView companySize;
	@BindView(id = R.id.companyIndustry)
	private FontTextView companyIndustry;
	@BindView(id = R.id.job)
	private FontTextView job;

	@BindView(id = R.id.collateralDescription)
	private FontTextView collateralDescription;
	@BindView(id = R.id.pay)
	private EditText pay;
	@BindView(id = R.id.tender, click = true)
	private FontTextView tender;

	@BindView(id = R.id.block)
	private LinearLayout block;

	// 担保方介绍
	@BindView(id = R.id.bondingDesc)
	private FontTextView bondingDesc;

	// 安全保障
	@BindView(id = R.id.guarantee)
	private FontTextView guarantee;

	// 审核状况
	@BindView(id = R.id.is_id_card)
	private FontTextView is_id_card;
	@BindView(id = R.id.is_house)
	private FontTextView is_house;
	@BindView(id = R.id.is_marry)
	private FontTextView is_marry;
	@BindView(id = R.id.is_household)
	private FontTextView is_household;
	@BindView(id = R.id.is_credit)
	private FontTextView is_credit;
	@BindView(id = R.id.is_organization)
	private FontTextView is_organization;
	@BindView(id = R.id.is_business_num)
	private FontTextView is_business_num;
	@BindView(id = R.id.is_legal_cardid)
	private FontTextView is_legal_cardid;
	@BindView(id = R.id.is_institution)
	private FontTextView is_institution;
	@BindView(id = R.id.is_platform)
	private FontTextView is_platform;

	// 借款人信息
	@BindView(id = R.id.personal)
	private LinearLayout personal;
	@BindView(id = R.id.enterprise)
	private LinearLayout enterprise;
	@BindView(id = R.id.o_real_name)
	private FontTextView o_real_name;
	@BindView(id = R.id.o_gender)
	private FontTextView o_gender;
	@BindView(id = R.id.o_age)
	private FontTextView o_age;
	@BindView(id = R.id.o_purpose)
	private FontTextView o_purpose;
	@BindView(id = R.id.company_name)
	private FontTextView company_name;
	@BindView(id = R.id.registered_capital)
	private FontTextView registered_capital;
	@BindView(id = R.id.legal_person)
	private FontTextView legal_person;
	@BindView(id = R.id.business_num)
	private FontTextView business_num;
	@BindView(id = R.id.industry)
	private FontTextView industry;
	@BindView(id = R.id.earning)
	private FontTextView earning;
	@BindView(id = R.id.simple_desc)
	private FontTextView simple_desc;
	@BindView(id = R.id.typep)
	private FontTextView typep;
	@BindView(id = R.id.typee)
	private FontTextView typee;

	@BindView(id = R.id.add_v)
	private LinearLayout add_v;
	@BindView(id = R.id.add)
	private FontTextView add;

	private KJHttp http;
	private HttpParams params;
	private DaiDetailProduct product;
	private int id;
	private int type;
	private String url;
	private int mul;
	private int max;
	private int min;
	private int available;

	private int page = 1;
	private boolean noMoreData;

	@BindView(id = R.id.listview1)
	private ListView listview1;
	private CommonAdapter<DetailPlan> adapter1;
	private List<DetailPlan> plan;

	@BindView(id = R.id.listview2)
	private KJListView listview2;
	private CommonAdapter<DetailRecord> adapter2;
	private List<DetailRecord> record;

	@Override
	public void setRootView() {
		setContentView(R.layout.activity_daidetail);
		UIHelper.setTitleView(this, "产品中心", "产品详情");
	}

	@Override
	public void initData() {
		super.initData();
		Intent intent = getIntent();
		id = intent.getIntExtra("id", 0);
		url = intent.getStringExtra("url");
		type = intent.getIntExtra("type", 0);
		http = new KJHttp();
		params = new HttpParams();
		getData(1);
	}

	private void getData(int page) {
		params.put("sid", AppVariables.sid);
		params.put("page", page);
		http.post(url, params, httpCallback);
	}

	@Override
	public void initWidget() {
		super.initWidget();
		adapter1 = new CommonAdapter<DetailPlan>(DaiDetailActivity.this,
				R.layout.item_detail_plan) {
			@Override
			public void canvas(ViewHolder holder, DetailPlan item) {
				System.out.println(item.toString());
				holder.setText(R.id.date, item.getDate(), false);
				holder.setText(R.id.principal, "￥" + item.getPrincipal(), false);
				holder.setText(R.id.interest, "￥" + item.getInterest(), false);
				holder.setText(R.id.amount, "￥" + item.getAmount(), false);
			}

			@Override
			public void click(int id, List<DetailPlan> list, int position,
					ViewHolder viewHolder) {
			}
		};
		adapter1.setList(plan);
		listview1.setAdapter(adapter1);
		adapter2 = new CommonAdapter<DetailRecord>(DaiDetailActivity.this,
				R.layout.item_detail_record) {
			@Override
			public void canvas(ViewHolder holder, DetailRecord item) {
				System.out.println(item.toString());
				holder.setText(R.id.realName, item.getRealName(), false);
				holder.setText(R.id.price, "￥" + item.getPrice(), false);
				holder.setText(R.id.createDate, item.getCreateDate(), false);
			}

			@Override
			public void click(int id, List<DetailRecord> list, int position,
					ViewHolder viewHolder) {
			}
		};
		adapter2.setList(record);
		listview2.setAdapter(adapter2);
		listview2.setOnRefreshListener(refreshListener);
	}

	private KJRefreshListener refreshListener = new KJRefreshListener() {
		@Override
		public void onRefresh() {
			getData(1);
		}

		@Override
		public void onLoadMore() {
			System.out.println("加载更多============");
			if (!noMoreData) {
				getData(page + 1);
			}
		}
	};

	private HttpCallBack httpCallback = new HttpCallBack(DaiDetailActivity.this) {
		public void success(JSONObject ret) {
			try {
				System.out.println("查询数据=====>ret" + ret);
				product = new DaiDetailProduct(ret);
				JSONObject p = ret.getJSONObject("product");
				mul = (p.getInt("baseLimitAmount")) / 100;
				max = Integer
						.parseInt(p.getString("remainingInvestmentAmount")) / 100;
				min = Integer.parseInt(p.getString("singlePurchaseLowerLimit")) / 100;
				plan = new DetailPlanList(ret.getJSONArray("productRepayPlan"),
						p.getString("firstPaybackDate")).getList();
				// System.out.println("查询数据=====>sss"+ret.getJSONArray("productRepayPlan"));
				initView();
				adapter1.setList(plan);
				JSONObject articles = ret.getJSONObject("productOrders");
				page = articles.getInt("currentPage");
				int maxPage = articles.getJSONObject("pager").getInt("maxPage");
				if (page >= maxPage) {
					listview2.hideFooter();
					noMoreData = true;
				} else {
					listview2.showFooter();
					noMoreData = false;
				}
				if (page < 2) {
					record = new DetailRecordList(
							articles.getJSONArray("items")).getList();
				} else {
					record = new DetailRecordList(record,
							articles.getJSONArray("items")).getList();
				}
				adapter2.setList(record);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("数据解析错误。");
				Toast.makeText(DaiDetailActivity.this, R.string.app_data_error,
						Toast.LENGTH_SHORT).show();
			}
		}

		public void onFinish() {
			super.onFinish();
			listview2.stopRefreshData();
		}
	};

	private void initView() {
		// 临时用，产品名称包含 壹财贷 的，下面的安全保障内容要换
		// String temp = product.getName();
		// if(temp.indexOf("壹财贷")==-1){
		// temp2.setText("2. 债权转让方按照合同约定承诺回购。");
		// temp3.setText("3. 芝金资产管理有限公司全额担保，如借款方无法偿还借款本息，由芝金资产全额偿还出借方的本金及收益");
		// }else{
		// temp2.setText("2. 借款方以房产、汽车等资产作为借款担保措施");
		// temp3.setText("3. 芝金资产管理有限公司承诺对转让方回购进行履约担保，如转让方违约，担保方将对投资人本息进行全额代偿。");
		// }
		// 产品详情
		name.setText(product.getName());
		String st = "";
		switch (product.getNewstatus()) {
		case 1:
			st = "还款中";
			pay.setText("产品已售罄");
			tender.setFocusable(false);
			pay.setFocusable(false);
			tender.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.btn_tender_gray));

			two.setVisibility(View.VISIBLE);
			break;
		case 2:
			st = "已售罄";
			pay.setText("产品已售罄");
			tender.setFocusable(false);
			pay.setFocusable(false);
			tender.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.btn_tender_gray));
			break;
		case 3:
			st = "预约";
			pay.setText("产品待售卖");
			tender.setFocusable(false);
			pay.setFocusable(false);
			tender.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.btn_tender_gray));
			break;
		case 4:
			st = "已结束";
			tender.setFocusable(false);
			pay.setFocusable(false);
			pay.setText("产品已售罄");
			tender.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.btn_tender_gray));

			two.setVisibility(View.VISIBLE);
			break;
		case 6:
			st = "已还款";
			tender.setFocusable(false);
			pay.setFocusable(false);
			pay.setText("产品已售罄");
			tender.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.btn_tender_gray));

			two.setVisibility(View.VISIBLE);
			break;
		case 5:
			st = "正在售卖";
			break;
		}
		status.setText(st);
		totalInvestment.setText(product.getTotalInvestment());
		totalInvestmentunit.setText(product.getTotalInvestmentunit());
		investmentPeriodDesc.setText(product.getInvestmentPeriodDesc());
		investmentPeriodDescunit.setText(product.getInvestmentPeriodDescunit());
		annualizedGain.setText(product.getAnnualizedGain());
		if (product.getActivityType() == 3) {
			Log.d("sss", product.getExtraRate() + "");
			annualizedGain.setText(FormatUtils.numFormat(Float
					.parseFloat(product.getAnnualizedGain())
					- (float) product.getExtraRate()));
			add_v.setVisibility(View.VISIBLE);
			add.setText(FormatUtils.numFormat((float) product.getExtraRate())
					+ "");
		}
		guaranteeModeName.setText(product.getGuaranteeModeName());
		repaymentMethodName.setText(product.getRepaymentMethodName());
		if (product.getStatus() == 1 || product.getStatus() == 2
				|| product.getStatus() == 6 || product.getStatus() == 4) {
			expirationDate.setText("计息开始时间");
			interestBeginDate.setText(product.getExpirationDate());
		} else {
			expirationDate.setText("剩余投资时间");
			interestBeginDate.setText(product.getExpirationDate());
		}
		remainingInvestmentAmount.setText(product
				.getRemainingInvestmentAmount());
		singlePurchaseLowerLimit.setText(product.getSinglePurchaseLowerLimit());
		if (product.getInvestmentProgress() == 100) {
			percentage.setText("已满标");
			percentagetxt.setText("");
			p.setVisibility(View.GONE);
		} else {
			percentage.setText("");
			percentagetxt.setText(product.getInvestmentProgress() + "");
			p.setVisibility(View.VISIBLE);
		}
		percentagepb.setProgress(product.getInvestmentProgress());
		// 贷款描述
		if ("".equals(product.getDescription())) {
			project_profile.setVisibility(View.GONE);
		} else {
			description.setText(product.getDescription());
		}
		// 个人信息
		username.setText(product.getUsername());
		gender.setText(product.getGender());
		age.setText(product.getAge());
		maritalStatus.setText(product.getMaritalStatus());
		permanentAddressCity.setText(product.getPermanentAddressCity());
		highestEducation.setText(product.getHighestEducation());
		// 个人资产及征信信息
		propertyCertificateFile.setText("房产："
				+ product.getPropertyCertificateFile());
		mortgageAmount.setText("房贷：" + product.getMortgageAmount());
		vehicleIdentificationFile.setText("车产："
				+ product.getVehicleIdentificationFile());
		carloanAmount.setText("车贷：" + product.getCarloanAmount());
		annualIncome.setText("年收入水平：" + product.getAnnualIncome());
		// 工作信息
		companyAddressCity.setText(product.getCompanyAddressCity());
		profession.setText(product.getProfession());
		companySize.setText(product.getCompanySize());
		companyIndustry.setText(product.getCompanyIndustry());
		job.setText(product.getJob());
		collateralDescription.setText(StringUtils.isEmpty(product
				.getCollateralDescription()) ? "无" : product
				.getCollateralDescription());
		pay.setHint("输入金额为" + mul + "的整数倍");
		// 担保方介绍
		bondingDesc.setText(product.getBondingDesc());
		guarantee.setText(product.getGuarantee());
		// 审核状况
		if (product.getAuditinfo() == 1) {
			if (product.getIs_id_card() == 1) {
				is_id_card.setVisibility(View.VISIBLE);
			}
			if (product.getIs_house() == 1) {
				is_house.setVisibility(View.VISIBLE);
			}
			if (product.getIs_marry() == 1) {
				is_marry.setVisibility(View.VISIBLE);
			}
			if (product.getIs_household() == 1) {
				is_household.setVisibility(View.VISIBLE);
			}
			if (product.getIs_credit() == 1) {
				is_credit.setVisibility(View.VISIBLE);
			}
			if (product.getIs_organization() == 1) {
				is_organization.setVisibility(View.VISIBLE);
			}
		} else if (product.getAuditinfo() == 2) {
			if (product.getIs_business_num() == 1) {
				is_business_num.setVisibility(View.VISIBLE);
			}
			if (product.getIs_legal_cardid() == 1) {
				is_legal_cardid.setVisibility(View.VISIBLE);
			}
			if (product.getIs_institution() == 1) {
				is_institution.setVisibility(View.VISIBLE);
			}
			if (product.getIs_platform() == 1) {
				is_platform.setVisibility(View.VISIBLE);
			}
		}
		if (type == 0) {// 默认有原始两个字，一财宝没有原始
			typee.setText("借款人信息");
			typep.setText("借款人信息");
		}
		// 审核状况
		if (product.getOrigininfo() == 1) {
			personal.setVisibility(View.VISIBLE);
			o_real_name.setText(product.getO_real_name());
			o_gender.setText(product.getO_gender());
			o_age.setText(product.getO_age());
			o_purpose.setText(product.getO_purpose());
		} else if (product.getOrigininfo() == 2) {
			enterprise.setVisibility(View.VISIBLE);
			company_name.setText(product.getCompany_name());
			registered_capital.setText(product.getRegistered_capital());
			legal_person.setText(product.getLegal_person());
			business_num.setText(product.getBusiness_num());
			industry.setText(product.getIndustry());
			earning.setText(product.getEarning());
			simple_desc.setText(product.getSimple_desc());
		}
	}

	;

	public void widgetClick(View v) {
		switch (v.getId()) {
		case R.id.one:
			one.setTextColor(getResources().getColor(R.color.white));
			one.setBackgroundResource(R.drawable.tab_left);
			two.setTextColor(getResources().getColor(R.color.app_blue));
			two.setBackgroundResource(R.drawable.red_two);
			three.setTextColor(getResources().getColor(R.color.app_blue));
			three.setBackgroundResource(R.drawable.red_three);
			mProduct.setVisibility(View.VISIBLE);
			mPlan.setVisibility(View.GONE);
			mRecord.setVisibility(View.GONE);
			break;
		case R.id.two:
			two.setTextColor(getResources().getColor(R.color.white));
			two.setBackgroundResource(R.drawable.tab_center);
			one.setTextColor(getResources().getColor(R.color.app_blue));
			one.setBackgroundResource(R.drawable.red_one);
			three.setTextColor(getResources().getColor(R.color.app_blue));
			three.setBackgroundResource(R.drawable.red_three);
			mProduct.setVisibility(View.GONE);
			mPlan.setVisibility(View.VISIBLE);
			mRecord.setVisibility(View.GONE);
			break;
		case R.id.three:
			three.setTextColor(getResources().getColor(R.color.white));
			three.setBackgroundResource(R.drawable.tab_right);
			two.setTextColor(getResources().getColor(R.color.app_blue));
			two.setBackgroundResource(R.drawable.red_two);
			one.setTextColor(getResources().getColor(R.color.app_blue));
			one.setBackgroundResource(R.drawable.red_one);
			mProduct.setVisibility(View.GONE);
			mPlan.setVisibility(View.GONE);
			mRecord.setVisibility(View.VISIBLE);
			break;
		case R.id.tender:
			if (product.getStatus() != 5)
				break;
			if (!AppVariables.isSignin) {
				startActivity(new Intent(DaiDetailActivity.this,
						SigninActivity.class));
				break;
			} else {
				Intent intent = new Intent(DaiDetailActivity.this,
						TenderActivity.class);
				intent.putExtra("activityType", product.getActivityType());
				intent.putExtra(
						"annualizedGain",
						FormatUtils.numFormat(Float.parseFloat(product
								.getAnnualizedGain())
								- (float) product.getExtraRate()) + "");
				intent.putExtra("add",
						FormatUtils.numFormat((float) product.getExtraRate())
								+ "");
				intent.putExtra("id", id);
				startActivity(intent);
				// getInfo();
			}
			break;
		}
	}

	private void getInfo() {
		params.put("sid", AppVariables.sid);
		http.post(AppConstants.GAIN, params, new HttpCallBack(
				DaiDetailActivity.this) {
			@Override
			public void onSuccess(String t) {
				try {
					JSONObject ret = new JSONObject(t);
					available = ret.getInt("available");
					final LoudingDialog ld = new LoudingDialog(
							DaiDetailActivity.this);
					String p = pay.getText().toString();
					if (StringUtils.isEmpty(p)) {
						ld.showConfirmHint("请输入金额");
						return;
					}
					int i = Integer.parseInt(p);
					if (i > available) {
						ld.showConfirmHint("可用余额不足,请先充值。");
						return;
					} else if (i > max) {
						ld.showConfirmHint("可投金额不足");
						return;
					} else if (i < min) {
						ld.showConfirmHint("请大于最小投资金额");
						return;
					} else if ((i % mul) > 0) {
						ld.showConfirmHint("输入金额为" + mul + "的整数倍");
						return;
					}
					ld.dismiss();
					Intent intent = new Intent(DaiDetailActivity.this,
							TenderActivity.class);
					intent.putExtra("id", id);
					intent.putExtra("price", i);
					startActivity(intent);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}

}
