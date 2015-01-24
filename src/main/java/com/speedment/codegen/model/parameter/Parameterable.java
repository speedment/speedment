package com.speedment.codegen.model.parameter;

import java.util.List;

/**
 *
 * @author pemi
 */
public interface Parameterable {

    Parameterable add(Parameter_ field_);

    List<Parameter_> getParameters();

    boolean hasParameter(Parameter_ parameter);

}
