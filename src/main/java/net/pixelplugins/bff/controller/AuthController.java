package net.pixelplugins.bff.controller;

import feign.Response;
import lombok.AllArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import net.pixelplugins.bff.dto.auth.request.LoginUserRequest;
import net.pixelplugins.bff.dto.auth.response.AuthenticateUserResponse;
import net.pixelplugins.bff.exception.PasswordNotMatchException;
import net.pixelplugins.bff.exception.UsernameAlreadyRegisteredException;
import net.pixelplugins.bff.service.auth.AuthenticationService;
import net.pixelplugins.bff.util.CookieUtil;
import net.pixelplugins.commons.user.dto.request.CreateUserRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@CrossOrigin(origins = "http://localhost:3000/", allowCredentials = "true")
@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/auth", produces = "application/json")
public class AuthController {

    private AuthenticationService service;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<AuthenticateUserResponse> login(@RequestBody LoginUserRequest request, HttpServletResponse servlet) throws PasswordNotMatchException, UsernameNotFoundException {
        var response = service.login(request);

        var cookie = new Cookie("access", response.getAccessToken());
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(3600);

        servlet.addCookie(cookie);

        return ResponseEntity.ok()
                .body(response);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<AuthenticateUserResponse> register(@RequestBody CreateUserRequest request, HttpServletResponse servlet) throws UsernameAlreadyRegisteredException {
        var response = service.register(request);

        var cookie = new Cookie("access", response.getAccessToken());
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(3600);

        servlet.addCookie(cookie);

        return ResponseEntity.ok()
                .body(response);
    }

    @GetMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> logout(HttpServletResponse response) {
        var cookie = new Cookie("access", null);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);

        response.addCookie(cookie);

        return ResponseEntity.ok().build();
    }

}
