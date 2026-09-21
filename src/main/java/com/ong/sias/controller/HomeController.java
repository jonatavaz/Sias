package com.ong.sias.controller;

import com.ong.sias.dao.DashboardDAO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String raiz() {
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String index(Model model){
        try {
            DashboardDAO dashboardDAO = new DashboardDAO();

            model.addAttribute("totalFamilias", dashboardDAO.contarFamilias());
            model.addAttribute("necessidadesPendentes", dashboardDAO.contarNecessidadesPendentes());
            model.addAttribute("necessidadesAtendidas", dashboardDAO.contarNecessidadesAtendidas());
            model.addAttribute("visitasPendentes", dashboardDAO.contarVisitasPendentes());
            model.addAttribute("visitasRealizadas", dashboardDAO.contarVisitasRealizadas());

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "home/index";
    }
}
