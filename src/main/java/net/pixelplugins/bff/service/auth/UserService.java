package net.pixelplugins.bff.service.auth;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import net.pixelplugins.bff.client.UserClient;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
public class UserService implements UserDetailsService {

    @NonNull
    private final UserClient client;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var response = client.findByUsername(username);

        return User.builder()
                .username(response.getUsername())
                .password(client.findPasswordByUsername(username))
                .authorities(new SimpleGrantedAuthority("ROLE_USER"))
                .build();
    }
}
