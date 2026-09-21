package com.ong.sias.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DashboardDAO {
    public int contarFamilias() throws SQLException {
        return executarCount("SELECT COUNT(*) FROM Familia");
    }

    public int contarNecessidadesPendentes() throws SQLException {
        return executarCount("SELECT COUNT(*) FROM Necessidade WHERE Atendida = 0");
    }

    public int contarVisitasPendentes() throws SQLException {
        return executarCount("SELECT COUNT(*) FROM AgendamentoVisita WHERE Realizada = 0");
    }

    public int contarVisitasRealizadas() throws SQLException {
        return executarCount("SELECT COUNT(*) FROM AgendamentoVisita WHERE Realizada = 1");
    }

    public int contarNecessidadesAtendidas() throws SQLException {
        return executarCount("SELECT COUNT(*) FROM Necessidade WHERE Atendida = 1");
    }

    private int executarCount(String sql) throws SQLException {
        int count = 0;
        Connection conexao = Conexao.getInstance().getConnection();
        try (PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                count = rs.getInt(1);
            }
        }
        return count;
    }


}
