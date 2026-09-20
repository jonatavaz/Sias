package com.ong.sias.model;

import java.util.Date;

public abstract class RegistroAssistencial {

    private int codFamilia;
    private boolean concluido;
    private int codUsuario;
    private Date dataHora;

    public RegistroAssistencial() {}

    public int getCodFamilia() { return codFamilia; }
    public void setCodFamilia(int codFamilia) { this.codFamilia = codFamilia; }

    public boolean isConcluido() { return concluido; }
    public void setConcluido(boolean concluido) { this.concluido = concluido; }

    public int getCodUsuario() { return codUsuario; }
    public void setCodUsuario(int codUsuario) { this.codUsuario = codUsuario; }

    public Date getDataHora() { return dataHora; }
    public void setDataHora(Date dataHora) { this.dataHora = dataHora; }

    public abstract String obterResumo();
}
