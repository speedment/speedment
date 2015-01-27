package com.speedment.codegen.model.javadoc;

import com.speedment.codegen.model.CodeModel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.speedment.util.$;

/**
 *
 * @author pemi
 */
public class Javadoc_ implements CodeModel {

    private final List<CharSequence> descriptions;
    private final Map<Tag, List<CharSequence>> tags;

    public Javadoc_() {
        descriptions = new ArrayList();
        tags = new HashMap<>();
    }

    @Override
    public Type getModelType() {
        return Type.JAVADOC;
    }

    public Javadoc_ add(CharSequence text) {
        getDescriptions().add(text);
        return this;
    }

    public Javadoc_ add(Tag tag, CharSequence text) {
        getTags().computeIfAbsent(tag, t -> new ArrayList<>()).add(text);
        return this;
    }

    public List<CharSequence> getDescriptions() {
        return descriptions;
    }

    public List<CharSequence> get(Tag tag) {
        return getTags().getOrDefault(tag, Collections.emptyList());
    }

    public Map<Tag, List<CharSequence>> getTags() {
        return tags;
    }

    public Javadoc_ return_(CharSequence text) {
        add(Tag.RETURN, text);
        return this;
    }

    public Javadoc_ param_(CharSequence text) {
        add(Tag.PARAM, text);
        return this;
    }

    public Javadoc_ throws_(CharSequence text) {
        add(Tag.THROWS, text);
        return this;
    }

    public Javadoc_ throws_(Class<? extends Exception> throwClass, CharSequence additionalText) {
        add(Tag.THROWS, new $(throwClass.getSimpleName(), " ", additionalText));
        return this;
    }

}
