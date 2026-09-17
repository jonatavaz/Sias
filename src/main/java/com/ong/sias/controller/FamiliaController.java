package com.ong.sias.controller;

import com.ong.sias.dao.*;
import com.ong.sias.dto.FamiliaDTO;
import com.ong.sias.dto.PessoaDTO;
import com.ong.sias.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.SQLException;

@Controller
@RequestMapping("/familias")
public class FamiliaController {

    @GetMapping
    public String index(Model model){
        model.addAttribute("hxUrlFab", "/familias/novo");
        model.addAttribute("iconeFab", "fa-solid fa-users");

        return "familia/index";
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
            endereco.setBairro(payload.getEndereco().getbairro());
            endereco.setCidade(payload.getEndereco().getCidade());
            endereco.setUf(payload.getEndereco().getUF());

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
}
