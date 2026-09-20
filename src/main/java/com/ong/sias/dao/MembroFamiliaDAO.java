package com.ong.sias.dao;

import com.ong.sias.model.Endereco;
import com.ong.sias.model.MembroFamilia;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MembroFamiliaDAO implements OperacoesBanco<MembroFamilia>{

    public void salvar(MembroFamilia membroFamilia) throws SQLException {

        Connection conexao = Conexao.getInstance().getConnection();

        String sql = "INSERT INTO MembroFamilia(CodONG, CodFamilia, CodPessoa, GrauParentesco, CodUsuario, DataHora)"+
                "VALUES(?,?,?,?,?,?)";

        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)){
            preparedStatement.setInt(1, 1);
            preparedStatement.setInt(2, membroFamilia.getCodFamilia());
            preparedStatement.setInt(3, membroFamilia.getCodPessoa());
            preparedStatement.setString(4, membroFamilia.getGrauParentesco());
            preparedStatement.setInt(5, 0);
            preparedStatement.setTimestamp(6, new java.sql.Timestamp(System.currentTimeMillis()));

            preparedStatement.executeUpdate();
        }
    }

    @Override
    public MembroFamilia buscar(String id) throws SQLException {
        throw new UnsupportedOperationException("A busca por string indisponível para a entidade Endereco.");
    }

    @Override
    public MembroFamilia buscar(int id) throws SQLException {

        return null;
    }

    @Override
    public void atualizar(MembroFamilia membroFamilia) throws SQLException {
        Connection conexao = Conexao.getInstance().getConnection();

        String sql = """
                    UPDATE MembroFamilia SET GrauParentesco = ?, CodUsuario_Modificado = ?, DataHora_Modificado = ? WHERE CodONG = ? AND CodFamilia = ? AND CodPessoa = ?
                    """;

        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)){

            preparedStatement.setString(1, membroFamilia.getGrauParentesco());
            preparedStatement.setInt(2, 0);
            preparedStatement.setTimestamp(3, new java.sql.Timestamp(System.currentTimeMillis()));
            preparedStatement.setInt(4, 1);
            preparedStatement.setInt(5, membroFamilia.getCodFamilia());
            preparedStatement.setInt(6, membroFamilia.getCodPessoa());

            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void deletar(int id1) throws SQLException {
    }

    @Override
    public void deletar(int id1, int id2) throws SQLException {
        throw new UnsupportedOperationException("A exclusão por 2 IDs não é suportada para a entidade Endereco.");
    }

    @Override
    public void deletar(int id1, int id2, int id3) throws SQLException {
        throw new UnsupportedOperationException("A exclusão por 3 IDs não é suportada para a entidade Endereco.");
    }

    @Override
    public List<MembroFamilia> listarTodos() throws SQLException {


        return new ArrayList<>();
    }
}
