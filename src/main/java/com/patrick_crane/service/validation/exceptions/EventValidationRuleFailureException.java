package com.patrick_crane.service.validation.exceptions;

import com.patrick_crane.service.validation.ValidationRuleFailureException;
import com.patrick_crane.web.dto.response.ErrorNotificationResponse;

/**
 *  Exception for failed AppDirect Event validation
 */
public class EventValidationRuleFailureException extends ValidationRuleFailureException {

  private static final long serialVersionUID = -3270898198176306312L;

  private ErrorNotificationResponse errorNotificationResponse;

  public EventValidationRuleFailureException(ErrorNotificationResponse errorNotificationResponse) {
    super(errorNotificationResponse.getMessage());
    this.errorNotificationResponse = errorNotificationResponse;
  }

  public ErrorNotificationResponse getErrorNotificationResponse() {
    return this.errorNotificationResponse;
  }

}
