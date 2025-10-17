package org.sistemaroti.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductoIngrediente {
    int id;
    int producto_id;
    int ingrediente_id;
    double cantidad;

}
