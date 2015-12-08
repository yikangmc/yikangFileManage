package com.yikangyiliao.base.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ApplicationContextUtil
  implements ApplicationContextAware
{
  public static ApplicationContext applicationContext;

  public void setApplicationContext(ApplicationContext arg0)
    throws BeansException
  {
    applicationContext = arg0;
  }
}