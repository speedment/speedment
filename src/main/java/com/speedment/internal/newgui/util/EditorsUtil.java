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
package com.speedment.internal.newgui.util;

import static com.speedment.util.StaticClassUtil.instanceNotAllowed;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.PasswordField;
import org.controlsfx.control.PropertySheet;
import org.controlsfx.property.editor.AbstractPropertyEditor;
import org.controlsfx.property.editor.PropertyEditor;

/**
 *
 * @author Emil Forslund
 */
public final class EditorsUtil {
    
    public static PropertyEditor<String> createPasswordEditor(PropertySheet.Item item) {
        return new AbstractPropertyEditor<String, PasswordField>(item, new PasswordField()) {
            
            @Override
            protected ObservableValue<String> getObservableValue() {
                return getEditor().textProperty();
            }

            @Override
            public void setValue(String t) {
                getEditor().textProperty().setValue(t);
            }
            
        };
    }
    
    private EditorsUtil() {
        instanceNotAllowed(getClass());
    }
}