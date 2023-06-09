package com.github.owakira.news.model.response;

import com.github.owakira.news.model.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
public class UserResponse {
    private Long id;
    @ToString.Exclude
    private String email;
    private String username;

    public static UserResponse fromDomain(User user) {
        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getUsername()
        );
    }
}
