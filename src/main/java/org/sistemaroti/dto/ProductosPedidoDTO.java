package org.sistemaroti.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductosPedidoDTO {

    private int id;
    private String producto;
    private int cantidad;
    private double monto;

}
