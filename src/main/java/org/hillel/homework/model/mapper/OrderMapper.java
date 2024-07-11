package org.hillel.homework.model.mapper;

import org.hillel.homework.model.dto.OrderDTO;
import org.hillel.homework.model.dto.ProductDTO;
import org.hillel.homework.model.entity.OrderEntity;
import org.hillel.homework.model.entity.ProductEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class OrderMapper implements Mapper<OrderEntity, OrderDTO> {
    private final ProductMapper productMapper;

    public OrderMapper(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    @Override
    public OrderDTO entityToDTO(OrderEntity entity) {
        if (entity == null) {
            throw new NullPointerException();
        }
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setCustomerName(entity.getCustomerName());
        orderDTO.setProducts(new ArrayList<>());
        for (ProductEntity product : entity.getProducts()) {
            orderDTO.getProducts().add(productMapper.entityToDTO(product));
        }
        return orderDTO;
    }

    public OrderEntity dtoToEntity(int id, OrderDTO dto) {
        if (dto == null) {
            throw new NullPointerException();
        }
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(id);
        orderEntity.setCustomerName(dto.getCustomerName());
        if (dto.getProducts() != null) {
            orderEntity.setProducts(new ArrayList<>());
            for (ProductDTO product : dto.getProducts()) {
                orderEntity.getProducts().add(productMapper.dtoToEntity(product));
            }
        }
        return orderEntity;
    }

    @Override
    public OrderEntity dtoToEntity(OrderDTO dto) {
        return dtoToEntity(-1, dto);
    }
}
