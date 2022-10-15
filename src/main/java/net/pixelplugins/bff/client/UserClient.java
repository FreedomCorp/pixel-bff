package net.pixelplugins.bff.client;

import net.pixelplugins.bff.configuration.UserClientConfiguration;
import net.pixelplugins.bff.dto.auth.ValueCheckResponse;
import net.pixelplugins.bff.exception.UsernameAlreadyRegisteredException;
import net.pixelplugins.commons.user.dto.request.CreateUserRequest;
import net.pixelplugins.commons.user.dto.request.MatchUserPasswordRequest;
import net.pixelplugins.commons.user.dto.response.ReadUserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(
        value = "user-service",
        url = "http://localhost:8080/api/v1/user",
        configuration = UserClientConfiguration.class
)
public interface UserClient {

    @PostMapping
    ReadUserResponse create(@RequestBody CreateUserRequest request) throws UsernameAlreadyRegisteredException;

    @GetMapping("/exists")
    ValueCheckResponse exists(@RequestParam String username);

    @GetMapping("/find")
    ReadUserResponse findByUsername(@RequestParam String username);

    @GetMapping("/find/{id}")
    ReadUserResponse findById(@PathVariable long id);

    @PutMapping
    ReadUserResponse update(@RequestBody ReadUserResponse request);

    @PostMapping("/password")
    ValueCheckResponse matchPassword(@RequestBody MatchUserPasswordRequest request);

    @GetMapping("/password")
    String findPasswordByUsername(@RequestParam String username);
}
