package com.patrick_crane.service.handlers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.patrick_crane.domain.entities.Subscription;
import com.patrick_crane.domain.entities.enums.SubscriptionState;
import com.patrick_crane.domain.repository.SubscriptionRepository;
import com.patrick_crane.service.AppDirectEventHandler;
import com.patrick_crane.service.validation.Rule;
import com.patrick_crane.service.validation.rules.CanTransitionToState;
import com.patrick_crane.service.validation.rules.SubscriptionExists;
import com.patrick_crane.web.dto.event.Event;
import com.patrick_crane.web.dto.event.Notice;
import com.patrick_crane.web.dto.event.NoticeType;
import com.patrick_crane.web.dto.response.NotificationResponse;
import com.patrick_crane.web.dto.response.SuccessNotificationResponse;

@Service(SubscriptionNotice.BEAN_NAME)
public class SubscriptionNotice implements AppDirectEventHandler {

  private static Logger LOGGER = Logger.getLogger(SubscriptionNotice.class);

  public static final String BEAN_NAME = "SubscriptionNotice";

  @Autowired
  private SubscriptionRepository subscriptionRepository;

	@Override
	@Transactional
	public NotificationResponse handle(Event event) {
    buildValidationRules().executeChain(event);

    Notice notice = event.getPayload().getNotice();
    String companyUuid = event.getPayload().getAccount().getAccountIdentifier();
    Subscription subscription = subscriptionRepository.findOne(companyUuid);
    if (notice.getType() == NoticeType.REACTIVATED || notice.getType() == NoticeType.DEACTIVATED) {
      SubscriptionState newState = SubscriptionState.valueOf(event.getPayload().getAccount().getStatus());
      subscription.setState(newState);
      LOGGER.info("Updating state to " + newState + " for company with UUID " + companyUuid);
      subscriptionRepository.save(subscription);
    } else if (notice.getType() == NoticeType.CLOSED) {
      LOGGER.info("Deleting subscription for company with UUID " + companyUuid);
      subscriptionRepository.delete(companyUuid);
    }

    return new SuccessNotificationResponse();
	}

  private Rule<Event> buildValidationRules() {
    return new SubscriptionExists(subscriptionRepository)
          .andThenWith(new CanTransitionToState(subscriptionRepository));
  }

}
