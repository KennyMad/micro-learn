package com.pet.common.exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String entityName, long entityId) {
        super("Entity " + entityName + " not found with id " + entityId);
    }

    public EntityNotFoundException(String entityName, String entityId) {
        super("Entity " + entityName + " not found with name " + entityId);
    }
}
