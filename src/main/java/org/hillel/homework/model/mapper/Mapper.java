package org.hillel.homework.model.mapper;

import org.hillel.homework.model.dto.DTO;
import org.hillel.homework.model.entity.Entity;

public interface Mapper<E extends Entity, D extends DTO> {
    D entityToDTO(E entity);
    E dtoToEntity(D dto);
}
