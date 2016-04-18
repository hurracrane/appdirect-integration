package com.patrick_crane.domain.entities.enums;

import java.util.Arrays;
import java.util.List;

public enum SubscriptionState {

  FREE_TRIAL,
  FREE_TRIAL_EXPIRED,
  ACTIVE,
  SUSPENDED,
  CANCELLED;

  public boolean canTransitionTo(SubscriptionState newState) {
    List<SubscriptionState> validTransitionStates;
    switch (this) {
    case FREE_TRIAL:
      validTransitionStates = Arrays.asList(ACTIVE, FREE_TRIAL_EXPIRED, CANCELLED);
      break;
    case FREE_TRIAL_EXPIRED:
      validTransitionStates = Arrays.asList(ACTIVE, CANCELLED);
      break;
    case ACTIVE:
      validTransitionStates = Arrays.asList(SUSPENDED, CANCELLED);
      break;
    case SUSPENDED:
      validTransitionStates = Arrays.asList(ACTIVE, CANCELLED);
      break;
    case CANCELLED:
      validTransitionStates = Arrays.asList();
      break;
    default:
      throw new IllegalArgumentException("Unrecognized subscription state: " + newState);
    }
    return validTransitionStates.contains(newState);
  }

}
