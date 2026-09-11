package com.ong.sias.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DoacaoEstoque {
    public int codDoacao;
    public String tipo;
    public String descricao;
    public int quantidade;
    public LocalDateTime dataHoraEntrada;
}
