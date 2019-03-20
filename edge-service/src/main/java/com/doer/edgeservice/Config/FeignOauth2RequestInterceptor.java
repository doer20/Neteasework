package com.doer.edgeservice.Config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class FeignOauth2RequestInterceptor implements RequestInterceptor {

    public final static String AUTHORIZATION_HEADER = "Authorization";

    public final static String BEARER_TOKEN_TYPE = "bearer";

    @Override
    public void apply(RequestTemplate requestTemplate) {
        if (requestTemplate.path().startsWith("/oauth/token")){
            requestTemplate.header(AUTHORIZATION_HEADER,
                    String.format("%s %s", "basic", getAuthHeader("neteasework","doerpass")));
        }else {
            SecurityContext securityContext = SecurityContextHolder.getContext();
            Authentication authentication = securityContext.getAuthentication();
            String tokenAttr = (String) ((ServletRequestAttributes)
                    RequestContextHolder.getRequestAttributes()).getRequest().getSession().getAttribute(AUTHORIZATION_HEADER);
            if (authentication != null && authentication.getDetails() instanceof OAuth2AuthenticationDetails) {
                OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
                requestTemplate.header(AUTHORIZATION_HEADER,String.format("%s %s",BEARER_TOKEN_TYPE,details.getTokenValue()));
            }else if (tokenAttr != null) {
                requestTemplate.header(AUTHORIZATION_HEADER,tokenAttr);
            }
        }

    }

    public static String getAuthHeader(String clientId,String secret){
        String param = clientId+":"+secret;
        return encode(param.getBytes());
    }

    public static String encode(byte[] content) {
        return new sun.misc.BASE64Encoder().encode(content);
    }
}
