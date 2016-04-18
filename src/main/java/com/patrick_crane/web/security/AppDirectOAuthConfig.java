package com.patrick_crane.web.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth.provider.ConsumerDetailsService;
import org.springframework.security.oauth.provider.filter.OAuthProviderProcessingFilter;
import org.springframework.security.oauth.provider.token.InMemorySelfCleaningProviderTokenServices;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.session.ConcurrentSessionFilter;

import com.patrick_crane.web.controllers.AppDirectIntegrationV1Controller;

@Configuration
public class AppDirectOAuthConfig extends WebSecurityConfigurerAdapter {

  private static Logger LOGGER = Logger.getLogger(AppDirectOAuthConfig.class);

  @Autowired
  private ConsumerDetailsService consumerDetailsService;

  @Override
  public void configure(HttpSecurity http) throws Exception {
    LOGGER.debug("Configuring OAuth security for AppDirect integration API");

    // signature validation only
    OneLeggedOAuthFilter filter = new OneLeggedOAuthFilter();
    filter.setFilterProcessesUrl(AppDirectIntegrationV1Controller.APPDIRECT_INTEGRATION_PATH);
    filter.setTokenServices(new InMemorySelfCleaningProviderTokenServices());
    filter.setConsumerDetailsService(consumerDetailsService);
    filter.afterPropertiesSet();

    // setup
    http
          .antMatcher(AppDirectIntegrationV1Controller.APPDIRECT_INTEGRATION_PATH)
          .csrf().disable()
          .exceptionHandling().authenticationEntryPoint(new UnauthorizedRequestExceptionHandler())
          .and()
          .authorizeRequests().anyRequest().authenticated()
          .and()
          .addFilterAfter(filter, ConcurrentSessionFilter.class);
  }

  /**
   * OAuth security filter which only validates the signature of the request
   */
  private static class OneLeggedOAuthFilter extends OAuthProviderProcessingFilter {

    private static Logger LOGGER = Logger.getLogger(OneLeggedOAuthFilter.class);

    @Override
    protected void onValidSignature(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
      // signature valid, continue chain
      LOGGER.debug("Request signature validated");
      chain.doFilter(request, response);
    }

  }

  /**
   * Return HTTP status 401 (without WWW-Authenticate header) for requests which signature could not be validated
   */
  private static class UnauthorizedRequestExceptionHandler implements AuthenticationEntryPoint {

    private static Logger LOGGER = Logger.getLogger(UnauthorizedRequestExceptionHandler.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
      LOGGER.debug("Request not authorized");
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
    }

  }

}
