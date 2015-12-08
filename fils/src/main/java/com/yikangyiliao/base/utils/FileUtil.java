package com.yikangyiliao.base.utils;

import java.io.PrintStream;
import java.util.UUID;

public class FileUtil
{
  public static String getFileSuffix(String fileName)
  {
    return fileName.substring(fileName.lastIndexOf("."), fileName.length());
  }

  public static String getUniqueFileName(String fileName)
  {
    String suffix = fileName.substring(fileName.lastIndexOf("."), fileName.length());
    UUID uuid = UUID.randomUUID();
    return uuid + suffix;
  }

  public static void main(String[] args)
  {
    String fileName = "dd.dd.img";
    String suffix = fileName.substring(fileName.lastIndexOf("."), fileName.length());
    UUID uuid = UUID.randomUUID();
    System.out.println(uuid + suffix);
  }
}