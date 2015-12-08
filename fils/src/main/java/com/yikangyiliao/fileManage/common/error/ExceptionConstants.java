package com.yikangyiliao.fileManage.common.error;

public class ExceptionConstants
{
  public static enum fileException
  {
    fileNumsException("040001", "文件数量超过限制数量！"), 
    fileSizeException("040002", "文件数量大小超过限制数量！"), 
    imageFileTypeException("040003", "文件类型错误！");

    public String errorCode;
    public String errorMessage;

    private fileException(String errorCode, String errorMessage) { this.errorCode = errorCode;
      this.errorMessage = errorMessage;
    }
  }

  public static enum loginException
  {
    userNameOrPasswordException("030001", "用户名或密码错误！");

    public String errorCode;
    public String errorMessage;

    private loginException(String errorCode, String errorMessage) { this.errorCode = errorCode;
      this.errorMessage = errorMessage;
    }
  }

  public static enum operationException
  {
    userDuplicateException("020001", "用户重复！");

    public String errorCode;
    public String errorMessage;

    private operationException(String errorCode, String errorMessage) { this.errorCode = errorCode;
      this.errorMessage = errorMessage;
    }
  }

  public static enum systemException
  {
    systemException("999999", "系统异常！");

    public String errorCode;
    public String errorMessage;

    private systemException(String errorCode, String errorMessage) { this.errorCode = errorCode;
      this.errorMessage = errorMessage;
    }
  }

  public static enum parameterException
  {
    parameterException("000001", "参数类型异常！"), 
    accessTiketException("000002", "accessTiket校验异常！");

    public String errorCode;
    public String errorMessage;

    private parameterException(String errorCode, String errorMessage) { this.errorCode = errorCode;
      this.errorMessage = errorMessage;
    }
  }

  public static enum responseSuccess
  {
    responseSuccess("000000", "操作成功！");

    public String code;
    public String message;

    private responseSuccess(String code, String message) { this.code = code;
      this.message = message;
    }
  }
}