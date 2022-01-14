package com.project.jwtspringsec.usuario.repository;

import com.project.jwtspringsec.usuario.model.Usuario;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UsuarioRepository extends CrudRepository<Usuario, Integer> {

    Optional<Usuario> findByLogin(String login);
}
