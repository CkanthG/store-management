package com.meghana.store.converter;

/**
 * Custom Entity to DTO converter
 * @param <E>
 * @param <D>
 */
@FunctionalInterface
public interface DtoConverter<E, D> {
    D convert(E entity);
}
