package com.ong.sias.dao;

import com.ong.sias.model.Endereco;
import com.ong.sias.model.Familia;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnderecoDAO implements OperacoesBanco<Endereco> {
    @Override
    public void salvar(Endereco endereco) throws SQLException {

        Connection conexao = Conexao.getInstance().getConnection();

        String sql = "INSERT INTO Endereco(CEP, Logradouro, Numero, Complemento, Bairro, Cidade, UF, CodUsuario, DataHora)"+
                "VALUES(?,?,?,?,?,?,?,?,?)";

        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            preparedStatement.setString(1,endereco.getCep());
            preparedStatement.setString(2,endereco.getLogradouro());
            preparedStatement.setString(3,endereco.getNumero());
            preparedStatement.setString(4, endereco.getComplemento());
            preparedStatement.setString(5, endereco.getBairro());
            preparedStatement.setString(6, endereco.getCidade());
            preparedStatement.setString(7, endereco.getUf());
            preparedStatement.setInt(8, 0);
            preparedStatement.setTimestamp(9, new java.sql.Timestamp(System.currentTimeMillis()));

            preparedStatement.executeUpdate();

            try(ResultSet resultSet = preparedStatement.getGeneratedKeys()){
                if (resultSet.next()){
                    endereco.setCodEndereco(resultSet.getInt(1));
                }
            }
        }
    }

    @Override
    public Endereco buscar(String id) throws SQLException {
        throw new UnsupportedOperationException("A busca por string indisponível para a entidade Endereco.");
    }

    @Override
    public Endereco buscar(int id) throws SQLException {

        return null;
    }

    @Override
    public void atualizar(Endereco endereco) throws SQLException {
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
    public List<Endereco> listarTodos() throws SQLException {


        return new ArrayList<>();
    }
}
