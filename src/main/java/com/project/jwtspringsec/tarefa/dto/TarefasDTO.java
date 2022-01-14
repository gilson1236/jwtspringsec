package com.project.jwtspringsec.tarefa.dto;

import com.project.jwtspringsec.tarefa.model.Tarefa;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TarefasDTO {

    private Integer id;
    private String descricao;

    public TarefasDTO(Tarefa entidade){
        this.id = entidade.getId();
        this.descricao = entidade.getDescricao();
    }
}
