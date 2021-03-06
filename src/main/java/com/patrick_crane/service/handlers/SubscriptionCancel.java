package com.patrick_crane.service.handlers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.patrick_crane.domain.repository.SubscriptionRepository;
import com.patrick_crane.service.AppDirectEventHandler;
import com.patrick_crane.service.validation.Rule;
import com.patrick_crane.service.validation.rules.SubscriptionExists;
import com.patrick_crane.web.dto.event.Event;
import com.patrick_crane.web.dto.response.NotificationResponse;
import com.patrick_crane.web.dto.response.SuccessNotificationResponse;

@Service(SubscriptionCancel.BEAN_NAME)
public class SubscriptionCancel implements AppDirectEventHandler {

  private static Logger LOGGER = Logger.getLogger(SubscriptionCancel.class);

  public static final String BEAN_NAME = "SubscriptionCancel";

  @Autowired
  private SubscriptionRepository subscriptionRepository;

  @Override
  @Transactional
  public NotificationResponse handle(Event event) {
    buildValidationRules().executeChain(event);

    String companyUuid = event.getPayload().getAccount().getAccountIdentifier();
    LOGGER.info("Deleting subscription for company with UUID " + companyUuid);
    subscriptionRepository.delete(companyUuid);

    return new SuccessNotificationResponse();
  }

  private Rule<Event> buildValidationRules() {
    Rule<Event> ruleChain = new SubscriptionExists(subscriptionRepository);
    return ruleChain;
  }

}
