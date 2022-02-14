package com.mah.statements.config;

import com.mah.statements.util.enums.UserRole;
import com.mah.statements.wrappers.RequestWrapper;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class ViewStatementsFilterConfig extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        var authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {

            var hasRoleUser = authentication.getAuthorities().contains(new SimpleGrantedAuthority(UserRole.ROLE_USER.name()));

            if (hasRoleUser) {

                var requestWrapper = new RequestWrapper(request);

                var requestAsString = StreamUtils.copyToString(requestWrapper.getInputStream(), StandardCharsets.UTF_8);

                if (requestAsString.contains("dateRange") || requestAsString.contains("amountRange")) {

                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }

                filterChain.doFilter(requestWrapper, response);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
