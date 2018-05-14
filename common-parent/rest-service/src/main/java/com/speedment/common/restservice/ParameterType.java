package com.speedment.common.restservice;

/**
 * Type of parameters that can be parsed by the endpoint.
 *
 * @author Emil Forslund
 * @since  1.0.0
 */
public enum ParameterType {
    INTEGER, LONG, FLOAT, DOUBLE,
    BOOLEAN, STRING,
    SET, LIST, MAP
}