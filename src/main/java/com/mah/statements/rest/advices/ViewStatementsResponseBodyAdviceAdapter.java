package com.mah.statements.rest.advices;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class ViewStatementsResponseBodyAdviceAdapter implements ResponseBodyAdvice<Object> {

    private final ObjectMapper objectMapper;

    @Override
    public boolean supports(MethodParameter returnType,
                            Class<? extends HttpMessageConverter<?>> converterType) {

        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body,
                                  MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request,
                                  ServerHttpResponse response) {

        if (request instanceof ServletServerHttpRequest && response instanceof ServletServerHttpResponse) {

            try {

                log.info(String.format(
                        "HTTP response for %s request on %s with body : %s",
                        request.getMethodValue(),
                        request.getURI().getPath(),
                        objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(body)));

            } catch (JsonProcessingException jsonProcessingException) {

                log.error("Error while parsing ViewStatementsResponse body");
            }
        }

        return body;
    }
}
