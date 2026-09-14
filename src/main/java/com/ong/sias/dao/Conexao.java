package com.ong.sias.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    private static Conexao instancia;

    private Connection conexao;

    private final String URL = "jdbc:sqlserver://localhost:1433;databaseName=SiasDB;encrypt=true;trustServerCertificate=true";
    private final String USUARIO = "sa";
    private final String SENHA = "Claudia1976$";

    private Conexao() throws SQLException{
        try {
            this.conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
        } catch (SQLException ex) {
            throw ex;
        }
    }

    public static Conexao getInstance() throws SQLException{
        if(instancia == null|| instancia.getConnection().isClosed()){
            instancia = new Conexao();
        }
        return instancia;
    }
    public Connection getConnection(){
        return this.conexao;
    }

}
