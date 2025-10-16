package org.sistemaroti.routes;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.sistemaroti.model.Cliente;
import org.sistemaroti.model.Ingrediente;
import org.sistemaroti.service.IngredienteService;
import org.sistemaroti.service.ProductoService;

import static spark.Spark.*;

public class IngredientesRoutes {
    private static final ObjectMapper mapper = new ObjectMapper();
    public static void init() {
        IngredienteService ingredienteService = new IngredienteService();
        path("/ingredientes", () -> {
            //Get Todos
            get("", (req, res) -> mapper.writeValueAsString(ingredienteService.listarIngredientes()));


        });
        //Crear Ingrediente
        post("/ingredientes/crear", (req, res) -> {
            Ingrediente nuevo = mapper.readValue(req.body(), Ingrediente.class);
            Ingrediente creado = ingredienteService.crearIngrediente(nuevo);
            res.type("application/json");
            return mapper.writeValueAsString(creado);
        });
        //Actualiza Cliente
        put("/ingredientes/actualizar", (req, res) -> {
            Ingrediente ingActualizado = mapper.readValue(req.body(), Ingrediente.class);
            Ingrediente status= ingredienteService.actualizarIngrediente(ingActualizado);
            res.type("application/json");
            return mapper.writeValueAsString(status);
        });


    }
}
