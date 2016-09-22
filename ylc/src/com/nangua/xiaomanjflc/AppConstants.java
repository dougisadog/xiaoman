package com.nangua.xiaomanjflc;

/**
 * @description:
 * @author: Liu wei
 * @mail: i@liuwei.co
 * @date: 2014-3-12
 */
public class AppConstants {
	
	public final static String CONF_APP_UNIQUEID = "APP_UNIQUEID";// App唯一标识

	public static String PATH_LOG_PATH = "/yilicai/Log/";// 日志默认保存目录

	public static String PATH_UPDATE_APK = "/yilicai/Update/";// 软件更新默认保存目录

	public static final int TIME = 120* 1000;// 手势密码出现持续时间  60秒

	public static final int PRODUCT_STATUS_REPAY = 1;// 还款中

	public static final int PRODUCT_STATUS_SOLD_OUT = 2;// 已售罄

	public static final int PRODUCT_STATUS_APPOINTMENT = 3;// 预约

	public static final int PRODUCT_STATUS_END = 4;// 已结束

	public static final int PRODUCT_STATUS_ON_SALE = 5;// 正在售卖
	
	public static final float BANNER_SCALE = 2.0f; // 长宽比

	/**
	 * 返回成功
	 */
	public static final int SUCCESS = 1;

	/**
	 * 返回失败
	 */
	public static final int FAILED = 2;
	
	public final static String INVITE_URL = "http://nangua.webok.net:9077"; //邀请地址测试
//	public final static String INVITE_URL = "http://passport.xiaomanjf.com"; //邀请地址线上
	
	// 内侧地址
//	public final static String HOST = "http://59.46.5.242:8089/mapp"; //mapp项目端口
//	public final static String HOST_IMAGE = "http://59.46.5.242:8089/docroot/upload/images";
//	public final static String SPECIALHOST = "http://59.46.5.242:8089"; //app项目 端口
	
	
	// 线上地址
//	public final static String HOST = "http://www.xiaomanjf.com/mapp"; //mapp项目端口
//	public final static String HOST_IMAGE = "http://www.xiaomanjf.com/docroot/upload/images";
//	public final static String SPECIALHOST = "http://www.xiaomanjf.com"; //app项目 端口
//	
	// 真实测试地址
//	public final static String HOST = "http://nangua.webok.net:9070/mapp"; //mapp项目端口
//	public final static String HOST_IMAGE = "http://nangua.webok.net:9070/docroot/upload/images";
//	public final static String SPECIALHOST = "http://nangua.webok.net:9070"; //app项目 端口

	// 内侧地址
	public final static String HOST = "http://nangua.webok.net:9080/mapp"; //mapp项目端口
	public final static String HOST_IMAGE = "http://nangua.webok.net:9080/docroot/upload/images";
	public final static String SPECIALHOST = "http://nangua.webok.net:9080"; //app项目 端口

	//本地外网
//	public final static String HOST = "http://nangua.webok.net:9973/p2p-mapp";
//	public final static String HOST_IMAGE = "http://nangua.webok.net:9973/p2p-mapp/docroot/upload/images";
//	public final static String SPECIALHOST = "http://nangua.webok.net:9973";

	//刘庆安
//	public final static String HOST = "http://192.168.199.43:8080/mapp";
//	public final static String HOST_IMAGE = "http://192.168.199.43:8080/mapp/docroot/upload/images";
//	public final static String SPECIALHOST = "http://192.168.199.43:8080";

	public final static String HTTP = "http://";

	/**
	 * 登录
	 */
	public static final String SIGNIN = HOST + "/login/noOauth";

	/**
	 * 注册账号
	 */
	public static final String SIGNUP = HOST + "/register/noOauth";
	

	/**
	 * 发送手机验证码
	 */
	public static final String GETCODE = HOST + "/reg/sendcode";

	/**
	 * 获取验证码
	 */
	public static final String CAPTCHA = HOST + "/etc/captcha";

	/**
	 * 验证是否登录
	 */
	public static final String ISSIGNIN = HOST + "/islogin";

	/**
	 * 找回登陆密码时发送验证码
	 */
	public static final String SENDCODE = HOST + "/findback/sendcode";

	/**
	 * 找回登陆密码时验证手机验证码
	 */
	public static final String VERIFY_CODE = HOST + "/findback/find";

	/**
	 * 找回登陆密码
	 */
	public static final String GET_LOSE = HOST + "/findback/reset";

	/**
	 * 产品列表
	 */
	public static final String PRODUCTS = HOST + "/product/all";

	/**
	 * 产品详情
	 */
	public static final String DETAIL_PRODUCT = HOST
			+ "/product/personal-loan/detail/";

	/**
	 * 直投宝列表
	 */
	public static final String DIRECT = HOST + "/product/direct";

	/**
	 * 购买产品
	 */
	public static final String BUY = HOST + "/product/";

	/**
	 * 公告列表
	 */
	public static final String ANNOUNCE = HOST + "/article/announce";

	/**
	 * 获取账户页信息
	 */
	public static final String GAIN = HOST + "/my";

	/**
	 * 获取邀请信息
	 */
	public static final String INVITE = HOST + "/my/invitation";

	/**
	 * 用户基本信息
	 */
	public static final String BASICINFO = HOST + "/my/basic";

	/**
	 * 添加用户名
	 */
	public static final String ADDNAME = HOST + "/my/name";

	/**
	 * 绑定邮箱
	 */
	public static final String BINDEMAIL = HOST + "/my/email/bind";

	/**
	 * 修改密码
	 */
	public static final String CHANGEPWD = HOST + "/my/password";

	/**
	 * 投资记录回款中
	 */
	public static final String INVEST_ORDER = HOST + "/my/order";

	/**
	 * 投资记录待确认
	 */
	public static final String INVEST_PENDING = HOST + "/my/order/pending";

	/**
	 * 投资记录已结清
	 */
	public static final String INVEST_CLOSED = HOST + "/my/order/closed";

	/**
	 * 投资记录流标
	 */
	public static final String INVEST_ABORTED = HOST + "/my/order/aborted";

	/**
	 * 交易记录
	 */
	public static final String TRANSACTION = HOST + "/my/bill";

	/**
	 * 现金券
	 */
	public static final String RED = HOST + "/my/cash";

	/**
	 * 新现金券
	 */
	public static final String NEWCASH = HOST + "/product/enablecash";
	
	/**
	 * ips web登录
	 */
	public static final String IPS_LOGIN = HOST + "/account/huanxun";
	/**
	 * 充值
	 */
	public static final String CHARGE = HOST + "/account/recharge";

	/**
	 * 提现
	 */
	public static final String CASH = HOST + "/account/withdraw";

	/**
	 * 提现手续费
	 */
	public static final String FEE = HOST + "/my/withdraw-bill";

	/**
	 * 实名认证
	 */
	public static final String IDCARD = HOST + "/account/register";

	/**
	 * 银行卡绑定
	 */
	public static final String BANKCARD = HOST + "/account/bindcard";

	/**
	 * 检测更新
	 */
	public static final String UPDATE = HOST + "/api/version/get";

	/**
	 * 服务协议 直接
	 */
	public static final String SERVICE_PROTOCOL = SPECIALHOST
			+ "/contract/show/new";
	/**
	 * 服务协议 债权
	 */
	public static final String SERVICE_PROTOCOL2 = SPECIALHOST
			+ "/tender/transfercontract.html";

	/**
	 * 借款协议
	 */
	public static final String INVEST_PROTOCOL = HOST + "/protocol/contract/";

	/**
	 * 获取首页轮播图数据
	 */
	public static final String GET_SLIDE_IMAGE = HOST + "/appadv";

	/**
	 * 关于我们
	 */
	public static final String ABOUT_US = SPECIALHOST + "/page/app/about/";
	

	/**
	 * 帮助中心
	 */
	public static final String FAQ = SPECIALHOST + "/page/app/faq/";

	/**
	 * 债权转让 可转让
	 */
	public static final String TRANSFER_CAN = HOST + "/claims/transfercan";

	/**
	 * 债权转让 转让中
	 */
	public static final String TRANSFER_ING = HOST + "/claims/transfering";

	/**
	 * 债权转让 已转让
	 */
	public static final String TRANSFER_ALREADY = HOST
			+ "/claims/transferalready";

	/**
	 * 债权转让 发布转让
	 */
	public static final String TRANSFER_PUBLISH = HOST
			+ "/claims/transferPublish";

	/**
	 * 账户中心 修改绑定手机号
	 */
	public static final String update_phone = HOST + "/my/phone";

	/**
	 * 消息中心
	 */
	public static final String MESSAGE_CENTER = HOST + "/my/center";

	/**
	 * 消息中心 已读
	 */
	public static final String MESSAGE_CENTER_ALREAD = HOST
			+ "/my/center/alread";

	/**
	 * 用户是否有借款
	 */
	public static final String ACCOUNT_ISLOAN = HOST + "/account/isloan";

	/**
	 * 我的积分
	 */
	public static final String MY_INTEGRAL = HOST + "/my/integral";
	/**
	 * 我的积分详情
	 */
	public static final String MY_INTEGRAL_DETAIL = HOST
			+ "/my/integral/detail";
	/**
	 * 积分商城
	 */
	public static final String MY_INTEGRAL_MALL = HOST + "/my/integral/mall";
	/**
	 * 积分商城 兑换
	 */
	public static final String MY_INTEGRAL_MALL_EXCHANGE = HOST
			+ "/my/integral/mall/exchange";

	/**
	 * 取得银行列表
	 */
	public static final String GET_BANKCODE = HOST + "/account/bankCode";

}
