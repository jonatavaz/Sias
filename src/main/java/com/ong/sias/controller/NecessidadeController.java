package com.ong.sias.controller;

import com.ong.sias.dao.FamiliaDAO;
import com.ong.sias.dao.NecessidadeDAO;
import com.ong.sias.dao.OperacoesBanco;
import com.ong.sias.dao.PessoaDAO;
import com.ong.sias.dto.NecessidadeDTO;
import com.ong.sias.model.Familia;
import com.ong.sias.model.Necessidade;
import com.ong.sias.model.Pessoa;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/necessidades")
public class NecessidadeController {
    @GetMapping
    public String index(Model model){
        model.addAttribute("hxUrlFab", "/necessidades/novo");
        model.addAttribute("iconeFab", "fa-solid fa-hand-holding-heart");

        try {
            OperacoesBanco<Necessidade> dao =new NecessidadeDAO();
            List<Necessidade> listaNecessidades = dao.listarTodos();

            model.addAttribute("necessidades", listaNecessidades);

        }catch (SQLException ex){
            ex.printStackTrace();
            model.addAttribute("necessidade", new ArrayList<Pessoa>());
        }

        return "necessidades/index";

    }

    @GetMapping("/novo")
    public String carregarFormularioNovo(Model model) {
        try {
            OperacoesBanco<Familia> familiaDAO = new FamiliaDAO();
            model.addAttribute("familias", familiaDAO.listarTodos());

        } catch (SQLException ex) {
            ex.printStackTrace();
            model.addAttribute("familias", new ArrayList<Familia>());
        }

        return "necessidades/partials/_formCadastroNecessidades :: formFragment";
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<String> cadastrar(@RequestBody NecessidadeDTO payload) {
        try {
            if (payload.getCodFamilia() <= 0) {
                return ResponseEntity.badRequest().body("É obrigatório selecionar uma família assistida.");
            }

            if (payload.getTipoNecessidade() == null || payload.getTipoNecessidade().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("O tipo da necessidade não pode ficar em branco.");
            }

            Necessidade necessidade = new Necessidade();
            necessidade.setCodFamilia(payload.getCodFamilia());
            necessidade.setTipoNecessidade(payload.getTipoNecessidade());
            necessidade.setDescricao(payload.getDescricao());
            necessidade.setAtendida(payload.isAtendida());

            OperacoesBanco<Necessidade> necessidadeDAO = new NecessidadeDAO();
            necessidadeDAO.salvar(necessidade);

            return ResponseEntity.ok("Necessidade registrada com sucesso!");

        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (SQLException ex) {
            ex.printStackTrace();
            return ResponseEntity.internalServerError().body("Erro de banco de dados: Não foi possível salvar a necessidade.");
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.internalServerError().body("Erro interno: " + ex.getMessage());
        }
    }

    @GetMapping("/editar/{id}")
    public String carregarFormularioEdicao(@PathVariable int id, Model model) {
        try {
            OperacoesBanco<Necessidade> necessidadeDAO = new NecessidadeDAO();
            Necessidade necessidade = necessidadeDAO.buscar(id);

            model.addAttribute("payload", necessidade);

            OperacoesBanco<Familia> familiaDAO = new FamiliaDAO();
            model.addAttribute("familias", familiaDAO.listarTodos());

            return "necessidades/partials/_formCadastroNecessidades :: formFragment";

        } catch (SQLException ex) {
            ex.printStackTrace();
            return "Erro ao buscar a necessidade.";
        }
    }

    @PostMapping("/atualizar")
    public ResponseEntity<String> atualizar(@RequestBody NecessidadeDTO payload) {

        try {
            if (payload.getCodNecessidade() <= 0) {
                return ResponseEntity.badRequest().body("ID da necessidade inválido.");
            }
            if (payload.getCodFamilia() <= 0) {
                return ResponseEntity.badRequest().body("É obrigatório selecionar uma família assistida.");
            }
            if (payload.getTipoNecessidade() == null || payload.getTipoNecessidade().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("O tipo da necessidade não pode ficar em branco.");
            }

            Necessidade necessidade = new Necessidade();
            necessidade.setCodNecessidade(payload.getCodNecessidade());
            necessidade.setCodFamilia(payload.getCodFamilia());
            necessidade.setTipoNecessidade(payload.getTipoNecessidade());
            necessidade.setDescricao(payload.getDescricao());
            necessidade.setAtendida(payload.isAtendida());

            OperacoesBanco<Necessidade> necessidadeDAO = new NecessidadeDAO();
            necessidadeDAO.atualizar(necessidade);

            return ResponseEntity.ok("Necessidade atualizada com sucesso!");

        } catch (SQLException ex) {
            ex.printStackTrace();
            return ResponseEntity.internalServerError().body("Erro ao atualizar no banco de dados.");
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.internalServerError().body("Erro interno: " + ex.getMessage());
        }
    }

    @DeleteMapping("/deletar/{id1}/{id2}")
    public ResponseEntity<String> deletar(@PathVariable int id1, @PathVariable int id2) {
        //id1 = CodFamilia, id2 = CodNecessidade

        try {
            OperacoesBanco<Necessidade> necessidadeDAO = new NecessidadeDAO();

            necessidadeDAO.deletar(id1, id2);

            return ResponseEntity.ok("Necessidade excluída com sucesso.");

        } catch (SQLException ex) {
            ex.printStackTrace();
            return ResponseEntity.internalServerError().body("Erro ao excluir o registro do banco de dados.");
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.internalServerError().body("Erro interno: " + ex.getMessage());
        }
    }
}
