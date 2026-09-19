package com.ong.sias.dto;

public class EnderecoDTO {
    private String cep;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String uf;

    public String getCep() { return this.cep; }
    public void setCep(String cep) { this.cep = cep; }

    public String getLogradouro() { return this.logradouro; }
    public void setLogradouro(String logradouro) { this.logradouro = logradouro; }

    public String getNumero() { return this.numero; }
    public void setNumero(String numero) { this.numero = numero; }

    public String getComplemento() { return this.complemento; }
    public void setComplemento(String complemento) { this.complemento = complemento; }

    public String getBairro() { return this.bairro; }
    public void setBairro(String bairro) { this.bairro = bairro; }

    public String getCidade() { return this.cidade; }
    public void setCidade(String cidade) { this.cidade = cidade; }

    public String getUf() { return this.uf; }
    public void setUf(String uf) { this.uf = uf; }
}
