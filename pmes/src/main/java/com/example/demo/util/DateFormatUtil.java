package com.example.demo.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class DateFormatUtil {
	private static SimpleDateFormat sdf ;
	static{
		sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	}
	
	/*
	 * 重置日期格式化工具
	 */
	public static void rebuildFormater(String formatStr){
		sdf = new SimpleDateFormat(formatStr);
	}
	/*
	 * 获取当前系统事件的字符串表达形式
	 */
	public static String getCurrentDateStr(){
		return getDateStr(new Date());
	}
	/*
	 * 转换传入日期对象
	 */
	public static String getDateStr(Date date){
		return sdf.format(date);
	}
}








