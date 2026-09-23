package com.ong.sias.controller;
import com.ong.sias.dao.AgendamentoVisitaDAO;
import com.ong.sias.dao.FamiliaDAO;
import com.ong.sias.dao.OperacoesBanco;
import com.ong.sias.dao.VoluntarioDAO;
import com.ong.sias.dto.AgendamentoVisitaDTO;
import com.ong.sias.model.AgendamentoVisita;
import com.ong.sias.model.Familia;
import com.ong.sias.model.Voluntario;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/agendamentos")
public class AgendamentoVisitaController {

    @GetMapping
    public String index(Model model) {
        model.addAttribute("hxUrlFab", "/agendamentos/novo");
        model.addAttribute("iconeFab", "fa-solid fa-calendar-day");

        try {
            OperacoesBanco<AgendamentoVisita> dao = new AgendamentoVisitaDAO();
            model.addAttribute("agendamentos", dao.listarTodos());
        } catch (SQLException ex) {
            ex.printStackTrace();
            model.addAttribute("agendamentos", new ArrayList<>());
        }

        return "agendamentos/index";
    }

    @GetMapping("/novo")
    public String carregarFormularioNovo(Model model) {
        try {
            OperacoesBanco<Familia> familiaDAO = new FamiliaDAO();
            model.addAttribute("familias", familiaDAO.listarTodos());

            OperacoesBanco<Voluntario> voluntarioDAO = new VoluntarioDAO();
            model.addAttribute("familias", familiaDAO.listarTodos());
            model.addAttribute("voluntarios", voluntarioDAO.listarTodos());

        } catch (SQLException ex) {
            ex.printStackTrace();
            model.addAttribute("familias", new ArrayList<Familia>());
            model.addAttribute("voluntarios", new ArrayList<Voluntario>());
        }

        return "agendamentos/partials/_formCadastroAgendamentoVisita :: formFragment";
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<String> cadastrar(@RequestBody AgendamentoVisitaDTO payload) {
        try {
            if (payload.getCodFamilia() <= 0) {
                return ResponseEntity.badRequest().body("É obrigatório selecionar uma família.");
            }

            if (payload.getTipoVisita() == null || payload.getTipoVisita().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("O tipo de visita não pode ficar em branco.");
            }

            AgendamentoVisita visita = new AgendamentoVisita();
            visita.setCodFamilia(payload.getCodFamilia());

            if (payload.getCodVoluntario() != null && payload.getCodVoluntario() > 0) {
                visita.setCodVoluntario(payload.getCodVoluntario());
            } else {
                visita.setCodVoluntario(null);
            }

            visita.setTipoVisita(payload.getTipoVisita());
            visita.setRealizada(payload.isRealizada());

            visita.setDataHora(new java.util.Date());

            OperacoesBanco<AgendamentoVisita> dao = new AgendamentoVisitaDAO();
            dao.salvar(visita);

            return ResponseEntity.ok("Agendamento registrado com sucesso!");

        } catch (SQLException ex) {
            ex.printStackTrace();
            return ResponseEntity.internalServerError().body("Erro ao salvar no banco de dados.");
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.internalServerError().body("Erro interno: " + ex.getMessage());
        }
    }


    @GetMapping("/editar/{id}")
    public String carregarFormularioEdicao(@PathVariable int id, Model model) {
        try {
            OperacoesBanco<AgendamentoVisita> visitaDAO = new AgendamentoVisitaDAO();
            model.addAttribute("payload", visitaDAO.buscar(id));

            OperacoesBanco<Familia> familiaDAO = new FamiliaDAO();
            model.addAttribute("familias", familiaDAO.listarTodos());

            OperacoesBanco<Voluntario> voluntarioDAO = new VoluntarioDAO();
            model.addAttribute("voluntarios", voluntarioDAO.listarTodos());

            return "agendamentos/partials/_formCadastroAgendamentoVisita :: formFragment";
        } catch (SQLException ex) {
            ex.printStackTrace();
            return "Erro ao buscar agendamento.";
        }
    }

    @PostMapping("/atualizar")
    public ResponseEntity<String> atualizar(@RequestBody AgendamentoVisitaDTO payload) {
        try {
            if (payload.getCodVisita() <= 0) {
                return ResponseEntity.badRequest().body("ID do agendamento inválido.");
            }
            if (payload.getCodFamilia() <= 0) {
                return ResponseEntity.badRequest().body("É obrigatório selecionar uma família.");
            }

            AgendamentoVisita visita = new AgendamentoVisita();
            visita.setCodVisita(payload.getCodVisita());
            visita.setCodFamilia(payload.getCodFamilia());

            if (payload.getCodVoluntario() != null && payload.getCodVoluntario() > 0) {
                visita.setCodVoluntario(payload.getCodVoluntario());
            } else {
                visita.setCodVoluntario(null);
            }

            visita.setTipoVisita(payload.getTipoVisita());
            visita.setRealizada(payload.isRealizada());

            OperacoesBanco<AgendamentoVisita> dao = new AgendamentoVisitaDAO();
            dao.atualizar(visita);

            return ResponseEntity.ok("Agendamento atualizado com sucesso!");

        } catch (SQLException ex) {
            ex.printStackTrace();
            return ResponseEntity.internalServerError().body("Erro ao atualizar no banco de dados.");
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.internalServerError().body("Erro interno: " + ex.getMessage());
        }
    }

    @DeleteMapping("/deletar/{id1}/{id2}/{id3}")
    public ResponseEntity<String> deletar(@PathVariable int id1, @PathVariable int id2, @PathVariable int id3) {
        //id1 = CodFamilia, id2 = CodVoluntario, id3 = CodVisita

        try {
            OperacoesBanco<AgendamentoVisita> dao = new AgendamentoVisitaDAO();
            dao.deletar(id1,id2,id3);
            return ResponseEntity.ok("Agendamento excluído com sucesso.");
        } catch (SQLException ex) {
            ex.printStackTrace();
            return ResponseEntity.internalServerError().body("Erro ao excluir o registro do banco de dados.");
        }
    }
}
