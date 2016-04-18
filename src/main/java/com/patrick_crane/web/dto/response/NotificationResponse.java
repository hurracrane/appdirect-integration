package com.patrick_crane.web.dto.response;

public abstract class NotificationResponse {

  private Boolean success;

  public NotificationResponse(Boolean success) {
    this.success = success;
  }

  public Boolean getSuccess() {
    return success;
  }

  public void setSuccess(Boolean success) {
    this.success = success;
  }

}
