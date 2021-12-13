package com.project.jwtspringsec.controller;

import com.project.jwtspringsec.dto.NovaTarefaDTO;
import com.project.jwtspringsec.dto.TarefaDetalhadaDTO;
import com.project.jwtspringsec.dto.TarefasDTO;
import com.project.jwtspringsec.exception.AcessoNegadoException;
import com.project.jwtspringsec.model.Tarefa;
import com.project.jwtspringsec.model.Usuario;
import com.project.jwtspringsec.repositories.TarefaRepository;
import com.project.jwtspringsec.repositories.UsuarioRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/tarefas")
public class TarefasController {

    private TarefaRepository tarefaRepository;
    private UsuarioRepository usuarioRepository;

    public TarefasController(TarefaRepository tarefaRepository,
                             UsuarioRepository usuarioRepository){
        this.tarefaRepository = tarefaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping
    public List<TarefasDTO> buscarTodas(HttpSession session){
        Usuario usuario = getUsuarioLogado(session);

        return this.tarefaRepository.findByUsuario(usuario)
                .stream()
                .map(TarefasDTO::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public TarefaDetalhadaDTO buscarPorId(@PathVariable("id") Integer id, HttpSession session) throws AcessoNegadoException {
        Tarefa tarefa = this.tarefaRepository.findById(id).get();
        Integer idUsuarioLogado = (Integer) session.getAttribute("idUsuarioLogado");

        if(!tarefa.getUsuario().getId().equals(idUsuarioLogado)){
            throw new AcessoNegadoException();
        }

        return new TarefaDetalhadaDTO(tarefa);
    }

    @PostMapping
    public void cadastrar(@RequestBody NovaTarefaDTO tarefa, HttpSession session){
        Usuario usuario = getUsuarioLogado(session);
        Tarefa entidade = new Tarefa();
        entidade.setUsuario(usuario);
        entidade.setDescricao(tarefa.getDescricao());
        entidade.setCriadaEm(LocalDateTime.now());
        this.tarefaRepository.save(entidade);
    }

    private Usuario getUsuarioLogado(HttpSession session){
        Integer idUsuarioLogado = (Integer) session.getAttribute("idUsuarioLogado");

        return this.usuarioRepository.findById(idUsuarioLogado).get();
    }
}
