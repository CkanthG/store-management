package com.meghana.store.exception;

/**
 * This exception class is send an exception if there is no item available for given input.
 */
public class EntityNotFoundException extends Exception{
    public EntityNotFoundException(String message) {
        super(message);
    }
}
