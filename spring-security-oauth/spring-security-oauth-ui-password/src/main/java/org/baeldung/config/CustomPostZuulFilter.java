package org.baeldung.config;

import java.io.InputStream;

import javax.servlet.http.Cookie;

import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

@Component
public class CustomPostZuulFilter extends ZuulFilter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Object run() {
        final RequestContext ctx = RequestContext.getCurrentContext();
        logger.info("in zuul filter " + ctx.getRequest().getRequestURI());
        if (ctx.getRequest().getRequestURI().contains("oauth/token")) {

            final ObjectMapper mapper = new ObjectMapper();
            JsonNode json;
            try {
                final InputStream is = ctx.getResponseDataStream();
                final String responseBody = IOUtils.toString(is, "UTF-8");

                ctx.setResponseBody(responseBody);

                if (responseBody.contains("refresh_token")) {
                    json = mapper.readTree(responseBody);
                    final String refreshToken = json.get("refresh_token").getTextValue();
                    final Cookie cookie = new Cookie("refreshToken", refreshToken);
                    cookie.setHttpOnly(true);
                    cookie.setPath(ctx.getRequest().getContextPath() + "/refreshToken");
                    cookie.setMaxAge(2592000); // 30 days
                    ctx.getResponse().addCookie(cookie);

                    logger.info("refresh token = " + refreshToken);
                }
            } catch (final Exception e) {
                logger.error("Error occured in zuul post filter", e);
            }

        }
        return null;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public int filterOrder() {
        return 10;
    }

    @Override
    public String filterType() {
        return "post";
    }

}
