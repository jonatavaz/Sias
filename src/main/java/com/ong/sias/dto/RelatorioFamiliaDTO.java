package com.ong.sias.dto;

public class RelatorioFamiliaDTO {
    private int codFamilia;
    private String responsavelFamilia;
    private String membroFamiliar;
    private String grauParentesco;
    private String enderecoCompleto;

    public RelatorioFamiliaDTO() {}

    public int getCodFamilia() {
        return codFamilia;
    }

    public void setCodFamilia(int codFamilia) {
        this.codFamilia = codFamilia;
    }

    public String getResponsavelFamilia() { return responsavelFamilia; }
    public void setResponsavelFamilia(String responsavelFamilia) { this.responsavelFamilia = responsavelFamilia; }

    public String getMembroFamiliar() { return membroFamiliar; }
    public void setMembroFamiliar(String membroFamiliar) { this.membroFamiliar = membroFamiliar; }

    public String getGrauParentesco() { return grauParentesco; }
    public void setGrauParentesco(String grauParentesco) { this.grauParentesco = grauParentesco; }

    public String getEnderecoCompleto() { return enderecoCompleto; }
    public void setEnderecoCompleto(String enderecoCompleto) { this.enderecoCompleto = enderecoCompleto; }
}
