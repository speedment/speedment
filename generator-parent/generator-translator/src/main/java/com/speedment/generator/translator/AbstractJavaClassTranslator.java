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

import com.speedment.common.annotation.GeneratedCode;
import com.speedment.common.codegen.Generator;
import com.speedment.common.codegen.controller.AlignTabs;
import com.speedment.common.codegen.controller.AutoImports;
import com.speedment.common.codegen.model.Class;
import com.speedment.common.codegen.model.Enum;
import com.speedment.common.codegen.model.*;
import com.speedment.common.injector.Injector;
import com.speedment.common.mapstream.MapStream;
import com.speedment.runtime.config.*;
import com.speedment.runtime.config.provider.BaseDocument;
import com.speedment.runtime.config.trait.HasEnabled;
import com.speedment.runtime.config.trait.HasId;
import com.speedment.runtime.config.trait.HasMainInterface;
import com.speedment.runtime.config.trait.HasName;
import com.speedment.runtime.core.component.InfoComponent;
import com.speedment.runtime.typemapper.TypeMapperComponent;

import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

import static com.speedment.common.codegen.constant.DefaultJavadocTag.AUTHOR;
import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toSet;

/**
 *
 * @param <D>  document type.
 * @param <T>    Java type (Interface, Class or Enum) to generate
 * 
 * @author  Per Minborg
 */
public abstract class AbstractJavaClassTranslator<D extends Document & HasId & HasName & HasEnabled & HasMainInterface, T extends ClassOrInterface<T>>
    implements JavaClassTranslator<D, T> {

    protected static final String GETTER_METHOD_PREFIX = "get";
    protected static final String SETTER_METHOD_PREFIX = "set";
    protected static final String FINDER_METHOD_PREFIX = "find";
    private static final String JAVADOC_MESSAGE
        = "\n<p>\nThis file is safe to edit. It will not be overwritten by the "
        + "code generator.";

    private final D document;
    private final Function<String, T> mainModelConstructor;
    private final List<BiConsumer<File, Builder<T>>> listeners;

    private final Generator generator;
    private final InfoComponent infoComponent;
    private final TypeMapperComponent typeMappers;
    private final Injector injector;

    protected AbstractJavaClassTranslator(
        final Injector injector,
        final D document,
        final Function<String, T> mainModelConstructor
    ) {
        this.injector             = requireNonNull(injector);
        this.generator            = injector.getOrThrow(Generator.class);
        this.infoComponent        = injector.getOrThrow(InfoComponent.class);
        this.typeMappers          = injector.getOrThrow(TypeMapperComponent.class);
        this.document             = requireNonNull(document);
        this.mainModelConstructor = requireNonNull(mainModelConstructor);
        this.listeners            = new CopyOnWriteArrayList<>();
    }

    protected Injector injector() {
        return injector;
    }

    protected InfoComponent infoComponent() {
        return infoComponent;
    }

    protected TypeMapperComponent typeMappers() {
        return typeMappers;
    }

    protected Generator generator() {
        return generator;
    }

    @Override
    public final TranslatorSupport<D> getSupport() {
        return new TranslatorSupport<>(injector, document);
    }

    @Override
    public final D getDocument() {
        return document;
    }

    protected AnnotationUsage generated() {
        final String owner = infoComponent.getTitle();
        return AnnotationUsage.of(GeneratedCode.class).set(Value.ofText(owner));
    }

    /**
     * Returns the name of the {@link Class}/{@link Interface}/{@link Enum} that
     * this translator will create. The name is only the short name. It does not
     * include package information.
     * <p>
     * Example: {@code HareImpl}
     *
     * @return the short filename of the file to generate
     */
    protected abstract String getClassOrInterfaceName();

    /**
     * Creates and configures a {@link Class}/{@link Interface}/{@link Enum} for
     * the specified {@code File}. This method uses a builder created using the
     * {@link #newBuilder(File, String)} method to make sure all the listeners
     * are notified when the model is built.
     * <p>
     * Observe that this method doesn't add the created model to the file.
     *
     * @param file the file to make a model for
     * @return the model made
     */
    protected abstract T makeCodeGenModel(File file);

    /**
     * This method is executed after the file has been created but before it is
     * returned to the code generator.
     *
     * @param file the file to operate on
     */
    protected void finializeFile(File file) {
        // Do nothing
    }

    @Override
    public File get() {
        final File file = File.of(getSupport().baseDirectoryName() + "/"
            + (isInGeneratedPackage() ? "generated/" : "")
            + getClassOrInterfaceName() + ".java"
        );

        final T item = makeCodeGenModel(file);
        if (!item.getJavadoc().isPresent()) {
            item.set(getJavaDoc());
        }
        file.add(item);
        finializeFile(file);
        file.call(new AutoImports(getCodeGenerator().getDependencyMgr()));
        file.call(new AlignTabs<>());
        return file;
    }

    protected abstract String getJavadocRepresentText();

    protected Javadoc getJavaDoc() {
        final String owner;
        final String message;

        if (isInGeneratedPackage()) {
            owner = infoComponent.getTitle();
            message = getGeneratedJavadocMessage();
        } else {
            owner = project().orElseThrow(NoSuchElementException::new).getCompanyName();
            message = JAVADOC_MESSAGE;
        }

        return Javadoc.of(getJavadocRepresentText() + message)
            .add(AUTHOR.setValue(owner));
    }

    @Override
    public Generator getCodeGenerator() {
        return generator;
    }

    @Override
    public boolean isInGeneratedPackage() {
        return false;
    }

    @Override
    public void onMake(BiConsumer<File, Builder<T>> action) {
        listeners.add(action);
    }

    @Override
    public Stream<BiConsumer<File, Builder<T>>> listeners() {
        return listeners.stream();
    }

    protected final class BuilderImpl implements Translator.Builder<T> {

        private static final String PROJECTS = "projects";
        private final String name;
        private final Map<Phase, Map<String, List<BiConsumer<T, Document>>>> map;

        // Special for this case
        private final Map<Phase, List<BiConsumer<T, ForeignKey>>> foreignKeyReferencesThisTableConsumers;

        public BuilderImpl(String name) {
            this.name = requireNonNull(name);
            // Phase stuff
            this.map = new EnumMap<>(Phase.class);
            this.foreignKeyReferencesThisTableConsumers = new EnumMap<>(Phase.class);
            for (final Phase phase : Phase.values()) {
                map.put(phase, new HashMap<>());
                foreignKeyReferencesThisTableConsumers.put(phase, new ArrayList<>());
            }
        }

        @Override
        public <P extends Document, D extends Document> Builder<T> forEvery(Phase phase, String key, BiFunction<P, Map<String, Object>, D> constructor, BiConsumer<T, D> consumer) {
            aquireListAndAdd(phase, key, wrap(consumer, constructor));
            return this;
        }

        @Override
        public Builder<T> forEveryProject(Phase phase, BiConsumer<T, Project> consumer) {
            aquireListAndAdd(phase, PROJECTS, wrap(consumer, Project::create));
            return this;
        }

        @Override
        public Builder<T> forEveryDbms(Phase phase, BiConsumer<T, Dbms> consumer) {
            aquireListAndAdd(phase, ProjectUtil.DBMSES, wrap(consumer, Dbms::create));
            return this;
        }

        @Override
        public Builder<T> forEverySchema(Phase phase, BiConsumer<T, Schema> consumer) {
            aquireListAndAdd(phase, DbmsUtil.SCHEMAS, wrap(consumer, Schema::create));
            return this;
        }

        @Override
        public Builder<T> forEveryTable(Phase phase, BiConsumer<T, Table> consumer) {
            aquireListAndAdd(phase, SchemaUtil.TABLES, wrap(consumer, Table::create));
            return this;
        }

        @Override
        public Builder<T> forEveryColumn(Phase phase, BiConsumer<T, Column> consumer) {
            aquireListAndAdd(phase, TableUtil.COLUMNS, wrap(consumer, Column::create));
            return this;
        }

        @Override
        public Builder<T> forEveryIndex(Phase phase, BiConsumer<T, Index> consumer) {
            aquireListAndAdd(phase, TableUtil.INDEXES, wrap(consumer, Index::create));
            return this;
        }

        @Override
        public Builder<T> forEveryForeignKey(Phase phase, BiConsumer<T, ForeignKey> consumer) {
            aquireListAndAdd(phase, TableUtil.FOREIGN_KEYS, wrap(consumer, ForeignKey::create));
            return this;
        }

        private <P extends Document, D extends Document> BiConsumer<T, Document> wrap(BiConsumer<T, D> consumer, BiFunction<P, Map<String, Object>, D> constructor) {
            return (t, doc) -> {
                @SuppressWarnings("unchecked") final P parent = (P) doc.getParent().orElse(null);
                consumer.accept(t, constructor.apply(parent, doc.getData()));
            };
        }

        @Override
        public Builder<T> forEveryForeignKeyReferencingThis(Phase phase, BiConsumer<T, ForeignKey> consumer) {
            foreignKeyReferencesThisTableConsumers.get(phase).add(requireNonNull(consumer));
            return this;
        }

        @SuppressWarnings("unchecked")
        private void aquireListAndAdd(Phase phase, String key, BiConsumer<T, Document> consumer) {
            aquireList(phase, key).add(requireNonNull(consumer));
        }

        @SuppressWarnings("unchecked")
        private <C extends Document> List<BiConsumer<T, C>> aquireList(Phase phase, String key) {
            return (List<BiConsumer<T, C>>) (List<?>) map.get(phase).computeIfAbsent(key, unused -> new CopyOnWriteArrayList<>());
        }

        private <D extends Document & HasMainInterface> void act(Phase phase, String key, T item, D document) {
            aquireList(phase, key).forEach(c
                -> c.accept(requireNonNull(item), requireNonNull(document))
            );
        }

        @Override
        public T build() {
            final T model = mainModelConstructor.apply(name);

            if (isInGeneratedPackage()) {
                model.add(generated());
            }

            for (Phase phase : Phase.values()) {
                project().ifPresent(p -> act(phase, PROJECTS, model, p));
                dbms().ifPresent(d -> act(phase, ProjectUtil.DBMSES, model, d));
                schema().ifPresent(s -> act(phase, DbmsUtil.SCHEMAS, model, s));
                table().ifPresent(t -> act(phase, SchemaUtil.TABLES, model, t));

                MapStream.of(map.get(phase))
                    .flatMapValue(List::stream)
                    .forEachOrdered((key, actor) -> table()
                        .ifPresent(table -> MapStream.of(table.getData())
                            .filterKey(key::equals)

                            // Filter out elements that map to a list and flat
                            // map the stream so that every value in the list
                            // becomes an element of the stream.
                            .filterValue(List.class::isInstance)
                            .mapValue(AbstractJavaClassTranslator::castToListOfMap)
                            .flatMapValue(List::stream)

                            // The foreignKeys-property is special in that only
                            // keys that reference enabled and existing table
                            // and columns are to be included.
                            .filter((k, v) -> {
                                if (TableUtil.FOREIGN_KEYS.equals(k)) {
                                    return ForeignKey.create(table, v)
                                        .foreignKeyColumns()
                                        .map(ForeignKeyColumn::findColumn)
                                        .allMatch(c ->
                                            c.filter(Column::isEnabled)
                                                .map(Column::getParentOrThrow)
                                                .filter(Table::isEnabled)
                                                .isPresent()
                                        );

                                    // Indexes, PrimaryKeyColumns and Columns should
                                    // be handled as usual.
                                } else return true;
                            })

                            // We are now done with the keys. Use the values of
                            // the stream to produce an ordinary stream of base
                            // documents.
                            .values()
                            .map(data -> new BaseDocument(table, data))

                            // All the documents that are enabled should be
                            // passed to the actor.
                            .filter(HasEnabled::test)
                            .forEachOrdered(c -> actor.accept(model, c))
                        )
                    );

                if (Table.class.equals(getDocument().mainInterface())) {
                    schema().ifPresent(schema -> schema.tables()
                        .filter(HasEnabled::test)
                        .flatMap(Table::foreignKeys)
                        .filter(HasEnabled::test)
                        .filter(fk -> fk.foreignKeyColumns()
                            .filter(fkc -> fkc.getForeignTableName().equals(getDocument().getId()))
                            .filter(HasEnabled::test)
                            .anyMatch(fkc -> fkc.findForeignColumn().map(HasEnabled::test).orElse(false))
                        ).forEachOrdered(fk
                            -> foreignKeyReferencesThisTableConsumers.get(phase).forEach(
                            c -> c.accept(model, fk)
                            )
                        )
                    );
                }

                // Traverse downwards, Fix #224
                namedChildren(document)
                    .forEach(e -> processDocument(model, phase, e.getKey(), e.getValue()));

            }
            return model;
        }

        private Stream<Map.Entry<String, Document>> namedChildren(Document doc) {
            @SuppressWarnings("unchecked")
            Stream<Map.Entry<String, Document>> s =
             doc.getData()
                .entrySet()
                .stream()
                .filter(e -> e.getValue() instanceof List)
                .flatMap(e -> ((List<Map<String, Object>>) e.getValue()).stream().map(m -> new AbstractMap.SimpleEntry<>(e.getKey(), new BaseDocument(document, m))));

                return s.filter(e -> HasEnabled.test(e.getValue()));
        }

        private final Set<String> aboveTable = Stream.of(
            ProjectUtil.DBMSES,
            DbmsUtil.SCHEMAS,
            SchemaUtil.TABLES
        )
        .collect(toSet());

        private void processDocument(T model, Phase phase, String key, Document doc) {
            // Recursively invoke on the children
            if (aboveTable.contains(key)) {
                namedChildren(doc)
                    .forEach(e ->
                        // Tables and below has already been handled.
                        processDocument(model, phase, e.getKey(), e.getValue())
                    );

                // Process the document if in right phase and for right key (type of document)
                map
                    .getOrDefault(phase, emptyMap())
                    .getOrDefault(key, emptyList())
                    .forEach(c -> c.accept(model, doc));
            }

        }

    }

    private static List<Map<String, Object>> castToListOfMap(Object v) {
        @SuppressWarnings("unchecked")
        final List<Map<String, Object>> val = (List<Map<String, Object>>)v;
        return val;
    }

    protected final Builder<T> newBuilder(File file, String className) {
        requireNonNull(file);
        requireNonNull(className);
        final Builder<T> builder = new BuilderImpl(className);
        listeners().forEachOrdered(action -> action.accept(file, builder));
        return builder;
    }

    public Field fieldFor(Column c) {
        return Field.of(
            getSupport().variableName(c), 
            typeMappers.get(c).getJavaType(c)
        );
    }

    public Constructor emptyConstructor() {
        return Constructor.of().public_();
    }

    public enum CopyConstructorMode {
        SETTER, FIELD
    }

    public Constructor copyConstructor(Type type, CopyConstructorMode mode) {
        final TranslatorSupport<D> support = getSupport();
        final Constructor constructor = Constructor.of().protected_()
            .add(Field.of(support.variableName(), type));

        columns().forEachOrdered(c -> {
            switch (mode) {
                case FIELD:
                    defineField(support, constructor, c);
                    break;
                case SETTER:
                    addSetter(support, constructor, c);
                    break;
                default:
                    throw new UnsupportedOperationException(
                        "Unknown mode '" + mode + "'."
                    );
            }
        });

        return constructor;
    }

    private void addSetter(TranslatorSupport<D> support, Constructor constructor, Column c) {
        if (c.isNullable()) {
            constructor.add(
                support.variableName() + "."
                + GETTER_METHOD_PREFIX + support.typeName(c)
                + "().ifPresent(this::"
                + SETTER_METHOD_PREFIX + support.typeName(c)
                + ");"
            );
        } else {
            constructor.add(
                SETTER_METHOD_PREFIX + support.typeName(c)
                + "(" + support.variableName()
                + ".get" + support.typeName(c)
                + "());"
            );
        }
    }

    private void defineField(TranslatorSupport<D> support, Constructor constructor, Column c) {
        constructor.add(
            "this." + support.variableName(c) +
            " = " + support.variableName() +
            "." + GETTER_METHOD_PREFIX +
            support.typeName(c) + "();");
    }

    protected String getGeneratedJavadocMessage() {
        return "\n<p>\nThis file has been automatically generated by " + 
            infoComponent.getTitle() + ". Any changes made to it will be overwritten.";
    }
}