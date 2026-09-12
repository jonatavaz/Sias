package com.ong.sias.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class ONG {
    @Id
    private int codONG;

    private String nome;
    private String cnpj;
    private int banco;
    private String agencia;
    private String agenciaDv;
    private String conta;
    private String contaDv;
    private String chavePix;
}
