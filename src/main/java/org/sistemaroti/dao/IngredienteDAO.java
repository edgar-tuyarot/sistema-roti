package org.sistemaroti.dao;

import org.sistemaroti.db.Conexion;
import org.sistemaroti.model.Cliente;
import org.sistemaroti.model.Ingrediente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IngredienteDAO {

    public List<Ingrediente> buscarTodos() {
        List<Ingrediente> arrayIngredientes = new ArrayList<>();
        String sql = "SELECT id, nombre, marca, precio FROM ingredientes WHERE eliminado = 0";

        try (Connection conn = Conexion.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Ingrediente i = new Ingrediente();
                i.setId(rs.getInt("id"));
                i.setNombre(rs.getString("nombre"));
                i.setMarca(rs.getString("marca"));
                i.setPrecio(rs.getDouble("precio"));
                arrayIngredientes.add(i);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return arrayIngredientes;
    }


    public  Ingrediente buscarIngrediente(String nombre, String marca) {
        Ingrediente i = new Ingrediente();
        String sql = "SELECT id, nombre, marca FROM ingredientes WHERE marca = '"+marca+"' AND nombre = '"+nombre+"' AND eliminado=0";

        try (Connection conn = Conexion.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            rs.next();
            i.setId(rs.getInt("id"));
            i.setNombre(rs.getString("nombre"));
            i.setMarca(rs.getString("marca"));
            i.setPrecio(rs.getDouble("precio"));


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return i;
    }

    public Ingrediente crearIngrediente(Ingrediente i){

        //Instanciamos el valor del request
        Ingrediente newI = i;
        //Validamos que no exista en bbdd
        Ingrediente ingBD = buscarIngrediente(newI.getNombre(), newI.getMarca());

        //validamos el ID
        if(ingBD.getId()>0){
            return ingBD;
        }else{
            String sql = "INSERT INTO ingredientes (nombre, marca, precio) VALUES (?, ?, ?)";

            try (PreparedStatement ps = Conexion.getConnection().prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, newI.getNombre());
                ps.setString(2, newI.getMarca());
                ps.setDouble(3, newI.getPrecio());
                ps.executeUpdate();
                System.out.println("Ingrediente insertado correctamente.");

                // Obtener el ID generado
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        i.setId(rs.getInt(1));
                        System.out.println(i);
                    }
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

            return i;
        }
    }


    public boolean actualizarIngrediente(Ingrediente i) {
        String sql = "UPDATE ingredientes SET nombre = ?, marca = ?, precio = ? WHERE id = ?";
        boolean actualizado = false;

        try (PreparedStatement ps = Conexion.getConnection().prepareStatement(sql)) {
            ps.setString(1, i.getNombre());
            ps.setString(2, i.getMarca());
            ps.setDouble(3, i.getPrecio());
            ps.setInt(4, i.getId());  // ID del cliente a actualizar

            int filas = ps.executeUpdate();
            actualizado = (filas > 0);
            if (actualizado) System.out.println("Ingrediente actualizado correctamente.");

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return actualizado;
    }

    public boolean borrar(int id) {
        String sql = "UPDATE clientes SET eliminado = 1 WHERE id = ?";
        boolean actualizado = false;

        try (PreparedStatement ps = Conexion.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            int filas = ps.executeUpdate();
            actualizado = (filas > 0);
            System.out.println("Cliente Borrar correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return actualizado;
    }

}
