package com.ong.sias.dto;

public class AgendamentoVisitaDTO {
    private int codVisita;
    private int codFamilia;
    private Integer codVoluntario;
    private String tipoVisita;
    private boolean realizada;

    public int getCodVisita() {
        return codVisita;
    }

    public void setCodVisita(int codVisita) {
        this.codVisita = codVisita;
    }

    public int getCodFamilia() {
        return codFamilia;
    }

    public void setCodFamilia(int codFamilia) {
        this.codFamilia = codFamilia;
    }

    public Integer getCodVoluntario() {
        return codVoluntario;
    }

    public void setCodVoluntario(Integer codVoluntario) {
        this.codVoluntario = codVoluntario;
    }

    public String getTipoVisita() {
        return tipoVisita;
    }

    public void setTipoVisita(String tipoVisita) {
        this.tipoVisita = tipoVisita;
    }

    public boolean isRealizada() {
        return realizada;
    }

    public void setRealizada(boolean realizada) {
        this.realizada = realizada;
    }
}
