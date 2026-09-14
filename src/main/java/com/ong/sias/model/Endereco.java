package com.ong.sias.model;

import lombok.Data;

@Data
public class Endereco {
    public int codEndereco;
    public String cep;
    public String logradouro;
    public String numero;
    public String complemento;
    public String bairro;
    public String cidade;
    public String uf;
}
