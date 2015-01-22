package com.speedment.codegen.model.method;

import java.util.List;

/**
 *
 * @author pemi
 */
public interface Methodable {

    List<Method_> getMethods();

    Methodable add(final Method_ method);

}
