package com.speedment.codegen.model.field;

import java.util.List;

/**
 *
 * @author pemi
 */
public interface Fieldable {

    List<Field_> getFields();

    Fieldable add(final Field_ field);

}
