package com.speedment.common.codegenxml.trait;

import com.speedment.common.codegenxml.ContentElement;
import com.speedment.common.codegenxml.Element;
import java.util.List;

/**
 *
 * @author Per Minborg
 * @param <R> type of self
 */
public interface HasElements<R extends HasElements<? super R>> {

    List<Element> elements();

    default R add(Element elem) {
        elements().add(elem);
        @SuppressWarnings("unchecked")
        final R self = (R) this;
        return self;
    }

    default R add(String row) {
        return add(ContentElement.of(row));
    }

}
