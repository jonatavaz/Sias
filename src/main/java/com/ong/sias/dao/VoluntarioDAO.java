package com.ong.sias.dao;

import com.ong.sias.model.Voluntario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VoluntarioDAO implements OperacoesBanco<Voluntario>{

    public void salvar(Voluntario voluntario) throws SQLException {

        Connection conexao = Conexao.getInstance().getConnection();

        String sql = "INSERT INTO Voluntario(CodONG, CodPessoa, ProfissaoHabilidade, Ativo, CodUsuario, DataHora)"+
                            "VALUES(?,?,?,?,?,?)";

        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)){
            preparedStatement.setInt(1, 1);
            preparedStatement.setInt(2, voluntario.getCodPessoa());
            preparedStatement.setString(3, voluntario.getProfissaoHabilidade());
            preparedStatement.setBoolean(4, voluntario.isAtivo());
            preparedStatement.setInt(5, 0);
            preparedStatement.setTimestamp(6, new java.sql.Timestamp(System.currentTimeMillis()));

            preparedStatement.executeUpdate();
        }
    }

    @Override
    public Voluntario buscar(String id) throws SQLException {
        throw new UnsupportedOperationException("A busca por string indisponível para a entidade Voluntario.");
    }

    @Override
    public Voluntario buscar(int id) throws SQLException {

        return null;
    }

    @Override
    public void atualizar(Voluntario voluntario) throws SQLException {
    }

    @Override
    public void deletar(int id1) throws SQLException {
    }

    @Override
    public void deletar(int id1, int id2) throws SQLException {
        throw new UnsupportedOperationException("A exclusão por 2 IDs não é suportada para a entidade Voluntario.");
    }

    @Override
    public void deletar(int id1, int id2, int id3) throws SQLException {
        throw new UnsupportedOperationException("A exclusão por 3 IDs não é suportada para a entidade Voluntario.");
    }

    @Override
    public List<Voluntario> listarTodos() throws SQLException {


        return new ArrayList<>();
    }
}
