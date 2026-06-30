package org.gestaomvc.gestaojogosmvc.controller;

import org.gestaomvc.gestaojogosmvc.model.Usuario;
import org.gestaomvc.gestaojogosmvc.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping
public class UsuarioController {

    private UsuarioService service = new UsuarioService();

    @PostMapping
    public String cadastrar(@RequestParam String nome,
                            @RequestParam String email,
                            @RequestParam String senha,
                            Model model){

        Usuario u = new Usuario(nome, email, senha);
        boolean sucesso = service.inserir(u);

        if (sucesso){
            return "redirect:/login";
        } else {
            model.addAttribute("erro", "Erro ao cadastrar usuario");
            return "cadastro";
        }

    }

}
