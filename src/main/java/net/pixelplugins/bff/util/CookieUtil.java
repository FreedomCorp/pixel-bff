package net.pixelplugins.bff.util;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
public class CookieUtil {

    @Value("${jwt.expiration.time}")
    private long expirationTime;

    @Value("${jwt.refresh.expiration.time}")
    private long refreshExpirationTime;

    public String getCookieResponse(String token, boolean isRefresh) {
        var refresh = isRefresh ? "refresh" : "access";
        var age = (isRefresh ? refreshExpirationTime : expirationTime) / 1000;

        return ResponseCookie.from(refresh, token)
                .httpOnly(true)
                .path("/")
                .maxAge(age)
                .build()
                .toString();
    }

    public String createCookie(String name, String value) {
        return ResponseCookie.from(name, value)
                .httpOnly(true)
                .secure(true)
                .sameSite("Strict")
                .path("/")
                .maxAge(1)
                .build()
                .toString();
    }

}
