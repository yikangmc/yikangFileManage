package com.yikangyiliao.base;

import com.yikangyiliao.base.encryption.AES;
import com.yikangyiliao.base.utils.AccessTiketCheckout;
import com.yikangyiliao.base.utils.ApplicationContextUtil;
import com.yikangyiliao.base.utils.InterfaceUtil;
import com.yikangyiliao.fileManage.common.error.ExceptionConstants;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ServiceController
{
  private ObjectMapper objectMapper = new ObjectMapper();

  private Logger logger = Logger.getLogger(ServiceController.class);

  @RequestMapping({"/service/{serverviceCode}"})
  @ResponseBody
  public Map<String, Object> doMethod(@PathVariable String serverviceCode, String appId, String accessTicket, String paramData, HttpServletRequest request) throws JsonGenerationException, JsonMappingException, IOException {
    Map rtnMap = new HashMap();
    if (null != serverviceCode)
    {
      String beanName = InterfaceUtil.getBeanNameByServiceCode(serverviceCode);

      String methodName = InterfaceUtil.getMethodNameByServiceCode(serverviceCode);

      if ((null != beanName) && 
        (null != methodName))
      {
        Object invokObject = ApplicationContextUtil.applicationContext.getBean(beanName);
        try
        {
          Method returnMethod = invokObject.getClass().getMethod(methodName, new Class[] { Map.class });

          Map paramMap = new HashMap();

          if ((null != paramData) && (paramData.length() >= 0) && 
            (paramData.length() > 5)) {
            paramData = AES.Decrypt(paramData, "1234567890abcDEF");
            this.logger.debug("serviceController --> 接收到的paramData数据：" + paramData);
            paramMap = (Map)this.objectMapper.readValue(paramData, Map.class);
          }

          if ((!serverviceCode.equals("login")) && (!serverviceCode.equals("registerUserAndSaveServiceInfo"))) {
            String accessTiket = request.getParameter("accessTicket");
            String UD = AccessTiketCheckout.getAccessTiketUD(accessTiket);
            String LDT = AccessTiketCheckout.getAccessTiketLDT(accessTiket);
            String MC = AccessTiketCheckout.getAccessTiketMC(accessTiket);

            paramMap.put("userId", UD);
            paramMap.put("loginDateTime", LDT);
            paramMap.put("machineCode", MC);
          }
          try {
            rtnMap = (Map)returnMethod.invoke(invokObject, new Object[] { paramMap });
          }
          catch (Exception e) {
            this.logger.error(e.getMessage());
            e.printStackTrace();

            rtnMap.put("status", ExceptionConstants.systemException.systemException.errorCode);
            rtnMap.put("message", ExceptionConstants.systemException.systemException.errorMessage);
          }

          if (null != rtnMap.get("data")) {
            Object data = rtnMap.get("data");
            String jsonStr = "";
            if (!serverviceCode.equals("login"))
              jsonStr = this.objectMapper.writeValueAsString(data);
            else {
              jsonStr = data.toString();
            }

            String enStr = AES.Encrypt(jsonStr, "1234567890abcDEF");
            rtnMap.put("data", enStr);
          }

          return rtnMap;
        }
        catch (IllegalAccessException e) {       
        	this.logger.debug(e.getMessage());
        }
        catch (IllegalArgumentException e) {         
        	this.logger.debug(e.getMessage());
        }
        catch (InvocationTargetException e) {
        	this.logger.debug(e.getMessage());         
        }
        catch (NoSuchMethodException e) {         
        	this.logger.debug(e.getMessage());
        }
        catch (SecurityException e) {          
        	this.logger.debug(e.getMessage());
        }
        catch (JsonGenerationException e) {         
        	this.logger.debug(e.getMessage());
        }
        catch (JsonMappingException e) {         
        	this.logger.debug(e.getMessage());
        }
        catch (IOException e) {          
        	this.logger.debug(e.getMessage());
        }
        catch (Exception e) {
        	this.logger.debug(e.getMessage());
        }

      }
      rtnMap.put("status", "999999");
      rtnMap.put("message", "没有对应服务！");
      return rtnMap;
    }
    rtnMap.put("status", "999999");
    rtnMap.put("message", "易康现在有点忙！");
    return rtnMap;
  }

  @RequestMapping({"/test"})
  @ResponseBody
  public String doMethodw2(ModelMap map, HttpServletRequest request) {
    return "{status:'999999',message:'易康现在有点忙！',data:''}";
  }
}