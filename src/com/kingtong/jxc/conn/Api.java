package com.kingtong.jxc.conn;

public class Api {
	/** 主机 */
	public static final String API_HOST = "http://jxc.xapsp.com";
	
	/** 登录接口 */
	public static final String API_LOGIN = API_HOST + "/api/login.php";
	
	/** 产品信息 */
	public static final String API_CHANPIN = API_HOST + "/api/chanpin.php";
	
	/** 竞品信息 */
	public static final String API_JINGPIN = API_HOST + "/api/jingpin.php";
	
	/** 公告 */
	public static final String API_GONGGAO = API_HOST + "/api/gonggao.php";
	
	/** 商户活动取指定竞品活动 */
	public static final String API_HUODONG = API_HOST + "/api/huodong.php";
	
	/** 商户信息 */
	public static final String API_SHANGHU = API_HOST + "/api/shanghu.php";
	
	/** 用户信息 */
	public static final String API_GEREN   = API_HOST + "/api/geren.php";
	
	/** 添加订单 */
	public static final String API_ADD_DINGDAN = API_HOST + "/api/adddingdan.php";
	
	/** 添加活动 */
	public static final String API_ADD_HUODONG = API_HOST + "/api/addhuodong.php";
	
	/** 修改送货单 */
	public static final String API_EDITSONGHUO  = API_HOST + "/api/editsonghuo.php";
	
	/**
	 * 上传图片
	 */
	public static final String API_UPLOAD_IMAGE  = API_HOST + "/api/upload.php";
	
	/** 获取所有产品 */
	public static final String API_CHANPINALL = API_HOST + "/api/chanpinall.php";
	
	/** 获取所有竞品 */
	public static final String API_JINGPINALL = API_HOST + "/api/jingpinall.php";
	
	/** 删除活动 */
	public static final String API_HUODONGDEL = API_HOST + "/api/huodongdel.php";
	
	/**获取订单产品列表 */
	public static final String API_DINGDANS = API_HOST + "/api/dinghuo_pro.php";
	
	/**获取某个产品订单列表 */
	public static final String API_DINGHUODANS = API_HOST + "/api/dinghuo.php";
	
	/**获取订单详情 */
	public static final String API_DINGDANINFO = API_HOST + "/api/dinghuo_info.php";
	
	/**获取送货单列表 */
	public static final String API_SONGHUODANS = API_HOST + "/api/songhuo.php";
	
	/**获取送货单详情 */
	public static final String API_SONGHUODANDETAIL = API_HOST + "/api/songhuo_info.php";
	
	/**修改送货单 */
	public static final String API_EDITSONGHUODAN = API_HOST + "/api/editsonghuo.php";
	
}
