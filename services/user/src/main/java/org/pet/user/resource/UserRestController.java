package org.pet.user.resource;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.pet.user.dto.request.CreateUserRequest;
import org.pet.user.dto.request.WithdrawRequest;
import org.pet.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody @Valid CreateUserRequest request) {
        return ResponseEntity.ok(userService.createUser(request));
    }

    @PostMapping("/{id}/withdraw")
    public ResponseEntity<?> withdrawFromUser(@PathVariable Long id, @RequestBody @Valid WithdrawRequest request) {
        return ResponseEntity.ok(userService.withdraw(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUser(id));
    }

}
