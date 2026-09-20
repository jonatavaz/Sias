package com.ong.sias.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Necessidade  extends RegistroAssistencial{
    public int codNecessidade;
    public String tipoNecessidade;
    public String descricao;
    public boolean atendida;
    private String nomeResponsavel;

    @Override
    public String obterResumo() {
        String status = this.atendida ? "Atendida" : "Pendente";
        return "Necessidade: " + tipoNecessidade + " | Status: " + status;
    }
}
