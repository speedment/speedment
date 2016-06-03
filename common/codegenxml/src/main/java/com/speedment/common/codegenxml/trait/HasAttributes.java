package com.speedment.common.codegenxml.trait;

import com.speedment.common.codegenxml.Attribute;
import java.util.List;

/**
 *
 * @author Per Minborg
 * @param <R> type of self
 */
public interface HasAttributes<R extends HasAttributes<? super R>> {

    List<Attribute> attributes();

    default R add(Attribute elem) {
        attributes().add(elem);
        @SuppressWarnings("unchecked")
        final R self = (R) this;
        return self;
    }

}
