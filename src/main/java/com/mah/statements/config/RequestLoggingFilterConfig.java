package com.mah.statements.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@Configuration
public class RequestLoggingFilterConfig {

    @Bean
    public CommonsRequestLoggingFilter commonsRequestLoggingFilter() {

        var commonsRequestLoggingFilter = new CommonsRequestLoggingFilter();

        commonsRequestLoggingFilter.setIncludeQueryString(true);
        commonsRequestLoggingFilter.setIncludePayload(true);
        commonsRequestLoggingFilter.setMaxPayloadLength(10000);
        commonsRequestLoggingFilter.setIncludeHeaders(false);
        commonsRequestLoggingFilter.setAfterMessagePrefix("REQUEST DATA : ");

        return commonsRequestLoggingFilter;
    }
}
