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
package com.speedment.internal.core.platform.component.impl;

import com.speedment.Speedment;
import com.speedment.component.DocumentPropertyComponent;
import com.speedment.config.db.Dbms;
import com.speedment.config.db.Project;
import com.speedment.config.db.Schema;
import com.speedment.config.db.mutator.DbmsMutator;
import com.speedment.exception.SpeedmentException;
import com.speedment.internal.core.runtime.DefaultSpeedmentApplicationLifecycle;
import com.speedment.internal.ui.config.AbstractChildDocumentProperty;
import com.speedment.internal.ui.config.ColumnProperty;
import com.speedment.internal.ui.config.DbmsProperty;
import com.speedment.internal.ui.config.ForeignKeyColumnProperty;
import com.speedment.internal.ui.config.ForeignKeyProperty;
import com.speedment.internal.ui.config.IndexColumnProperty;
import com.speedment.internal.ui.config.IndexProperty;
import com.speedment.internal.ui.config.PrimaryKeyColumnProperty;
import com.speedment.internal.ui.config.ProjectProperty;
import com.speedment.internal.ui.config.SchemaProperty;
import com.speedment.internal.ui.config.TableProperty;
import com.speedment.internal.util.ImmutableListUtil;
import com.speedment.ui.config.DocumentProperty;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Stream;
import org.controlsfx.control.PropertySheet;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Emil Forslund
 */
public class DocumentPropertyComponentImplTest {
    
    private Speedment speedment;
    private DocumentPropertyComponent component;
    
    @Before
    public void setUp() {
        speedment = new DefaultSpeedmentApplicationLifecycle().build();
        component = speedment.getDocumentPropertyComponent();
    }
    
    @Test
    public void testStructure() {
        final Field root;
        try {
            root = DocumentPropertyComponentImpl.class.getDeclaredField("root");
            root.setAccessible(true);
            
            final Method toString = root.getType().getMethod("toString");
            final Object rootObj  = root.get(component);
            
            if (rootObj == null) {
                throw new NullPointerException("Root is null.");
            }
            
            final Object o = toString.invoke(rootObj);
            //System.out.println(o);
            
        } catch (final NoSuchFieldException | NoSuchMethodException | SecurityException | IllegalAccessException | InvocationTargetException ex) {
            throw new SpeedmentException("Could not call toString on component", ex);
        }
    }

    @Test
    public void testDefaultInstallments() {

        final DocumentProperty project = component.getConstructor(DocumentPropertyComponent.PROJECTS).create(null);
        final DocumentProperty dbms = component.getConstructor(DocumentPropertyComponent.DBMSES).create(project);
        final DocumentProperty schema = component.getConstructor(DocumentPropertyComponent.SCHEMAS).create(dbms);
        final DocumentProperty table = component.getConstructor(DocumentPropertyComponent.TABLES).create(schema);
        final DocumentProperty column = component.getConstructor(DocumentPropertyComponent.COLUMNS).create(table);
        final DocumentProperty index = component.getConstructor(DocumentPropertyComponent.INDEXES).create(table);
        final DocumentProperty indexColumn = component.getConstructor(DocumentPropertyComponent.INDEX_COLUMNS).create(index);
        final DocumentProperty foreignKey = component.getConstructor(DocumentPropertyComponent.FOREIGN_KEYS).create(table);
        final DocumentProperty foreignKeyColumn = component.getConstructor(DocumentPropertyComponent.FOREIGN_KEY_COLUMNS).create(foreignKey);
        final DocumentProperty primaryKey = component.getConstructor(DocumentPropertyComponent.PRIMARY_KEY_COLUMNS).create(table);
        
        assertEquals("Make sure ProjectProperty is used by default: ", ProjectProperty.class, project.getClass());
        assertEquals("Make sure DbmsProperty is used by default: ", DbmsProperty.class, dbms.getClass());
        assertEquals("Make sure SchemaProperty is used by default: ", SchemaProperty.class, schema.getClass());
        assertEquals("Make sure TableProperty is used by default: ", TableProperty.class, table.getClass());
        assertEquals("Make sure ColumnProperty is used by default: ", ColumnProperty.class, column.getClass());
        assertEquals("Make sure IndexProperty is used by default: ", IndexProperty.class, index.getClass());
        assertEquals("Make sure IndexColumnProperty is used by default: ", IndexColumnProperty.class, indexColumn.getClass());
        assertEquals("Make sure ForeignKeyProperty is used by default: ", ForeignKeyProperty.class, foreignKey.getClass());
        assertEquals("Make sure ForeignKeyColumnProperty is used by default: ", ForeignKeyColumnProperty.class, foreignKeyColumn.getClass());
        assertEquals("Make sure PrimaryKeyColumnProperty is used by default: ", PrimaryKeyColumnProperty.class, primaryKey.getClass());
    }
    
    @Test
    public void testAlternateInstallments() {
        component.setConstructor(parent -> new AlternativeDbms((Project) parent), DocumentPropertyComponent.DBMSES);
        
        final DocumentProperty project = component.getConstructor(DocumentPropertyComponent.PROJECTS).create(null);
        final DocumentProperty dbms = component.getConstructor(DocumentPropertyComponent.DBMSES).create(project);
        
        assertEquals(ProjectProperty.class, project.getClass());
        assertEquals(AlternativeDbms.class, dbms.getClass());
    }
    
    private final static class AlternativeDbms extends AbstractChildDocumentProperty<Project, AlternativeDbms> implements Dbms {

        public AlternativeDbms(Project parent) {
            super(parent);
        }
        
        @Override
        protected List<String> keyPathEndingWith(String key) {
            return ImmutableListUtil.concat(DocumentPropertyComponent.DBMSES, key);
        }

        @Override
        public Stream<PropertySheet.Item> getUiVisibleProperties(Speedment speedment) {
            throw new UnsupportedOperationException("Not required by test.");
        }

        @Override
        public DbmsMutator<Dbms> mutator() {
            throw new UnsupportedOperationException("Not required by test.");
        }

        @Override
        public Stream<? extends Schema> schemas() {
            throw new UnsupportedOperationException("Not required by test.");
        }
    }
}