package com.ong.sias.dao;

import java.sql.SQLException;
import java.util.List;

public interface OperacoesBanco<T> {

    void salvar(T endidade) throws SQLException;
    T buscar(String id) throws SQLException;
    T buscar(int id) throws SQLException;
    void atualizar(T entidade) throws SQLException;
    void deletar(int id1) throws SQLException;
    void deletar(int id1, int id2) throws SQLException;
    void deletar(int id1, int id2, int id3) throws SQLException;
    List<T> listarTodos() throws SQLException;
}
