package com.yikangyiliao.base.utils;

import com.yikangyiliao.base.encryption.AES;
import com.yikangyiliao.fileManage.common.error.ExceptionConstants;
import com.yikangyiliao.fileManage.common.error.ExceptionConstants.parameterException;
import com.yikangyiliao.fileManage.common.error.ExceptionInfo;
import java.io.PrintStream;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

public class AccessTiketCheckout
{
  private static final String secretKey = "yi99kang88yi99ka";
  private static Logger logger = Logger.getLogger(AccessTiketCheckout.class);

  public static Boolean checkAccessTiketLayout(String accessTiket, HttpServletRequest hsr)
    throws Exception
  {
    try
    {
      String accessTik = AES.Decrypt(accessTiket, "yi99kang88yi99ka");

      if ((accessTik.contains("UD")) && (accessTik.contains("￥LDT")) && 
        (accessTik
        .contains("￥MC")))
      {
        int udIndex = accessTik.indexOf("UD");
        int ldtIndex = accessTik.indexOf("￥LDT");
        int mcIndex = accessTik.indexOf("￥MC");

        if ((ldtIndex > udIndex) && (mcIndex > ldtIndex)) {
          return Boolean.valueOf(true);
        }
        logger.error("ip为：" + NetworkUtil.getIpAddress(hsr) + "-->输入格式accessTiket 错误数据，内容为：" + accessTiket + " 这货，已经到了测试 参数 顺序这层，注意了。");

        throw new ExceptionInfo(ExceptionConstants.parameterException.accessTiketException.errorCode, ExceptionConstants.parameterException.accessTiketException.errorMessage);
      }

      logger.error("ip为：" + NetworkUtil.getIpAddress(hsr) + "-->输入格式accessTiket 错误数据，内容为：" + accessTiket);

      throw new ExceptionInfo(ExceptionConstants.parameterException.accessTiketException.errorCode, ExceptionConstants.parameterException.accessTiketException.errorMessage);
    }
    catch (Exception e)
    {
      e.printStackTrace();
      logger.error("ip为：" + NetworkUtil.getIpAddress(hsr) + "-->输入格式accessTiket 错误数据，内容为：" + accessTiket);
    }
    throw new ExceptionInfo(ExceptionConstants.parameterException.accessTiketException.errorCode, ExceptionConstants.parameterException.accessTiketException.errorMessage);
  }

  public static String getAccessTiketLDT(String accessTiket, HttpServletRequest hsr)
    throws Exception
  {
    if (checkAccessTiketLayout(accessTiket, hsr).booleanValue()) {
      String accessTik = AES.Decrypt(accessTiket, "yi99kang88yi99ka");
      String[] strs = accessTik.split("￥LDT");
      strs = strs[1].split("￥MC");

      return strs[0];
    }
    return "";
  }

  public static String getAccessTiketLDT(String accessTiket)
    throws Exception
  {
    String accessTik = AES.Decrypt(accessTiket, "yi99kang88yi99ka");
    String[] strs = accessTik.split("￥LDT");
    strs = strs[1].split("￥MC");
    return strs[0];
  }

  public static String getAccessTiketMC(String accessTiket, HttpServletRequest hsr)
    throws Exception
  {
    if (checkAccessTiketLayout(accessTiket, hsr).booleanValue()) {
      String accessTik = AES.Decrypt(accessTiket, "yi99kang88yi99ka");
      String[] strs = accessTik.split("￥MC");

      return strs[1];
    }
    return "";
  }

  public static String getAccessTiketMC(String accessTiket) throws Exception {
    String accessTik = AES.Decrypt(accessTiket, "yi99kang88yi99ka");
    String[] strs = accessTik.split("￥MC");
    return strs[1];
  }

  public static String getAccessTiketUD(String accessTiket, HttpServletRequest hsr)
    throws Exception
  {
    if (checkAccessTiketLayout(accessTiket, hsr).booleanValue()) {
      String accessTik = AES.Decrypt(accessTiket, "yi99kang88yi99ka");
      String[] strs = accessTik.split("￥LDT");

      return strs[0];
    }
    return "";
  }

  public static String getAccessTiketUD(String accessTiket) throws Exception {
    String accessTik = AES.Decrypt(accessTiket, "yi99kang88yi99ka");
    String[] strs = accessTik.split("￥LDT");
    return strs[0].substring(2);
  }

  public static String generateAccessTicket(String loginId, Long loginDateTime, String machineCode)
    throws Exception
  {
    return AES.Encrypt("UD" + loginId + "￥LDT" + loginDateTime + "￥MC" + machineCode, "yi99kang88yi99ka");
  }

  public static void main(String[] args) {
    try {
      System.out.println(AES.Decrypt("ae7433528cdd54dc76e922c1d612c82dfcfc343a64e68012c34c1b74c4cd73dd5e4c7afd323d91954ba85f0a1bf9bb45", "yi99kang88yi99ka"));
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
}