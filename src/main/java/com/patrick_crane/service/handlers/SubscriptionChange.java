package com.patrick_crane.service.handlers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.patrick_crane.domain.entities.Subscription;
import com.patrick_crane.domain.repository.SubscriptionRepository;
import com.patrick_crane.service.AppDirectEventHandler;
import com.patrick_crane.service.utils.SubscriptionPopulator;
import com.patrick_crane.service.validation.Rule;
import com.patrick_crane.service.validation.rules.SubscriptionExists;
import com.patrick_crane.web.dto.event.Event;
import com.patrick_crane.web.dto.response.NotificationResponse;
import com.patrick_crane.web.dto.response.SuccessNotificationResponse;

@Service(SubscriptionChange.BEAN_NAME)
public class SubscriptionChange implements AppDirectEventHandler {

  private static Logger LOGGER = Logger.getLogger(SubscriptionChange.class);

  public static final String BEAN_NAME = "SubscriptionChange";

  @Autowired
  private SubscriptionRepository subscriptionRepository;

	@Override
	@Transactional
	public NotificationResponse handle(Event event) {
    buildValidationRules().executeChain(event);

    String companyUuid = event.getPayload().getAccount().getAccountIdentifier();
    Subscription subscription = subscriptionRepository.findOne(companyUuid);
	  SubscriptionPopulator.populateSubscriptionOrder(subscription, event);
    LOGGER.info("Changing subscription for company with UUID " + companyUuid);
    subscriptionRepository.save(subscription);

	  return new SuccessNotificationResponse();
	}

  private Rule<Event> buildValidationRules() {
    return new SubscriptionExists(subscriptionRepository);
  }

}
