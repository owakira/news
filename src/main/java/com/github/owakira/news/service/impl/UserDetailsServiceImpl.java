package com.github.owakira.news.service.impl;

import com.github.owakira.news.repository.UserRepository;
import com.github.owakira.news.security.SecurityUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Load user by username: [username={}]", username);
        var user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> {
                    log.info("User not found");
                    return new UsernameNotFoundException("User not found");
                });
        log.info("User found: [user={}]", user);
        return SecurityUser.fromEntity(user);
    }
}
