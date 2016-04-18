package com.patrick_crane.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.patrick_crane.service.handlers.SubscriptionCancel;
import com.patrick_crane.service.handlers.SubscriptionChange;
import com.patrick_crane.service.handlers.SubscriptionCreate;
import com.patrick_crane.service.handlers.SubscriptionNotice;
import com.patrick_crane.service.handlers.SubscriptionUserAssignment;
import com.patrick_crane.service.handlers.SubscriptionUserUnassignment;
import com.patrick_crane.service.handlers.SubscriptionUserUpdated;
import com.patrick_crane.web.dto.event.EventType;

@Service
public class AppDirectEventHandlerFactory {

  @Autowired
  @Qualifier(SubscriptionCreate.BEAN_NAME)
  private AppDirectEventHandler subscriptionCreate;
  @Autowired
  @Qualifier(SubscriptionChange.BEAN_NAME)
  private AppDirectEventHandler subscriptionChange;
  @Autowired
  @Qualifier(SubscriptionCancel.BEAN_NAME)
  private AppDirectEventHandler subscriptionCancel;
  @Autowired
  @Qualifier(SubscriptionNotice.BEAN_NAME)
  private AppDirectEventHandler subscriptionNotice;
  @Autowired
  @Qualifier(SubscriptionUserAssignment.BEAN_NAME)
  private AppDirectEventHandler userAssignment;
  @Autowired
  @Qualifier(SubscriptionUserUnassignment.BEAN_NAME)
  private AppDirectEventHandler userUnassignment;
  @Autowired
  @Qualifier(SubscriptionUserUpdated.BEAN_NAME)
  private AppDirectEventHandler userUpdated;

  /**
   * Get the appropriate event handler given the event type
   * @param type event type
   * @return event handler for the event type
   */
  public AppDirectEventHandler getEventHandler(EventType type) {
    switch (type) {
    case SUBSCRIPTION_ORDER:
      return subscriptionCreate;
    case SUBSCRIPTION_CHANGE:
      return subscriptionChange;
    case SUBSCRIPTION_CANCEL:
      return subscriptionCancel;
    case SUBSCRIPTION_NOTICE:
      return subscriptionNotice;
    case USER_ASSIGNMENT:
      return userAssignment;
    case USER_UNASSIGNMENT:
      return userUnassignment;
    case USER_UPDATED:
      return userUpdated;
    case ADDON_ORDER:
      throw new UnsupportedOperationException("Order addon not configured");
    case ADDON_CANCEL:
      throw new UnsupportedOperationException("Order addon cancel not configured");
    default:
      throw new IllegalArgumentException("Unrecognized event type: " + type);
    }
  }

}
