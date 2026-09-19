package com.ong.sias.dto;

import lombok.Data;

@Data
public class VoluntarioDTO {
    private String profissaoHabilidade;
    private boolean ativo;

    public String getProfissaoHabilidade() {
        return this.profissaoHabilidade;
    }

    public void setProfissaoHabilidade(String profissaoHabilidade) {
        this.profissaoHabilidade = profissaoHabilidade;
    }

    public boolean getAtivo() {
        return this.ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}
