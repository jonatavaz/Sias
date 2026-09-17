package com.ong.sias.dto;



public class FamiliaDTO {

    private PessoaDTO pessoa;

    private String CpfResponsavel;
    private int CodPessoaResponsavel;
    private int CodEndereco;
    private String Telefone;
    private String GrauParentesco;

    private EnderecoDTO endereco;

    public String getCpfResponsavel() {
        return this.CpfResponsavel;
    }
    public int getCodPessoaResponsave() {
        return this.CodPessoaResponsavel;
    }
    public int getCodEndereco() {
        return this.CodEndereco;
    }
    public String getTelefone() {
        return this.Telefone;
    }
    public String getGrauParentesco() {
        return this.GrauParentesco;
    }
    public EnderecoDTO getEndereco() { return endereco; }
    public void setEndereco(EnderecoDTO endereco) { this.endereco = endereco; }

    public PessoaDTO getPessoa() { return pessoa; }
    public void setPessoa(PessoaDTO pessoa) { this.pessoa = pessoa; }

}
