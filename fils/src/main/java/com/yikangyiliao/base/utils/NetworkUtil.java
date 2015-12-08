package com.yikangyiliao.base.utils;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

public final class NetworkUtil
{
  private static Logger logger = Logger.getLogger(NetworkUtil.class);

  public static final String getIpAddress(HttpServletRequest request)
    throws IOException
  {
    String ip = request.getHeader("X-Forwarded-For");
    if (logger.isInfoEnabled()) {
      logger.info("getIpAddress(HttpServletRequest) - X-Forwarded-For - String ip=" + ip);
    }

    if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
      if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
        ip = request.getHeader("Proxy-Client-IP");
        if (logger.isInfoEnabled()) {
          logger.info("getIpAddress(HttpServletRequest) - Proxy-Client-IP - String ip=" + ip);
        }
      }
      if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
        ip = request.getHeader("WL-Proxy-Client-IP");
        if (logger.isInfoEnabled()) {
          logger.info("getIpAddress(HttpServletRequest) - WL-Proxy-Client-IP - String ip=" + ip);
        }
      }
      if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
        ip = request.getHeader("HTTP_CLIENT_IP");
        if (logger.isInfoEnabled()) {
          logger.info("getIpAddress(HttpServletRequest) - HTTP_CLIENT_IP - String ip=" + ip);
        }
      }
      if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
        ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        if (logger.isInfoEnabled()) {
          logger.info("getIpAddress(HttpServletRequest) - HTTP_X_FORWARDED_FOR - String ip=" + ip);
        }
      }
      if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
        ip = request.getRemoteAddr();
        if (logger.isInfoEnabled())
          logger.info("getIpAddress(HttpServletRequest) - getRemoteAddr - String ip=" + ip);
      }
    }
    else if (ip.length() > 15) {
      String[] ips = ip.split(",");
      for (int index = 0; index < ips.length; index++) {
        String strIp = ips[index];
        if (!"unknown".equalsIgnoreCase(strIp)) {
          ip = strIp;
          break;
        }
      }
    }
    return ip;
  }
}