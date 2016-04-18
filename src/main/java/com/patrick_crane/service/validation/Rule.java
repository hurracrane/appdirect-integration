package com.patrick_crane.service.validation;

/**
 * Validation rule to be executed on a given type
 *
 * @param <T> type of entity to be validated
 */
public abstract class Rule<T> {

  private Rule<T> nextRule = null;

  /**
   * Add the following rule to be validated against entity
   * @param nextRule the following rule
   * @return the following rule
   */
  public Rule<T> andThenWith(Rule<T> nextRule) {
    this.nextRule = nextRule;
    return nextRule;
  }

  /**
   * Execute validation rules against given entity
   * @param entity entity to be validated
   */
  public void executeChain(T entity) {
    validate(entity);
    if (nextRule != null) {
      nextRule.executeChain(entity);
    }
  }

  /**
   * Validate entity against its rule
   * @param entity entity to be validated
   */
  protected abstract void validate(T entity);

}
