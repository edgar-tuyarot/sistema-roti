package org.sistemaroti.service;

import org.sistemaroti.dao.ProductoDAO;
import org.sistemaroti.model.Cliente;
import org.sistemaroti.model.Producto;

import java.util.List;

public class ProductoService {
    private final ProductoDAO dao = new ProductoDAO();

    public List<Producto> listarProductos() {
        return dao.buscarTodos();
    }



}
