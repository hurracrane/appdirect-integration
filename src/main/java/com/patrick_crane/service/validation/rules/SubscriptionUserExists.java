package com.patrick_crane.service.validation.rules;

import com.patrick_crane.domain.entities.SubscriptionUser;
import com.patrick_crane.domain.repository.SubscriptionUserRepository;
import com.patrick_crane.service.validation.Rule;
import com.patrick_crane.service.validation.exceptions.EventValidationRuleFailureException;
import com.patrick_crane.web.dto.event.Event;
import com.patrick_crane.web.dto.response.ErrorCode;
import com.patrick_crane.web.dto.response.ErrorNotificationResponse;

/**
 * Check that user exists under company
 */
public class SubscriptionUserExists extends Rule<Event> {

  private SubscriptionUserRepository userRepository;

  public SubscriptionUserExists(SubscriptionUserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public void validate(Event event) {
    String userUuid = event.getPayload().getUser().getUuid();
    String companyUuid = event.getPayload().getAccount().getAccountIdentifier();
    SubscriptionUser existingUser = userRepository.findByUuidAndSubscription_companyUuid(userUuid, companyUuid);
    if (existingUser == null) {
      ErrorNotificationResponse notificationResponse =
            new ErrorNotificationResponse(ErrorCode.USER_NOT_FOUND, "User with uuid " + userUuid + " under company with company UUID " + companyUuid + " cannot be found");
      throw new EventValidationRuleFailureException(notificationResponse);
    }
  }

}
