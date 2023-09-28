package ru.geekbrains.gym.mapper;

import org.mapstruct.*;

import java.util.List;

public interface EntityMapper <E, D>{

    E toEntity(D dto);

    D toDto(E entity);

    @IterableMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
    List<D> toDtos(List<E> entityList);

    @Named("partialUpdate")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(@MappingTarget E entity, D dto);
}
