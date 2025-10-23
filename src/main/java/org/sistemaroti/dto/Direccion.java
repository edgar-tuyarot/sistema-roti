package org.sistemaroti.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Direccion {
    int id;
    int id_cliente;
    String direccion;
}
