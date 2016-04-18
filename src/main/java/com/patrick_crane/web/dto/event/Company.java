package com.patrick_crane.web.dto.event;

public class Company {

  private String country;

  private String name;

  private String uuid;

  private String website;

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getUuid() {
    return uuid;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  public String getWebsite() {
    return website;
  }

  public void setWebsite(String website) {
    this.website = website;
  }

  @Override
  public String toString() {
    return "Company [country=" + country + ", name=" + name + ", uuid=" + uuid + ", website=" + website + "]";
  }

}
