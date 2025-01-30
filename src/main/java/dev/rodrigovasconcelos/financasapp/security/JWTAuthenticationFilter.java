package dev.rodrigovasconcelos.financasapp.security;

import dev.rodrigovasconcelos.financasapp.exception.BusinessException;
import dev.rodrigovasconcelos.financasapp.repository.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private static final String URL_LOGON_PATTERN = "/v1/logon";
    private static final String URL_LOGIN_PATTERN = "/v1/login";

    private JwtUtil jwtUtil;

    public JWTAuthenticationFilter() {
        this.jwtUtil = new JwtUtil();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestPath = request.getRequestURI();
        if (requestPath.equals(URL_LOGIN_PATTERN) || requestPath.equals(URL_LOGON_PATTERN)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = getTokenFromRequest(request);

        if (token != null && jwtUtil.validateToken(token, jwtUtil.extractUsername(token))) {
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    jwtUtil.extractUsername(token), null, new ArrayList<>() // sem authority
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if(header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        throw new BusinessException("Não foi possível autenticar a conexão!");
    }
}
