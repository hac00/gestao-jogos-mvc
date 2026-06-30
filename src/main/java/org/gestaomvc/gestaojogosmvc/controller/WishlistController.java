package org.gestaomvc.gestaojogosmvc.controller;

import jakarta.servlet.http.HttpSession;
import org.gestaomvc.gestaojogosmvc.model.Usuario;
import org.gestaomvc.gestaojogosmvc.model.Wishlist;
import org.gestaomvc.gestaojogosmvc.service.WishlistService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/wishlist")
public class WishlistController {

    private WishlistService service = new WishlistService();

    @GetMapping
    public String listar(@RequestParam(required = false) String acao,
                         @RequestParam(required = false) Integer id,
                         @RequestParam(required = false) String msg,
                         HttpSession session,
                         Model model){

        Usuario logado = (Usuario) session.getAttribute("usuario");
        if (logado == null){
            return "redirect:/login";
        }

        if ("editar".equals(acao) && id != null){
            Wishlist w = service.buscarId(id, logado.getId());
            model.addAttribute("itemWishlist", w);
        }

        if ("excluir".equals(acao) && id != null){
            service.excluir(id, logado.getId());
            return "redirect:/wishlist?msg=excluido";
        }

        List<Wishlist> lista = service.listarPorUsuario(logado.getId());
        model.addAttribute("itensWishlist", lista);
        model.addAttribute("msg", msg);

        return "wishlist";
    }

    @PostMapping
    public String salvar(@RequestParam(required = false) Integer id,
                         @RequestParam String nome,
                         @RequestParam String plataforma,
                         @RequestParam(required = false, defaultValue = "0.0") Double precoMaximo,
                         HttpSession session,
                         Model model,
                         RedirectAttributes redirectAttrs){

        Usuario logado = (Usuario) session.getAttribute("usuario");
        if (logado == null){
            return "redirect:/login";
        }

        Wishlist w = new Wishlist(logado.getId(), nome, plataforma, precoMaximo);

        try {
            if (id != null){
                w.setId(id);
                service.atualizar(w);
                redirectAttrs.addAttribute("msg", "editado");
            } else {
                service.inserir(w);
                redirectAttrs.addAttribute("msg", "salvo");
            }
            return "redirect:/wishlist";
        } catch (IllegalArgumentException e) {
            model.addAttribute("erroValidacao", e.getMessage());
            model.addAttribute("itemWishlist", w);

            List<Wishlist> lista = service.listarPorUsuario(logado.getId());
            model.addAttribute("itensWishlist", lista);

            return "wishlist";
        }

    }

}
