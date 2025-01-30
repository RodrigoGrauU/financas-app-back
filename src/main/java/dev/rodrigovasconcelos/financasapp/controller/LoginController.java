package dev.rodrigovasconcelos.financasapp.controller;

import dev.rodrigovasconcelos.financasapp.repository.JwtUtil;
import dev.rodrigovasconcelos.financasapp.service.impl.UsuarioServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
@RequestMapping("/v1")
public class LoginController {
    private final AuthenticationManager authenticationManager;
    private final SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();
    private SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();
    private UserDetailsManager userDetailsManager;
    private UsuarioServiceImpl usuarioService;
    private final JwtUtil jwtUtil;

    public LoginController(AuthenticationManager authenticationManager, UserDetailsManager userDetailsManager,
                           UsuarioServiceImpl usuarioService, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userDetailsManager = userDetailsManager;
        this.usuarioService = usuarioService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<TokenLoginResponse> login(@RequestBody LoginRequest loginRequest, HttpServletRequest request, HttpServletResponse response) {
         authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.username, loginRequest.password));
        String token = jwtUtil.generateToken(loginRequest.username);
         return ResponseEntity.ok(new TokenLoginResponse(token));
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
        usuarioService.registrarEmailUsuario(logonRequest.username, logonRequest.email);
    }

    public record LogonRequest(String username, String password, String email) {}

    public record TokenLoginResponse(String token) {
        public TokenLoginResponse(String token) {
            this.token = token;
        }
    }
}
