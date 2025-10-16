package org.sistemaroti.model;

import lombok.Data;

@Data
public class Pedido {
    private int id;
    private int cliente_id;
    private int estado_id;
    private double total;
}
