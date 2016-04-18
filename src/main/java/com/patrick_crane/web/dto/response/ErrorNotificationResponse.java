package com.patrick_crane.web.dto.response;

public class ErrorNotificationResponse extends NotificationResponse {

  private ErrorCode errorCode;

  private String message;

  public ErrorNotificationResponse(ErrorCode errorCode, String message) {
    super(false);
    this.errorCode = errorCode;
    this.message = message;
  }

  public ErrorCode getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(ErrorCode errorCode) {
    this.errorCode = errorCode;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

}
