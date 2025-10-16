package org.sistemaroti.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Producto {
    int id;
    String nombre;
    String detalle;
    double precio;

}
