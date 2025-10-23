package org.sistemaroti.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sistemaroti.dto.Direccion;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {

    private int id;
    private String nombre;
    private String telefono;
    private String mail;
    private List<Direccion> direcciones;
    private String direccion_inicial;



}
