package com.yikangyiliao.fileManage.common.error;

public class ExceptionInfo extends Exception
{
  private static final long serialVersionUID = -901352075010858921L;
  private String errorCode;
  private String message;

  public ExceptionInfo(String errorCode, String message)
  {
    this.errorCode = errorCode;
    this.message = message;
  }

  public String getErrorCode() {
    return this.errorCode;
  }
  public void setErrorCode(String errorCode) {
    this.errorCode = errorCode;
  }
  public String getMessage() {
    return this.message;
  }
  public void setMessage(String message) {
    this.message = message;
  }
}