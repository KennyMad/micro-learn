package org.pet.user.mapper;

import org.pet.user.dto.request.CreateUserRequest;
import org.pet.user.dto.request.UserResponse;
import org.pet.user.entity.User;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class UserMapper {

    public User toEntity(CreateUserRequest request) {
        if (request == null)
            return null;

        User user = new User();
        user.setBalance(new BigDecimal(0));
        user.setFullname(request.fullname());
        user.setEmail(request.email());
        user.setCity(request.city());
        user.setBirthday(request.birthday());
        return user;
    }

    public UserResponse toResponse(User user) {
        if (user == null)
            return null;
        return new UserResponse(user.getId(), user.getFullname(), user.getCity(), user.getEmail(), user.getBirthday(), user.getBalance());
    }
}
