package com.patrick_crane.clients;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.http.HttpRequest;

import com.sun.jersey.oauth.signature.OAuthRequest;

public class OAuthHttpRequestAdapter implements OAuthRequest {

  private HttpRequest httpRequest;

  public OAuthHttpRequestAdapter(HttpRequest httpRequest) {
    this.httpRequest = httpRequest;
  }

  @Override
  public String getRequestMethod() {
    return httpRequest.getMethod().name();
  }

  @Override
  public URL getRequestURL() {
    URL url;

    try {
      url = httpRequest.getURI().toURL();
    } catch (MalformedURLException e) {
      throw new RuntimeException("Cannot parse url");
    }

    return url;
  }

  @Override
  public Set<String> getParameterNames() {
    return parseQueryParameters(httpRequest.getURI().getQuery()).keySet();
  }

  @Override
  public List<String> getParameterValues(String name) {
    return parseQueryParameters(httpRequest.getURI().getQuery()).get(name);
  }

  @Override
  public List<String> getHeaderValues(String name) {
    return httpRequest.getHeaders().get(name);
  }

  @Override
  public void addHeaderValue(String name, String value) throws IllegalStateException {
    httpRequest.getHeaders().add(name, value);
  }

  /**
   * Parse query string and return as a map
   * @param queryString
   * @return
   */
  private Map<String, List<String>> parseQueryParameters(String queryString) {
    Map<String, List<String>> map = new HashMap<String, List<String>>();
    if (queryString != null) {
      String[] keyValue;
      String[] params = queryString.split("&");
      for (String param : params) {
        keyValue = param.split("=");
        String name = keyValue[0];
        String value = keyValue[1];
        if (map.get(name) == null) {
          map.put(name, new ArrayList<String>());
        }
        map.get(name).add(value);
      }
    }
    return map;
  }

}
