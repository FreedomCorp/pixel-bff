package net.pixelplugins.bff.service.auth;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.pixelplugins.bff.client.UserClient;
import net.pixelplugins.bff.dto.auth.request.LoginUserRequest;
import net.pixelplugins.bff.dto.auth.response.AuthenticateUserResponse;
import net.pixelplugins.bff.exception.PasswordNotMatchException;
import net.pixelplugins.bff.exception.UsernameAlreadyRegisteredException;
import net.pixelplugins.bff.util.JwtUtil;
import net.pixelplugins.commons.user.dto.request.CreateUserRequest;
import net.pixelplugins.commons.user.dto.request.MatchUserPasswordRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthenticationService {

    @NonNull private final UserClient client;
    @NonNull private JwtUtil jwt;
    @NonNull private AuthenticationManager authenticationManager;
    @NonNull private PasswordEncoder passwordEncoder;

    public AuthenticateUserResponse login(LoginUserRequest request) throws PasswordNotMatchException, UsernameNotFoundException {
        if (!client.exists(request.getUsername()).isValue()){
            throw new UsernameNotFoundException("O usuário " + request.getUsername() + " não foi encontrado.");
        }

        var matchRequest = new MatchUserPasswordRequest(request.getUsername(), request.getPassword());
        var match = client.matchPassword(matchRequest);

        if (!match.isValue()){
            throw new PasswordNotMatchException();
        }

        log.info("Autenticando o usuário " + request.getUsername());

        return generateTokens(request.getUsername(), request.getPassword());
    }

    public AuthenticateUserResponse register(CreateUserRequest request) throws UsernameAlreadyRegisteredException {
        log.info("Registrando o usuário " + request.getUsername());
        client.create(request);
        return generateTokens(request.getUsername(), request.getPassword());
    }

    public AuthenticateUserResponse generateTokens(String username, String password) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        username,
                        password
                )
        );

        var accessToken = jwt.generate(username, passwordEncoder.encode(password), false);
        var refreshToken = jwt.generate(username, passwordEncoder.encode(password), true);

        return new AuthenticateUserResponse(accessToken, refreshToken);
    }
}
