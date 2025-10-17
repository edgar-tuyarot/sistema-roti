package org.sistemaroti.dao;

import org.sistemaroti.db.Conexion;
import org.sistemaroti.model.Ingrediente;
import org.sistemaroti.model.Producto;
import org.sistemaroti.model.ProductoIngrediente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoIngredienteDAO {
    public List<ProductoIngrediente> buscarTodos() {
        List<ProductoIngrediente> arrayProductos = new ArrayList<>();
        String sql = "SELECT id, producto_id, ingrediente_id, cantidad FROM ingredientes_productos WHERE eliminado = 0";

        try (Connection conn = Conexion.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                ProductoIngrediente producto = new ProductoIngrediente();
                producto.setId(rs.getInt("id"));
                producto.setIngrediente_id(rs.getInt("ingrediente_id"));
                producto.setProducto_id(rs.getInt("producto_id"));
                producto.setCantidad(rs.getDouble("cantidad"));
                arrayProductos.add(producto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return arrayProductos;
    }

    public String agreagarIngredienteProducto(int producto_id, int ingrediente_id, double cantidad){
            String sql = "INSERT INTO ingredientes_productos (producto_id, ingrediente_id, cantidad) VALUES (?, ?, ?)";

            try (PreparedStatement ps = Conexion.getConnection().prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, producto_id );
                ps.setInt(2, ingrediente_id);
                ps.setDouble(3, cantidad);
                ps.executeUpdate();
                System.out.println("Ingrediente agregado.");


            } catch (SQLException e) {
                e.printStackTrace();
            }

            return "Ingrediente agregado";
        }
}
