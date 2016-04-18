package com.patrick_crane.domain.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.patrick_crane.domain.entities.enums.OrderItemUnit;
import com.patrick_crane.domain.entities.enums.SubscriptionState;

@Entity
@Table(name = "pc_subscription")
public class Subscription {

  @Id
  @Column(name = "company_uuid", columnDefinition = "CHAR(36)")
  private String companyUuid;

  @Column(name = "company_name")
  private String companyName;

  @Column(name = "company_country")
  private String companyCountry;

  @Column(name = "company_phone_number")
  private String companyPhoneNumber;

  @Column(name = "company_website", columnDefinition = "VARCHAR(2000)")
  private String companyWebsite;

  @Column(name = "marketplace_url", columnDefinition = "VARCHAR(2000)")
  private String marketplaceUrl;

  @Column(name = "marketplace_partner")
  private String marketplacePartner;

  @Column(name = "edition_code")
  private String editionCode;

  @Column(name = "pricing_duration")
  private String pricingDuration;

  @Column(name = "max_orders_items")
  private Integer maxOrderItems;

  @Column(name = "order_item_unit")
  private OrderItemUnit orderUnit;

  @Column(name = "state")
  private SubscriptionState state;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "subscription")
  private Set<SubscriptionUser> subscriptionUsers = new HashSet<>();

  public Integer getMaxOrderItems() {
    return maxOrderItems;
  }

  public void setMaxOrderItems(Integer maxOrderItems) {
    this.maxOrderItems = maxOrderItems;
  }

  public OrderItemUnit getOrderUnit() {
    return orderUnit;
  }

  public void setOrderUnit(OrderItemUnit orderUnit) {
    this.orderUnit = orderUnit;
  }

  public String getCompanyUuid() {
    return companyUuid;
  }

  public void setCompanyUuid(String companyUuid) {
    this.companyUuid = companyUuid;
  }

  public String getCompanyName() {
    return companyName;
  }

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }

  public String getCompanyCountry() {
    return companyCountry;
  }

  public void setCompanyCountry(String companyCountry) {
    this.companyCountry = companyCountry;
  }

  public String getCompanyPhoneNumber() {
    return companyPhoneNumber;
  }

  public void setCompanyPhoneNumber(String companyPhoneNumber) {
    this.companyPhoneNumber = companyPhoneNumber;
  }

  public String getCompanyWebsite() {
    return companyWebsite;
  }

  public void setCompanyWebsite(String companyWebsite) {
    this.companyWebsite = companyWebsite;
  }

  public String getMarketplaceUrl() {
    return marketplaceUrl;
  }

  public void setMarketplaceUrl(String marketplaceUrl) {
    this.marketplaceUrl = marketplaceUrl;
  }

  public String getMarketplacePartner() {
    return marketplacePartner;
  }

  public void setMarketplacePartner(String marketplacePartner) {
    this.marketplacePartner = marketplacePartner;
  }

  public Set<SubscriptionUser> getSubscriptionUsers() {
    return subscriptionUsers;
  }

  public void setSubscriptionUsers(Set<SubscriptionUser> subscriptionUsers) {
    this.subscriptionUsers = subscriptionUsers;
  }

  public String getEditionCode() {
    return editionCode;
  }

  public void setEditionCode(String editionCode) {
    this.editionCode = editionCode;
  }

  public String getPricingDuration() {
    return pricingDuration;
  }

  public void setPricingDuration(String pricingDuration) {
    this.pricingDuration = pricingDuration;
  }

  public SubscriptionState getState() {
    return state;
  }

  public void setState(SubscriptionState state) {
    this.state = state;
  }


}
