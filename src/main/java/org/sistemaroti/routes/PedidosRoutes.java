package org.sistemaroti.routes;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.sistemaroti.model.Pedido;
import org.sistemaroti.model.PedidoDetalles;
import org.sistemaroti.service.PedidoService;


import static spark.Spark.*;
import static spark.Spark.delete;
import static spark.Spark.put;

public class PedidosRoutes {
    private static final ObjectMapper mapper = new ObjectMapper();
    public static void init() {

        PedidoService pedidoService = new PedidoService();
        //path("/pedidos", () -> {
            //Get Todos
          //  get("", (req, res) -> mapper.writeValueAsString(pedidoService.listarProductos()));
        //});

        //Buscar un pedido, con datos y productos.
        get("pedidos/:id", (req, res) -> mapper.writeValueAsString(pedidoService.buscarPedido(Integer.parseInt(req.params(":id")))));

        //Crear producto
        post("/pedidos", (req, res) -> {
            Pedido nuevo = mapper.readValue(req.body(), Pedido.class);
            Pedido creado = pedidoService.crearPedido(nuevo);
            res.type("application/json");
            return mapper.writeValueAsString(creado);
        });
        //Agregar un producto al pedido
        post("/pedidos/agregar-producto", (req, res) -> {
            PedidoDetalles nuevo = mapper.readValue(req.body(), PedidoDetalles.class);
            PedidoDetalles creado = pedidoService.agregarProductoDetalle(nuevo);
            res.type("application/json");
            return mapper.writeValueAsString(creado);
        });

        //Borrar elemento del pedido
        delete("pedidos/quitar-producto/:id", (req, res) -> {
            boolean status = pedidoService.quitarProductoDelPedido(Integer.parseInt(req.params(":id")));
            return status;
        });



    }
}
