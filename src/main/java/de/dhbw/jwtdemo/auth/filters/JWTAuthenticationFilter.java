package de.dhbw.jwtdemo.auth.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.dhbw.jwtdemo.auth.dto.LoginRequest;
import de.dhbw.jwtdemo.auth.dto.LoginResponse;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        var credentials = new ObjectMapper().readValue(request.getInputStream(), LoginRequest.class);
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(credentials.getUsername(), credentials.getPassword(), new ArrayList<>())
        );
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        var expiresAt = new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(60));
        var user = (User) authResult.getPrincipal();

        var token = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(expiresAt)
                .sign(Algorithm.HMAC512("SECRET")); // Secret should be read from configuration file

        var loginResponse = new LoginResponse();
        loginResponse.setAccessToken(token);
        loginResponse.setExpiresAt(expiresAt);

        response.setContentType("application/json");
        response.getWriter().write(new ObjectMapper().writeValueAsString(loginResponse));
    }
}
