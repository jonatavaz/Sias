package com.ong.sias.dto;

import java.util.Date;

public class PessoaDTO {
    private String nome;
    private String cpf;
    private String telefone;
    private String email;
    private Date dataNascimento;

    private EnderecoDTO endereco;
    private VoluntarioDTO voluntario;

    public String getNome() {
        return this.nome;
    }

    public String getCpf() {
        return this.cpf;
    }
    public String getTelefone() {
        return this.telefone;
    }
    public String getEmail() {
        return this.email;
    }
    public Date getDataNascimento() {
        return this.dataNascimento;
    }
}
