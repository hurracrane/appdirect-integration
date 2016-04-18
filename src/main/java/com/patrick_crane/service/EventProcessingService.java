package com.patrick_crane.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.patrick_crane.clients.EventClient;
import com.patrick_crane.web.dto.event.Event;
import com.patrick_crane.web.dto.response.NotificationResponse;

@Service
public class EventProcessingService {

  @Autowired
  private EventClient appDirectEventClient;

  @Autowired
  private AppDirectEventHandlerFactory appDirectEventHandlerFactory;

  /**
   * Process the event URL from an AppDirect notification
   * @param eventURL
   * @return success or error notification response
   */
  public NotificationResponse processEventUrl(String eventURL) {
    Event event = appDirectEventClient.fetchEvent(eventURL);

    AppDirectEventHandler handler = appDirectEventHandlerFactory.getEventHandler(event.getType());

    return handler.handle(event);
  }

}
