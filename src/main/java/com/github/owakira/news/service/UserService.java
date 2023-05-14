package com.github.owakira.news.service;

import com.github.owakira.news.model.domain.User;
import com.github.owakira.news.model.dto.CreateUserDTO;

import java.util.Optional;

public interface UserService {
    User createUser(CreateUserDTO dto);

    Optional<User> findUserById(Long id);
}
