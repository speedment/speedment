package com.speedment.codegen.model.annotation;

import java.util.List;

/**
 *
 * @author pemi
 */
public interface Annotatable {

    List<Annotation_> getAnnotations();

    boolean has(Annotation_ annotation_);

    Annotatable add(final Annotation_ annotation);

}
