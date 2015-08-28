package com.speedment.api.field;

import com.speedment.api.annotation.Api;

/**
 *
 * @author pemi
 * @param <ENTITY>
 * @param <FK>
 */
@Api(version = "2.1")
public interface ReferenceComparableForeignKeyStringField<ENTITY, FK> extends 
    ReferenceComparableField<ENTITY, String>, 
    ReferenceForeignKeyField<ENTITY, String, FK>, 
    ReferenceComparableStringField<ENTITY> {}