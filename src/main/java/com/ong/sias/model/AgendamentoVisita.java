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

    private Voluntario voluntarioObj;
    private String enderecoConcatenado;

    private Endereco enderecoObj;

    public Voluntario getVoluntarioObj() { return voluntarioObj; }
    public void setVoluntarioObj(Voluntario voluntarioObj) { this.voluntarioObj = voluntarioObj; }

    public Endereco getEnderecoObj() { return enderecoObj; }
    public void setEnderecoObj(Endereco enderecoObj) { this.enderecoObj = enderecoObj; }

    @Override
    public String obterResumo() {
        String status = this.realizada ? "Realizada" : "Agendada";
        return "Visita: " + tipoVisita + " | Status: " + status;
    }
}
