package org.sistemaroti.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoClienteDTO {
    int id;
    String nombre;
    private Timestamp fecha;
    private String estado;
    private String direccion;
    private String telefono;
    private double monto;
    private String fechaFormateada;
    
}