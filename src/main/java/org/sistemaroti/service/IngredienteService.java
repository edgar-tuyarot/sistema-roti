package org.sistemaroti.service;

import org.sistemaroti.dao.IngredienteDAO;
import org.sistemaroti.model.Ingrediente;

import java.util.List;

public class IngredienteService {
    private final IngredienteDAO dao = new IngredienteDAO();

    public List<Ingrediente> listarIngredientes() {
        return dao.buscarTodos();
    }

    public Ingrediente crearIngrediente (Ingrediente i){
        return dao.crearIngrediente(i);

    }

    public Ingrediente actualizarIngrediente(Ingrediente i){
        if(dao.actualizarIngrediente(i)){
            return i;
        }
        return dao.buscarIngrediente(i.getNombre(),i.getMarca());
    }

}
