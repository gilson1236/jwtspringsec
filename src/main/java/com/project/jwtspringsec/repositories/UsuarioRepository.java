package com.project.jwtspringsec.repositories;

import com.project.jwtspringsec.model.Usuario;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UsuarioRepository extends CrudRepository<Usuario, Integer> {

    Optional<Usuario> findByLogin(String login);
}
