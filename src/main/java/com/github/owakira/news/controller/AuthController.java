package com.github.owakira.news.controller;

import com.github.owakira.news.exception.IncorrectUsernameOrPasswordException;
import com.github.owakira.news.model.request.LoginRequest;
import com.github.owakira.news.model.response.TokensResponse;
import com.github.owakira.news.security.JwtTokenProvider;
import com.github.owakira.news.security.SecurityUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequiredArgsConstructor
@RequestMapping(AuthController.AUTH_API_ENDPOINT)
@Log4j2
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public final static String AUTH_API_ENDPOINT = "/api/auth";
    public final static String LOGIN_ENDPOINT = "/login";

    @PostMapping(LOGIN_ENDPOINT)
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        var auth = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
        try {
            var authenticate = authenticationManager.authenticate(auth);
            var user = (SecurityUser) authenticate.getPrincipal();
            var claims = new HashMap<String, Object>() {{
                put("sub", user.getUsername());
            }};
            var token = jwtTokenProvider.createAccessToken(claims);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new TokensResponse(token));
        } catch (AuthenticationException ex) {
            log.info("Incorrect username or password. request={}", request);
            throw new IncorrectUsernameOrPasswordException();
        }
    }
}
