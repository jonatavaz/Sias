package com.ong.sias.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
