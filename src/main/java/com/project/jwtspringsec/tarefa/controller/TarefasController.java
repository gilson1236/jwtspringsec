package com.project.jwtspringsec.tarefa.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestAttribute;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.project.jwtspringsec.tarefa.dto.NovaTarefaDTO;
import com.project.jwtspringsec.tarefa.dto.TarefaDetalhadaDTO;
import com.project.jwtspringsec.tarefa.dto.TarefasDTO;
import com.project.jwtspringsec.tarefa.exception.AcessoNegadoException;
import com.project.jwtspringsec.tarefa.model.Tarefa;
import com.project.jwtspringsec.usuario.model.Usuario;
import com.project.jwtspringsec.tarefa.repository.TarefaRepository;
import com.project.jwtspringsec.usuario.repository.UsuarioRepository;

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
    public List<TarefasDTO> buscarTodas(Principal principal){
        String loginUsuario = principal.getName();
        Usuario usuario = this.usuarioRepository.findByLogin(loginUsuario).get();

        return this.tarefaRepository.findByUsuario(usuario)
                .stream()
                .map(TarefasDTO::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public TarefaDetalhadaDTO buscarPorId(@PathVariable("id") Integer id, Principal principal) throws AcessoNegadoException {
        String loginUsuario = principal.getName();
        Usuario usuario = this.usuarioRepository.findByLogin(loginUsuario).get();
        Tarefa tarefa = this.tarefaRepository.findById(id).get();

        if(!tarefa.getUsuario().getId().equals(usuario.getId())){
            throw new AcessoNegadoException();
        }

        return new TarefaDetalhadaDTO(tarefa);
    }

    @PostMapping
    public void cadastrar(@RequestBody NovaTarefaDTO tarefa, Principal principal){
        String loginUsuario = principal.getName();
        Usuario usuario = this.usuarioRepository.findByLogin(loginUsuario).get();
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
