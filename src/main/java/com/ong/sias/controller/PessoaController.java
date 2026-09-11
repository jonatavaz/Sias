package com.ong.sias.controller;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pessoas")
public class PessoaController {

    @GetMapping
    public String index(Model model){
        model.addAttribute("hxUrlFab", "/pessoas/novo");
        model.addAttribute("iconeFab", "fa-solid fa-user-plus");

        return "pessoa/index";

    }

    @GetMapping("/novo")
    public String formularioNovaPessoa(){
        return "pessoa/partials/_formCadastroPessoa";
    }
}
