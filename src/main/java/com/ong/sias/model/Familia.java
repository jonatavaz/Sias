package com.ong.sias.model;

import lombok.Data;

@Data
public class Familia extends Pessoa{
    private int CodFamilia;
    private int CodPessoaResponsavel;
    private int CodEndereco;
    private String Telefone;
}
