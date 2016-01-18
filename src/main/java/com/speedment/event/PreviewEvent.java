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
package com.speedment.event;

import com.speedment.annotation.Api;
import com.speedment.internal.ui.config.DocumentProperty;
import static java.util.Objects.requireNonNull;
import javafx.scene.layout.VBox;

/**
 *
 * @author Emil Forslund
 */
@Api(version = "2.4")
public final class PreviewEvent implements Event {
    
    private final DocumentProperty document;
    private final VBox preview;
    
    public PreviewEvent(DocumentProperty document, VBox preview) {
        this.document = requireNonNull(document);
        this.preview  = requireNonNull(preview);
    }

    public DocumentProperty getDocument() {
        return document;
    }

    public VBox getPreview() {
        return preview;
    }
}