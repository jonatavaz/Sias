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
    public String nome;

    @Column(name = "CPF")
    public String cpf;

    @Column(name = "DataNascimento")
    public Date dataNascimento;

    @Column(name = "Email")
    public String email;

    @Column(name = "CodUsuario")
    private int codUsuarioRegistro;

    @Column(name = "DataHora")
    private Date dataHora;

    @Column(name = "CodUsuario_Modificado")
    private Integer codUsuarioModificado;

    @Column(name = "DataHora_Modificado")
    private Date dataHoraModificado;
}
