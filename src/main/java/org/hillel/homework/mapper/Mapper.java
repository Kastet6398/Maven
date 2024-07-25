package org.hillel.homework.mapper;

public interface Mapper<Q, S, E> {
    E requestToEntity(Q request);
    S entityToResponse(E entity);
}
