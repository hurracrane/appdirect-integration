package com.patrick_crane.service.validation.rules;

import org.springframework.beans.factory.annotation.Autowired;

import com.patrick_crane.domain.entities.Subscription;
import com.patrick_crane.domain.entities.enums.SubscriptionState;
import com.patrick_crane.domain.repository.SubscriptionRepository;
import com.patrick_crane.service.validation.Rule;
import com.patrick_crane.service.validation.exceptions.EventValidationRuleFailureException;
import com.patrick_crane.web.dto.event.Event;
import com.patrick_crane.web.dto.response.ErrorCode;
import com.patrick_crane.web.dto.response.ErrorNotificationResponse;

public class CanTransitionToState extends Rule<Event> {

  @Autowired
  private SubscriptionRepository subscriptionRepository;

  public CanTransitionToState(SubscriptionRepository subscriptionRepository) {
    this.subscriptionRepository = subscriptionRepository;
  }

  @Override
  protected void validate(Event event) {
    String companyUuid = event.getPayload().getAccount().getAccountIdentifier();
    Subscription subscription = subscriptionRepository.findOne(companyUuid);
    SubscriptionState currentState = subscription.getState();
    SubscriptionState newState = SubscriptionState.valueOf(event.getPayload().getAccount().getStatus());
    if (!currentState.canTransitionTo(newState)) {
      ErrorNotificationResponse notificationResponse =
            new ErrorNotificationResponse(ErrorCode.FORBIDDEN, "Cannot transition to " + newState + " from " + currentState);
      throw new EventValidationRuleFailureException(notificationResponse);
    }
  }

}
