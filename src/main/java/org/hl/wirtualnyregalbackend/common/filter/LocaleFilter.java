package org.hl.wirtualnyregalbackend.common.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Locale;


@Component
public class LocaleFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String langTag = request.getHeader("Accept-Language");
        if (langTag != null && !langTag.isBlank()) {
            LocaleContextHolder.setLocale(Locale.forLanguageTag(langTag));
        }
        filterChain.doFilter(request, response);
    }

}
