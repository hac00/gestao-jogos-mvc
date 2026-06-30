package org.gestaomvc.gestaojogosmvc.service;

import org.gestaomvc.gestaojogosmvc.dao.UsuarioDAO;
import org.gestaomvc.gestaojogosmvc.model.Usuario;

public class UsuarioService {

    UsuarioDAO dao = new UsuarioDAO();

    public boolean inserir(Usuario u){
        return dao.inserir(u);
    }

}
