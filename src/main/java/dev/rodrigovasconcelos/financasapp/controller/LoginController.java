package dev.rodrigovasconcelos.financasapp.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/")
public class LoginController {
    private final AuthenticationManager authenticationManager;
    private final SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();
    private SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();
    private UserDetailsManager userDetailsManager;

    public LoginController(AuthenticationManager authenticationManager, UserDetailsManager userDetailsManager) {
        this.authenticationManager = authenticationManager;
        this.userDetailsManager = userDetailsManager;
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<SessionResponse> login(@RequestBody LoginRequest loginRequest, HttpServletRequest request, HttpServletResponse response) {
        Authentication authenticationRequest = UsernamePasswordAuthenticationToken
                .unauthenticated(loginRequest.username(), loginRequest.password());
        Authentication authenticationResponse = this.authenticationManager.authenticate(authenticationRequest);

        SecurityContext context = securityContextHolderStrategy.createEmptyContext();
        context.setAuthentication(authenticationResponse);
        securityContextHolderStrategy.setContext(context);
        securityContextRepository.saveContext(context, request, response);
        SessionResponse sessionResponse = new SessionResponse(request.getSession().getId());
        return ResponseEntity.ok(sessionResponse);
    }

    public record SessionResponse(String sessionId) {}

    public record LoginRequest(String username, String password) {
    }

    @PostMapping("logon")
    @ResponseStatus(HttpStatus.CREATED)
    public void registrarUsuario(@RequestBody LogonRequest logonRequest) {
        UserDetails user = User.builder()
                .username(logonRequest.username)
                .password("{noop}" + logonRequest.password)
                .roles("USER").build();

        this.userDetailsManager.createUser(user);
    }

    public record LogonRequest(String username, String password, String email) {}
}
