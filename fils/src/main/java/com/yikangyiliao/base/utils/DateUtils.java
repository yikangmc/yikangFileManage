package com.yikangyiliao.base.utils;

import java.io.PrintStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils
{
  public static Long getＭillisecond(String dateStr)
    throws ParseException
  {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    return Long.valueOf(sdf.parse(dateStr).getTime());
  }

  public static void main(String[] args) throws ParseException {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date d = sdf.parse("2015-07-15");
    System.out.println(d.getTime());
    System.out.println(sdf2.format(sdf.parse("2015-07-15")));
  }
}