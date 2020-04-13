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
package com.speedment.generator.translator;

import com.speedment.runtime.application.AbstractApplicationMetadata;
import com.speedment.runtime.config.*;
import com.speedment.runtime.config.trait.HasNameUtil;
import com.speedment.runtime.config.trait.HasTypeMapperUtil;
import com.speedment.runtime.core.Speedment;
import org.junit.jupiter.api.BeforeEach;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

/**
 *
 * @author Per Minborg
 */
public abstract class SimpleModel {

    protected static final String TABLE_NAME = "user";
    protected static final String TABLE_NAME2 = "S_P";
    protected static final String SCHEMA_NAME = "mySchema";
    protected static final String COLUMN_NAME = "first_name";
    protected static final String COLUMN_NAME2 = "item";

    protected Speedment speedment;
    protected Project project;
    protected Dbms dbms;
    protected Schema schema;
    protected Table table;
    protected Column column;
    protected PrimaryKeyColumn pkColumn;
    protected Table table2;
    protected Column column2;
    
 /*   public final static class SilentTranslatorManager extends AbstractTranslatorManager {

        public SilentTranslatorManager(
            final InfoComponent info,
            final PathComponent paths,
            final EventComponent events,
            final ProjectComponent projects,
            final CodeGenerationComponent codeGenerationComponent,
            final @Config(name="skipClear", value="false") boolean skipClear
        ) {
            super(info, paths, events, projects, codeGenerationComponent, skipClear);
        }

        @Override
        public void clearExistingFiles() {}

        @Override
        public void writeToFile(Project project, Meta<File, String> meta, boolean overwriteExisting) {}

        @Override
        public void writeToFile(Project project, String filename, String content, boolean overwriteExisting) {}

        @Override
        public void writeToFile(Path location, String content, boolean overwriteExisting) {}

        @Override
        public int getFilesCreated() {return 0;}
    }*/

    @BeforeEach
    public void simpleModelTestSetUp() {

        /*speedment = new DefaultApplicationBuilder(SimpleMetadata.class)
            .withBundle(GeneratorBundle.class)
            .withComponent(SilentTranslatorManager.class)
            // .withLogging(ApplicationBuilder.LogType.APPLICATION_BUILDER)
            .withSkipCheckDatabaseConnectivity()
            .withSkipValidateRuntimeConfig()
            .build();*/

        project = new SimpleMetadata().makeProject();

/*        project  = speedment.getOrThrow(ProjectComponent.class).getProject();*/
        dbms     = project.dbmses().findAny().orElseThrow(NoSuchElementException::new);
        schema   = dbms.schemas().findAny().orElseThrow(NoSuchElementException::new);
        table    = schema.tables().filter(t -> TABLE_NAME.equals(t.getId())).findAny().orElseThrow(NoSuchElementException::new);
        column   = table.columns().findAny().orElseThrow(NoSuchElementException::new);
        pkColumn = table.primaryKeyColumns().findAny().orElseThrow(NoSuchElementException::new);

        table2  = schema.tables().filter(t -> TABLE_NAME2.equals(t.getId())).findAny().orElseThrow(NoSuchElementException::new);
        column2 = table2.columns().findAny().orElseThrow(NoSuchElementException::new);
    }
    
    public final static class SimpleMetadata extends AbstractApplicationMetadata {

        private String quote(String s) {
            return "\"" + s + "\"";
        }

        private String name(String s) {
            return quote(HasNameUtil.NAME) + " : " + quote(s);
        }

        private String dbTypeName(String dbmsTypeName) {
            return quote(DbmsUtil.TYPE_NAME) + " : " + quote(dbmsTypeName);
        }

        private String columnDatabaseType(String typeName) {
            return quote(HasTypeMapperUtil.DATABASE_TYPE) + " : " + quote(typeName);
        }

        private String array(String name, String... s) {
            return quote(name) + " : [\n" + Stream.of(s)
                .map(line -> line.replace("\n", "\n    "))
                .collect(joining(",\n    ")
            ) + "\n]";
        }

        private String objectWithKey(String name, String... s) {
            return quote(name) + " : " + object(s);
        }

        private String object(String... s) {
            return "{\n" + Stream.of(s)
                .map(line -> line.replace("\n", "\n    "))
                .collect(joining(",\n    ")) + "\n}";
        }
        
        @Override
        public Optional<String> getMetadata() {
            return Optional.of("{"
                + objectWithKey("config",
                    name("myProject"),
                    array(ProjectUtil.DBMSES,
                        object(name("myDbms"),
                            dbTypeName("MySQL"),
                            array(DbmsUtil.SCHEMAS,
                                object(
                                    name(SCHEMA_NAME),
                                    array(SchemaUtil.TABLES,
                                        object(
                                            name(TABLE_NAME),
                                            array(TableUtil.COLUMNS,
                                                object(
                                                    name(COLUMN_NAME),
                                                    columnDatabaseType(String.class.getName())
                                                )
                                            ),
                                            array(TableUtil.PRIMARY_KEY_COLUMNS,
                                                object(
                                                    name(COLUMN_NAME)
                                                )
                                            )
                                        ),
                                        object(
                                            name(TABLE_NAME2),
                                            array(TableUtil.COLUMNS,
                                                object(
                                                    name(COLUMN_NAME2),
                                                    columnDatabaseType(String.class.getName())
                                                )
                                            ),
                                            array(TableUtil.PRIMARY_KEY_COLUMNS,
                                                object(
                                                    name(COLUMN_NAME2)
                                                )
                                            )
                                        )
                                    )
                                )
                            )
                        )
                    )
                )
                + "}"
            );
        }
    }
}