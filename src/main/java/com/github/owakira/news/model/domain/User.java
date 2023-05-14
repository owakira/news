package com.github.owakira.news.model.domain;

import com.github.owakira.news.model.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
public class User {
    private Long id;
    @ToString.Exclude
    private String email;
    private String username;

    public static User fromEntity(UserEntity userEntity) {
        return new User(
                userEntity.getId(),
                userEntity.getEmail(),
                userEntity.getUsername()
        );
    }
}
