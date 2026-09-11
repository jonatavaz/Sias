package com.ong.sias.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Campanha {
    public int codCampanha;
    public String nome;
    public LocalDateTime dataInicio;
    public LocalDateTime dataFim;
    public String metaArrecadacao;
}
