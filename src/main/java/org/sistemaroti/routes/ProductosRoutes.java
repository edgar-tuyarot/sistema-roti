package org.sistemaroti.routes;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.sistemaroti.model.Cliente;
import org.sistemaroti.model.Producto;
import org.sistemaroti.service.ClienteService;
import org.sistemaroti.service.ProductoService;

import static spark.Spark.*;
import static spark.Spark.delete;
import static spark.Spark.put;

public class ProductosRoutes {
    private static final ObjectMapper mapper = new ObjectMapper();
    public static void init() {
        ProductoService productoService = new ProductoService();
        path("/productos", () -> {
            //Get Todos
            get("", (req, res) -> mapper.writeValueAsString(productoService.listarProductos()));
        });
        //Crear producto
        post("/producto/crear", (req, res) -> {
            Producto nuevo = mapper.readValue(req.body(), Producto.class);
            Producto creado = productoService.crearProducto(nuevo);
            res.type("application/json");
            return mapper.writeValueAsString(creado);
        });
    }
}
