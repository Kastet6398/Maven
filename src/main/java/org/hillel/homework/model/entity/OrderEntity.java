package org.hillel.homework.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderEntity implements Entity {
    private int id;
    private String customerName;
    private List<ProductEntity> products;
}
