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
import com.speedment.config.db.Column;
import com.speedment.config.db.Table;
import com.speedment.internal.util.document.TraitUtil.AbstractTraitView;
import static com.speedment.internal.util.document.TraitUtil.viewOf;
import java.util.Map;
import java.util.Optional;

/**
 *
 * @author Emil Forslund
 */
@Api(version = "2.3")
public interface HasColumn extends Document, HasName {

    default Optional<? extends Column> findColumn() {
        return ancestors()
            .filter(Table.class::isInstance)
            .map(Table.class::cast)
            .findFirst()
            .flatMap(table -> table
                .columns()
                .filter(col -> col.getName().equals(getName()))
                .findAny()
            );
    }
    
    static HasColumn of(Document document) {
        return viewOf(document, HasColumn.class, HasColumnView::new);
    }
}

class HasColumnView extends AbstractTraitView implements HasColumn {

    HasColumnView(Document parent, Map<String, Object> data, Class<? extends Document> mainInterface) {
        super(parent, data, mainInterface);
    }
}