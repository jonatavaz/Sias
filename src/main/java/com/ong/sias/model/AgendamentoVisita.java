package com.ong.sias.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AgendamentoVisita extends RegistroAssistencial {

    private int codVisita;
    private Integer codVoluntario;
    private String tipoVisita;
    private boolean realizada;

    @Override
    public String obterResumo() {
        String status = this.realizada ? "Realizada" : "Agendada";
        return "Visita: " + tipoVisita + " | Status: " + status;
    }
}
