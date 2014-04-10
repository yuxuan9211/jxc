package com.kingtong.jxc.conn;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import com.kingtong.jxc.common.JsonUtil;
import com.kingtong.jxc.domain.Chanpin;
import com.kingtong.jxc.domain.DingDan;
import com.kingtong.jxc.domain.DingDanDetail;
import com.kingtong.jxc.domain.DingHuoDan;
import com.kingtong.jxc.domain.GeRen;
import com.kingtong.jxc.domain.GongGao;
import com.kingtong.jxc.domain.HuoDong;
import com.kingtong.jxc.domain.JingPin;
import com.kingtong.jxc.domain.JingPinHuoDong;
import com.kingtong.jxc.domain.Shanghu;
import com.kingtong.jxc.domain.SongHuoConfirm;
import com.kingtong.jxc.domain.SongHuoDan;
import com.kingtong.jxc.domain.SongHuoDetail;
import com.kingtong.jxc.domain.User;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

public class DataManager {
	
	private Context context;
	
	public DataManager(Context context) {
		this.context = context;
	}
	
	private void log(String message) {
		Log.d(this.getClass().getSimpleName(), message);
	}
	
	/**
	 * 登录
	 * @param username
	 * @param passwd
	 * @return
	 */
	public boolean login(String username, String passwd, boolean save)
	{
		String url = Api.API_LOGIN;
		// 设置参数
		List<NameValuePair> params = new ArrayList<NameValuePair>(2);
		params.add(new BasicNameValuePair("u", username));
		params.add(new BasicNameValuePair("p", passwd));
		// 发送请求
		String json = MyHttpClient.post(url, params);
		log("login response string:" + json);
		// 处理结果
		if(json !=null && !json.startsWith("#")) {//< 返回正确值
			//保存用户
			User user = JsonUtil.paserUser(json);
			if(null == user || user.getUid().equals("0")) {
				return false;
			}
			SharedPreferences _data = context.getSharedPreferences("_data", Context.MODE_WORLD_WRITEABLE);
			Editor editor = _data.edit();
			editor.putString("user_id", user.getUid());
			editor.putString("user_name", user.getUsername());
			if(save) {
				editor.putString("user_passwd", passwd);
			} else {
				editor.putString("user_passwd", null);
			}
			editor.commit();
			return true;
		}
		return false;
	}
	
	/**
	 * 获取产品信息列表
	 * @param start
	 * @param count
	 * @return
	 */
	public List<Chanpin> getChanpins(int start, int count) {
		String url = Api.API_CHANPIN;
		// 设置参数
		List<NameValuePair> params = new ArrayList<NameValuePair>(2);
		params.add(new BasicNameValuePair("s", String.valueOf(start)));
		params.add(new BasicNameValuePair("n", String.valueOf(count)));
		// 发送请求
		String json = MyHttpClient.post(url, params);
		log("getChanpin response string:" + json);
		// 处理结果
		if(json !=null && !json.startsWith("#")) {//< 返回正确值
			List<Chanpin> list = JsonUtil.paserChanpinArray(json);
//			// 将结果中的产品名称存放到sp
//			if(null != list && list.size() > 0) {
//				SharedPreferences _chanpin = context.getSharedPreferences("_chanpin", Context.MODE_WORLD_WRITEABLE);
//				Editor editor = _chanpin.edit();
//				for (int i=0,len=list.size(); i<len; i++) {
//					int key = start + i;
//					editor.putString("" + key, list.get(i).getTitle());
//				}
//				editor.commit();
//			}
			return list;
		}
		return null;
	}
	
	/**
	 * 获取竞品信息列表
	 * @param start
	 * @param count
	 * @return
	 */
	public List<JingPin> getJingpins(int start, int count) {
		String url = Api.API_JINGPIN;
		// 设置参数
		List<NameValuePair> params = new ArrayList<NameValuePair>(2);
		params.add(new BasicNameValuePair("s", String.valueOf(start)));
		params.add(new BasicNameValuePair("n", String.valueOf(count)));
		// 发送请求
		String json = MyHttpClient.post(url, params);
		log("getJingPin response string:" + json);
		// 处理结果
		if(json !=null && !json.startsWith("#")) {//< 返回正确值
			return JsonUtil.paserJingpinArray(json);
		}
		return null;
	}
	
	/**
	 * 获取公告列表
	 * @param start
	 * @param count
	 * @return
	 */
	public List<GongGao> getGonggaos(int start, int count) {
		String url = Api.API_GONGGAO;
		// 设置参数
		List<NameValuePair> params = new ArrayList<NameValuePair>(2);
		params.add(new BasicNameValuePair("s", String.valueOf(start)));
		params.add(new BasicNameValuePair("n", String.valueOf(count)));
		// 发送请求
		String json = MyHttpClient.post(url, params);
		log("getGonggao response string:" + json);
		// 处理结果
		if(json !=null && !json.startsWith("#")) {//< 返回正确值
			return JsonUtil.paserGonggaoArray(json);
		}
		return null;
	}
	
	/**
	 * 获取商户活动取指定竞品活动列表
	 * @param start
	 * @param count
	 * @param id
	 * @return
	 */
	public List<HuoDong> getHuodongs(int start, int count, long id) {
		String url = Api.API_HUODONG;
		// 设置参数
		List<NameValuePair> params = new ArrayList<NameValuePair>(2);
		params.add(new BasicNameValuePair("s", String.valueOf(start)));
		params.add(new BasicNameValuePair("n", String.valueOf(count)));
		params.add(new BasicNameValuePair("id", String.valueOf(id)));
		// 发送请求
		String json = MyHttpClient.post(url, params);
		log("getHuodong response string:" + json);
		// 处理结果
		if(json !=null && !json.startsWith("#")) {//< 返回正确值
			return JsonUtil.paserHuodongArray(json);
		}
		return null;
	}
	
	/**
	 * 获取商户信息列表
	 * @param start
	 * @param count
	 * @return
	 */
	public List<Shanghu> getShanghus(int start, int count) {
		String url = Api.API_SHANGHU;
		// 设置参数
		List<NameValuePair> params = new ArrayList<NameValuePair>(2);
		params.add(new BasicNameValuePair("s", String.valueOf(start)));
		params.add(new BasicNameValuePair("n", String.valueOf(count)));
		// 发送请求
		String json = MyHttpClient.post(url, params);
		log("getShanghu response string:" + json);
		// 处理结果
		if(json !=null && !json.startsWith("#")) {//< 返回正确值
			return JsonUtil.paserShanghuArray(json);
		}
		return null;
	}
	
	/**
	 * @brief 获取订单产品列表
	 * @param start
	 * @param count
	 * @return
	 */
	public List<DingDan> getDingDans(int start, int count) {
		String url = Api.API_DINGDANS;
		// 设置参数
		List<NameValuePair> params = new ArrayList<NameValuePair>(2);
		params.add(new BasicNameValuePair("s", String.valueOf(start)));
		params.add(new BasicNameValuePair("n", String.valueOf(count)));
		SharedPreferences _data = context.getSharedPreferences("_data", Context.MODE_WORLD_WRITEABLE);
		String uid = _data.getString("user_id", "0");
		params.add(new BasicNameValuePair("u", uid));
		// 发送请求
		String json = MyHttpClient.post(url, params);
		log("getDingdans response string:" + json);
		// 处理结果
		if(json !=null && !json.startsWith("#")) {//< 返回正确值
			return JsonUtil.paserDingDanArray(json);
		}
		return null;
	}
	/**
	 * @brief 获取订货单列表
	 * @param start
	 * @param count
	 * @param pId
	 * @return
	 */
	public List<DingHuoDan> getDingHuoDans(int start, int count, int pId) {
		String url = Api.API_DINGHUODANS;
		// 设置参数
		List<NameValuePair> params = new ArrayList<NameValuePair>(2);
		params.add(new BasicNameValuePair("s", String.valueOf(start)));
		params.add(new BasicNameValuePair("n", String.valueOf(count)));
		params.add(new BasicNameValuePair("p", String.valueOf(pId)));
		// 发送请求
		String json = MyHttpClient.post(url, params);
		log("getDingdans response string:" + json);
		// 处理结果
		if(json !=null && !json.startsWith("#")) {//< 返回正确值
			return JsonUtil.paserDingHuoDanArray(json);
		}
		return null;
	}
	
	/**
	 * @brief 获取订单详情
	 * @param orderId
	 * @return
	 */
	public DingDanDetail getDingDanDetail(int orderId) {
		String url = Api.API_DINGDANINFO;
		// 设置参数
		List<NameValuePair> params = new ArrayList<NameValuePair>(2);
		params.add(new BasicNameValuePair("id", String.valueOf(orderId)));
		// 发送请求
		String json = MyHttpClient.post(url, params);
		log("getDingdans response string:" + json);
		// 处理结果
		if(json !=null && !json.startsWith("#")) {//< 返回正确值
			return JsonUtil.paserDingoDanInfoArray(json);
		}
		return null;
	}
	
	/**
	 * @brief 获取送货单列表
	 * @param start
	 * @param count
	 * @return
	 */
	public List<SongHuoDan> getSongHuoDans(int start, int count) {
		String url = Api.API_SONGHUODANS;
		// 设置参数
		List<NameValuePair> params = new ArrayList<NameValuePair>(2);
		params.add(new BasicNameValuePair("s", String.valueOf(start)));
		params.add(new BasicNameValuePair("n", String.valueOf(count)));
		SharedPreferences _data = context.getSharedPreferences("_data", Context.MODE_WORLD_WRITEABLE);
		String uid = _data.getString("user_id", "0");
		params.add(new BasicNameValuePair("u", uid));
		// 发送请求
		String json = MyHttpClient.post(url, params);
		log("getDingdans response string:" + json);
		// 处理结果
		if(json !=null && !json.startsWith("#")) {//< 返回正确值
			return JsonUtil.paserSongHuoDanArray(json);
		}
		return null;
	}
	
	/**
	 * @brief 获取送货单详情 
	 * @param sId
	 * @return
	 */
	public SongHuoDetail getSongHuoDetail(int id) {
		String url = Api.API_SONGHUODANDETAIL;
		// 设置参数
		List<NameValuePair> params = new ArrayList<NameValuePair>(2);
		params.add(new BasicNameValuePair("id", String.valueOf(id)));
		// 发送请求
		String json = MyHttpClient.post(url, params);
		log("getDingdans response string:" + json);
		// 处理结果
		if(json !=null && !json.startsWith("#")) {//< 返回正确值
			return JsonUtil.paserSongHuoInfoArray(json);
		}
		return null;
	}
	
	/**
	 * 获取商户信息列表
	 * @param start
	 * @param count
	 * @return
	 */
	public GeRen getGeren(String id) {
		String url = Api.API_GEREN;
		// 设置参数
		List<NameValuePair> params = new ArrayList<NameValuePair>(1);
		params.add(new BasicNameValuePair("uid", id));
		// 发送请求
		String json = MyHttpClient.post(url, params);
		log("getGeren response string:" + json);
		// 处理结果
		if(json !=null && !json.startsWith("#")) {//< 返回正确值
			GeRen geRen = JsonUtil.paserGeRen(json);
			geRen.setId(id);
			return geRen;
		}
		return null;
	}
	
	/**
	 * 提交订单信息
	 * @param start
	 * @param count
	 * @return
	 */
	public boolean sendDingdan(List<DingDan> list, long cid) {
		String url = Api.API_ADD_DINGDAN;
		// 组织发送json
		SharedPreferences _data = context.getSharedPreferences("_data", Context.MODE_WORLD_WRITEABLE);
		String uid = _data.getString("user_id", "0");
		String json = JsonUtil.makeDingDansJsonString(list, uid, cid);
		log("sendDingdan req json=" + json);
		// 设置参数
		List<NameValuePair> params = new ArrayList<NameValuePair>(1);
		params.add(new BasicNameValuePair("req", json));
		// 发送请求
		String json2 = MyHttpClient.post(url, params);
		log("sendDingdan response string:" + json);
		// 处理结果
		if(json2 !=null && !json2.startsWith("#")) {//< 返回正确值
//			try {
//				JSONObject jsonObject = new JSONObject(json2);
//				return jsonObject.getBoolean("s");
//			} catch (JSONException e) {
//				e.printStackTrace();
//			}
			return true;
		}
		return false;
	}
	
	/**
	 * 提交竞品活动信息
	 * @param start
	 * @param count
	 * @return
	 */
	public String sendJingPinHuoDong(List<JingPinHuoDong> list, long cid) {
		String url = Api.API_ADD_HUODONG;
		// 组织发送json
		SharedPreferences _data = context.getSharedPreferences("_data", Context.MODE_WORLD_WRITEABLE);
		String uid = _data.getString("user_id", "0");
		String json = JsonUtil.makeJingPinHuoDongsJsonString(list, uid, cid);
		log("sendJingPinHuoDong req json=" + json);
		// 设置参数
		List<NameValuePair> params = new ArrayList<NameValuePair>(1);
		params.add(new BasicNameValuePair("req", json));
		// 发送请求
		String json2 = MyHttpClient.post(url, params);

		log("sendJingPinHuoDong response string:" + json);
		return json2;		
		// 处理结果
		//if(json2 !=null && !json2.startsWith("#")) {//< 返回正确值
		//	return String json2;
		//}
		//return false;
	}
	
	/**
	 * 提交送货确认信息
	 * @param start
	 * @param count
	 * @return
	 */
	public boolean sendEditSongHuo(List<SongHuoConfirm> list) {
		String url = Api.API_EDITSONGHUO;
		// 组织发送json
		SharedPreferences _data = context.getSharedPreferences("_data", Context.MODE_WORLD_WRITEABLE);
		String uid = _data.getString("user_id", "0");
		String json = JsonUtil.makeSonghuoJsonString(list, uid);
		log("sendEditSongHuo req json=" + json);
		// 设置参数
		List<NameValuePair> params = new ArrayList<NameValuePair>(1);
		params.add(new BasicNameValuePair("req", json));
		// 发送请求
		String json2 = MyHttpClient.post(url, params);
		log("sendEditSongHuo response string:" + json);
		// 处理结果
		if(json2 !=null && !json2.startsWith("#")) {//< 返回正确值
//			try {
//				JSONObject jsonObject = new JSONObject(json2);
//				return jsonObject.getBoolean("s");
//			} catch (JSONException e) {
//				e.printStackTrace();
//			}
			return true;
		}
		return false;
	}
	
	/**
	 * 获取所有产品信息
	 * @return
	 */
	public List<Chanpin> getAllChanpin() {
		String url = Api.API_CHANPINALL;
		// 设置参数
		List<NameValuePair> params = new ArrayList<NameValuePair>(0);
		// 发送请求
		String json = MyHttpClient.post(url, params);
		log("getChanpinAll response string:" + json);
		// 处理结果
		if(json !=null && !json.startsWith("#")) {//< 返回正确值
			return JsonUtil.paserChanpinArray(json);
		}
		return null;
	}
	
	/**
	 * 获取所有竞品信息
	 * @param start
	 * @param count
	 * @return
	 */
	public List<JingPin> getJingpinsAll() {
		String url = Api.API_JINGPINALL;
		// 设置参数
		List<NameValuePair> params = new ArrayList<NameValuePair>(0);
		// 发送请求
		String json = MyHttpClient.post(url, params);
		log("getJingPinAll response string:" + json);
		// 处理结果
		if(json !=null && !json.startsWith("#")) {//< 返回正确值
			return JsonUtil.paserJingpinArray(json);
		}
		return null;
	}
	
	/**
	 * 获取商户信息列表
	 * @param start
	 * @param count
	 * @return
	 */
	public boolean deleteHuodong(int id) {
		String url = Api.API_HUODONGDEL;
		// 设置参数
		List<NameValuePair> params = new ArrayList<NameValuePair>(1);
		params.add(new BasicNameValuePair("id", String.valueOf(id)));
		// 发送请求
		String json = MyHttpClient.post(url, params);
		log("deleteHuodong response string:" + json);
		// 处理结果
		if(json !=null && !json.startsWith("#")) {//< 返回正确值
			return true;
		}
		return false;
	}
	
	public boolean EditSongHuoDan(List<Integer> Idlist, List<Integer> countList) {
		String url = Api.API_EDITSONGHUODAN;
		// 组织发送json
		String json = JsonUtil.makeEidtSonghuoJsonString(Idlist, countList);
		log("sendDingdan req json=" + json);
		// 设置参数
		List<NameValuePair> params = new ArrayList<NameValuePair>(1);
		params.add(new BasicNameValuePair("req", json));
		// 发送请求
		String json2 = MyHttpClient.post(url, params);
		log("sendDingdan response string:" + json);
		// 处理结果
		if(json2 !=null && !json2.startsWith("#")) {//< 返回正确值
//			try {
//				JSONObject jsonObject = new JSONObject(json2);
//				return jsonObject.getBoolean("s");
//			} catch (JSONException e) {
//				e.printStackTrace();
//			}
			return true;
		}
		return false;
	}
	
}
