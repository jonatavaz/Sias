package com.ong.sias.dao;

import com.ong.sias.model.Familia;
import com.ong.sias.model.Pessoa;
import com.ong.sias.model.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FamiliaDAO implements OperacoesBanco<Familia>{

    @Override
    public void salvar(Familia familia) throws SQLException {

        Connection conexao = Conexao.getInstance().getConnection();

        String sql = "INSERT INTO Familia(CodONG, CodPessoaResponsavel, CodEndereco, Telefone, CodUsuario, DataHora)"+
                            "VALUES(?,?,?,?,?,?)";

        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            preparedStatement.setInt(1,1);
            preparedStatement.setInt(2, familia.getCodPessoaResponsavel());
            preparedStatement.setInt(3, familia.getCodEndereco());
            preparedStatement.setString(4, familia.getTelefone());
            preparedStatement.setInt(5, 0);
            preparedStatement.setTimestamp(6, new java.sql.Timestamp(System.currentTimeMillis()));

            preparedStatement.executeUpdate();

            try(ResultSet resultSet = preparedStatement.getGeneratedKeys()){
                if (resultSet.next()){
                    familia.setCodFamilia(resultSet.getInt(1));
                }
            }
        }
    }

    @Override
    public Familia buscar(int id) throws SQLException {

        return null;
    }

    @Override
    public void atualizar(Familia familia) throws SQLException {
    }

    @Override
    public void deletar(int id) throws SQLException {
    }

    @Override
    public List<Familia> listarTodos() throws SQLException {


        return new ArrayList<>();
    }
}
