package com.nangua.xiaomanjflc.ui;

import java.util.List;

import org.json.JSONObject;

import android.content.Intent;
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
import com.louding.frame.widget.KJListView;
import com.louding.frame.widget.KJRefreshListener;
import com.nangua.xiaomanjflc.AppConstants;
import com.nangua.xiaomanjflc.AppVariables;
import com.nangua.xiaomanjflc.adapter.CommonAdapter;
import com.nangua.xiaomanjflc.adapter.ViewHolder;
import com.nangua.xiaomanjflc.bean.DetailPlan;
import com.nangua.xiaomanjflc.bean.DetailPlanList;
import com.nangua.xiaomanjflc.bean.DetailProduct;
import com.nangua.xiaomanjflc.bean.DetailRecord;
import com.nangua.xiaomanjflc.bean.DetailRecordList;
import com.nangua.xiaomanjflc.support.UIHelper;
import com.nangua.xiaomanjflc.utils.ToastUtil;
import com.nangua.xiaomanjflc.widget.CircleProgressBar;
import com.nangua.xiaomanjflc.widget.FontTextView;
import com.nangua.xiaomanjflc.R;

public class DetailActivity extends KJActivity {
	
	public static final int DETAIL_CODE = 10001;

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
	@BindView(id = R.id.ll_project_profile)
	private LinearLayout ll_project_profile;

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
	@BindView(id = R.id.add_v)
	private LinearLayout add_v;
	@BindView(id = R.id.add)
	private FontTextView add;
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
	@BindView(id = R.id.percentagepb)
	private CircleProgressBar percentagepb;
	// 贷款描述
	@BindView(id = R.id.description)
	private FontTextView description;

	// 个人信息
	@BindView(id = R.id.ll_person)
	private LinearLayout ll_person;
	@BindView(id = R.id.tv_person_info)
	private FontTextView tv_person_info;
	@BindView(id = R.id.tv_username)
	private FontTextView tv_username;
	@BindView(id = R.id.tv_sex)
	private FontTextView tv_sex;
	@BindView(id = R.id.tv_age)
	private FontTextView tv_age;
	@BindView(id = R.id.tv_purpose)
	private FontTextView tv_purpose;

	// 企业信息
	@BindView(id = R.id.ll_business)
	private LinearLayout ll_business;
	@BindView(id = R.id.tv_business_info)
	private FontTextView tv_business_info;
	@BindView(id = R.id.tv_company_name)
	private FontTextView tv_company_name;
	@BindView(id = R.id.tv_legalPerson)
	private FontTextView tv_legalPerson;
	@BindView(id = R.id.tv_industry)
	private FontTextView tv_industry;
	@BindView(id = R.id.tv_registered_capital)
	private FontTextView tv_registered_capital;
	@BindView(id = R.id.tv_purpose1)
	private FontTextView tv_purpose1;
	@BindView(id = R.id.tv_company_introduce)
	private FontTextView tv_company_introduce;

	// 个人审核状况
	@BindView(id = R.id.ll_personal)
	private LinearLayout ll_personal;
	@BindView(id = R.id.tv_idCardCheckFlg)
	private FontTextView tv_idCardCheckFlg;
	@BindView(id = R.id.tv_estateCheckFlg)
	private FontTextView tv_estateCheckFlg;
	@BindView(id = R.id.tv_marriageCheckFlg)
	private FontTextView tv_marriageCheckFlg;
	@BindView(id = R.id.tv_householdCheckFlg)
	private FontTextView tv_householdCheckFlg;
	@BindView(id = R.id.tv_credibilityCheckFlg)
	private FontTextView tv_credibilityCheckFlg;
	@BindView(id = R.id.tv_guaranteeCheckFlg)
	private FontTextView tv_guaranteeCheckFlg;

	// 企业审核状况
	@BindView(id = R.id.ll_company)
	private LinearLayout ll_company;
	@BindView(id = R.id.tv_businessCheckFlg)
	private FontTextView tv_businessCheckFlg;
	@BindView(id = R.id.tv_legalCardCheckFlg)
	private FontTextView tv_legalCardCheckFlg;
	@BindView(id = R.id.tv_bondingCheckFlg)
	private FontTextView tv_bondingCheckFlg;
	@BindView(id = R.id.tv_platformCheckFlg)
	private FontTextView tv_platformCheckFlg;
	@BindView(id = R.id.tv_addressCheckFlg)
	private FontTextView tv_addressCheckFlg;

	@BindView(id = R.id.pay)
	private EditText pay;
	@BindView(id = R.id.tender, click = true)
	private FontTextView tender;
	@BindView(id = R.id.tv_description)
	private FontTextView tv_description;
	@BindView(id = R.id.ll_description)
	private LinearLayout ll_description;

	@BindView(id = R.id.block)
	private LinearLayout block;

	@BindView(id = R.id.tv_description_riskDescri)
	private FontTextView tv_description_riskDescri;
	@BindView(id = R.id.ll_description_riskDescri)
	private LinearLayout ll_description_riskDescri;

	private KJHttp http;
	private HttpParams params;
	private DetailProduct product;
	private int id;
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
		setContentView(R.layout.activity_detail);
		UIHelper.setTitleView(this, "产品中心", "产品详情");
	}

	@Override
	public void initData() {
		super.initData();
		Intent intent = getIntent();
		id = intent.getIntExtra("id", 0);
		http = new KJHttp();
		params = new HttpParams();
		getData(1);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		getData(1);
		super.onActivityResult(requestCode, resultCode, data);
	}

	private void getData(int page) {
		params.put("sid", AppVariables.sid);
		params.put("id", id);
		params.put("page", page);
		http.post(AppConstants.DETAIL_PRODUCT + id, params, httpCallback);
	}

	@Override
	public void initWidget() {
		super.initWidget();
		adapter1 = new CommonAdapter<DetailPlan>(DetailActivity.this,
				R.layout.item_detail_plan) {
			@Override
			public void canvas(ViewHolder holder, DetailPlan item) {
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
		adapter2 = new CommonAdapter<DetailRecord>(DetailActivity.this,
				R.layout.item_detail_record) {
			@Override
			public void canvas(ViewHolder holder, DetailRecord item) {
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
		listview2.setEmptyView(findViewById(R.id.empty));
	}

	private KJRefreshListener refreshListener = new KJRefreshListener() {
		@Override
		public void onRefresh() {
			getData(1);
		}

		@Override
		public void onLoadMore() {
			if (!noMoreData) {
				getData(page + 1);
			}
		}
	};

	private HttpCallBack httpCallback = new HttpCallBack(DetailActivity.this) {
		public void success(org.json.JSONObject ret) {
			try {

				product = new DetailProduct(ret);

				JSONObject product = ret.getJSONObject("product");
				mul = (product.getInt("baseLimitAmount")) / 100;
				max = Integer.parseInt(product
						.getString("remainingInvestmentAmount")) / 100;
				min = Integer.parseInt(product
						.getString("singlePurchaseLowerLimit")) / 100;
				plan = new DetailPlanList(ret.getJSONArray("productRepayPlan"),
						product.getString("firstPaybackDate")).getList();
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

				record = new DetailRecordList(articles.getJSONArray("items"))
						.getList();
				adapter2.setList(record);
			} catch (Exception e) {
				e.printStackTrace();
				Toast.makeText(DetailActivity.this, R.string.app_data_error,
						Toast.LENGTH_SHORT).show();
			}
		}

		public void onFinish() {
			super.onFinish();
			listview2.stopRefreshData();
		}
	};
	
	/**
	 * 刷新tender（下方投标按钮）的显示
	 */
	private void refreshTenderView() {
		tender.setFocusable(false);
		pay.setFocusable(false);
		tender.setBackgroundResource(R.drawable.btn_tender_gray);
	}

	private void initView() {
		// 产品详情
		name.setText(product.getName());
		String st = "";
		switch (product.getNewstatus()) {
		case 1:
			st = "还款中";
			pay.setText("产品已售罄");
			refreshTenderView();
			two.setVisibility(View.VISIBLE);
			break;
		case 2:
			st = "已售罄";
			pay.setText("产品已售罄");
			refreshTenderView();
			break;
		case 3:
			st = "预约";
			pay.setText("产品待售卖");
			refreshTenderView();
			break;
		case 4:
			st = "已结束";
			pay.setText("产品已售罄");
			refreshTenderView();

			two.setVisibility(View.VISIBLE);
			break;
		case 5:
			st = "正在售卖";
			break;
		case 6:
			st = "已还款";
			pay.setText("产品已售罄");
			refreshTenderView();

			two.setVisibility(View.VISIBLE);
			break;
		case 7:
			st = "审核中";
			pay.setText("审核中");
			tender.setFocusable(false);
			pay.setFocusable(false);
			tender.setEnabled(false);
			tender.setBackgroundResource(R.drawable.btn_tender_gray);
			break;
		}
		status.setText(st);
		totalInvestment.setText(product.getTotalInvestment());
		totalInvestmentunit.setText(product.getTotalInvestmentunit());
		investmentPeriodDesc.setText(product.getInvestmentPeriodDesc());
		investmentPeriodDescunit.setText(product.getInvestmentPeriodDescunit());
		annualizedGain.setText(product.getAnnualizedGain());
		if (!"".equals(product.getTenderAward())) {
			add_v.setVisibility(View.VISIBLE);
			add.setText(product.getTenderAward());
		}
		else {
			add_v.setVisibility(View.GONE);
		}
		annualizedGain.setText(product.getAnnualizedGain());
		guaranteeModeName.setText(product.getGuaranteeModeName());
		if(product.getGuaranteeModeName().isEmpty()){
			guaranteeModeName.setVisibility(View.GONE);
		}
		repaymentMethodName.setText(product.getRepaymentMethodName());

		if (product.getStatus() == 1 || product.getStatus() == 4
				|| product.getStatus() == 6) {
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
		} else {
			percentage.setText("");
			percentagetxt.setText(product.getInvestmentProgress() + "%");
		}
		percentagepb.setProgress(product.getInvestmentProgress());

		// 担保方介绍
		if ("".equals(product.getDescription())) {
			ll_description.setVisibility(View.GONE);
		} else {
			ll_description.setVisibility(View.VISIBLE);
			tv_description.setText(product.getDescription());
		}

		// 项目简介
		if ("".equals(product.getDetailDescription())) {
			ll_project_profile.setVisibility(View.GONE);
		} else {
			ll_project_profile.setVisibility(View.VISIBLE);
			description.setText(product.getDetailDescription());
		}

		// 安全保障
		if ("".equals(product.getDescriptionRiskDescri())) {
			ll_description_riskDescri.setVisibility(View.GONE);
		} else {
			ll_description_riskDescri.setVisibility(View.VISIBLE);
			tv_description_riskDescri.setText(product
					.getDescriptionRiskDescri());
		}

		// 借款方区分 0:无 1:借款人信息 2:原始借款人信息 3:原始借款企业借款信息
		if ("0".equals(product.getPersonTypeKbn())) {
			ll_person.setVisibility(View.GONE);
			ll_business.setVisibility(View.GONE);

		} else if ("1".equals(product.getPersonTypeKbn())
				|| "2".equals(product.getPersonTypeKbn())) {
			ll_person.setVisibility(View.VISIBLE);
			ll_business.setVisibility(View.GONE);

			tv_person_info.setText(product.getPersonTypeTitle());
			tv_username.setText(product.getUsername());
			tv_sex.setText(product.getGender());
			tv_age.setText(product.getAge());
			tv_purpose.setText(product.getPurpose());
			pay.setHint("输入金额为" + mul + "的整数倍");
		} else if ("3".equals(product.getPersonTypeKbn())) {
			ll_person.setVisibility(View.GONE);
			ll_business.setVisibility(View.VISIBLE);

			tv_business_info.setText("原始借款企业信息");
			tv_company_name.setText(product.getgCompanyName());
			tv_legalPerson.setText(product.getgLegalPerson());
			tv_registered_capital.setText(product.getgRegisteredCapital());
			tv_industry.setText(product.getgIndustry());
			tv_purpose1.setText(product.getgPurpose1());
			tv_company_introduce.setText(product.getgCompanyIntroduce());
		}

		// 审核信息区分 0:无 1:个人或者原始借款人审核信息 2:原始企业审核信息
		if ("0".equals(product.getCheckInfoFlg())) {
			ll_personal.setVisibility(View.GONE);
			ll_company.setVisibility(View.GONE);
		} else if ("1".equals(product.getCheckInfoFlg())) {
			// ll_personal.setVisibility(View.VISIBLE);
			// ll_company.setVisibility(View.GONE);

			if (product.isIdCardCheckFlg() || product.isEstateCheckFlg()
					|| product.isMarriageCheckFlg()
					|| product.isHouseholdCheckFlg()
					|| product.isCredibilityCheckFlg()
					|| product.isGuaranteeCheckFlg()) {
				ll_personal.setVisibility(View.VISIBLE);
				ll_company.setVisibility(View.GONE);

				if (product.isIdCardCheckFlg()) {
					tv_idCardCheckFlg.setVisibility(View.VISIBLE);
				} else {
					tv_idCardCheckFlg.setVisibility(View.GONE);
				}
				if (product.isEstateCheckFlg()) {
					tv_estateCheckFlg.setVisibility(View.VISIBLE);
				} else {
					tv_estateCheckFlg.setVisibility(View.GONE);
				}
				if (product.isMarriageCheckFlg()) {
					tv_marriageCheckFlg.setVisibility(View.VISIBLE);
				} else {
					tv_marriageCheckFlg.setVisibility(View.GONE);
				}
				if (product.isHouseholdCheckFlg()) {
					tv_householdCheckFlg.setVisibility(View.VISIBLE);
				} else {
					tv_householdCheckFlg.setVisibility(View.GONE);
				}
				if (product.isCredibilityCheckFlg()) {
					tv_credibilityCheckFlg.setVisibility(View.VISIBLE);
				} else {
					tv_credibilityCheckFlg.setVisibility(View.GONE);
				}
				if (product.isGuaranteeCheckFlg()) {
					tv_guaranteeCheckFlg.setVisibility(View.VISIBLE);
				} else {
					tv_guaranteeCheckFlg.setVisibility(View.GONE);
				}
			} else {
				ll_personal.setVisibility(View.GONE);
				ll_company.setVisibility(View.GONE);
			}
		} else if ("2".equals(product.getCheckInfoFlg())) {
			// ll_personal.setVisibility(View.GONE);
			// ll_company.setVisibility(View.VISIBLE);

			if (product.isBusinessCheckFlg() || product.isLegalCardCheckFlg()
					|| product.isBondingCheckFlg()
					|| product.isPlatformCheckFlg()
					|| product.isPlatformCheckFlg()
					|| product.isAddressCheckFlg()) {
				ll_personal.setVisibility(View.GONE);
				ll_company.setVisibility(View.VISIBLE);

				if (product.isBusinessCheckFlg()) {
					tv_businessCheckFlg.setVisibility(View.VISIBLE);
				} else {
					tv_businessCheckFlg.setVisibility(View.GONE);
				}
				if (product.isLegalCardCheckFlg()) {
					tv_legalCardCheckFlg.setVisibility(View.VISIBLE);
				} else {
					tv_legalCardCheckFlg.setVisibility(View.GONE);
				}
				if (product.isBondingCheckFlg()) {
					tv_bondingCheckFlg.setVisibility(View.VISIBLE);
				} else {
					tv_bondingCheckFlg.setVisibility(View.GONE);
				}
				if (product.isPlatformCheckFlg()) {
					tv_platformCheckFlg.setVisibility(View.VISIBLE);
				} else {
					tv_platformCheckFlg.setVisibility(View.GONE);
				}
				if (product.isAddressCheckFlg()) {
					tv_addressCheckFlg.setVisibility(View.VISIBLE);
				} else {
					tv_addressCheckFlg.setVisibility(View.GONE);
				}

			} else {
				ll_personal.setVisibility(View.GONE);
				ll_company.setVisibility(View.GONE);
			}
		}
	}

	public void widgetClick(android.view.View v) {
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
			if (!AppVariables.isSignin) {
				ToastUtil.showToast(DetailActivity.this, "请登录或注册后查看",
						Toast.LENGTH_SHORT);
			} else {
				two.setTextColor(getResources().getColor(R.color.white));
				two.setBackgroundResource(R.drawable.tab_center);
				one.setTextColor(getResources().getColor(R.color.app_blue));
				one.setBackgroundResource(R.drawable.red_one);
				three.setTextColor(getResources().getColor(R.color.app_blue));
				three.setBackgroundResource(R.drawable.red_three);
				mProduct.setVisibility(View.GONE);
				mPlan.setVisibility(View.VISIBLE);
				mRecord.setVisibility(View.GONE);
			}
			break;
		case R.id.three:
			if (!AppVariables.isSignin) {
				ToastUtil.showToast(DetailActivity.this, "请登录或注册后查看",
						Toast.LENGTH_SHORT);
			} else {
				three.setTextColor(getResources().getColor(R.color.white));
				three.setBackgroundResource(R.drawable.tab_right);
				two.setTextColor(getResources().getColor(R.color.app_blue));
				two.setBackgroundResource(R.drawable.red_two);
				one.setTextColor(getResources().getColor(R.color.app_blue));
				one.setBackgroundResource(R.drawable.red_one);
				mProduct.setVisibility(View.GONE);
				mPlan.setVisibility(View.GONE);
				mRecord.setVisibility(View.VISIBLE);
			}
			break;
		case R.id.tender:
			if (product.getStatus() != 5)
				break;
			if (!AppVariables.isSignin) {
				startActivity(new Intent(DetailActivity.this,
						SigninActivity.class));
				break;
			} else {
				Intent intent = new Intent(DetailActivity.this,
						TenderActivity.class);
				intent.putExtra("tenderAward", product.getTenderAward());// 加息
				intent.putExtra("id", id);
				startActivityForResult(intent, DETAIL_CODE);
			}
			break;
		}
	}

}
