package com.speedment.runtime.internal.runtime;

import java.util.Optional;

/**
 *
 * @author  Emil Forslund
 * @since   2.4.0
 */
public final class EmptyApplicationMetadata extends AbstractApplicationMetadata {

    @Override
    protected Optional<String> getMetadata() {
        return Optional.empty();
    }
    
}