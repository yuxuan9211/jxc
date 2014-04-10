package com.kingtong.jxc.common;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.kingtong.jxc.conn.Api;
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

public class JsonUtil {
	
	public static User paserUser(String jsonString) {
		try {
			JSONObject jsObject = new JSONObject(jsonString);
			User user = new User();
			user.setUid(jsObject.getString("uid"));
			user.setUsername(jsObject.getString("uname"));
			return user;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static List<Chanpin> paserChanpinArray(String jsonArrayString) {
		try {
			JSONArray jsArray = new JSONArray(jsonArrayString);
			List<Chanpin> list = new ArrayList<Chanpin>();
			for (int i = 0,len=jsArray.length(); i < len; i++) {
				JSONObject object = jsArray.getJSONObject(i);
				Chanpin bean = new Chanpin();
				bean.setId(object.optLong("id"));
				bean.setJg(object.getString("jg"));
				bean.setPic(Api.API_HOST + object.getString("pic"));
				bean.setRl(object.getString("rl"));
				bean.setTitle(object.getString("title"));
				list.add(bean);
			}
			return list;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
	

	public static List<JingPin> paserJingpinArray(String jsonArrayString) {
		try {
			JSONArray jsArray = new JSONArray(jsonArrayString);
			List<JingPin> list = new ArrayList<JingPin>();
			for (int i = 0,len=jsArray.length(); i < len; i++) {
				JSONObject object = jsArray.getJSONObject(i);
				JingPin bean = new JingPin();
				bean.setId(object.optLong("id"));
				bean.setJg(object.getString("jg"));
				bean.setPic(Api.API_HOST + object.getString("pic"));
				bean.setRl(object.getString("rl"));
				bean.setTitle(object.getString("title"));
				list.add(bean);
			}
			return list;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static List<GongGao> paserGonggaoArray(String jsonArrayString) {
		try {
			JSONArray jsArray = new JSONArray(jsonArrayString);
			List<GongGao> list = new ArrayList<GongGao>();
			for (int i = 0,len=jsArray.length(); i < len; i++) {
				JSONObject object = jsArray.getJSONObject(i);
				GongGao bean = new GongGao();
				bean.setText(object.getString("text"));
				bean.setTitle(object.getString("title"));
				list.add(bean);
			}
			return list;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static List<HuoDong> paserHuodongArray(String jsonArrayString) {
		try {
			JSONArray jsArray = new JSONArray(jsonArrayString);
			List<HuoDong> list = new ArrayList<HuoDong>();
			for (int i = 0,len=jsArray.length(); i < len; i++) {
				JSONObject object = jsArray.getJSONObject(i);
				HuoDong bean = new HuoDong();
				bean.setId(object.optLong("id"));
				bean.setTitle(object.getString("title"));
				bean.seteTime(object.getString("etime"));
				bean.setInfo(object.getString("info"));
				bean.setJxs(object.getString("jxs"));
				bean.setsTime(object.getString("stime"));
				list.add(bean);
			}
			return list;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static List<Shanghu> paserShanghuArray(String jsonArrayString) {
		try {
			JSONArray jsArray = new JSONArray(jsonArrayString);
			List<Shanghu> list = new ArrayList<Shanghu>();
			for (int i = 0,len=jsArray.length(); i < len; i++) {
				JSONObject object = jsArray.getJSONObject(i);
				Shanghu bean = new Shanghu();
				bean.setId(object.optLong("id"));
				bean.setTitle(object.getString("title"));
				bean.setAdd(object.getString("add"));
				bean.setFax(object.getString("fax"));
				bean.setLxr(object.getString("lxr"));
				bean.setMp(object.getString("mp"));
				bean.setTel(object.getString("tel"));
				list.add(bean);
			}
			return list;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static List<DingDan> paserDingDanArray(String jsonArrayString) {
		try {
			JSONArray jsArray = new JSONArray(jsonArrayString);
			List<DingDan> list = new ArrayList<DingDan>();
			for (int i = 0,len=jsArray.length(); i < len; i++) {
				JSONObject object = jsArray.getJSONObject(i);
				DingDan bean = new DingDan();
				if(object.has("pid"))
				{
					bean.setId(object.getInt("pid"));
				}
				if(object.has("pname"))
				{
					bean.setName(object.getString("pname"));
				}
				if(object.has("price"))
				{
					bean.setPrice(object.getDouble("price"));
				}
				if(object.has("num"))
				{
					bean.setCount(object.getInt("num"));
				}
				if(object.has("time"))
				{
					bean.setTime(object.getLong("time"));
				}
				list.add(bean);
			}
			return list;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static List<DingHuoDan> paserDingHuoDanArray(String jsonArrayString) {
		try {
			JSONArray jsArray = new JSONArray(jsonArrayString);
			List<DingHuoDan> list = new ArrayList<DingHuoDan>();
			for (int i = 0,len=jsArray.length(); i < len; i++) {
				JSONObject object = jsArray.getJSONObject(i);
				DingHuoDan bean = new DingHuoDan();
				if(object.has("id"))
				{
					bean.setOid(object.getInt("id"));
				}
				if(object.has("name"))
				{
					bean.setShanghuName(object.getString("name"));
				}
				if(object.has("num"))
				{
					String num = object.getString("num");
					if(!num.equals("null") && !num.equals(""))
					{
						bean.setNum(Integer.parseInt(num));
					}
				}
				list.add(bean);
			}
			return list;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static List<SongHuoDan> paserSongHuoDanArray(String jsonArrayString) {
		try {
			JSONArray jsArray = new JSONArray(jsonArrayString);
			List<SongHuoDan> list = new ArrayList<SongHuoDan>();
			for (int i = 0,len=jsArray.length(); i < len; i++) {
				JSONObject object = jsArray.getJSONObject(i);
				SongHuoDan bean = new SongHuoDan();
				if(object.has("id"))
				{
					bean.setId(object.getInt("id"));
				}
				if(object.has("title"))
				{
					bean.setName(object.getString("title"));
				}
				if(object.has("sid"))
				{
					bean.setSid(object.getInt("sid"));
				}
				if(object.has("addtime"))
				{
					String sendTime = object.getString("addtime");
					if(sendTime !=null && !sendTime.equals("null") && !sendTime.equals(""))
					{
						bean.setAddTime(TimeUtil.pareTime2ShortString(Long.parseLong(sendTime)));
					}else
					{
						bean.setAddTime(TimeUtil.pareTime2ShortString(System.currentTimeMillis()));
					}
				}
				list.add(bean);
			}
			return list;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static DingDanDetail paserDingoDanInfoArray(String jsonObjString) {
		try {
				JSONObject object = new JSONObject(jsonObjString);
				DingDanDetail bean = new DingDanDetail();
				if(object.has("name"))
				{
					bean.setName(object.getString("name"));
				}
				if(object.has("dizhi"))
				{
					bean.setAddress(object.getString("dizhi"));
				}
				if(object.has("dianhua"))
				{
					bean.setPhone(object.getString("dianhua"));
				}
				if(object.has("chuanzhen"))
				{
					bean.setFax(object.getString("chuanzhen"));
				}
				if(object.has("linxiren"))
				{
					bean.setContactor(object.getString("linxiren"));
				}
				if(object.has("shouji"))
				{
					bean.setMobile(object.getString("shouji"));
				}
				if(object.has("shsj"))
				{
					String sendTime = object.getString("shsj");
					if(sendTime !=null && !sendTime.equals("null") && !sendTime.equals(""))
					{
						bean.setSendTime(TimeUtil.pareTime2LongString(Long.parseLong(sendTime)));
					}else
					{
						bean.setSendTime(TimeUtil.pareTime2LongString(System.currentTimeMillis()));
					}
				}
				if(object.has("pro"))
				{
					JSONArray dinDanArray = object.getJSONArray("pro");
					List<DingDan> dingDanList = new ArrayList<DingDan>();
					for(int j =0; j < dinDanArray.length(); j ++)
					{
						JSONObject subObj = dinDanArray.getJSONObject(j);
						DingDan dingDan = new DingDan();
						if(subObj.has("name"))
						{
							dingDan.setName(subObj.getString("name"));
						}
						if(subObj.has("jiage"))
						{
							dingDan.setPrice(subObj.getDouble("jiage"));
						}
						if(subObj.has("num"))
						{
							dingDan.setCount(subObj.getInt("num"));
						}
						dingDanList.add(dingDan);
					}
					bean.setDingDanList(dingDanList);
				}
			return bean;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static SongHuoDetail paserSongHuoInfoArray(String jsonObjString) {
		try {
				JSONObject object = new JSONObject(jsonObjString);
				SongHuoDetail bean = new SongHuoDetail();
				if(object.has("id"))
				{
					bean.setId(object.getInt("id"));
				}
				if(object.has("name"))
				{
					bean.setTitle(object.getString("name"));
				}
				if(object.has("add"))
				{
					bean.setAddress(object.getString("add"));
				}
				if(object.has("tel"))
				{
					bean.setPhone(object.getString("tel"));
				}
				if(object.has("lxr"))
				{
					bean.setContactor(object.getString("lxr"));
				}
				if(object.has("mp"))
				{
					bean.setMobile(object.getString("mp"));
				}
				if(object.has("orderinfo"))
				{
					JSONArray songHuoArray = object.getJSONArray("orderinfo");
					List<SongHuoConfirm> confirmList = new ArrayList<SongHuoConfirm>();
					for(int j =0; j < songHuoArray.length(); j ++)
					{
						JSONObject subObj = songHuoArray.getJSONObject(j);
						SongHuoConfirm songHuo = new SongHuoConfirm();
						if(subObj.has("id"))
						{
							songHuo.setId(subObj.getInt("id"));
						}
						if(subObj.has("order_id"))
						{
							songHuo.setOid(subObj.getInt("order_id"));
						}
						if(subObj.has("uid"))
						{
							songHuo.setUid(subObj.getInt("uid"));
						}
						if(subObj.has("pid"))
						{
							songHuo.setPid(subObj.getInt("pid"));
						}
						if(subObj.has("pname"))
						{
							songHuo.setName(subObj.getString("pname"));
						}
						if(subObj.has("price"))
						{
							songHuo.setPrice(subObj.getDouble("price"));
						}
						if(subObj.has("num"))
						{
							songHuo.setCount(subObj.getInt("num"));
						}
						if(subObj.has("sjnum"))
						{
							songHuo.setConfirmCount(subObj.getInt("sjnum"));
						}
						confirmList.add(songHuo);
					}
					bean.setOrderInfo(confirmList);
				}
			return bean;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static GeRen paserGeRen(String jsonString) {
		try {
			JSONObject jsObject = new JSONObject(jsonString);
			GeRen user = new GeRen();
			user.setName(jsObject.optString("name"));
			user.setPhoto(jsObject.optString("photo"));
			user.setEmail(jsObject.optString("email"));
			return user;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String makeDingDanJsonString(DingDan dingDan) {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("id", dingDan.getId());
			jsonObject.put("count", dingDan.getCount());
			jsonObject.put("price", dingDan.getPrice());
			jsonObject.put("name", dingDan.getName());
			jsonObject.put("time", dingDan.getTime());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObject.toString();
	}
	
	public static String makeDingDansJsonString(List<DingDan> list, String uid, long cid) {
		JSONObject json = new JSONObject();
		try {
			json.put("uid", uid);
			json.put("cid", cid);
			JSONArray array = new JSONArray();
			for(int i=1,len=list.size(); i<len; i++) {
				JSONObject object = new JSONObject(makeDingDanJsonString(list.get(i)));
				array.put(object);
			}
			json.put("list", array);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json.toString();
	}
	
	public static String makeJingPinHuoDongJsonString(JingPinHuoDong dingDan) {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("id", dingDan.getId());
			jsonObject.put("info", dingDan.getInfo());
			jsonObject.put("price", dingDan.getPrice());
			jsonObject.put("name", dingDan.getName());
			jsonObject.put("stime", dingDan.getStartTiem());
			jsonObject.put("etime", dingDan.getEndTiem());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObject.toString();
	}
	
	public static String makeJingPinHuoDongsJsonString(List<JingPinHuoDong> list, String uid, long cid) {
		JSONObject json = new JSONObject();
		try {
			json.put("uid", uid);
			json.put("cid", cid);
			JSONArray array = new JSONArray();
			for(int i=0,len=list.size(); i<len; i++) {
				JSONObject object = new JSONObject(makeJingPinHuoDongJsonString(list.get(i)));
				array.put(object);
			}
			json.put("list", array);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json.toString();
	}
	
	public static String makeSonghuoJsonString(List<SongHuoConfirm> list, String uid) {
		JSONArray jsonArray = new JSONArray();
		
		for(SongHuoConfirm bean : list) {
			JSONObject jsonObject = new JSONObject();
			try {
				jsonObject.put("uid", uid);
				jsonObject.put("oid", Long.valueOf(bean.getOid()));
				jsonObject.put("count", bean.getConfirmCount());
				
				jsonArray.put(jsonObject);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		return jsonArray.toString();
	}
	
	public static String makeUploadProductJsonString(String userId, String merchantId, String latitude, String longitude)
	{
		JSONObject json = new JSONObject();
		try {
				json.put("uid", userId);
				json.put("mid", merchantId);
				json.put("latitude", latitude);
				json.put("longitude", longitude);
			} catch (JSONException e) 
			{
				e.printStackTrace();
			}
			return json.toString();
	}
	
	public static String makeEidtSonghuoJsonString(List<Integer> idList, List<Integer> countList) {
		JSONArray jsonArray = new JSONArray();
		
		for(int i =0; i < idList.size(); i ++) {
			JSONObject jsonObject = new JSONObject();
			try {
				jsonObject.put("id", ""+idList.get(i).intValue());
				jsonObject.put("sjnum", countList.get(i).intValue());
				
				jsonArray.put(jsonObject);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		return jsonArray.toString();
	}
	
}
