package org.gestaomvc.gestaojogosmvc.controller;

import jakarta.servlet.http.HttpSession;
import org.gestaomvc.gestaojogosmvc.model.Estatisticas;
import org.gestaomvc.gestaojogosmvc.model.Jogo;
import org.gestaomvc.gestaojogosmvc.model.Usuario;
import org.gestaomvc.gestaojogosmvc.service.JogoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/jogo")
public class JogoController{

    private JogoService service = new JogoService();

    @GetMapping
    public String listar(@RequestParam(required = false) String acao,
                         @RequestParam(required = false) Integer id,
                         @RequestParam(required = false) String msg,
                         HttpSession session,
                         Model model){

        Usuario logado = (Usuario) session.getAttribute("usuario");
        if (logado == null){
            return "redirect:/index.jsp";
        }

        if ("editar".equals(acao) && id != null){
            Jogo j = service.buscarId(id, logado.getId());
            model.addAttribute("jogo", j);
        }

        if ("excluir".equals(acao) && id != null){
            service.excluir(id, logado.getId());
            return "redirect:/jogo?msg=excluido";
        }

        List<Jogo> lista = service.listarUsuario(logado.getId());
        model.addAttribute("jogos", lista);

        Estatisticas stats = service.calcularEstatisticas(lista);
        model.addAttribute("stats", stats);
        model.addAttribute("msg", msg);

        return "jogos";
    }

    @PostMapping
    public String salvar(@RequestParam(required = false) Integer id,
                         @RequestParam String nome,
                         @RequestParam String genero,
                         @RequestParam String plataforma,
                         @RequestParam(required = false, defaultValue = "0.0") Double horas,
                         @RequestParam(required = false, defaultValue = "0") Integer nota,
                         @RequestParam(required = false) String jogando,
                         @RequestParam String capa,
                         HttpSession session,
                         Model model,
                         RedirectAttributes redirectAttrs){

        Usuario logado = (Usuario) session.getAttribute("usuario");
        if (logado == null){
            return "redirect:/index.jsp";
        }

        Boolean isJogando = jogando != null && (jogando.equals("true") || jogando.equals("on"));
        Jogo j = new Jogo(logado.getId(), nome, genero, plataforma, horas, nota, isJogando, capa);

        try {
            if (id != null){
                j.setId(id);
                service.atualizar(j);
                redirectAttrs.addAttribute("msg", "editado");
            } else {
                service.atualizar(j);
                redirectAttrs.addAttribute("msg", "salvo");
            }
            return "redirect:/jogo";
        } catch (IllegalArgumentException e) {
            model.addAttribute("erroValidacao", e.getMessage());
            model.addAttribute("jogo", j);

            List<Jogo> lista = service.listarUsuario(logado.getId());
            model.addAttribute("jogos", lista);
            model.addAttribute("stats", service.calcularEstatisticas(lista));
            return "jogos";
        }

    }

}

