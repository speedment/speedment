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
package com.speedment.generator.standard.util;

import com.speedment.common.codegen.model.Interface;
import com.speedment.common.injector.Injector;
import com.speedment.generator.standard.StandardTranslatorKey;
import com.speedment.generator.translator.JavaClassTranslator;
import com.speedment.generator.translator.component.CodeGenerationComponent;
import com.speedment.runtime.config.Column;
import com.speedment.runtime.config.ForeignKey;
import com.speedment.runtime.config.ForeignKeyColumn;
import com.speedment.runtime.config.Table;
import com.speedment.runtime.core.exception.SpeedmentException;

import java.util.NoSuchElementException;

import static com.speedment.runtime.config.util.DocumentUtil.ancestor;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author pemi
 */
public final class FkHolder {

    private final CodeGenerationComponent codeGenerationComponent;
    
    private final ForeignKey fk;
    private final ForeignKeyColumn fkc;
    private final Column column;
    private final Table table;
    private final Column foreignColumn;
    private final Table foreignTable;

    public FkHolder(Injector injector, ForeignKey fk) {
        requireNonNull(injector);
        requireNonNull(fk);
        this.codeGenerationComponent = injector.getOrThrow(CodeGenerationComponent.class);
        
        this.fk  = fk;
        this.fkc = fk.foreignKeyColumns().findFirst().orElseThrow(this::noEnabledForeignKeyException);
        
        this.column        = fkc.findColumnOrThrow();
        this.table         = ancestor(column, Table.class).orElseThrow(NoSuchElementException::new);
        this.foreignColumn = fkc.findForeignColumn().orElseThrow(this::foreignKeyWasNullException);
        this.foreignTable  = fkc.findForeignTable().orElseThrow(this::foreignKeyWasNullException);
    }

    public Column getColumn() {
        return column;
    }

    public Table getTable() {
        return table;
    }

    public Column getForeignColumn() {
        return foreignColumn;
    }

    public Table getForeignTable() {
        return foreignTable;
    }

    public JavaClassTranslator<Table, Interface> getEmt() {
        @SuppressWarnings("unchecked")
        final JavaClassTranslator<Table, Interface> translator = 
            (JavaClassTranslator<Table, Interface>)
            codeGenerationComponent.findTranslator(getTable(), StandardTranslatorKey.MANAGER);
        return translator;
    }

    public JavaClassTranslator<Table, Interface> getForeignEmt() {
        @SuppressWarnings("unchecked")
        final JavaClassTranslator<Table, Interface> translator = 
            (JavaClassTranslator<Table, Interface>)
            codeGenerationComponent.findTranslator(getForeignTable(), StandardTranslatorKey.MANAGER);
        return translator;
    }

    private IllegalStateException noEnabledForeignKeyException() {
        return new IllegalStateException(
            "FK " + fk.getId() + " does not have an enabled ForeignKeyColumn"
        );
    }

    private SpeedmentException foreignKeyWasNullException() {
        return new SpeedmentException(
            "Could not find referenced foreign column '" + 
            fkc.getForeignColumnName() + "' in table '" + 
            fkc.getForeignTableName() + "'."
        );
    }
}