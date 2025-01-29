package dev.rodrigovasconcelos.financasapp.service.impl;

import dev.rodrigovasconcelos.financasapp.entity.Usuario;
import dev.rodrigovasconcelos.financasapp.exception.BusinessException;
import dev.rodrigovasconcelos.financasapp.repository.UsuarioRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

@Service
public class UsuarioServiceImpl {

    private final UsuarioRepository usuarioRepository;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario findUserByUsername(String username) {
        return usuarioRepository.findByUsername(username).orElseThrow(usuarioNaoEncontradoException());
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

    public void registrarEmailUsuario(String username, String email) {
        Usuario usuario = usuarioRepository.findByUsername(username).orElseThrow(usuarioNaoEncontradoException());
        usuario.setEmail(email);
        usuarioRepository.save(usuario);
    }

    private Supplier<BusinessException> usuarioNaoEncontradoException() {
        return () -> new BusinessException("Usuário não encontrado");
    }
}
