package com.patrick_crane.web.dto.event;

public class Order {

  private String editionCode;

  private String addonOfferingCode;

  private String pricingDuration;

  private OrderItem orderItem;

  public String getEditionCode() {
    return editionCode;
  }

  public void setEditionCode(String editionCode) {
    this.editionCode = editionCode;
  }

  public String getAddonOfferingCode() {
    return addonOfferingCode;
  }

  public void setAddonOfferingCode(String addonOfferingCode) {
    this.addonOfferingCode = addonOfferingCode;
  }

  public String getPricingDuration() {
    return pricingDuration;
  }

  public void setPricingDuration(String pricingDuration) {
    this.pricingDuration = pricingDuration;
  }

  public OrderItem getOrderItem() {
    return orderItem;
  }

  public void setOrderItem(OrderItem orderItem) {
    this.orderItem = orderItem;
  }

}
