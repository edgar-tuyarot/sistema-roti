package org.sistemaroti.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDetalles {
    int id;
    int pedido_id;
    int producto_id;
    int cantidad;
    double monto;
}
