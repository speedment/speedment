/*
 * Copyright (c) Emil Forslund, 2016.
 * All Rights Reserved.
 * 
 * NOTICE:  All information contained herein is, and remains
 * the property of Emil Forslund and his suppliers, if any. 
 * The intellectual and technical concepts contained herein 
 * are proprietary to Emil Forslund and his suppliers and may 
 * be covered by U.S. and Foreign Patents, patents in process, 
 * and are protected by trade secret or copyright law. 
 * Dissemination of this information or reproduction of this 
 * material is strictly forbidden unless prior written 
 * permission is obtained from Emil Forslund himself.
 */
package com.speedment.runtime.internal.field.streamer;

import com.speedment.runtime.field.Field;
import com.speedment.runtime.field.trait.HasComparableOperators;
import com.speedment.runtime.field.trait.HasFinder;
import com.speedment.runtime.manager.Manager;
import java.util.stream.Stream;
import com.speedment.runtime.field.method.BackwardFinder;
import static java.util.Objects.requireNonNull;

/**
 *
 * @param <ENTITY>     the source entity
 * @param <FK_ENTITY>  the target entity
 * @param <T>          the column type
 * 
 * @author  Emil Forslund
 * @since   3.0.0
 */
public class BackwardFinderImpl<ENTITY, FK_ENTITY, T extends Comparable<? super T>, FIELD extends Field<FK_ENTITY, T> & HasComparableOperators<FK_ENTITY, T> & HasFinder<FK_ENTITY, ENTITY, T>>
    implements BackwardFinder<ENTITY, FK_ENTITY, T> {

    private final FIELD target;
    private final Manager<FK_ENTITY> manager;
    
    public BackwardFinderImpl(FIELD target, Manager<FK_ENTITY> manager) {
        this.target  = requireNonNull(target);
        this.manager = requireNonNull(manager);
    }
    
    @Override
    public final FIELD getField() {
        return target;
    }

    @Override
    public final Manager<FK_ENTITY> getTargetManager() {
        return manager;
    }

    @Override
    public Stream<FK_ENTITY> apply(ENTITY entity) {
        final T value = getField().getReferencedField().getter().apply(entity);
        if (value == null) {
            return null;
        } else {
            return getTargetManager().stream().filter(getField().equal(value));
        }
    }
}