package com.ong.sias.model;

import java.util.Date;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Pessoa")
public class Pessoa {

    @Id
    @Column(name = "CodPessoa")
    private int codPessoa;

    @ManyToOne
    @JoinColumn(name = "codONG")
    private ONG ong;

    @Column(name = "Nome")
    private String nome;

    @Column(name = "CPF")
    private String cpf;

    @Column(name = "DataNascimento")
    private Date dataNascimento;

    @Column(name = "Email")
    private String email;

    @Column(name = "Telefone")
    private String telefone;


    @Column(name = "CodUsuario")
    private int codUsuarioRegistro;

    @Column(name = "DataHora")
    private Date dataHora;

    @Column(name = "CodUsuario_Modificado")
    private Integer codUsuarioModificado;

    @Column(name = "DataHora_Modificado")
    private Date dataHoraModificado;

    private Usuario usuario;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setNome(String nome){
        if(nome == null || nome.trim().length() < 3){
            throw new IllegalArgumentException("O nome informado é inválido. Insira pelo menos três caracteres.");
        }
        this.nome = nome.trim();
    }

    public void setCpf(String cpf){
        if(cpf == null){
            throw new IllegalArgumentException("O CPF é obrigatório");
        }

        String cpfLimpo = cpf.replaceAll("[^0-9]", "");

        if(cpfLimpo.length() != 11){
            throw new IllegalArgumentException("O CPF deve conter 11 digítos.");
        }
        this.cpf = cpfLimpo;
    }

    public String getNome() {return nome;}
    public String getCpf() {return cpf;}
}
