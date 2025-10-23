package org.sistemaroti.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.sistemaroti.db.Conexion;
import org.sistemaroti.dto.PedidoClienteDTO;
import org.sistemaroti.dto.ProductosPedidoDTO;
import org.sistemaroti.model.Pedido;
import org.sistemaroti.model.PedidoDetalles;

public class PedidoDAO {

    //Crear Pedido
    public Pedido crearPedido(Pedido p){

            String sql = "INSERT INTO pedidos (cliente_id, direccion, estado_id) VALUES (?, ?, ?)";

            try (PreparedStatement ps = Conexion.getConnection().prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, p.getCliente_id());
                ps.setString(2,p.getDireccion());
                ps.setInt(3, p.getEstado_id());

                ps.executeUpdate();
                System.out.println("Pedido creado.");

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



    //Rertorna lista de id /  nombre / cantidad  / monto de cada producto de un pedido
    public List<ProductosPedidoDTO> buscarProductosPedido(int id) {
        List<ProductosPedidoDTO> arrayProductosPedido = new ArrayList<>();

        String sql = "SELECT\n" +
                "    pr.nombre AS producto_nombre,\n" +
                " dp.id as id,  \n"+
                "    dp.cantidad,\n" +
                "    dp.monto\n" +
                "FROM clientes c\n" +
                "JOIN pedidos p ON c.id = p.cliente_id\n" +
                "JOIN detalles_pedido dp ON p.id = dp.pedido_id\n" +
                "JOIN productos pr ON dp.producto_id = pr.id\n" +
                "JOIN estado_pedido ep on p.estado_id = ep.id\n" +
                "WHERE p.id = "+id+";";

        try (Connection conn = Conexion.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                ProductosPedidoDTO productosPedidoDTO = new ProductosPedidoDTO();
                productosPedidoDTO.setId(rs.getInt("id"));
                productosPedidoDTO.setProducto(rs.getString("producto_nombre"));
                productosPedidoDTO.setCantidad(rs.getInt("cantidad"));
                productosPedidoDTO.setMonto(rs.getDouble("monto"));

                arrayProductosPedido.add(productosPedidoDTO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return arrayProductosPedido;
    }

    //Buscar Pedido por id
    public PedidoClienteDTO buscarPedido(int id) {
        PedidoClienteDTO pedidoClienteDTO = new PedidoClienteDTO();
        String sql = "SELECT\n" +
                "    p.id AS id,\n" +
                "    c.nombre,\n" +
                "    p.fecha_creacion AS fecha,\n" +
                "    ep.nombre AS estado,\n" +
                "    p.direccion AS direccion,\n" +
                "    c.telefono AS cliente_telefono,\n" +
                "    SUM(dp.monto) AS total_pedido\n" +
                "FROM pedidos p\n" +
                "JOIN clientes c ON c.id = p.cliente_id\n" +
                "JOIN estado_pedido ep ON p.estado_id = ep.id\n" +
                "LEFT JOIN detalles_pedido dp ON p.id = dp.pedido_id\n" +
                "WHERE p.id = " + id + " \n" +
                "GROUP BY p.id, c.nombre, p.fecha_creacion, ep.nombre, p.direccion, c.telefono;";
        try (Connection conn = Conexion.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            rs.next();
            pedidoClienteDTO.setId(rs.getInt("id"));
            pedidoClienteDTO.setNombre(rs.getString("nombre"));
            pedidoClienteDTO.setTelefono(rs.getString("cliente_telefono"));
            pedidoClienteDTO.setFecha(rs.getTimestamp("fecha"));
            pedidoClienteDTO.setDireccion(rs.getString("direccion"));
            pedidoClienteDTO.setEstado(rs.getString("estado"));
            pedidoClienteDTO.setMonto(rs.getDouble("total_pedido"));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pedidoClienteDTO;
    }

    //Agregar producot al pedido
    public PedidoDetalles agregarProductoPedido(PedidoDetalles pd){

            String sql = "INSERT INTO detalles_pedido (pedido_id, producto_id, cantidad, monto) VALUES (?, ?, ?, ?)";

            try (PreparedStatement ps = Conexion.getConnection().prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, pd.getPedido_id());
                ps.setInt(2, pd.getProducto_id());
                ps.setInt(3, pd.getCantidad());
                ps.setDouble(4, pd.getMonto());
                ps.executeUpdate();
                System.out.println("Producto agregado al pedido.");

                // Obtener el ID generado
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        pd.setId(rs.getInt(1));
                        System.out.println(pd);
                    }
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

            return pd;

    }

    public boolean  quitarProductoDelPedido(int idProducto){


        String sql = "DELETE FROM detalles_pedido WHERE id = ?";
        boolean actualizado = false;
        try (PreparedStatement ps = Conexion.getConnection().prepareStatement(sql)) {
            ps.setInt(1, idProducto);
            int filas = ps.executeUpdate();
            actualizado = (filas > 0);
            System.out.println("Pedido Actualizado.");
        } catch (SQLException e) {
            e.printStackTrace();
        }





        return actualizado;
    }



    public Pedido cambiarEstado(Pedido p) {
        String sql = "UPDATE pedidos SET estado_id = ? WHERE id = ?";


        try (PreparedStatement ps = Conexion.getConnection().prepareStatement(sql)) {
            ps.setInt(1,p.getEstado_id());
            ps.setInt(2, p.getId());
            int filas = ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return p;
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