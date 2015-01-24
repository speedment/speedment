package com.speedment.codegen.model.generic;

import java.util.List;

/**
 *
 * @author pemi
 */
public interface Genericable {

    Genericable add(Generic_ generic);

    List<Generic_> getGenerics();

    boolean hasGeneric(Generic_ generic);

}
