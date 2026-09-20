package com.ong.sias.dto;

import lombok.Data;

@Data
public class NecessidadeDTO {
    private int codNecessidade;
    private int codFamilia;
    private String tipoNecessidade;
    private String descricao;
    private boolean atendida;
}
