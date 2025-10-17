package org.sistemaroti.service;

import org.sistemaroti.dao.IngredienteDAO;
import org.sistemaroti.dao.ProductoIngredienteDAO;
import org.sistemaroti.model.Ingrediente;

import java.util.List;

public class ProductoIngredienteService {
    private final ProductoIngredienteDAO dao = new ProductoIngredienteDAO();

    public String crearIngrediente (int p_id, int i_id, double cantidad){
        return dao.agreagarIngredienteProducto(p_id,i_id,cantidad);

    }

}
