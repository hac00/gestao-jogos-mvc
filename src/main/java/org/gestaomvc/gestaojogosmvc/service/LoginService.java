package org.gestaomvc.gestaojogosmvc.service;

import org.gestaomvc.gestaojogosmvc.dao.UsuarioDAO;
import org.gestaomvc.gestaojogosmvc.model.Usuario;

public class LoginService {

    private UsuarioDAO dao = new UsuarioDAO();

    public Usuario autenticar(String email, String senha){
        try {
            return dao.autenticar(email, senha);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}

