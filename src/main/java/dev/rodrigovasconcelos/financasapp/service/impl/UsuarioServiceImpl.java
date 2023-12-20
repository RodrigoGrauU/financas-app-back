package dev.rodrigovasconcelos.financasapp.service.impl;

import dev.rodrigovasconcelos.financasapp.entity.Usuario;
import dev.rodrigovasconcelos.financasapp.exception.BusinessException;
import dev.rodrigovasconcelos.financasapp.repository.UsuarioRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl {

    private final UsuarioRepository usuarioRepository;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario findUserByUsername(String username) {
        return usuarioRepository.findByUsername(username).orElseThrow(
                () -> new BusinessException("Usuário não encontrado")
        );
    }

    public String getUsername() {
        SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();
        return securityContextHolderStrategy.getContext().getAuthentication().getName();
    }

    public Usuario findUserByContext() {
        return this.findUserByUsername(this.getUsername());
    }

    public Long getUserId() {
        return this.findUserByContext().getId();
    }
}
