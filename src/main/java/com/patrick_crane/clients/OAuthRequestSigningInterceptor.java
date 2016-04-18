package com.patrick_crane.clients;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import com.sun.jersey.oauth.signature.OAuthParameters;
import com.sun.jersey.oauth.signature.OAuthRequest;
import com.sun.jersey.oauth.signature.OAuthSecrets;
import com.sun.jersey.oauth.signature.OAuthSignature;
import com.sun.jersey.oauth.signature.OAuthSignatureException;
import com.sun.jersey.oauth.signature.OAuthSignatureMethod;

public class OAuthRequestSigningInterceptor implements ClientHttpRequestInterceptor {

  private static final Logger LOGGER = LoggerFactory.getLogger(OAuthRequestSigningInterceptor.class);

  private String consumerKey;

  private String consumerSecret;

  private OAuthSignatureMethod signatureMethod;

  public OAuthRequestSigningInterceptor(String consumerKey, String consumerSecret, OAuthSignatureMethod signatureMethod) {
    this.consumerKey = consumerKey;
    this.consumerSecret = consumerSecret;
    this.signatureMethod = signatureMethod;
  }

  @Override
  public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {

    LOGGER.debug("Intercepting outgoing request for signature");

    OAuthRequest oauthRequest = new OAuthHttpRequestAdapter(request);

    OAuthParameters oauthParams = new OAuthParameters();
    oauthParams.consumerKey(consumerKey);
    oauthParams.setSignatureMethod(signatureMethod.name());
    oauthParams.setNonce();
    oauthParams.setTimestamp();
    oauthParams.setVersion();

    OAuthSecrets oauthSecrets = new OAuthSecrets();
    oauthSecrets.consumerSecret(consumerSecret);

    try {
      OAuthSignature.sign(oauthRequest, oauthParams, oauthSecrets);
    } catch (OAuthSignatureException e) {
      LOGGER.error("Cannot sign request", e);
      throw new IOException("Cannot sign request");
    }

    LOGGER.debug("Request signed");

    return execution.execute(request, body);
  }

}
