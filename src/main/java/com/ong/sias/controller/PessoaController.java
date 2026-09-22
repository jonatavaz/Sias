package com.ong.sias.controller;

import com.ong.sias.dao.OperacoesBanco;
import com.ong.sias.dao.PessoaDAO;
import com.ong.sias.dao.UsuarioDAO;
import com.ong.sias.dao.VoluntarioDAO;
import com.ong.sias.dto.PessoaDTO;
import com.ong.sias.model.Pessoa;
import com.ong.sias.model.Usuario;
import com.ong.sias.model.Voluntario;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/pessoas")
public class PessoaController {

    @GetMapping
    public String index(Model model){
        model.addAttribute("hxUrlFab", "/pessoas/novo");
        model.addAttribute("iconeFab", "fa-solid fa-user-plus");

        try {
            OperacoesBanco<Pessoa> dao =new PessoaDAO();
            List<Pessoa> listaPessoas = dao.listarTodos();

            model.addAttribute("pessoas", listaPessoas);

        }catch (SQLException ex){
            ex.printStackTrace();
            model.addAttribute("pessoas", new ArrayList<Pessoa>());
        }

        return "pessoa/index";

    }

    @GetMapping("/novo")
    public String formularioNovaPessoa(){
        return "pessoa/partials/_formCadastroPessoa";
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<String> cadastrar(@RequestBody PessoaDTO payload){
        try {

            Pessoa pessoa = new Pessoa();
            pessoa.setNome(payload.getNome());
            pessoa.setCpf(payload.getCpf());
            pessoa.setTelefone(payload.getTelefone());
            pessoa.setEmail(payload.getEmail());
            pessoa.setDataNascimento(payload.getDataNascimento());

            OperacoesBanco<Pessoa> pessoaDAO = new PessoaDAO();

            pessoaDAO.salvar(pessoa);

            if (payload.isPessoaVoluntario()){
                Usuario usuario = new Usuario();
                usuario.setCodPessoa(pessoa.getCodPessoa());
                usuario.setAtivo(true);

                OperacoesBanco<Usuario> usuarioDAO = new UsuarioDAO();
                usuarioDAO.salvar(usuario);

                Voluntario voluntario = new Voluntario();
                voluntario.setCodPessoa(pessoa.getCodPessoa());

                if (payload.getVoluntario() != null) {
                    voluntario.setProfissaoHabilidade(payload.getVoluntario().getProfissaoHabilidade());
                    voluntario.setAtivo(payload.getVoluntario().getAtivo());
                } else {
                    voluntario.setAtivo(true);
                }

                OperacoesBanco<Voluntario> voluntarioDAO = new VoluntarioDAO();
                voluntarioDAO.salvar(voluntario);
            }

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
    public String carregarFormularioEdicao(@PathVariable int id, Model model){
        try{
            OperacoesBanco<Pessoa> dao = new PessoaDAO();
            Pessoa pessoa = dao.buscar(id);

            model.addAttribute("pessoa", pessoa);

            return "pessoa/partials/_formCadastroPessoa :: formFragment";

        }catch (SQLException ex){
            ex.printStackTrace();
            return "Erro ao buscar pessoa.";
        }
    }

    @PostMapping("/atualizar")
    public String atualizar(Pessoa pessoa) {
        try {
            OperacoesBanco<Pessoa> dao = new PessoaDAO();

            dao.atualizar(pessoa);

            return "redirect:/pessoas";

        } catch (SQLException ex) {
            ex.printStackTrace();
            return "Erro ao atualizar pessoa.";
        }
    }

    @DeleteMapping("/deletar/{id1}/{id2}/{id3}")
    public ResponseEntity<String> deletar(@PathVariable int id1,@PathVariable int id2,@PathVariable  int id3, Model model){
        //id1 = CodONG, id2 = CodPessoa, id3 = CodUsuario

        try{
            OperacoesBanco<Usuario> usuarioDAO = new UsuarioDAO();
            usuarioDAO.deletar(id1, id2, id3);

            OperacoesBanco<Pessoa> pessoaDAO = new PessoaDAO();
            pessoaDAO.deletar(id1, id2);

            return ResponseEntity.ok("Cadastro excluído com sucesso.");

        }catch (SQLException ex){
            ex.printStackTrace();
            return ResponseEntity.internalServerError().body("Erro ao excluir o registro do banco de dados.");
        }
    }
}
