package com.patrick_crane.web.dto.event;

public class Event {

  private EventType type;

  private Marketplace marketplace;

  private String applicationUuid;

  private Flag flag;

  private String returnUrl;

  private Creator creator;

  private Payload payload;

  public EventType getType() {
    return type;
  }

  public void setEventType(EventType type) {
    this.type = type;
  }

  public Marketplace getMarketplace() {
    return marketplace;
  }

  public void setMarketplace(Marketplace marketplace) {
    this.marketplace = marketplace;
  }

  public String getApplicationUuid() {
    return applicationUuid;
  }

  public void setApplicationUuid(String applicationUuid) {
    this.applicationUuid = applicationUuid;
  }

  public Flag getFlag() {
    return flag;
  }

  public void setFlag(Flag flag) {
    this.flag = flag;
  }

  public String getReturnUrl() {
    return returnUrl;
  }

  public void setReturnUrl(String returnUrl) {
    this.returnUrl = returnUrl;
  }

  public Creator getCreator() {
    return creator;
  }

  public void setCreator(Creator creator) {
    this.creator = creator;
  }

  public Payload getPayload() {
    return payload;
  }

  public void setPayload(Payload payload) {
    this.payload = payload;
  }

}
