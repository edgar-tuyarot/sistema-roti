package org.sistemaroti.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoClienteDTO {
    private String nombre;
    private double total;
    private String estado;
    private String direccion;
}