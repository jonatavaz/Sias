package com.ong.sias.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "Voluntario")
@PrimaryKeyJoinColumn(name = "CodPessoa")
public class Voluntario extends Pessoa{

    @Column(name = "CodVoluntario", insertable = false, updatable = false)
    private int codVoluntario;

    @Column(name = "ProfissaoHabilidade")
    private String profissaoHabilidade;

    @Column(name = "Ativo")
    private boolean ativo;
}
