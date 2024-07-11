package org.hillel.homework.model.mapper;

import org.hillel.homework.model.dto.ProductDTO;
import org.hillel.homework.model.entity.ProductEntity;
import org.hillel.homework.model.entity.ProductEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class ProductMapper implements Mapper<ProductEntity, ProductDTO> {

    @Override
    public ProductDTO entityToDTO(ProductEntity entity) {
        if (entity == null) {
            throw new NullPointerException();
        }
        return new ProductDTO(entity.getName(), entity.getPrice());
    }

    public ProductEntity dtoToEntity(int id, ProductDTO dto) {
        if (dto == null) {
            throw new NullPointerException();
        }
        return new ProductEntity(id, dto.getName(), dto.getPrice());
    }

    @Override
    public ProductEntity dtoToEntity(ProductDTO dto) {
        return dtoToEntity(-1, dto);
    }
}
