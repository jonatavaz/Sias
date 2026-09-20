package com.ong.sias.controller;

import com.ong.sias.dao.*;
import com.ong.sias.dto.FamiliaDTO;
import com.ong.sias.dto.PessoaDTO;
import com.ong.sias.dto.RelatorioFamiliaDTO;
import com.ong.sias.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/familias")
public class FamiliaController {

    @GetMapping
    public String index(Model model){
        model.addAttribute("hxUrlFab", "/familias/novo");
        model.addAttribute("iconeFab", "fa-solid fa-users");
        try {
            FamiliaDAO dao = new FamiliaDAO();

            List<RelatorioFamiliaDTO> listaFamilias = dao.listarMembrosFamilia();

            model.addAttribute("familias", listaFamilias);

        }catch (SQLException ex){
            ex.printStackTrace();
            model.addAttribute("familias", new ArrayList<RelatorioFamiliaDTO>());
        }
        return "/familia/index";
    }

    @GetMapping("/novo")
    public String formNovaFamilia(){
        return "familia/partials/_formCadastroFamilia";
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<String> cadastrar(@RequestBody FamiliaDTO payload){
        try {
            OperacoesBanco<Endereco> enderecoDAO = new EnderecoDAO();
            Endereco endereco = new Endereco();
            endereco.setCep(payload.getEndereco().getCep());
            endereco.setLogradouro(payload.getEndereco().getLogradouro());
            endereco.setNumero(payload.getEndereco().getNumero());
            endereco.setComplemento(payload.getEndereco().getComplemento());
            endereco.setBairro(payload.getEndereco().getBairro());
            endereco.setCidade(payload.getEndereco().getCidade());
            endereco.setUf(payload.getEndereco().getUf());

            enderecoDAO.salvar(endereco);


            OperacoesBanco<Pessoa> pessoaDAO = new PessoaDAO();

            Pessoa pessoaEncontrada = pessoaDAO.buscar(payload.getCpfResponsavel());

            if (pessoaEncontrada == null) {
                return ResponseEntity.badRequest().body("Nenhum responsável encontrado com o CPF informado.");
            }

            Pessoa pessoa = new Pessoa();
            pessoa.setNome(payload.getPessoa().getNome());
            pessoa.setCpf(payload.getPessoa().getCpf());
            pessoa.setTelefone(payload.getPessoa().getTelefone());
            pessoa.setEmail(payload.getPessoa().getEmail());
            pessoa.setDataNascimento(payload.getPessoa().getDataNascimento());


            pessoaDAO.salvar(pessoa);

            Familia familia = new Familia();
            OperacoesBanco<Familia> familiaDAO = new FamiliaDAO();
            familia.setCodPessoaResponsavel(pessoaEncontrada.getCodPessoa());
            familia.setCodEndereco(endereco.getCodEndereco());
            familia.setTelefone(payload.getTelefone());
            familiaDAO.salvar(familia);

            MembroFamilia membroFamilia = new MembroFamilia();
            OperacoesBanco<MembroFamilia> membroFamiliaDAO = new MembroFamiliaDAO();
            membroFamilia.setCodFamilia(familia.getCodFamilia());
            membroFamilia.setCodPessoa(pessoa.getCodPessoa());
            membroFamilia.setGrauParentesco(payload.getGrauParentesco());

            membroFamiliaDAO.salvar(membroFamilia);

            return ResponseEntity.ok("Cadastro realizado com sucesso");
        }catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }catch (SQLException ex){
            ex.printStackTrace();
            return ResponseEntity.internalServerError().body("Erro ao salvar no banco de dados.");
        }catch (Exception ex){
            ex.printStackTrace();
            return ResponseEntity.internalServerError().body("Erro: " + ex.getMessage());
        }
    }

    @GetMapping("/editar/{id}")
    public String carregarFormularioEdicao(@PathVariable int id, Model model) {
        try {
            FamiliaDAO familiaDAO = new FamiliaDAO();
            FamiliaDTO dto = familiaDAO.buscarFamilia(id);

            model.addAttribute("payload", dto);

            return "familia/partials/_formCadastroFamilia :: formFragment";

        } catch (SQLException ex) {
            ex.printStackTrace();
            model.addAttribute("mensagem", "Erro interno: Não foi possível carregar os dados desta família.");
            return "<div class='alert alert-danger'>Erro interno ao buscar a família. Verifique o console da IDE.</div>";
        }
    }

    @PostMapping("/atualizar")
    public ResponseEntity<String> atualizar(@RequestBody FamiliaDTO payload) {
        try {
            OperacoesBanco<Endereco> enderecoDAO = new EnderecoDAO();
            Endereco endereco = enderecoDAO.buscar(payload.getCodEndereco());

            if (endereco != null) {
                endereco.setCep(payload.getEndereco().getCep());
                endereco.setLogradouro(payload.getEndereco().getLogradouro());
                endereco.setNumero(payload.getEndereco().getNumero());
                endereco.setComplemento(payload.getEndereco().getComplemento());
                endereco.setBairro(payload.getEndereco().getBairro());
                endereco.setCidade(payload.getEndereco().getCidade());
                endereco.setUf(payload.getEndereco().getUf());

                enderecoDAO.atualizar(endereco);
            }

            OperacoesBanco<Pessoa> pessoaDAO = new PessoaDAO();
            Pessoa pessoaEncontrada = pessoaDAO.buscar(payload.getCpfResponsavel());
            if (pessoaEncontrada == null) {
                return ResponseEntity.badRequest().body("Nenhum responsável encontrado com o CPF informado.");
            }

            Pessoa pessoa = pessoaDAO.buscar(payload.getPessoa().getCodPessoa());

            if (pessoa != null) {
                pessoa.setNome(payload.getPessoa().getNome());
                pessoa.setCpf(payload.getPessoa().getCpf());
                pessoa.setTelefone(payload.getPessoa().getTelefone());
                pessoa.setEmail(payload.getPessoa().getEmail());
                pessoa.setDataNascimento(payload.getPessoa().getDataNascimento());

                pessoaDAO.atualizar(pessoa);
            }

            OperacoesBanco<Familia> familiaDAO = new FamiliaDAO();
            Familia familia = familiaDAO.buscar(payload.getCodFamilia());

//            if (familia != null) {
//                familia.setCodPessoaResponsavel(pessoaEncontrada.getCodPessoa());
//
//                if(payload.getTelefone() != null) {
//                    familia.setTelefone(payload.getTelefone());
//                }
//
//                familiaDAO.atualizar(familia);
//            }

            OperacoesBanco<MembroFamilia> membroFamiliaDAO = new MembroFamiliaDAO();
            MembroFamilia membroFamilia = new MembroFamilia();

            membroFamilia.setCodFamilia(payload.getCodFamilia());
            membroFamilia.setCodPessoa(payload.getPessoa().getCodPessoa());
            membroFamilia.setGrauParentesco(payload.getGrauParentesco());

            membroFamiliaDAO.atualizar(membroFamilia);

            return ResponseEntity.ok("Cadastro atualizado com sucesso!");

        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (SQLException ex) {
            ex.printStackTrace();
            return ResponseEntity.internalServerError().body("Erro ao atualizar no banco de dados.");
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.internalServerError().body("Erro interno: " + ex.getMessage());
        }
    }
}
