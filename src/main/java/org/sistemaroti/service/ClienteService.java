package org.sistemaroti.service;

import org.sistemaroti.dao.*;
import org.sistemaroti.model.Cliente;

import java.util.List;


public class ClienteService {
    private final ClienteDAO  dao = new ClienteDAO();

    public List<Cliente> listarUsuarios() {
        return dao.buscarTodos();
    }

    public Cliente buscarCliente(int id){
        return dao.buscarCliente(id);
    }

    public Cliente crearCliente(Cliente c){
        return dao.crearCliente(c);
    }

    public boolean actualizarCliente(Cliente c){
        return dao.actualizarCliente(c);
    }

    public boolean borrarCliente(int id){
        return dao.borrar(id);
    }

}
