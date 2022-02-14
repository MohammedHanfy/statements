package com.mah.statements.rest.advices;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Type;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class ViewStatementsRequestBodyAdviceAdapter extends RequestBodyAdviceAdapter {

    private final HttpServletRequest httpServletRequest;

    private final ObjectMapper objectMapper;

    @Override
    public boolean supports(MethodParameter methodParameter,
                            Type targetType,
                            Class<? extends HttpMessageConverter<?>> converterType) {

        return true;
    }

    @Override
    public Object afterBodyRead(Object body,
                                HttpInputMessage inputMessage,
                                MethodParameter parameter,
                                Type targetType,
                                Class<? extends HttpMessageConverter<?>> converterType) {

        try {

            log.info(String.format(
                    "HTTP %s request received on %s with body : %s",
                    httpServletRequest.getMethod(),
                    httpServletRequest.getRequestURI(),
                    objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(body)));

        } catch (JsonProcessingException jsonProcessingException) {

            log.error("Error while parsing ViewStatementsRequest body");
        }

        return super.afterBodyRead(body, inputMessage, parameter, targetType, converterType);
    }
}
