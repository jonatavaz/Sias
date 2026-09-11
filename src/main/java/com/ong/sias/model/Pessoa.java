package com.ong.sias.model;

import java.util.Date;
import lombok.Data;
@Data
public class Pessoa {
    public int codPessoa;
    public String nome;
    public String cpf;
    public Date dataNascimento;
}
