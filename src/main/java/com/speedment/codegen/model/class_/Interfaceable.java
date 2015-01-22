package com.speedment.codegen.model.class_;

import java.util.List;

/**
 *
 * @author pemi
 */
public interface Interfaceable {

    List<Interface_> getInterfaces();

    Interfaceable add(final Interface_ interface_);

}
