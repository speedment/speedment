package com.speedment.runtime.core.manager;

import com.speedment.runtime.core.exception.SpeedmentException;
import java.util.function.UnaryOperator;

/**
 *
 * @author Per Minborg
 */
@FunctionalInterface
public interface Updater<ENTITY> extends UnaryOperator<ENTITY> {

    @Override
    public ENTITY apply(ENTITY t) throws SpeedmentException;

}
