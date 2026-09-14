package com.ong.sias.dao;

import java.sql.SQLException;
import java.util.List;

public interface OperacoesBanco<T> {

    void salvar(T endidade) throws SQLException;
    T buscar(int id) throws SQLException;
    void atualizar(T entidade) throws SQLException;
    void deletar(int id) throws SQLException;
    List<T> listarTodos() throws SQLException;
}
