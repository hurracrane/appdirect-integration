package com.patrick_crane.service.validation.rules;

import com.patrick_crane.domain.entities.Subscription;
import com.patrick_crane.domain.repository.SubscriptionRepository;
import com.patrick_crane.service.validation.Rule;
import com.patrick_crane.service.validation.exceptions.EventValidationRuleFailureException;
import com.patrick_crane.web.dto.event.Event;
import com.patrick_crane.web.dto.response.ErrorCode;
import com.patrick_crane.web.dto.response.ErrorNotificationResponse;

/**
 *  Check that subscription exists
 */
public class SubscriptionExists extends Rule<Event> {

  private SubscriptionRepository subscriptionRepository;

  public SubscriptionExists(SubscriptionRepository subscriptionRepository) {
    this.subscriptionRepository = subscriptionRepository;
  }

  @Override
  public void validate(Event event) {
    String companyUuid = event.getPayload().getAccount().getAccountIdentifier();
    Subscription subscription = subscriptionRepository.findOne(companyUuid);
    if (subscription == null) {
      ErrorNotificationResponse notificationResponse =
            new ErrorNotificationResponse(ErrorCode.ACCOUNT_NOT_FOUND, "Cannot find subscription account with company UUID " + companyUuid);
      throw new EventValidationRuleFailureException(notificationResponse);
    }
  }

}
