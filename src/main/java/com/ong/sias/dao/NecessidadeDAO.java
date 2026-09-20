package com.ong.sias.dao;

import com.ong.sias.model.Necessidade;
import com.ong.sias.model.Pessoa;
import com.ong.sias.model.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NecessidadeDAO implements OperacoesBanco<Necessidade>{
    @Override
    public void salvar(Necessidade necessidade) throws SQLException {

        Connection conexao = Conexao.getInstance().getConnection();

        String sql = """
                INSERT INTO Necessidade(CodONG, CodFamilia, TipoNecessidade, Descricao, Atendida, CodUsuario, DataHora)
                    	VALUES(?,?,?,?,?,?,?);
                    """;

        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)){
            preparedStatement.setInt(1,1);
            preparedStatement.setInt(2, necessidade.getCodFamilia());
            preparedStatement.setString(3, necessidade.getTipoNecessidade());
            preparedStatement.setString(4, necessidade.getDescricao());
            preparedStatement.setBoolean(5, necessidade.isAtendida());
            preparedStatement.setInt(6, 0);
            preparedStatement.setTimestamp(7, new java.sql.Timestamp(System.currentTimeMillis()));

            preparedStatement.executeUpdate();

        }
    }

    @Override
    public Necessidade buscar(String id) throws SQLException {

        return null;
    }

    @Override
    public Necessidade buscar(int id) throws SQLException {
        Connection conexao = Conexao.getInstance().getConnection();

        String sql = "select CodFamilia, CodNecessidade, TipoNecessidade, Descricao, Atendida from Necessidade WHERE CodONG = ? AND CodNecessidade = ?";

        try(PreparedStatement preparedStatement = conexao.prepareStatement(sql)){
            preparedStatement.setInt(1, 1);
            preparedStatement.setInt(2, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Necessidade necessidade = new Necessidade();
                necessidade.setCodFamilia(resultSet.getInt("CodFamilia"));
                necessidade.setCodNecessidade(resultSet.getInt("CodNecessidade"));
                necessidade.setTipoNecessidade(resultSet.getString("TipoNecessidade"));
                necessidade.setDescricao(resultSet.getString("Descricao"));
                necessidade.setAtendida(resultSet.getBoolean("Atendida"));

                return necessidade;
            }
        }
        return null;
    }

    @Override
    public void atualizar(Necessidade necessidade) throws SQLException {
        Connection conexao = Conexao.getInstance().getConnection();

        String sql = """
                UPDATE Necessidade SET TipoNecessidade = ?, Descricao = ?, Atendida = ?, CodUsuario_Modificado = ?, DataHora_Modificado = ? WHERE CodONG = ? AND CodFamilia = ? AND CodNecessidade = ?;
                    """;

        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)){
            preparedStatement.setString(1, necessidade.getTipoNecessidade());
            preparedStatement.setString(2, necessidade.getDescricao());
            preparedStatement.setBoolean(3, necessidade.isAtendida());
            preparedStatement.setInt(4, 0);
            preparedStatement.setTimestamp(5, new java.sql.Timestamp(System.currentTimeMillis()));
            preparedStatement.setInt(6, 1);
            preparedStatement.setInt(7, necessidade.getCodFamilia());
            preparedStatement.setInt(8, necessidade.getCodNecessidade());

            preparedStatement.executeUpdate();

        }
    }

    @Override
    public void deletar(int id1) throws SQLException {
        throw new UnsupportedOperationException("A exclusão por 1 IDs não é suportada para a entidade Necessidade.");
    }

    @Override
    public void deletar(int id1, int id2) throws SQLException {
        Connection conexao = Conexao.getInstance().getConnection();
        String sql = "DELETE FROM Necessidade WHERE CodONG = ? AND CodFamilia = ? AND CodNecessidade = ?";

        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
            preparedStatement.setInt(1, 1);
            preparedStatement.setInt(2, id1);
            preparedStatement.setInt(3, id2);
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void deletar(int id1, int id2, int id3) throws SQLException {
        throw new UnsupportedOperationException("A exclusão por 3 IDs não é suportada para a entidade Necessidade.");
    }

    @Override
    public List<Necessidade> listarTodos() throws SQLException {
        List<Necessidade> listaNecessidades = new ArrayList<>();
        Connection conexao = Conexao.getInstance().getConnection();

        String sql = """
            SELECT n.CodNecessidade, n.CodFamilia, n.TipoNecessidade, n.Descricao, n.Atendida, 
                   p.Nome AS NomeResponsavel 
            FROM Necessidade n
            INNER JOIN Familia f ON n.CodONG = f.CodONG AND n.CodFamilia = f.CodFamilia
            INNER JOIN Pessoa p ON f.CodONG = p.CodONG AND f.CodPessoaResponsavel = p.CodPessoa
            """;

        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Necessidade necessidade = new Necessidade();

                necessidade.setCodNecessidade(resultSet.getInt("CodNecessidade"));
                necessidade.setCodFamilia(resultSet.getInt("CodFamilia"));
                necessidade.setTipoNecessidade(resultSet.getString("TipoNecessidade"));
                necessidade.setDescricao(resultSet.getString("Descricao"));
                necessidade.setAtendida(resultSet.getBoolean("Atendida"));

                necessidade.setNomeResponsavel(resultSet.getString("NomeResponsavel"));

                listaNecessidades.add(necessidade);
            }
        }
        return listaNecessidades;
    }
}
