package com.ong.sias.dao;


import com.ong.sias.model.Pessoa;
import com.ong.sias.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO implements OperacoesBanco<Usuario>{

    @Override
    public void salvar(Usuario usuario) throws SQLException {

        Connection conexao = Conexao.getInstance().getConnection();

        String sql = "INSERT INTO Usuario(CodONG, CodPessoa, Ativo, DataHora)"+
                        "VALUES(?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)){
            preparedStatement.setInt(1,1);
            preparedStatement.setInt(2, usuario.getCodPessoa());
            preparedStatement.setBoolean(3, usuario.isAtivo());
            preparedStatement.setTimestamp(4, new java.sql.Timestamp(System.currentTimeMillis()));

            preparedStatement.executeUpdate();
        }
    }

    @Override
    public Usuario buscar(int id) throws SQLException {
        return null;
    }

    @Override
    public void atualizar(Usuario usuario) throws SQLException {
    }

    @Override
    public void deletar(int id) throws SQLException {
    }

    @Override
    public List<Usuario> listarTodos() throws SQLException {

        return new ArrayList<>();
    }
}
