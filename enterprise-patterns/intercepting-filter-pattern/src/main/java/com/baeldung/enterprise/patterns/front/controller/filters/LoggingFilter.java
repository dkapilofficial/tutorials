package com.baeldung.enterprise.patterns.front.controller.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(servletNames = "front-controller")
public class LoggingFilter extends BaseFilter {
    private static final Logger log = LoggerFactory.getLogger(LoggingFilter.class);

    @Override
    public void doFilter(
      ServletRequest request,
      ServletResponse response,
      FilterChain chain
    ) throws IOException, ServletException {
        chain.doFilter(request, response);
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String username = (String) httpServletRequest.getAttribute("username");
        if (username != null && username.length() > 0) {
            username = username.concat("@");
        } else {
            username = "";
        }
        String intro = "Request";
        if (username.length() > 0) {
            intro = "Authenticated request";
        }
        log.info("{} from '{}{}': {}?{}", intro, username, request.getRemoteAddr(),
          httpServletRequest.getRequestURI(), request.getParameterMap());
    }
}
