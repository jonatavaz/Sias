package com.ong.sias.controller;

import com.ong.sias.dao.FamiliaDAO;
import com.ong.sias.dao.OperacoesBanco;
import com.ong.sias.dao.PessoaDAO;
import com.ong.sias.dao.UsuarioDAO;
import com.ong.sias.dto.FamiliaDTO;
import com.ong.sias.dto.PessoaDTO;
import com.ong.sias.model.Familia;
import com.ong.sias.model.Pessoa;
import com.ong.sias.model.Usuario;
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
            Familia familia = new Familia();

            OperacoesBanco<Familia> pessoaDAO = new FamiliaDAO();

            pessoaDAO.salvar(familia);

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
