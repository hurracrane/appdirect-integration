package com.patrick_crane.service.validation;

/**
 *  Exception to throw for failed validation rules
 */
public abstract class ValidationRuleFailureException extends RuntimeException {

  private static final long serialVersionUID = -4631453222210224460L;

  public ValidationRuleFailureException(String message) {
    super(message);
  }

}
