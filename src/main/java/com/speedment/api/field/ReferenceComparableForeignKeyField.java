package com.speedment.api.field;

import com.speedment.api.annotation.Api;

/**
 *
 * @author pemi
 * @param <ENTITY>
 * @param <V>
 * @param <FK>
 */
@Api(version = "2.1")
public interface ReferenceComparableForeignKeyField<ENTITY, V extends Comparable<? super V>, FK> extends 
    ReferenceComparableField<ENTITY, V>, ReferenceForeignKeyField<ENTITY, V, FK> {}