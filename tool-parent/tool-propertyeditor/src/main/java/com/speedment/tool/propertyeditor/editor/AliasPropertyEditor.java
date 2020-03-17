/*
 *
 * Copyright (c) 2006-2020, Speedment, Inc. All Rights Reserved.
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
package com.speedment.tool.propertyeditor.editor;

import com.speedment.common.injector.annotation.Inject;
import com.speedment.runtime.typemapper.TypeMapperComponent;
import com.speedment.generator.translator.namer.JavaLanguageNamer;
import com.speedment.tool.config.ColumnProperty;
import com.speedment.tool.config.TableProperty;
import com.speedment.tool.config.trait.HasAliasProperty;
import com.speedment.tool.propertyeditor.PropertyEditor;
import com.speedment.tool.propertyeditor.item.DefaultTextFieldItem;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableStringValue;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.util.NoSuchElementException;
import java.util.stream.Stream;

import static com.speedment.common.codegen.util.Formatting.shortName;
import static java.util.Objects.requireNonNull;

/**
 *
 * @param <T>  the document type
 *
 * @author Simon Jonassons
 * @since 3.0.0
 */
public class AliasPropertyEditor<T extends HasAliasProperty> implements PropertyEditor<T> {

    @Inject public JavaLanguageNamer namer;
    @Inject public TypeMapperComponent typeMapperComponent;

    @Override
    public Stream<PropertyEditor.Item> fieldsFor(T document) {
        return Stream.of(new CodeDefaultTextFileItem(
                "Java Alias",
                document.nameProperty(),
                document.aliasProperty(),
                "The name that will be used for this in generated code.",
                document,
                namer,
                typeMapperComponent
            )
        );
    }

    private final class CodeDefaultTextFileItem extends DefaultTextFieldItem {

        private final JavaLanguageNamer namer;
        private final TypeMapperComponent typeMapperComponent;
        private final T document;

        CodeDefaultTextFileItem(
            String label,
            ObservableStringValue defaultValue,
            StringProperty value,
            String tooltip,
            T document,
            JavaLanguageNamer namer,
            TypeMapperComponent typeMapperComponent

        ) {
            super(label, defaultValue, value, tooltip, NO_DECORATOR);
            this.document = requireNonNull(document);
            this.namer = requireNonNull(namer);
            this.typeMapperComponent = requireNonNull(typeMapperComponent);
        }

        @Override
        protected HBox createUndecoratedEditor() {
            final HBox hBox = super.createUndecoratedEditor();
            if (document instanceof ColumnProperty || document instanceof TableProperty) {
                final TextInputControl inputControl = hBox.getChildren().stream()
                    .filter(TextInputControl.class::isInstance)
                    .map(TextInputControl.class::cast)
                    .findFirst()
                    .orElseThrow(() -> new NoSuchElementException("No TextInputControl found for the children of " + hBox));

                final TextField code = new TextField(toCode(inputControl.getText()));

                code.setDisable(true);
                code.setStyle("-fx-font-family: 'monospaced';");

                attachListener(inputControl.textProperty(), (ov, o, n) ->
                    code.textProperty().set(toCode(n))
                );

                if (document instanceof ColumnProperty) {
                    final ColumnProperty columnProperty = (ColumnProperty) document;
                    attachListener(columnProperty.typeMapperProperty(), (ov, o, n) ->
                        code.textProperty().set(toCode(inputControl.getText()))
                    );
                }
                hBox.getChildren().add(code);
                HBox.setHgrow(code, Priority.ALWAYS);
            }
            return hBox;
        }

        private String toCode(String name) {
            if (document instanceof ColumnProperty) {
                final ColumnProperty columnProperty = (ColumnProperty) document;
                final java.lang.reflect.Type javaType = typeMapperComponent.typeOf(columnProperty);
                return String.format("%s get%s();", shortName(javaType.getTypeName()), namer.javaTypeName(name));
            }
            if (document instanceof TableProperty) {
                return String.format("%s %s;", namer.javaTypeName(name), namer.javaVariableName(name));
            }
            return "";
        }
    }

}
