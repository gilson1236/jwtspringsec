package com.project.jwtspringsec.tarefa.repository;

import com.project.jwtspringsec.tarefa.model.Tarefa;
import com.project.jwtspringsec.usuario.model.Usuario;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TarefaRepository extends CrudRepository<Tarefa, Integer> {

    List<Tarefa> findByUsuario(Usuario usuario);
}
