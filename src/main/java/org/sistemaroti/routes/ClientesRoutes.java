package org.sistemaroti.routes;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.sistemaroti.model.Cliente;
import org.sistemaroti.service.ClienteService;

import static spark.Spark.*;

public class ClientesRoutes {
    private static final ObjectMapper mapper = new ObjectMapper();
    public static void init() {
        ClienteService clientes = new ClienteService();
        path("/clientes", () -> {
            //Get Todos
            get("", (req, res) -> mapper.writeValueAsString(clientes.listarUsuarios()));

            //Get cliente
            get("/ver/:id", (req, res) -> {
                res.type("application/json");
                int id = Integer.parseInt(req.params("id"));
                return mapper.writeValueAsString(clientes.buscarCliente(id));
            });


            //Crear cliente
            post("/crear", (req, res) -> {
                Cliente nuevo = mapper.readValue(req.body(), Cliente.class);
                Cliente creado = clientes.crearCliente(nuevo);
                res.type("application/json");
                return mapper.writeValueAsString(creado);
            });
            //Actualiza Cliente
            put("/actualizar", (req, res) -> {
                Cliente actualizado = mapper.readValue(req.body(), Cliente.class);
                boolean status= clientes.actualizarCliente(actualizado);
                res.type("application/json");
                return status;
            });
            //Borrar Cliente
            delete("/borrar/:id", (req, res) -> {
                 boolean status = clientes.borrarCliente(Integer.parseInt(req.params(":id")));
                  return status;
            });
        });
    }
}