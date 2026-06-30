package org.gestaomvc.gestaojogosmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cadastro")
public class CadastroController {

    @GetMapping
    public String exibirCadastro(){
        return "cadastro";
    }

}
