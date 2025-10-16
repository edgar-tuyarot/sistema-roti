package org.sistemaroti.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ingrediente {
    int id;
    String nombre;
    String marca;
    double precio;
    double cantidad;
}
