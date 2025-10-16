package org.sistemaroti.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.sistemaroti.db.Conexion;
import org.sistemaroti.dto.PedidoClienteDTO;
import org.sistemaroti.model.Cliente;
import org.sistemaroti.model.Pedido;

public class PedidoDAO {

    public List<PedidoClienteDTO> buscarPedidosConCliente() {
        List<PedidoClienteDTO> lista = new ArrayList<>();
        String sql =
        """
                SELECT
                c.nombre AS nombre_cliente,
                p.total,
                e.nombre AS estado,
                c.direccion
                FROM pedidos p
                INNER JOIN clientes c ON p.cliente_id = c.id
                INNER JOIN estado_pedido e ON p.estado_id = e.id
                WHERE p.eliminado = 0
        """;

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                PedidoClienteDTO dto = new PedidoClienteDTO();
                dto.setNombre(rs.getString("nombre_cliente"));
                dto.setTotal(rs.getDouble("total"));
                dto.setEstado(rs.getString("estado"));
                dto.setDireccion(rs.getString("direccion"));
                lista.add(dto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public  Cliente buscarCliente(int id) {
        Cliente c = new Cliente();

        String sql = "SELECT id, nombre, mail,telefono,direccion FROM clientes WHERE id = "+id+" AND eliminado = 0";

        try (Connection conn = Conexion.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            rs.next();
            c.setId(rs.getInt("id"));
            c.setNombre(rs.getString("nombre"));
            c.setTelefono(rs.getString("telefono"));
            c.setMail(rs.getString("mail"));
            c.setDireccion(rs.getString("direccion"));


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return c;
    }
    public  Cliente buscarClienteMail(String mail) {
        Cliente c = new Cliente();

        String sql = "SELECT id, nombre, mail,telefono,direccion FROM clientes WHERE mail = '"+mail+"' AND eliminado = 0";

        try (Connection conn = Conexion.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            rs.next();
            c.setId(rs.getInt("id"));
            c.setNombre(rs.getString("nombre"));
            c.setTelefono(rs.getString("telefono"));
            c.setMail(rs.getString("mail"));
            c.setDireccion(rs.getString("direccion"));


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return c;
    }

    public Cliente crearCliente(Cliente c){
        Cliente newC = c;

        Cliente clienteExistente = buscarClienteMail(c.getMail());

        //validamos el mail
        if(clienteExistente.getId()>0){
            return clienteExistente;
        }else{

            String sql = "INSERT INTO clientes (nombre, mail, telefono, direccion) VALUES (?, ?, ?, ?)";

            try (PreparedStatement ps = Conexion.getConnection().prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, newC.getNombre());
                ps.setString(2, newC.getMail());
                ps.setString(3, newC.getTelefono());
                ps.setString(4, newC.getDireccion());
                ps.executeUpdate();
                System.out.println("Cliente insertado correctamente.");

                // Obtener el ID generado
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        c.setId(rs.getInt(1));
                        System.out.println(c);
                    }
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

            return c;
        }
    }


    public boolean actualizarCliente(Cliente c) {
        String sql = "UPDATE clientes SET nombre = ?, mail = ?, telefono = ?, direccion = ? WHERE id = ?";
        boolean actualizado = false;

        try (PreparedStatement ps = Conexion.getConnection().prepareStatement(sql)) {
            ps.setString(1, c.getNombre());
            ps.setString(2, c.getMail());
            ps.setString(3, c.getTelefono());
            ps.setString(4, c.getDireccion());
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


}