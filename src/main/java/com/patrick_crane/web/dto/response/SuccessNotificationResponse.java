package com.patrick_crane.web.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class SuccessNotificationResponse extends NotificationResponse {

	private String accountIdentifier;

	public SuccessNotificationResponse() {
		super(true);
	}

	public SuccessNotificationResponse(String accountIdentifier) {
		super(true);
		this.accountIdentifier = accountIdentifier;
	}

	public String getAccountIdentifier() {
		return accountIdentifier;
	}

	public void setAccountIdentifier(String accountIdentifier) {
		this.accountIdentifier = accountIdentifier;
	}

}
