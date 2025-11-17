package com.base.common.mapper;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author : AnhNT
 * @since : 10/11/2021, Wed
 */
public interface EntityMapper<D, E> {

    D toDto(E e);

    E toEntity(D d);

    List<D> toDtoList(List<E> e);

    List<E> toEntityList(List<D> d);

    default Page<D> mapEntitiesPage(Page<E> entities) {
        if (entities == null) {

            return null;

        }
        return entities.map(this::toDto);
    }

    default Page<E> mapDtosPage(Page<D> dtos) {
        if (dtos == null) {
            return null;
        }
        return dtos.map(this::toEntity);
    }

}
