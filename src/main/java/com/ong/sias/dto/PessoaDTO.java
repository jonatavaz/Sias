package com.ong.sias.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class PessoaDTO {
    private int codPessoa;

    private String nome;
    private String cpf;
    private String telefone;
    private String email;
    private boolean pessoaVoluntario;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dataNascimento;

    private VoluntarioDTO voluntario;

    public PessoaDTO() {}

    public int getCodPessoa() { return codPessoa; }
    public void setCodPessoa(int codPessoa) { this.codPessoa = codPessoa; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Date getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(Date dataNascimento) { this.dataNascimento = dataNascimento; }

    public boolean isPessoaVoluntario() { return pessoaVoluntario; }
    public void setPessoaVoluntario(boolean pessoaVoluntario) { this.pessoaVoluntario = pessoaVoluntario; }

    public VoluntarioDTO getVoluntario() { return voluntario; }
    public void setVoluntario(VoluntarioDTO voluntario) { this.voluntario = voluntario; }
}
