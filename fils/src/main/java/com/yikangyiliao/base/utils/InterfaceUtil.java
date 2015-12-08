package com.yikangyiliao.base.utils;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;

public class InterfaceUtil
{
  private static Map<String, String> serviceClassName = null;

  private static Map<String, String> mathodClassPath = null;

  public void loadConfigInterface(String filePath)
  {
    SAXParserFactory factory = SAXParserFactory.newInstance();
    try {
      SAXParser saxParser = factory.newSAXParser();
      InputStream localInputStream = InterfaceUtil.class.getClassLoader().getResourceAsStream("student.xml");
    }
    catch (ParserConfigurationException e)
    {
      e.printStackTrace();
    }
    catch (SAXException e) {
      e.printStackTrace();
    }
  }

  public static String getBeanNameByServiceCode(String serviceCode)
  {
    if (serviceCode.indexOf('-') != -1) {
      serviceCode = serviceCode.substring(0, serviceCode.lastIndexOf('-'));
    }
    return (String)serviceClassName.get(serviceCode);
  }

  public static String getMethodNameByServiceCode(String serviceCode)
  {
    return (String)mathodClassPath.get(serviceCode);
  }

  public static void main(String[] args)
  {
    String codeStr = "00-02-01";
    System.out.println(codeStr.substring(0, codeStr.lastIndexOf('-')));
  }

  static
  {
    serviceClassName = new HashMap();
    mathodClassPath = new HashMap();
    serviceClassName.put("0-0", "test");
    mathodClassPath.put("0-0-1", "test");

    serviceClassName.put("00-01", "seniorAccountService");

    mathodClassPath.put("00-01-01", "saveSeniorAccount");

    mathodClassPath.put("00-01-02", "getSeniorAccountById");

    mathodClassPath.put("00-01-03", "getSeniorAccountByUserId");

    serviceClassName.put("00-02", "scaleService");

    mathodClassPath.put("00-02-01", "saveAssessment");

    serviceClassName.put("00-03", "lifeNurseAssessmentService");
    mathodClassPath.put("00-03-01", "getQuestionCrosswiseByTableName");
    mathodClassPath.put("00-03-02", "getQuestionUnitAnswerMapByQuetionCrosswiseId");

    serviceClassName.put("00-04", "answerService");
    mathodClassPath.put("00-04-01", "saveAnswer");
    mathodClassPath.put("00-04-02", "saveAnswerForQuestion");

    serviceClassName.put("00-05", "oldManTumbleService");
    mathodClassPath.put("00-05-01", "getTable");

    serviceClassName.put("00-06", "sicknessAssessmentService");
    mathodClassPath.put("00-06-01", "getTable");

    serviceClassName.put("00-07", "depressionAssessmentService");
    mathodClassPath.put("00-07-01", "getTable");

    serviceClassName.put("00-08", "depressionSelfTestingService");
    mathodClassPath.put("00-08-01", "getTable");

    serviceClassName.put("00-09", "oldManCommonQuestionService");
    mathodClassPath.put("00-09-01", "getTable");

    serviceClassName.put("00-10", "pserceptionCommunicationsService");
    mathodClassPath.put("00-10-01", "getTable");

    serviceClassName.put("00-11", "socialParticipationService");
    mathodClassPath.put("00-11-01", "getTable");

    serviceClassName.put("00-12", "dailyIndexesService");
    mathodClassPath.put("00-12-01", "getTable");

    serviceClassName.put("00-13", "mentalIndexesService");
    mathodClassPath.put("00-13-01", "getTable");

    serviceClassName.put("00-14", "alzheimerDiseaseService");
    mathodClassPath.put("00-14-01", "getTable");

    serviceClassName.put("00-15", "assessmentService");
    mathodClassPath.put("00-15-01", "saveAssessment");
    mathodClassPath.put("00-15-02", "getAssessmentBySeniorId");

    serviceClassName.put("login", "login");
    mathodClassPath.put("login", "login");

    serviceClassName.put("00-16", "surveyTableService");
    mathodClassPath.put("00-16-01", "getTableList");
    mathodClassPath.put("00-16-02", "getQuestionsAndAnswersIsCheck");
    mathodClassPath.put("00-16-03", "getQuestionAndAnswersIsCheckTwo");
    mathodClassPath.put("00-16-04", "getQuestionAndAnswersIsCheckThree");

    serviceClassName.put("regist", "userService");
    mathodClassPath.put("regist", "registerUser");

    serviceClassName.put("registerUserAndSaveServiceInfo", "userService");
    mathodClassPath.put("registerUserAndSaveServiceInfo", "saveRegisterUserAndSaveServiceInfo");

    serviceClassName.put("00-17", "userService");
    mathodClassPath.put("00-17-01", "registerUser");
    mathodClassPath.put("00-17-02", "saveServiceUserInfo");
    mathodClassPath.put("00-17-03", "registerUserAndSaveServiceInfo");

    mathodClassPath.put("00-17-04", "getUserServiceInfoByUserId");

    serviceClassName.put("00-18", "deviceService");

    mathodClassPath.put("00-18-01", "saveDevice");

    mathodClassPath.put("00-18-02", "getAliasByUser");
  }
}