package com.meghana.store.converter;

/**
 * Custom DTO to Entity converter
 * @param <D>
 * @param <E>
 */
@FunctionalInterface
public interface EntityConverter<D, E> {
    E convert(D dto);
}
