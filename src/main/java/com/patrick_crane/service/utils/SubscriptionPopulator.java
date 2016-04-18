package com.patrick_crane.service.utils;

import com.patrick_crane.domain.entities.Subscription;
import com.patrick_crane.domain.entities.enums.OrderItemUnit;
import com.patrick_crane.web.dto.event.Company;
import com.patrick_crane.web.dto.event.Event;
import com.patrick_crane.web.dto.event.Marketplace;
import com.patrick_crane.web.dto.event.Order;
import com.patrick_crane.web.dto.event.OrderItem;

public final class SubscriptionPopulator {

  private SubscriptionPopulator() {
    // static class
  }

  public static void populateNewSubcription(Subscription subscription, Event event) {
    populateCompany(subscription, event);
    populateMarketplace(subscription, event);
    populateSubscriptionOrder(subscription, event);
  }

  public static void populateCompany(Subscription subscription, Event event) {
    Company company = event.getPayload().getCompany();
    subscription.setCompanyUuid(company.getUuid());
    subscription.setCompanyName(company.getName());
    subscription.setCompanyCountry(company.getCountry());
    subscription.setCompanyWebsite(company.getWebsite());
  }

  public static void populateMarketplace(Subscription subscription, Event event) {
    Marketplace marketplace = event.getMarketplace();
    subscription.setMarketplaceUrl(marketplace.getBaseUrl());
    subscription.setMarketplacePartner(marketplace.getPartner());
  }

  public static void populateSubscriptionOrder(Subscription subscription, Event event) {
    Order order = event.getPayload().getOrder();
    subscription.setEditionCode(order.getEditionCode());
    subscription.setPricingDuration(order.getPricingDuration());

    OrderItem orderItem = order.getOrderItem();
    if (orderItem != null) {
      subscription.setMaxOrderItems(orderItem.getQuantity());
      subscription.setOrderUnit(OrderItemUnit.valueOf(orderItem.getOrderItemUnit()));
    }
  }

}
