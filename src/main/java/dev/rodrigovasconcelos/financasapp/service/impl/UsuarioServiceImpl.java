package dev.rodrigovasconcelos.financasapp.service.impl;

import dev.rodrigovasconcelos.financasapp.entity.Usuario;
import dev.rodrigovasconcelos.financasapp.exception.BusinessException;
import dev.rodrigovasconcelos.financasapp.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl {

    private UsuarioRepository usuarioRepository;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario findUserByUsername(String username) {
        return usuarioRepository.findByUsername(username).orElseThrow(
                () -> new BusinessException("Usuário não encontrado")
        );
    }
}
