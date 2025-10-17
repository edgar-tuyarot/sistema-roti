package org.sistemaroti.dao;

import org.sistemaroti.db.Conexion;
import org.sistemaroti.model.Cliente;
import org.sistemaroti.model.Ingrediente;
import org.sistemaroti.model.Producto;

import java.sql.*;
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
                producto.setPrecio(buscarCosto(rs.getInt("id")));
                arrayProductos.add(producto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return arrayProductos;
    }


    public double buscarCosto(int id){
        double costo = 0;
        String sql = "SELECT SUM(ip.cantidad * i.precio * 1.5) AS costo_total  FROM productos p " +
                "INNER JOIN ingredientes_productos ip ON p.id = ip.producto_id " +
                "INNER JOIN ingredientes i ON ip.ingrediente_id = i.id WHERE p.id = ?";

        try (PreparedStatement ps = Conexion.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                costo = rs.getDouble("costo_total");
            } else {
                System.out.println("No se encontró costo para el producto con ID: " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }




        return costo;
    }

    public Producto crearProducto (Producto p){
        //Instanciamos el valor del request
        Producto newP = p;
        //Validamos que no exista en bbdd
        Producto pBD = buscarProducto(newP.getNombre());

        //validamos el ID
        if(pBD.getId()>0){
            return pBD;
        }else{
            String sql = "INSERT INTO productos (nombre, detalle) VALUES (?, ?)";

            try (PreparedStatement ps = Conexion.getConnection().prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, newP.getNombre());
                ps.setString(2, newP.getDetalle());
                ps.executeUpdate();
                System.out.println("Producto creado correctamente.");

                // Obtener el ID generado
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        p.setId(rs.getInt(1));
                        System.out.println(p);
                    }
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

            return p;
        }
    }

    public  Producto buscarProducto(String nombre) {
        Producto p = new Producto();
        String sql = "SELECT id, nombre, detalles FROM productos WHERE nombre = '"+nombre+"' AND eliminado=0";

        try (Connection conn = Conexion.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            rs.next();
            p.setId(rs.getInt("id"));
            p.setNombre(rs.getString("nombre"));
            p.setDetalle(rs.getString("detalle"));



        } catch (SQLException e) {
            e.printStackTrace();
        }

        return p;
    }

}
