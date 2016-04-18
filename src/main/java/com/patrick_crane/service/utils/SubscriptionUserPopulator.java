package com.patrick_crane.service.utils;

import com.patrick_crane.domain.entities.SubscriptionUser;
import com.patrick_crane.web.dto.event.AbstractUser;
import com.patrick_crane.web.dto.event.Creator;
import com.patrick_crane.web.dto.event.Event;
import com.patrick_crane.web.dto.event.User;

public final class SubscriptionUserPopulator {

  private SubscriptionUserPopulator() {
    // static class
  }

  public static void populateSubscriptionAdministrator(SubscriptionUser subscriptionUser, Event event) {
    Creator creator = event.getCreator();
    populateSubscriptionUser(subscriptionUser, creator);
    subscriptionUser.setSubscriptionAdministrator(true);
  }

  public static void populateSubscriptionUser(SubscriptionUser subscriptionUser, Event event) {
    User user = event.getPayload().getUser();
    populateSubscriptionUser(subscriptionUser, user);
  }

  private static void populateSubscriptionUser(SubscriptionUser subscriptionUser, AbstractUser abstractUser) {
    subscriptionUser.setFirstName(abstractUser.getFirstName());
    subscriptionUser.setLastName(abstractUser.getLastName());
    subscriptionUser.setLanguage(abstractUser.getLanguage());
    subscriptionUser.setEmail(abstractUser.getEmail());
    subscriptionUser.setUuid(abstractUser.getUuid());
  }

}
