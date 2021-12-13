package com.project.jwtspringsec.repositories;

import com.project.jwtspringsec.model.Tarefa;
import com.project.jwtspringsec.model.Usuario;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TarefaRepository extends CrudRepository<Tarefa, Integer> {

    List<Tarefa> findByUsuario(Usuario usuario);
}
