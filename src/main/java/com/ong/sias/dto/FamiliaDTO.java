package com.ong.sias.dto;



public class FamiliaDTO {

    private int codFamilia;

    private String cpfResponsavel;
    private int codPessoaResponsavel;
    private int codEndereco;
    private String telefone;
    private String grauParentesco;

    private EnderecoDTO endereco;
    private PessoaDTO pessoa;

    public FamiliaDTO() {}


    public int getCodFamilia() { return codFamilia; }
    public void setCodFamilia(int codFamilia) { this.codFamilia = codFamilia; }

    public String getCpfResponsavel() { return cpfResponsavel; }
    public void setCpfResponsavel(String cpfResponsavel) { this.cpfResponsavel = cpfResponsavel; }

    // (Corrigido o erro de digitação que faltava o "l" no final)
    public int getCodPessoaResponsavel() { return codPessoaResponsavel; }
    public void setCodPessoaResponsavel(int codPessoaResponsavel) { this.codPessoaResponsavel = codPessoaResponsavel; }

    public int getCodEndereco() { return codEndereco; }
    public void setCodEndereco(int codEndereco) { this.codEndereco = codEndereco; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getGrauParentesco() { return grauParentesco; }
    public void setGrauParentesco(String grauParentesco) { this.grauParentesco = grauParentesco; }

    public EnderecoDTO getEndereco() { return endereco; }
    public void setEndereco(EnderecoDTO endereco) { this.endereco = endereco; }

    public PessoaDTO getPessoa() { return pessoa; }
    public void setPessoa(PessoaDTO pessoa) { this.pessoa = pessoa; }

}
