package org.sistemaroti.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sistemaroti.model.Ingrediente;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoIngredienteDTO {
   int producto_id;
   int ingrediente_id;
   String ingrediente_nombre;
   String ingrediente_marca;
   double cantidad_kg;
   double costo_ingrediente;
}
