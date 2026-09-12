package com.ong.sias.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name="Usuario")
@PrimaryKeyJoinColumn(name = "CodPessoa")
public class Usuario extends Pessoa{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CodUsuario")
    public int codUsuario;

    @Column(name = "Ativo")
    public boolean ativo;
}
