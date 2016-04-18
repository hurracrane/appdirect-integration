package com.patrick_crane.service.validation.rules;

import com.patrick_crane.domain.entities.SubscriptionUser;
import com.patrick_crane.domain.repository.SubscriptionUserRepository;
import com.patrick_crane.service.validation.Rule;
import com.patrick_crane.service.validation.exceptions.EventValidationRuleFailureException;
import com.patrick_crane.web.dto.event.Event;
import com.patrick_crane.web.dto.response.ErrorCode;
import com.patrick_crane.web.dto.response.ErrorNotificationResponse;

/**
 *  Check that user does not exist under company
 */
public class SubscriptionUserDoesNotExist extends Rule<Event> {

  private SubscriptionUserRepository userRepository;

  public SubscriptionUserDoesNotExist(SubscriptionUserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public void validate(Event event) {
    String userUuid = event.getPayload().getUser().getUuid();
    String companyUuid = event.getPayload().getAccount().getAccountIdentifier();
    SubscriptionUser existingUser = userRepository.findByUuidAndSubscription_companyUuid(userUuid, companyUuid);
    if (existingUser != null) {
      ErrorNotificationResponse notificationResponse =
            new ErrorNotificationResponse(ErrorCode.USER_ALREADY_EXISTS, "User with uuid + " + userUuid + " under company with company UUID " + companyUuid + " already exists");
      throw new EventValidationRuleFailureException(notificationResponse);
    }
  }

}
