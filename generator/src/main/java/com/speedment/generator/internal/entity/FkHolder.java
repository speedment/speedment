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
package com.speedment.generator.internal.entity;

import com.speedment.Speedment;
import com.speedment.config.db.Column;
import com.speedment.config.db.ForeignKey;
import com.speedment.config.db.ForeignKeyColumn;
import com.speedment.config.db.Table;
import com.speedment.exception.SpeedmentException;
import com.speedment.fika.codegen.Generator;
import com.speedment.generator.internal.manager.EntityManagerTranslator;
import static com.speedment.internal.util.document.DocumentUtil.ancestor;
import static com.speedment.util.NullUtil.requireNonNulls;

/**
 *
 * @author pemi
 */
public final class FkHolder {

    private final ForeignKey fk;
    private final ForeignKeyColumn fkc;
    private final Column column;
    private final Table table;
    private final Column foreignColumn;
    private final Table foreignTable;
    private final EntityManagerTranslator emt;
    private final EntityManagerTranslator foreignEmt;

    FkHolder(Speedment speedment, Generator generator, ForeignKey fk) {
        requireNonNulls(speedment, generator, fk);
        
        this.fk = fk;
        this.fkc = fk.foreignKeyColumns().findFirst().orElseThrow(this::noEnabledForeignKeyException);
        
        column        = fkc.findColumn().orElseThrow(this::couldNotFindLocalColumnException);
        table         = ancestor(column, Table.class).get();
        foreignColumn = fkc.findForeignColumn().orElseThrow(this::foreignKeyWasNullException);
        foreignTable  = fkc.findForeignTable().orElseThrow(this::foreignKeyWasNullException);
        emt           = new EntityManagerTranslator(speedment, generator, getTable());
        foreignEmt    = new EntityManagerTranslator(speedment, generator, getForeignTable());
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

    public EntityManagerTranslator getEmt() {
        return emt;
    }

    public EntityManagerTranslator getForeignEmt() {
        return foreignEmt;
    }

    private IllegalStateException noEnabledForeignKeyException() {
        return new IllegalStateException(
            "FK " + fk.getName() + " does not have an enabled ForeignKeyColumn"
        );
    }
    
    private SpeedmentException couldNotFindLocalColumnException() {
        return new SpeedmentException(
            "Could not find referenced local column '" + 
            fkc.getName() + "' in table '" + 
            fkc.getParent().flatMap(ForeignKey::getParent).get().getName() + "'."
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