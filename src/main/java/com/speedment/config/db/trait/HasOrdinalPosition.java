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
import com.speedment.internal.util.document.TraitUtil.AbstractTraitView;
import static com.speedment.internal.util.document.TraitUtil.viewOf;
import static com.speedment.stream.MapStream.comparing;
import java.util.Comparator;
import java.util.Map;

/**
 *
 * @author Emil Forslund
 */
@Api(version = "2.3")
public interface HasOrdinalPosition extends Document {
    
    final int ORDINAL_FIRST = 1, UNSET = -1;
    
    final String ORDINAL_POSITION = "ordinalPosition";
    
    final Comparator<HasOrdinalPosition> COMPARATOR = 
        comparing(HasOrdinalPosition::getOrdinalPosition);
    
    /**
     * Returns the position to use when ordering this node.
     * 
     * @return the ordinal position.
     */
    default int getOrdinalPosition() {
        return getAsInt(ORDINAL_POSITION).orElse(0);
    }
    
    static HasOrdinalPosition of(Document document) {
        return viewOf(document, HasOrdinalPosition.class, HasOrdinalPositionView::new);
    }
}

class HasOrdinalPositionView extends AbstractTraitView implements HasOrdinalPosition {

    HasOrdinalPositionView(Document parent, Map<String, Object> data, Class<? extends Document> mainInterface) {
        super(parent, data, mainInterface);
    }
}