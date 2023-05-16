package com.github.owakira.news.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
@Log4j2
public class JwtTokenFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var header = request.getHeader(HttpHeaders.AUTHORIZATION);
        log.info("Jwt token filter: [header={}]", header);
        if (header == null || !header.startsWith("Bearer")) {
            log.warn("Incorrect authorization header: [header={}]", header);
            filterChain.doFilter(request, response);
            return;
        }
        var accessToken = header.substring(7);
        if (jwtTokenProvider.validateAccessToken(accessToken)) {
            var auth = jwtTokenProvider.getAuthentication(accessToken);
            if (auth != null) {
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        filterChain.doFilter(request, response);
    }
}
