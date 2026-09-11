package com.ong.sias.model;

import lombok.Data;

@Data
public class Necessidade {
    public int codNecessidade;
    public String tipoNecessidade;
    public String descricao;
    public boolean atendida;
}
