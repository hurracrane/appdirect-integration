package com.patrick_crane.web.dto.event;

import java.util.Map;

public class User extends AbstractUser {

  private Map<String, String> attributes;

  public Map<String, String> getAttributes() {
    return attributes;
  }

  public void setAttributes(Map<String, String> attributes) {
    this.attributes = attributes;
  }

  @Override
  public String toString() {
    return "User [attributes=" + attributes + "]";
  }

}
