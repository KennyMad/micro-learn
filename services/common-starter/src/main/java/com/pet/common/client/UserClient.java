package com.pet.common.client;

import com.pet.common.dto.request.WithdrawRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;

@FeignClient(url = "http://localhost:8030/api/v1/user", name = "User")
public interface UserClient {

    @PostMapping("/{id}/withdraw")
    BigDecimal withdrawFromUser(@PathVariable("id") long id, @RequestBody WithdrawRequest withdrawRequest);
}
