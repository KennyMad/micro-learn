package org.pet.user.service;

import com.pet.common.exception.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.pet.user.dto.request.CreateUserRequest;
import org.pet.user.dto.request.UserResponse;
import org.pet.user.dto.request.WithdrawRequest;
import org.pet.user.entity.User;
import org.pet.user.exception.IllegalAmountException;
import org.pet.user.mapper.UserMapper;
import org.pet.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public long createUser(CreateUserRequest request) {
        return userRepository.save(userMapper.toEntity(request)).getId();
    }

    public UserResponse getUser(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("User", id));
    }

    @Transactional
    public BigDecimal withdraw(Long id, WithdrawRequest request) {
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User", id));
        if (user.getBalance().subtract(request.amount()).compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalAmountException("Insufficient funds");
        }
        user.setBalance(user.getBalance().subtract(request.amount()));
        userRepository.save(user);
        return user.getBalance();
    }
}
