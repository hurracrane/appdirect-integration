package com.patrick_crane.service;

import com.patrick_crane.web.dto.event.Event;
import com.patrick_crane.web.dto.response.NotificationResponse;

public interface AppDirectEventHandler {

  /**
   * Process incoming AppDirect notification event
   * @param event notification event to process
   * @return response success or error notification responsesw
   */
  NotificationResponse handle(Event event);

}
