package com.patrick_crane.service.validation.rules;

import com.patrick_crane.domain.entities.Subscription;
import com.patrick_crane.domain.repository.SubscriptionRepository;
import com.patrick_crane.service.validation.Rule;
import com.patrick_crane.service.validation.exceptions.EventValidationRuleFailureException;
import com.patrick_crane.web.dto.event.Event;
import com.patrick_crane.web.dto.response.ErrorCode;
import com.patrick_crane.web.dto.response.ErrorNotificationResponse;

/**
 *  Check that subscription does not exist
 */
public class SubscriptionDoesNotExist extends Rule<Event> {

  private SubscriptionRepository subscriptionRepository;

  public SubscriptionDoesNotExist(SubscriptionRepository subscriptionRepository) {
    this.subscriptionRepository = subscriptionRepository;
  }

  @Override
  public void validate(Event event) {
    String companyUuid;
    // company either in account or company DTO depending on state
    if (event.getPayload().getAccount() != null) {
      companyUuid = event.getPayload().getAccount().getAccountIdentifier();
    } else {
      companyUuid = event.getPayload().getCompany().getUuid();
    }
    Subscription subscription = subscriptionRepository.findOne(companyUuid);
    if (subscription != null) {
      ErrorNotificationResponse notificationResponse =
            new ErrorNotificationResponse(ErrorCode.FORBIDDEN, "Subscription account with company UUID " + companyUuid + " already exists");
      throw new EventValidationRuleFailureException(notificationResponse);
    }
  }

}
