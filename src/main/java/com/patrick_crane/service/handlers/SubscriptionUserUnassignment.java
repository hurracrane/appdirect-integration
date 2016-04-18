package com.patrick_crane.service.handlers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.patrick_crane.domain.entities.SubscriptionUser;
import com.patrick_crane.domain.repository.SubscriptionRepository;
import com.patrick_crane.domain.repository.SubscriptionUserRepository;
import com.patrick_crane.service.AppDirectEventHandler;
import com.patrick_crane.service.validation.Rule;
import com.patrick_crane.service.validation.rules.SubscriptionExists;
import com.patrick_crane.service.validation.rules.SubscriptionUserExists;
import com.patrick_crane.web.dto.event.Event;
import com.patrick_crane.web.dto.response.NotificationResponse;
import com.patrick_crane.web.dto.response.SuccessNotificationResponse;

@Service(SubscriptionUserUnassignment.BEAN_NAME)
public class SubscriptionUserUnassignment implements AppDirectEventHandler {

  private static Logger LOGGER = Logger.getLogger(SubscriptionUserUnassignment.class);

  public static final String BEAN_NAME = "UserUnassignment";

  @Autowired
  private SubscriptionRepository subscriptionRepository;

  @Autowired
  private SubscriptionUserRepository subscriptionUserRepository;

  @Override
  @Transactional
  public NotificationResponse handle(Event event) {
    buildValidationRules().executeChain(event);

    String userUuid = event.getPayload().getUser().getUuid();
    String companyUuid = event.getPayload().getAccount().getAccountIdentifier();

    SubscriptionUser subscriptionUser = subscriptionUserRepository.findByUuidAndSubscription_companyUuid(userUuid, companyUuid);
    LOGGER.info("Removing user with UUID " + userUuid + " from subscription for company with UUID " + companyUuid);
    subscriptionUserRepository.delete(subscriptionUser);

    return new SuccessNotificationResponse();
  }

  private Rule<Event> buildValidationRules() {
    return new SubscriptionExists(subscriptionRepository)
          .andThenWith(new SubscriptionUserExists(subscriptionUserRepository));
  }

}
