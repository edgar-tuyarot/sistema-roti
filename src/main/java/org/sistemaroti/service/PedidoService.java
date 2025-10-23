package org.sistemaroti.service;

import org.sistemaroti.dao.PedidoDAO;
import org.sistemaroti.dao.ProductoDAO;
import org.sistemaroti.dto.PedidoDTO;
import org.sistemaroti.model.Cliente;
import org.sistemaroti.model.Pedido;
import org.sistemaroti.model.PedidoDetalles;


public class PedidoService {
    private final PedidoDAO dao = new PedidoDAO();
    private final ProductoDAO pDao = new ProductoDAO();


    public Pedido crearPedido (Pedido p){
        return dao.crearPedido(p);

    }

    public PedidoDetalles agregarProductoDetalle(PedidoDetalles pd) {
        pd.setMonto(pDao.buscarCosto(pd.getProducto_id())*pd.getCantidad());
        return dao.agregarProductoPedido(pd);
    }

    public PedidoDTO buscarPedido(int id){
        PedidoDTO pDTO = new PedidoDTO();
        pDTO.setDatos(dao.buscarPedido(id));
        pDTO.setProductos(dao.buscarProductosPedido(id));

        return pDTO;
    }

    public boolean quitarProductoDelPedido(int idProducto){
        return dao.quitarProductoDelPedido(idProducto);
    }

    public PedidoDTO cambiarEstado(Pedido p){

        return buscarPedido(dao.cambiarEstado(p).getId());
    }


}
