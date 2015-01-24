package com.speedment.codegen.model.class_;

import java.util.List;

/**
 *
 * @author pemi
 */
public interface Nestable {

    Nestable add(final Class_ nestedClass);
    
    List<ClassAndInterfaceBase<?, ?>> getNestedClasses();

}
