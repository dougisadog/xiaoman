package com.nangua.xiaomanjflc.utils;

import android.util.Log;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.DeserializationConfig.Feature;
import org.codehaus.jackson.type.JavaType;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.louding.frame.utils.StringUtils;

public class FormatUtils {

	public static String priceFormat(long price) {
		String st = "";
		if (price < 0) {
			price = -price;
			st = "-";
		}
		long a = price / 100;
		long b = price % 100;
		if (b == 0) {
			st += a + ".00";
		} else if (b < 10) {
			st += a + ".0" + b;
		} else if (b > 9) {
			st += a + "." + b;
		}
		return st;
	}

	public static String priceFormat(int price) {
		String st = "";
		long a = price / 100;
		long b = price % 100;
		if (b == 0) {
			st = a + ".00";
		} else if (b < 10) {
			st = a + ".0" + b;
		} else if (b > 9) {
			st = a + "." + b;
		}
		return st;
	}

	public static String getAmount(String s) {
		String a;
		float i = Float.parseFloat(s);
		if (i > 1000000) {
			if (i % 1000000 == 0) {
				a = ((long) i / 1000000) + "万";
			} else {
				a = (i / 1000000) + "万";
			}
		} else {
			if (i % 100 == 0) {
				a = ((long) i / 100) + "元";
			} else {
				a = (i / 100) + "元";
			}
		}
		return a;
	}

	public static ArrayList<String> getNewAmount(String s) {
		String a;
		String b;
		float i = Float.parseFloat(s);
		if (i > 1000000) {
			if (i % 1000000 == 0) {
				a = ((long) i / 1000000) + "";
			} else {
				a = (i / 1000000) + "";
			}
			b = "万";
		} else {
			if (i % 100 == 0) {
				a = ((long) i / 100) + "";
			} else {
				a = (i / 100) + "";
			}
			b = "元";
		}
		ArrayList<String> ss = new ArrayList<String>();
		ss.add(a);
		ss.add(b);
		return ss;
	}
	
	public static String numSecretHide(String num) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < num.length(); i++) {
			String current = num.charAt(i) + "";
			if (StringUtils.isNumber(current)) {
				current = "*";
			}
			builder.append(current);
		}
		return builder.toString();
	}

	public static String urlFormat(String url) {
		if (url.indexOf("http") == -1) {
			url = "http://" + url;
		}
		return url;
	}

	// 处理浮点数显示的时候后面会出现.0的问题
	public static String numFormat(float num) {
		String str = String.valueOf(num);
		String[] s = str.split("\\.");
		Log.d("numFormat", s.length + "");
		Log.d("numFormat", str);
		for (int i = 0; i < s.length; i++) {
			Log.d("numFormat", s[i]);
		}
		if (s.length > 1 && s[1].equals("0")) {
			return s[0];
		} else {
			return str;
		}
	}
	
	/**
	 * 将每三个数字加上逗号处理（通常使用金额方面的编辑）
	 *
	 * @param text
	 *            无逗号的数字
	 * @return 加上逗号的数字
	 */
	public static String fmtMicrometer(String text) {
		DecimalFormat df = null;
		if (text.indexOf(".") > 0) {
			if (text.length() - text.indexOf(".") - 1 == 0) {
				df = new DecimalFormat("###,##0.");
			} else if (text.length() - text.indexOf(".") - 1 == 1) {
				df = new DecimalFormat("###,##0.0");
			} else {
				df = new DecimalFormat("###,##0.00");
			}
		} else {
			df = new DecimalFormat("###,##0");
		}
		double number = 0.0;
		try {
			number = Double.parseDouble(text);
		} catch (Exception e) {
			number = 0.0;
		}
		return df.format(number);
	}
	
	public static ObjectMapper mapper;
	
	public static ObjectMapper getMapper() {
		if (null == mapper) {
			mapper = new ObjectMapper();
			//忽略未知参数的转换
			mapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);			
		}
		return mapper;
	}
	
	
	public static <T> List<T> getListJson(String json, Class<T> clazz) {
		List<T> result = new ArrayList<T>();
		try {
			result = getMapper().readValue(json, getCollectionType(List.class, clazz));
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
		return getMapper().getTypeFactory().constructParametricType(collectionClass, elementClasses);   
	}  
	
	public static String getJson(Object o) {
		String Json = null;
		try {
			Json = getMapper().writeValueAsString(o);
		} catch (Exception e) {
			e.printStackTrace();
			return Json;
		}
		return Json;
	}
	
	/**
	 * jsons数据解析成为对应类
	 * @param json
	 * @param clazz 所属class
	 * @return
	 */
	public static <T> T jsonParse(String json, Class<T> clazz) {
		T result = null;
		try {
			result = getMapper().readValue(json, clazz);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 将以.00结尾的数字变为整数
	 */
	public static String getSimpleNum(String num) {
		if (num.endsWith(".00"))
			num = num.substring(0, num.length() - 3);
		return num;
	}

}
