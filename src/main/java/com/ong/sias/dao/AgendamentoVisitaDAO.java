package com.ong.sias.dao;

import com.ong.sias.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AgendamentoVisitaDAO implements OperacoesBanco<AgendamentoVisita>{
    @Override
    public void salvar(AgendamentoVisita agendamentoVisita) throws SQLException {
        Connection conexao = Conexao.getInstance().getConnection();

        String sql = """
                    
                INSERT INTO AgendamentoVisita(CodONG, CodFamilia, CodVoluntario, TipoVisita, Realizada, CodUsuario, DataHora)
                    	VALUES(?,?,?,?,?,?,?);
                    """;

        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)){
            preparedStatement.setInt(1,1);
            preparedStatement.setInt(2, agendamentoVisita.getCodFamilia());
            preparedStatement.setInt(3, agendamentoVisita.getCodVoluntario());
            preparedStatement.setString(4, agendamentoVisita.getTipoVisita());
            preparedStatement.setBoolean(5, agendamentoVisita.isRealizada());
            preparedStatement.setInt(6, 0);
            preparedStatement.setTimestamp(7, new java.sql.Timestamp(System.currentTimeMillis()));
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public AgendamentoVisita buscar(String id) throws SQLException {


        return null;
    }

    @Override
    public AgendamentoVisita buscar(int id) throws SQLException {
        AgendamentoVisita visita = new AgendamentoVisita();
        Connection conexao = Conexao.getInstance().getConnection();

        String sql = "SELECT CodVisita, CodFamilia, CodVoluntario, TipoVisita, Realizada FROM AgendamentoVisita WHERE CodONG = ? AND CodVisita = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, 1);
            stmt.setInt(2, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                visita = new AgendamentoVisita();
                visita.setCodVisita(rs.getInt("CodVisita"));
                visita.setCodFamilia(rs.getInt("CodFamilia"));

                int codVol = rs.getInt("CodVoluntario");
                if (rs.wasNull()) {
                    visita.setCodVoluntario(null);
                } else {
                    visita.setCodVoluntario(codVol);
                }

                visita.setTipoVisita(rs.getString("TipoVisita"));
                visita.setRealizada(rs.getBoolean("Realizada"));
            }
        }

        return visita;
    }

    @Override
    public void atualizar(AgendamentoVisita agendamentoVisita) throws SQLException {
        Connection conexao = Conexao.getInstance().getConnection();

        String sql = """
                    
                UPDATE AgendamentoVisita SET TipoVisita = ?, Realizada = ?, CodUsuario_Modificado = ?, DataHora_Modificado = ? WHERE CodONG = ? AND CodFamilia = ? AND CodVoluntario = ? AND CodVisita = ?;
                    """;

        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)){
            preparedStatement.setString(1, agendamentoVisita.getTipoVisita());
            preparedStatement.setBoolean(2, agendamentoVisita.isRealizada());
            preparedStatement.setInt(3, 0);
            preparedStatement.setTimestamp(4, new java.sql.Timestamp(System.currentTimeMillis()));

            preparedStatement.setInt(5,1);
            preparedStatement.setInt(6, agendamentoVisita.getCodFamilia());
            preparedStatement.setInt(7, agendamentoVisita.getCodVoluntario());
            preparedStatement.setInt(8, agendamentoVisita.getCodVisita());

            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void deletar(int id1) throws SQLException {
        throw new UnsupportedOperationException("A exclusão por 1 IDs não é suportada para a entidade Necessidade.");
    }

    @Override
    public void deletar(int id1, int id2) throws SQLException {

    }

    @Override
    public void deletar(int id1, int id2, int id3) throws SQLException {
        Connection conexao = Conexao.getInstance().getConnection();
        String sql = "DELETE FROM AgendamentoVisita WHERE CodONG = ? AND CodFamilia = ? AND CodVoluntario = ? AND CodVisita = ?";

        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
            preparedStatement.setInt(1, 1);
            preparedStatement.setInt(2, id1);
            preparedStatement.setInt(3, id2);
            preparedStatement.setInt(4, id3);
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public List<AgendamentoVisita> listarTodos() throws SQLException {
        List<AgendamentoVisita> lista = new ArrayList<>();
        Connection conexao = Conexao.getInstance().getConnection();

        String sql = """
            SELECT 
                av.CodVisita, av.CodFamilia, av.CodVoluntario, av.TipoVisita, av.Realizada,
                pf.Nome AS ResponsavelFamilia,
                CONCAT(e.Logradouro, ', ', e.Numero, ' - ', e.Bairro, ', ', e.Cidade, '/', e.UF) AS EnderecoCompleto,
                pv.Nome AS NomeVoluntario
            FROM AgendamentoVisita av
            INNER JOIN Familia f ON av.CodONG = f.CodONG AND av.CodFamilia = f.CodFamilia
            INNER JOIN Pessoa pf ON f.CodONG = pf.CodONG AND f.CodPessoaResponsavel = pf.CodPessoa
            INNER JOIN Endereco e ON f.CodEndereco = e.CodEndereco
            LEFT JOIN Voluntario v ON f.CodONG = v.CodONG AND  av.CodVoluntario = v.CodVoluntario
            LEFT JOIN Pessoa pv ON v.CodONG = pv.CodONG AND v.CodPessoa = pv.CodPessoa
            """;

        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                AgendamentoVisita visita = new AgendamentoVisita();
                visita.setCodVisita(resultSet.getInt("CodVisita"));
                visita.setCodFamilia(resultSet.getInt("CodFamilia"));
                visita.setTipoVisita(resultSet.getString("TipoVisita"));
                visita.setRealizada(resultSet.getBoolean("Realizada"));

                Familia familia = new Familia();
                familia.setNome(resultSet.getString("ResponsavelFamilia"));
                visita.setFamilia(familia);

                Endereco endereco = new Endereco();
                endereco.setLogradouro(resultSet.getString("EnderecoCompleto"));
                visita.setEnderecoObj(endereco);

                String nomeVoluntario = resultSet.getString("NomeVoluntario");
                int codVoluntario = resultSet.getInt("CodVoluntario");
                if (nomeVoluntario != null) {
                    Voluntario voluntario = new Voluntario();
                    voluntario.setNome(nomeVoluntario);
                    voluntario.setCodVoluntario(codVoluntario);
                    visita.setVoluntarioObj(voluntario);
                }

                lista.add(visita);
            }
        }
        return lista;
    }
}
