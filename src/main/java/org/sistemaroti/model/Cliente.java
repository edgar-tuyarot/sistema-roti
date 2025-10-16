package org.sistemaroti.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {

    private int id;
    private String nombre;
    private String direccion;
    private String telefono;
    private String mail;



}
