package org.sistemaroti.dao;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.sistemaroti.db.Conexion;
import org.sistemaroti.dto.Direccion;
import org.sistemaroti.dto.PedidoClienteDTO;
import org.sistemaroti.model.Cliente;

public class ClienteDAO {


    public List<Direccion> buscarDirecciones(int id){
        List<Direccion> direcciones = new ArrayList<>();

        String sql = "SELECT id, cliente_id,direccion  FROM direcciones_cliente WHERE eliminado = 0 AND cliente_id = "+id;

        try (Connection conn = Conexion.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {


            while (rs.next()) {
                Direccion dir = new Direccion();
                dir.setId(rs.getInt("id"));
                dir.setId_cliente(rs.getInt("cliente_id"));
                dir.setDireccion(rs.getString("direccion"));

                direcciones.add(dir);
                System.out.println(dir);
            }




        } catch (SQLException e) {
            e.printStackTrace();
        }

        return direcciones;

    }

    public  List<Cliente> buscarTodos() {
        List<Cliente> arrayClientes = new ArrayList<>();
        String sql = "SELECT id, nombre, mail, telefono FROM clientes WHERE eliminado = 0";

        try (Connection conn = Conexion.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Cliente c = new Cliente();
                c.setId(rs.getInt("id"));
                c.setNombre(rs.getString("nombre"));
                c.setTelefono(rs.getString("telefono"));
                c.setMail(rs.getString("mail"));
                c.setDirecciones(buscarDirecciones(rs.getInt("id")));
                arrayClientes.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return arrayClientes;
    }

    public  Cliente buscarCliente(int id) {
        Cliente c = new Cliente();

        String sql = "SELECT id, nombre, mail,telefono FROM clientes WHERE id = "+id+" AND eliminado = 0";

        try (Connection conn = Conexion.getConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql)) {
            rs.next();
            c.setId(rs.getInt("id"));
            c.setNombre(rs.getString("nombre"));
            c.setTelefono(rs.getString("telefono"));
            c.setMail(rs.getString("mail"));
            c.setDirecciones(buscarDirecciones(c.getId()));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return c;
    }

    public  Cliente buscarClienteMail(String mail) {
        Cliente c = new Cliente();

        String sql = "SELECT id, nombre, mail,telefono FROM clientes WHERE mail = '"+mail+"' AND eliminado = 0";

        try (Connection conn = Conexion.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            rs.next();
            c.setId(rs.getInt("id"));
            c.setNombre(rs.getString("nombre"));
            c.setTelefono(rs.getString("telefono"));
            c.setMail(rs.getString("mail"));


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return c;
    }

    //Crear cliente
    public Cliente crearCliente(Cliente c){
        Cliente newC = c;
        boolean completo = false;

        Cliente clienteExistente = buscarClienteMail(c.getMail());

        //validamos el mail
        if(clienteExistente.getId()>0){
            return clienteExistente;
        }else{

        String sql = "INSERT INTO clientes (nombre, mail, telefono) VALUES (?, ?, ?)";

        try (PreparedStatement ps = Conexion.getConnection().prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, newC.getNombre());
            ps.setString(2, newC.getMail());
            ps.setString(3, newC.getTelefono());
            ps.executeUpdate();
            System.out.println("Cliente insertado correctamente.");


            // Obtener el ID generado
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    c.setId(rs.getInt(1));
                    System.out.println(c);

                    if (c.getId()>0){
                       completo = agregarDireccion(c.getId(),c.getDireccion_inicial());
                    }
                }

            }



        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (completo){
            return c;
        }else{
            return null;
        }

        }
    }


    public boolean actualizarCliente(Cliente c) {
        String sql = "UPDATE clientes SET nombre = ?, mail = ?, telefono = ? WHERE id = ?";
        boolean actualizado = false;

        try (PreparedStatement ps = Conexion.getConnection().prepareStatement(sql)) {
            ps.setString(1, c.getNombre());
            ps.setString(2, c.getMail());
            ps.setString(3, c.getTelefono());
            ps.setInt(5, c.getId());  // ID del cliente a actualizar

            int filas = ps.executeUpdate();
            actualizado = (filas > 0);
            System.out.println("Cliente actualizado correctamente.");
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

    public boolean agregarDireccion (int id, String direccion){

        String sql = "INSERT INTO direcciones_cliente (cliente_id, direccion) VALUES (?,?)";
        try (PreparedStatement ps = Conexion.getConnection().prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, id);
            ps.setString(2, direccion);
            ps.executeUpdate();
            return true;




        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }



    }



}