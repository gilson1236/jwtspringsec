package com.project.jwtspringsec.tarefa.dto;

import com.project.jwtspringsec.tarefa.model.Tarefa;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TarefaDetalhadaDTO {

    private String descricao;
    private LocalDateTime criadaEm;

    public TarefaDetalhadaDTO(Tarefa entidade){
        this.descricao = entidade.getDescricao();
        this.criadaEm = entidade.getCriadaEm();
    }
}
