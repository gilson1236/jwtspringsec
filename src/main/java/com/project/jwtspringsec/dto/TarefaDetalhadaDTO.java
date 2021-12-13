package com.project.jwtspringsec.dto;

import com.project.jwtspringsec.model.Tarefa;

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
