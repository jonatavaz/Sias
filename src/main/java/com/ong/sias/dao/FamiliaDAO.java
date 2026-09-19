package com.ong.sias.dao;

import com.ong.sias.dto.EnderecoDTO;
import com.ong.sias.dto.FamiliaDTO;
import com.ong.sias.dto.PessoaDTO;
import com.ong.sias.dto.RelatorioFamiliaDTO;
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
    public Familia buscar(String id) throws SQLException {
        throw new UnsupportedOperationException("A busca por string indisponível para a entidade Familia.");
    }

    @Override
    public Familia buscar(int id) throws SQLException {

        return null;
    }


    public FamiliaDTO buscarFamilia(int id) throws SQLException {
        Connection conexao = Conexao.getInstance().getConnection();

        String sql = """
                     
                SELECT f.CodFamilia, f.CodPessoaResponsavel, p1.CPF CPFResponsavel, p1.Nome AS ResponsavelFamilia, p2.CodPessoa, p2.CPF AS CPFFamiliar, p2.Email, p2.Telefone, p2.DataNascimento, p2.Nome AS MembroFamiliar, mf.GrauParentesco, e.CodEndereco, e.CEP,	e.Logradouro, e.Numero, e.Complemento, e.Bairro, e.Cidade, e.UF                                                                                                                                                                                                                                    
                    FROM Familia f
                     INNER JOIN MembroFamilia mf ON f.CodONG = mf.CodONG AND f.CodFamilia = mf.CodFamilia
                     INNER JOIN Pessoa P1 ON f.CodONG = p1.CodONG AND f.CodPessoaResponsavel = p1.CodPessoa
                     INNER JOIN Pessoa P2 ON f.CodONG = p2.CodONG AND mf.CodPessoa = p2.CodPessoa
                     INNER JOIN Endereco e ON f.CodONG = p2.CodONG AND f.CodEndereco = e.CodEndereco
                WHERE f.CodONG = ? AND f.CodFamilia = ?;
                     """;

        try(PreparedStatement preparedStatement = conexao.prepareStatement(sql)){
            preparedStatement.setInt(1, 1);
            preparedStatement.setInt(2, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                FamiliaDTO dto = new FamiliaDTO();

                dto.setCodFamilia(resultSet.getInt("CodFamilia"));
                dto.setCodPessoaResponsavel(resultSet.getInt("CodPessoaResponsavel"));
                dto.setCodEndereco(resultSet.getInt("CodEndereco"));
                dto.setCpfResponsavel(resultSet.getString("CPFResponsavel"));
                dto.setGrauParentesco(resultSet.getString("GrauParentesco"));

                EnderecoDTO endereco = new EnderecoDTO();
                endereco.setCep(resultSet.getString("CEP"));
                endereco.setLogradouro(resultSet.getString("Logradouro"));
                endereco.setNumero(resultSet.getString("Numero"));
                endereco.setComplemento(resultSet.getString("Complemento"));
                endereco.setBairro(resultSet.getString("Bairro"));
                endereco.setCidade(resultSet.getString("Cidade"));
                endereco.setUf(resultSet.getString("UF"));

                dto.setEndereco(endereco);

                PessoaDTO pessoa = new PessoaDTO();
                pessoa.setCodPessoa(resultSet.getInt("CodPessoa"));
                pessoa.setNome(resultSet.getString("MembroFamiliar"));
                pessoa.setCpf(resultSet.getString("CPFFamiliar"));
                pessoa.setTelefone(resultSet.getString("Telefone"));
                pessoa.setEmail(resultSet.getString("Email"));

                java.sql.Date dataNascimentoSql = resultSet.getDate("DataNascimento");
                if (dataNascimentoSql != null) {
                    pessoa.setDataNascimento(new java.util.Date(dataNascimentoSql.getTime()));
                }

                dto.setPessoa(pessoa);

                return dto;
            }
        }
        return null;
    }

    @Override
    public void atualizar(Familia familia) throws SQLException {
    }

    @Override
    public void deletar(int id1) throws SQLException {
    }

    @Override
    public void deletar(int id1, int id2) throws SQLException {
        throw new UnsupportedOperationException("A exclusão por 2 IDs não é suportada para a entidade Familia.");
    }

    @Override
    public void deletar(int id1, int id2, int id3) throws SQLException {
        throw new UnsupportedOperationException("A exclusão por 3 IDs não é suportada para a entidade Familia.");
    }

    @Override
    public List<Familia> listarTodos() throws SQLException {
        return new ArrayList<>();
    }

    public List<RelatorioFamiliaDTO> listarMembrosFamilia() throws SQLException {
        List<RelatorioFamiliaDTO> listaFamilias = new ArrayList<>();
        Connection conexao = Conexao.getInstance().getConnection();

        String sql = """
            SELECT f.CodFamilia, p1.Nome AS ResponsavelFamilia, 
                   p2.Nome AS MembroFamiliar, 
                   mf.GrauParentesco, 
                   CONCAT(e.Logradouro, ', ', e.Numero, ' - ', e.Complemento, ' - ', e.Bairro, ', ', e.Cidade, '/', e.UF, ' - CEP: ', e.CEP) AS EnderecoCompleto 
            FROM Familia f
            INNER JOIN MembroFamilia mf ON f.CodONG = mf.CodONG AND f.CodFamilia = mf.CodFamilia
            INNER JOIN Pessoa P1 ON f.CodONG = p1.CodONG AND f.CodPessoaResponsavel = p1.CodPessoa
            INNER JOIN Pessoa P2 ON f.CodONG = p2.CodONG AND mf.CodPessoa = p2.CodPessoa
            INNER JOIN Endereco e ON f.CodONG = p2.CodONG AND f.CodEndereco = e.CodEndereco
            """;

        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                RelatorioFamiliaDTO dto = new RelatorioFamiliaDTO();

                dto.setCodFamilia(resultSet.getInt("CodFamilia"));
                dto.setResponsavelFamilia(resultSet.getString("ResponsavelFamilia"));
                dto.setMembroFamiliar(resultSet.getString("MembroFamiliar"));
                dto.setGrauParentesco(resultSet.getString("GrauParentesco"));
                dto.setEnderecoCompleto(resultSet.getString("EnderecoCompleto"));

                listaFamilias.add(dto);
            }
        }

        return listaFamilias;
    }
}
