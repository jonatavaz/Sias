package com.ong.sias.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class PessoaDTO {
    private String nome;
    private String cpf;
    private String telefone;
    private String email;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dataNascimento;

    private VoluntarioDTO voluntario;

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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
