package com.ong.sias.controller;

import com.ong.sias.dao.UsuarioDAO;
import com.ong.sias.model.Usuario;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String index() {
        return "login/index";
    }

    @PostMapping("/logar")
    public String processarLogin(@RequestParam String cpf, @RequestParam String senha, HttpSession session, RedirectAttributes redirectAttributes) {
        try {
            String cpfLimpo = cpf.replaceAll("[^0-9]", "");

            UsuarioDAO dao = new UsuarioDAO();
            Usuario usuarioValidado = dao.autenticar(cpfLimpo, senha);

            if (usuarioValidado != null) {
                session.setAttribute("usuarioLogado", usuarioValidado);
                return "redirect:/home";
            } else {
                redirectAttributes.addFlashAttribute("erro", "CPF ou Senha incorretos, ou usuário inativo.");
                return "redirect:/login";
            }
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("erro", "Erro interno ao tentar fazer login.");
            return "redirect:/login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
