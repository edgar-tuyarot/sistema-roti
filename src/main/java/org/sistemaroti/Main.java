package org.sistemaroti;

import org.sistemaroti.db.Conexion;
import org.sistemaroti.routes.*;

import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {
        Conexion.getConnection();
        port(8080);
        options("/*", (request, response) -> {
            String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }

            String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }

            return "OK";
        });

        before((request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            response.header("Access-Control-Allow-Headers", "Content-Type, Authorization");
        });
        ClientesRoutes.init();
        ProductosRoutes.init();
        IngredientesRoutes.init();
        ProductoIngredienteRoutes.init();
        PedidosRoutes.init();


    }
}