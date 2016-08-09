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
package com.speedment.tool.property.editor;

import com.speedment.tool.property.item.DefaultTextAreaItem;
import com.speedment.common.injector.Injector;
import com.speedment.common.injector.annotation.Inject;
import com.speedment.runtime.annotation.Api;
import com.speedment.runtime.component.DbmsHandlerComponent;
import com.speedment.runtime.exception.SpeedmentException;
import com.speedment.runtime.internal.util.document.DocumentDbUtil;
import com.speedment.tool.config.DbmsProperty;
import com.speedment.tool.property.PropertyEditor;
import java.util.stream.Stream;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;

/**
 *
 * @author Simon Jonassons
 * @param <T>  the document type
 * @since 3.0.0
 */
@Api(version="3.0")
public class ConnectionUrlPropertyEditor<T extends DbmsProperty> implements PropertyEditor<T> {

    private @Inject Injector injector;
    
    @Override
    public Stream<Item> fieldsFor(T document) {
        final DbmsHandlerComponent dbmsHandler = injector.getOrThrow(DbmsHandlerComponent.class);
        
        return Stream.of(
            new DefaultTextAreaItem(
                "Connection URL", 
                defaultConnectionUrlProperty(document, dbmsHandler), 
                document.connectionUrlProperty(), 
                "The connection URL that should be used when establishing " +
                "connection with the database. If this is set to Auto, the " +
                "DbmsType will generate one."
            )
        );
    }
    
    protected StringBinding defaultConnectionUrlProperty(T document, DbmsHandlerComponent dbmsHandlerComponent) throws SpeedmentException {
        return Bindings.createStringBinding(() -> 
            DocumentDbUtil.findDbmsType(dbmsHandlerComponent, document).getConnectionUrlGenerator().from(document), 
                document.typeNameProperty(),
                document.ipAddressProperty(),
                document.portProperty(),
                document.usernameProperty()
        );
    }
}