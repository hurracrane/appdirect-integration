package com.patrick_crane.service.validation.rules;

import com.patrick_crane.domain.entities.Subscription;
import com.patrick_crane.domain.repository.SubscriptionRepository;
import com.patrick_crane.service.validation.Rule;
import com.patrick_crane.service.validation.exceptions.EventValidationRuleFailureException;
import com.patrick_crane.web.dto.event.Event;
import com.patrick_crane.web.dto.response.ErrorCode;
import com.patrick_crane.web.dto.response.ErrorNotificationResponse;

/**
 *  Check that another unit can be added to subscription
 *  Subscription must exist
 */
public class BelowUserLimit extends Rule<Event> {

  private SubscriptionRepository subscriptionRepository;

  public BelowUserLimit(SubscriptionRepository subscriptionRepository) {
    this.subscriptionRepository = subscriptionRepository;
  }

  @Override
  protected void validate(Event event) {
    String companyUuid = event.getPayload().getAccount().getAccountIdentifier();
    Subscription subscription = subscriptionRepository.findOne(companyUuid);
    int numberOfUsers = subscription.getSubscriptionUsers().size();
    int maxNumberOfUnits = subscription.getMaxOrderItems();
    if (numberOfUsers + 1 > maxNumberOfUnits) {
      ErrorNotificationResponse notificationResponse =
            new ErrorNotificationResponse(ErrorCode.MAX_USERS_REACHED, "Cannot add more than " + maxNumberOfUnits + " users");
      throw new EventValidationRuleFailureException(notificationResponse);
    }
  }

}
