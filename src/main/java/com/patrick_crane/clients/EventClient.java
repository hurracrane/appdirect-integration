package com.patrick_crane.clients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.patrick_crane.config.AppDirectConfig;
import com.patrick_crane.web.dto.event.Event;
import com.sun.jersey.oauth.signature.HMAC_SHA1;

@Component
public class EventClient {

  @Autowired
  private AppDirectConfig appDirectConfig;

  private RestTemplate restTemplate = null;

  public Event fetchEvent(String eventUrl) {
    return getRestTemplate().getForObject(eventUrl, Event.class);
  }

  private RestTemplate getRestTemplate() {
    if(restTemplate == null) {
      restTemplate = new RestTemplate();
      restTemplate.getInterceptors()
            .add(new OAuthRequestSigningInterceptor(
                  appDirectConfig.getConsumerKey(),
                  appDirectConfig.getConsumerSecret(),
                  new HMAC_SHA1()));
    }
    return restTemplate;
  }

}
