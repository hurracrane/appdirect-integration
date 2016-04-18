package com.patrick_crane.service.handlers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.patrick_crane.domain.entities.Subscription;
import com.patrick_crane.domain.entities.SubscriptionUser;
import com.patrick_crane.domain.repository.SubscriptionRepository;
import com.patrick_crane.domain.repository.SubscriptionUserRepository;
import com.patrick_crane.service.AppDirectEventHandler;
import com.patrick_crane.service.utils.SubscriptionUserPopulator;
import com.patrick_crane.service.validation.Rule;
import com.patrick_crane.service.validation.rules.BelowUserLimit;
import com.patrick_crane.service.validation.rules.SubscriptionExists;
import com.patrick_crane.service.validation.rules.SubscriptionUserDoesNotExist;
import com.patrick_crane.web.dto.event.Event;
import com.patrick_crane.web.dto.response.NotificationResponse;
import com.patrick_crane.web.dto.response.SuccessNotificationResponse;

@Service(SubscriptionUserAssignment.BEAN_NAME)
public class SubscriptionUserAssignment implements AppDirectEventHandler {

  private static Logger LOGGER = Logger.getLogger(SubscriptionUserAssignment.class);

  public static final String BEAN_NAME = "UserAssigment";

  @Autowired
  private SubscriptionRepository subscriptionRepository;

  @Autowired
  private SubscriptionUserRepository subscriptionUserRepository;

  @Override
  public NotificationResponse handle(Event event) {
    buildValidationRules().executeChain(event);

    String companyUuid = event.getPayload().getAccount().getAccountIdentifier();
    Subscription subscription = subscriptionRepository.findOne(companyUuid);

    SubscriptionUser newSubscriptionUser = new SubscriptionUser();
    SubscriptionUserPopulator.populateSubscriptionUser(newSubscriptionUser, event);
    newSubscriptionUser.setSubscription(subscription);
    LOGGER.info("Assigning user with UUID " + newSubscriptionUser.getUuid() + " to subsription for company with UUID " + companyUuid);
    subscriptionUserRepository.save(newSubscriptionUser);

    return new SuccessNotificationResponse();
  }

  private Rule<Event> buildValidationRules() {
    Rule<Event> ruleChain = new SubscriptionExists(subscriptionRepository)
          .andThenWith(new BelowUserLimit(subscriptionRepository))
          .andThenWith(new SubscriptionUserDoesNotExist(subscriptionUserRepository));
    return ruleChain;
  }

}
