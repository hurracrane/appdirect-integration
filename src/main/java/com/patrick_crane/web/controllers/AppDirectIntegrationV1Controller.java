package com.patrick_crane.web.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.patrick_crane.service.EventProcessingService;
import com.patrick_crane.service.validation.exceptions.EventValidationRuleFailureException;
import com.patrick_crane.web.dto.response.ErrorCode;
import com.patrick_crane.web.dto.response.ErrorNotificationResponse;
import com.patrick_crane.web.dto.response.NotificationResponse;

@Controller
@RequestMapping(path = AppDirectIntegrationV1Controller.APPDIRECT_INTEGRATION_PATH)
public class AppDirectIntegrationV1Controller {

  public static final String APPDIRECT_INTEGRATION_PATH = "/api/v1/integration";

  private static Logger LOGGER = Logger.getLogger(AppDirectIntegrationV1Controller.class);

  @Autowired
  private EventProcessingService eventProcessingService;

  @RequestMapping(method = RequestMethod.GET)
  public ResponseEntity<NotificationResponse> processNotification(@RequestParam("event") String eventURL) {
    NotificationResponse notificationResponse;

    try {
      notificationResponse = eventProcessingService.processEventUrl(eventURL);
    } catch (UnsupportedOperationException e) {
      LOGGER.warn("Event notification receveived for unsupported operation", e);
      notificationResponse = new ErrorNotificationResponse(ErrorCode.CONFIGURATION_ERROR, e.getMessage());
    } catch (EventValidationRuleFailureException e) {
      LOGGER.warn("Event notification has not passed the validation rules", e);
      notificationResponse = e.getErrorNotificationResponse();
    } catch (Exception e) {
      LOGGER.error("Unable to handle event notification", e);
      notificationResponse = new ErrorNotificationResponse(ErrorCode.UNKNOWN_ERROR, "Notification could not be processed");
    }

    return new ResponseEntity<NotificationResponse>(notificationResponse, HttpStatus.OK);
  }

}
