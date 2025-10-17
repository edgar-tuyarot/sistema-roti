package org.sistemaroti;

import org.sistemaroti.db.Conexion;
import org.sistemaroti.routes.ClientesRoutes;
import org.sistemaroti.routes.IngredientesRoutes;
import org.sistemaroti.routes.ProductoIngredienteRoutes;
import org.sistemaroti.routes.ProductosRoutes;

import static spark.Spark.port;

public class Main {
    public static void main(String[] args) {
        Conexion.getConnection();
        port(8080);
        ClientesRoutes.init();
        ProductosRoutes.init();
        IngredientesRoutes.init();
        ProductoIngredienteRoutes.init();


    }
}