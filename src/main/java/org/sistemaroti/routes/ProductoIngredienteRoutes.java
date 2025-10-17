package org.sistemaroti.routes;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.sistemaroti.model.Ingrediente;
import org.sistemaroti.model.ProductoIngrediente;
import org.sistemaroti.service.IngredienteService;
import org.sistemaroti.service.ProductoIngredienteService;

import static spark.Spark.*;
import static spark.Spark.put;

public class ProductoIngredienteRoutes {
    private static final ObjectMapper mapper = new ObjectMapper();
    public static void init() {
        ProductoIngredienteService productoIngredienteService = new ProductoIngredienteService();
        path("/ingredientes", () -> {
            //Get Todos
            //get("", (req, res) -> mapper.writeValueAsString(productoIngredienteService.listarIngredientes()));


        });
        //Crear Ingrediente
        post("/producto/ingrediente/agregar", (req, res) -> {
            ProductoIngrediente nuevo = mapper.readValue(req.body(), ProductoIngrediente.class);
            String creado = productoIngredienteService.crearIngrediente(nuevo.getProducto_id(),nuevo.getIngrediente_id(),nuevo.getCantidad());
            res.type("application/json");
            return mapper.writeValueAsString(creado);
        });
        //Actualiza Cliente


    }
}
