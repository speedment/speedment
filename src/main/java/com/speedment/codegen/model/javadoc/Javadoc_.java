/**
 *
 * Copyright (c) 2006-2015, Speedment, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); You may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.speedment.codegen.model.javadoc;

import com.speedment.codegen.model.AbstractCodeModel;
import com.speedment.codegen.model.CodeModel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.speedment.util.$;
import static com.speedment.codegen.CodeUtil.*;

/**
 *
 * @author pemi
 */
public class Javadoc_ extends AbstractCodeModel<Javadoc_> implements CodeModel {

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
        return with(text, getDescriptions()::add);
    }

    public Javadoc_ add(Tag tag, CharSequence text) {
        return with(tag, text, (a, b) -> {
            getTags().computeIfAbsent(tag, t -> new ArrayList<>()).add(text);
        });
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
        return add(Tag.RETURN, text);
    }

    public Javadoc_ param_(CharSequence text) {
        return add(Tag.PARAM, text);
    }

    public Javadoc_ throws_(CharSequence text) {
        return add(Tag.THROWS, text);
    }

    public Javadoc_ throws_(Class<? extends Exception> throwClass, CharSequence additionalText) {
        return add(Tag.THROWS, new $(throwClass.getSimpleName(), SPACE, additionalText));
    }

}
