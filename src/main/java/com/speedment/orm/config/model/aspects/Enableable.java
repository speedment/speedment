package com.speedment.orm.config.model.aspects;

/**
 *
 * @author Emil Forslund
 */
public interface Enableable {
    void setEnabled(boolean enabled);
    boolean isEnabled();
}
