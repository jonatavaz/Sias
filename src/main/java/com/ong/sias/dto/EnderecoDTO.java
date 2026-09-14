package com.ong.sias.dto;

import lombok.Data;

@Data
public class EnderecoDTO {

    private String cep;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String uf;

    public String getCep() {
        return this.cep;
    }
    public String getLogradouro() {
        return this.logradouro;
    }
    public String getNumero() {
        return this.numero;
    }
    public String getComplemento() {
        return this.complemento;
    }
    public String getbairro() {
        return this.bairro;
    }
    public String getUF() {
        return this.uf;
    }
}
