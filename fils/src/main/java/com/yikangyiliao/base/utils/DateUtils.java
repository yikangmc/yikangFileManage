package com.yikangyiliao.base.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

	private static String defaultFormatDateStr = "yyyy-MM-dd HH:mm:ss";
	
	private static SimpleDateFormat simpleDateFormat;

	public static Long getＭillisecond(String dateStr) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return Long.valueOf(sdf.parse(dateStr).getTime());
	}

	/**
	 * @author liushuaic
	 * @date 2015/12/12 00:49 
	 * 格式化日期
	 * **/
	public static String formartDateToDateStr(Date date){
		simpleDateFormat =new SimpleDateFormat(defaultFormatDateStr);
		return simpleDateFormat.format(date);
	}
	
	public static void main(String[] args) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d = sdf.parse("2015-07-15");
		System.out.println(d.getTime());
		System.out.println(sdf2.format(sdf.parse("2015-07-15")));
	}
}