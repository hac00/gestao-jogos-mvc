package org.gestaomvc.gestaojogosmvc.controller;

import jakarta.servlet.http.HttpSession;
import org.gestaomvc.gestaojogosmvc.model.Usuario;
import org.gestaomvc.gestaojogosmvc.service.LoginService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/login")
public class LoginController {

    private LoginService service = new LoginService();

    @GetMapping("/")
    public String index(){
        return "redirect:/login";
    }

    @GetMapping
    public String exibirLogin(){
        return "index";
    }

    @PostMapping
    public String autenticar(@RequestParam String email,
                             @RequestParam String senha,
                             HttpSession session,
                             Model model){

        Usuario usuario = service.autenticar(email, senha);

        if (usuario != null){
            session.setAttribute("usuario", usuario);
            return "redirect:/produto";
        } else {
            model.addAttribute("erro", "Usuario ou senha invalidos");
            return "index";
        }
    }

}
