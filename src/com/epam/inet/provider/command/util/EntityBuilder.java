package com.epam.inet.provider.command.util;

import com.epam.inet.provider.entity.Entity;
import com.epam.inet.provider.command.exception.BuildException;

import java.util.Map;

/**
 * Abstract entity from request builder
 */
public abstract class EntityBuilder<T extends Entity> {

    /**
     * Build entity from request
     *
     *
     * @param map @return
     * @throws BuildException
     */
    public abstract T build(Map<String, String[]> map) throws BuildException;
}
