package com.patrick_crane.web.dto.event;

public class Payload {

  private Account account;

  private User user;

  private Company company;

  private Order order;

  private AddonInstance addonInstance;

  private Notice notice;

  public Account getAccount() {
    return account;
  }

  public void setAccount(Account account) {
    this.account = account;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Company getCompany() {
    return company;
  }

  public void setCompany(Company company) {
    this.company = company;
  }

  public Order getOrder() {
    return order;
  }

  public void setOrder(Order order) {
    this.order = order;
  }

  public AddonInstance getAddonInstance() {
    return addonInstance;
  }

  public void setAddonInstance(AddonInstance addonInstance) {
    this.addonInstance = addonInstance;
  }

  public Notice getNotice() {
    return notice;
  }

  public void setNotice(Notice notice) {
    this.notice = notice;
  }

  @Override
  public String toString() {
    return "Payload [user=" + user + ", company=" + company + ", order=" + order + ", addonInstance=" + addonInstance + "]";
  }

}
