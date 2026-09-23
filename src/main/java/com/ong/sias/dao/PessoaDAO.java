package com.ong.sias.dao;

import com.ong.sias.model.Pessoa;
import com.ong.sias.model.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PessoaDAO implements OperacoesBanco<Pessoa>{

    @Override
    public void salvar(Pessoa pessoa) throws SQLException{

        Connection conexao = Conexao.getInstance().getConnection();

        String sql = "INSERT INTO Pessoa(CodONG, Nome, CPF, DataNascimento, CodUsuario, DataHora, Email, Telefone)"+
                    "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            preparedStatement.setInt(1,1);
            preparedStatement.setString(2, pessoa.getNome());
            preparedStatement.setString(3, pessoa.getCpf());
            preparedStatement.setDate(4, new java.sql.Date(pessoa.getDataNascimento().getTime()));
            preparedStatement.setInt(5, 0);
            preparedStatement.setTimestamp(6, new java.sql.Timestamp(System.currentTimeMillis()));
            preparedStatement.setString(7, pessoa.getEmail());
            preparedStatement.setString(8, pessoa.getTelefone());

            preparedStatement.executeUpdate();

            try(ResultSet resultSet = preparedStatement.getGeneratedKeys()){
                if (resultSet.next()){
                    pessoa.setCodPessoa(resultSet.getInt(1));
                }
            }
        }
    }

    @Override
    public Pessoa buscar(String id) throws SQLException {
        Connection conexao = Conexao.getInstance().getConnection();

        String sql = "SELECT CodPessoa, Nome, CPF, Telefone, Email, DataNascimento FROM Pessoa WHERE CodONG = ? AND CPF = ?";

        try(PreparedStatement preparedStatement = conexao.prepareStatement(sql)){
            preparedStatement.setInt(1, 1);
            preparedStatement.setString(2, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Pessoa pessoa = new Pessoa();
                pessoa.setCodPessoa(resultSet.getInt("CodPessoa"));
                pessoa.setNome(resultSet.getString("Nome"));
                pessoa.setCpf(resultSet.getString("CPF"));
                pessoa.setTelefone(resultSet.getString("Telefone"));
                pessoa.setEmail(resultSet.getString("Email"));

                java.sql.Date dataNascimentoSql = resultSet.getDate("DataNascimento");
                if (dataNascimentoSql != null) {
                    pessoa.setDataNascimento(new java.util.Date(dataNascimentoSql.getTime()));
                }
                return pessoa;
            }
        }
        return null;
    }

    @Override
    public Pessoa buscar(int id) throws SQLException {
        Connection conexao = Conexao.getInstance().getConnection();

        String sql = "SELECT CodPessoa, Nome, CPF, Telefone, Email, DataNascimento FROM Pessoa WHERE CodONG = ? AND CodPessoa = ?";

        try(PreparedStatement preparedStatement = conexao.prepareStatement(sql)){
            preparedStatement.setInt(1, 1);
            preparedStatement.setInt(2, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Pessoa pessoa = new Pessoa();
                pessoa.setCodPessoa(resultSet.getInt("CodPessoa"));
                pessoa.setNome(resultSet.getString("Nome"));
                pessoa.setCpf(resultSet.getString("CPF"));
                pessoa.setTelefone(resultSet.getString("Telefone"));
                pessoa.setEmail(resultSet.getString("Email"));

                java.sql.Date dataNascimentoSql = resultSet.getDate("DataNascimento");
                if (dataNascimentoSql != null) {
                    pessoa.setDataNascimento(new java.util.Date(dataNascimentoSql.getTime()));
                }
                return pessoa;
            }
        }
        return null;
    }

    @Override
    public void atualizar(Pessoa pessoa) throws SQLException {

        Connection conexao = Conexao.getInstance().getConnection();

        String sql = """
                    UPDATE Pessoa SET Nome = ?, CPF = ?, Telefone = ?, Email = ?, DataNascimento = ?, CodUsuario_Modificado = ?, DataHora_Modificado = ? WHERE CodONG = ? AND CodPessoa = ?
                    """;

        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            preparedStatement.setString(1, pessoa.getNome());
            preparedStatement.setString(2, pessoa.getCpf());
            preparedStatement.setString(3, pessoa.getTelefone());
            preparedStatement.setString(4, pessoa.getEmail());
            preparedStatement.setDate(5, new java.sql.Date(pessoa.getDataNascimento().getTime()));
            preparedStatement.setInt(6, 0);
            preparedStatement.setTimestamp(7, new java.sql.Timestamp(System.currentTimeMillis()));
            preparedStatement.setInt(8, 1);
            preparedStatement.setInt(9, pessoa.getCodPessoa());


            preparedStatement.executeUpdate();

            try(ResultSet resultSet = preparedStatement.getGeneratedKeys()){
                if (resultSet.next()){
                    pessoa.setCodPessoa(resultSet.getInt(1));
                }
            }
        }
    }

    @Override
    public void deletar(int id1) throws SQLException {
        throw new UnsupportedOperationException("A exclusão por 1 IDs não é suportada para a entidade Pessoa.");
    }

    @Override
    public void deletar(int id1, int id2) throws SQLException {
        Connection conexao = Conexao.getInstance().getConnection();
        String sql = "DELETE FROM Pessoa WHERE CodOng = ? AND CodPessoa = ?";

        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {

            preparedStatement.setInt(1, id1);
            preparedStatement.setInt(2, id2);

            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void deletar(int id1, int id2, int id3) throws SQLException {
        throw new UnsupportedOperationException("A exclusão por 3 IDs não é suportada para a entidade Pessoa.");
    }

    @Override
    public List<Pessoa> listarTodos() throws SQLException {
        List<Pessoa> listaPessoas = new ArrayList<>();
        Connection conexao = Conexao.getInstance().getConnection();

        String sql = """
            SELECT p.CodPessoa, p.Nome, p.CPF, p.Telefone, p.Email, p.DataNascimento, 
                   u.CodUsuario, u.Ativo 
            FROM Pessoa p
            LEFT JOIN Usuario u ON p.CodONG = u.CodONG AND p.CodPessoa = u.CodPessoa
            """;

        try(PreparedStatement preparedStatement = conexao.prepareStatement(sql)){
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                Pessoa pessoa = new Pessoa();

                pessoa.setCodPessoa(resultSet.getInt("CodPessoa"));
                pessoa.setNome(resultSet.getString("Nome"));
                pessoa.setCpf(resultSet.getString("CPF"));
                pessoa.setTelefone(resultSet.getString("Telefone"));
                pessoa.setEmail(resultSet.getString("Email"));

                java.sql.Date dataNascimentoSql = resultSet.getDate("DataNascimento");
                if (dataNascimentoSql != null) {
                    pessoa.setDataNascimento(new java.util.Date(dataNascimentoSql.getTime()));
                }

                Usuario usuario = new Usuario();
                usuario.setCodUsuario(resultSet.getInt("CodUsuario"));
                usuario.setAtivo(resultSet.getBoolean("Ativo"));
                pessoa.setUsuario(usuario);

                listaPessoas.add(pessoa);
            }
        }
        return listaPessoas;
    }

}
