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
    public Usuario buscar(String id) throws SQLException {
        throw new UnsupportedOperationException("A busca por string indisponível para a entidade Usuario.");
    }

    @Override
    public Usuario buscar(int id) throws SQLException {
        return null;
    }

    @Override
    public void atualizar(Usuario usuario) throws SQLException {
    }

    @Override
    public void deletar(int id1) throws SQLException {
        throw new UnsupportedOperationException("A exclusão por 1 IDs não é suportada para a entidade Usuario.");
    }

    @Override
    public void deletar(int id1, int id2) throws SQLException {
        throw new UnsupportedOperationException("A exclusão por 2 IDs não é suportada para a entidade Usuario.");
    }

    @Override
    public void deletar(int id1, int id2, int id3) throws SQLException {
        Connection conexao = Conexao.getInstance().getConnection();
        String sql = "DELETE FROM Usuario WHERE CodOng = ? AND CodPessoa = ? AND CodUsuario = ?";

        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {

            preparedStatement.setInt(1, id1);
            preparedStatement.setInt(2, id2);
            preparedStatement.setInt(3, id3);

            preparedStatement.executeUpdate();
        }
    }



    @Override
    public List<Usuario> listarTodos() throws SQLException {

        return new ArrayList<>();
    }


    public Usuario autenticar(String cpf, String senha) throws SQLException {
        Usuario usuario = new Usuario();
        Connection conexao = Conexao.getInstance().getConnection();

        String sql = """
            SELECT p.CodONG, u.CodUsuario, u.CodPessoa, p.Nome, u.Ativo 
            FROM Usuario u
            INNER JOIN Pessoa p ON u.CodONG = p.CodONG AND u.CodPessoa = p.CodPessoa
            WHERE p.CPF = ? AND u.Senha = ? AND u.Ativo = 1
            """;

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            stmt.setString(2, senha);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                usuario = new Usuario();
                usuario.setCodUsuario(rs.getInt("CodUsuario"));
                usuario.setCodPessoa(rs.getInt("CodPessoa"));
                usuario.setAtivo(rs.getBoolean("Ativo"));
                usuario.setNome(rs.getString("Nome"));

                com.ong.sias.model.ONG ong = new com.ong.sias.model.ONG();
                ong.setCodONG(rs.getInt("CodONG"));
                usuario.setOng(ong);

            }
        }
        return usuario;
    }
}
