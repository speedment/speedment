/**
 *
 * Copyright (c) 2006-2016, Speedment, Inc. All Rights Reserved.
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
package com.speedment.config.db.trait;

import com.speedment.annotation.Api;
import com.speedment.config.Document;
import com.speedment.config.db.Project;
import com.speedment.exception.SpeedmentException;
import com.speedment.internal.util.document.TraitUtil.AbstractTraitView;
import static com.speedment.internal.util.document.TraitUtil.viewOf;
import java.util.Map;
import java.util.Optional;

/**
 *
 * @author Emil Forslund
 */
@Api(version = "2.3")
public interface HasName extends Document, HasMainInterface {

    final String NAME = "name";

    /**
     * Gets the name of this Document. If no name is present, this method might
     * create a name if the parent implements the {@link HasChildren} interface.
     * If it does not and this document is unnamed, an
     * {@link SpeedmentException} is called.
     *
     * @return the name of this Document
     * @throws SpeedmentException if no name is specified and the parent can't
     * generate one
     */
    default String getName() throws SpeedmentException {
        final Optional<String> name = getAsString(NAME);

        if (name.isPresent()) {
            return name.get();

        } else if (getParent()
                .filter(HasChildren.class::isInstance)
                .isPresent()) {

            final String defaultName = getParent()
                    .map(HasChildren.class::cast)
                    .map(parent -> parent.defaultNameFor(this))
                    .get();

            getData().put(NAME, defaultName);
            return defaultName;
        } else if (this instanceof Project) {
            return Project.class.getSimpleName();
        } else {
            throw new SpeedmentException(
                "A name is required for node of type '" + getClass().getSimpleName() + "'."
            );
        }
    }

    static HasName of(Document document) {
        return viewOf(document, HasName.class, HasNameView::new);
    }
}

class HasNameView extends AbstractTraitView implements HasName {

    HasNameView(Document parent, Map<String, Object> data, Class<? extends Document> mainInterface) {
        super(parent, data, mainInterface);
    }
}
