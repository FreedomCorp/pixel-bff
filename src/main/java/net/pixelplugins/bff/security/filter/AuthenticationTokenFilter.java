package net.pixelplugins.bff.security.filter;

import lombok.extern.slf4j.Slf4j;
import net.pixelplugins.bff.service.auth.AuthenticationService;
import net.pixelplugins.bff.service.auth.UserService;
import net.pixelplugins.bff.util.JwtUtil;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class AuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private UserService service;

    @Autowired
    private JwtUtil jwt;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return request.getRequestURI().contains("/api/v1/auth");
    }

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            var token = jwt.parse(request);

            if (token != null && jwt.validate(token)) {
                var username = jwt.findByToken(token);
                var user = service.loadUserByUsername(username);

                var authentication = new UsernamePasswordAuthenticationToken(user, "", user.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);

            }
        } catch (Exception exception) {
            log.error("Não foi possível autenticar o usuário: {}", exception.getMessage());
        }

        filterChain.doFilter(request, response);
    }


}
