package com.patrick_crane.service.handlers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.patrick_crane.domain.entities.Subscription;
import com.patrick_crane.domain.entities.SubscriptionUser;
import com.patrick_crane.domain.entities.enums.SubscriptionState;
import com.patrick_crane.domain.repository.SubscriptionRepository;
import com.patrick_crane.domain.repository.SubscriptionUserRepository;
import com.patrick_crane.service.AppDirectEventHandler;
import com.patrick_crane.service.utils.SubscriptionPopulator;
import com.patrick_crane.service.utils.SubscriptionUserPopulator;
import com.patrick_crane.service.validation.Rule;
import com.patrick_crane.service.validation.rules.SubscriptionDoesNotExist;
import com.patrick_crane.web.dto.event.Event;
import com.patrick_crane.web.dto.response.NotificationResponse;
import com.patrick_crane.web.dto.response.SuccessNotificationResponse;

@Service(SubscriptionCreate.BEAN_NAME)
public class SubscriptionCreate implements AppDirectEventHandler {

  private static Logger LOGGER = Logger.getLogger(SubscriptionCreate.class);

  public static final String BEAN_NAME = "SubscriptionCreate";

  @Autowired
  private SubscriptionRepository subscriptionRepository;

  @Autowired
  private SubscriptionUserRepository userRespository;

  @Override
  @Transactional
  public NotificationResponse handle(Event event) {
    buildValidationRules().executeChain(event);

    Subscription subscription = new Subscription();
    SubscriptionPopulator.populateNewSubcription(subscription, event);
    subscription.setState(SubscriptionState.ACTIVE);
    LOGGER.info("Creating subscription for company with UUID " + subscription.getCompanyUuid());
    subscriptionRepository.save(subscription);

    SubscriptionUser creator = new SubscriptionUser();
    SubscriptionUserPopulator.populateSubscriptionAdministrator(creator, event);
    creator.setSubscription(subscription);
    LOGGER.info("Creating administrator user with UUID " + creator.getUuid() + " for company with UUID " + subscription.getCompanyUuid());
    userRespository.save(creator);

    return new SuccessNotificationResponse(subscription.getCompanyUuid());
  }

  private Rule<Event> buildValidationRules() {
    return new SubscriptionDoesNotExist(subscriptionRepository);
  }

}
