package com.ong.sias.dto;



public class FamiliaDTO {

    private int CodPessoaResponsavel;
    private int CodEndereco;
    private String Telefone;

    private EnderecoDTO endereco;

    public int getCodPessoaResponsave() {
        return this.CodPessoaResponsavel;
    }
    public int getCodEndereco() {
        return this.CodEndereco;
    }
    public String getTelefone() {
        return this.Telefone;
    }


}
