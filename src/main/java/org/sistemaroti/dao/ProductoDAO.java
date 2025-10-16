package org.sistemaroti.dao;

import org.sistemaroti.db.Conexion;
import org.sistemaroti.model.Cliente;
import org.sistemaroti.model.Producto;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {
    public List<Producto> buscarTodos() {
        List<Producto> arrayProductos = new ArrayList<>();
        String sql = "SELECT id, nombre, detalle, precio FROM productos WHERE eliminado = 0";

        try (Connection conn = Conexion.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Producto producto = new Producto();
                producto.setId(rs.getInt("id"));
                producto.setNombre(rs.getString("nombre"));
                producto.setDetalle(rs.getString("detalle"));
                producto.setPrecio(rs.getDouble("precio"));
                arrayProductos.add(producto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return arrayProductos;
    }


}
