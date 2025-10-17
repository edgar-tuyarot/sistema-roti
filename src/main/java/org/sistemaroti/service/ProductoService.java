package org.sistemaroti.service;

import org.sistemaroti.dao.IngredienteDAO;
import org.sistemaroti.dao.ProductoDAO;
import org.sistemaroti.dao.ProductoIngredienteDAO;
import org.sistemaroti.model.Cliente;
import org.sistemaroti.model.Ingrediente;
import org.sistemaroti.model.Producto;
import org.sistemaroti.model.ProductoIngrediente;

import java.util.ArrayList;
import java.util.List;

public class ProductoService {
    private final ProductoDAO dao = new ProductoDAO();
    public List<Producto> listarProductos(){
        return dao.buscarTodos();
    }
    public Producto  crearProducto(Producto p){
        return dao.crearProducto(p);
    }



}
