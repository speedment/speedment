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

import com.speedment.common.codegen.model.Class;
import com.speedment.common.codegen.model.*;
import com.speedment.common.codegen.provider.StandardJavaGenerator;
import com.speedment.common.injector.Injector;
import com.speedment.generator.translator.provider.StandardJavaLanguageNamer;
import com.speedment.generator.translator.provider.StandardTypeMapperComponent;
import com.speedment.runtime.config.*;
import com.speedment.runtime.config.provider.BaseDocument;
import com.speedment.runtime.config.trait.HasId;
import com.speedment.runtime.config.trait.HasIdUtil;
import com.speedment.runtime.config.trait.HasMainInterface;
import com.speedment.runtime.config.trait.HasNameUtil;
import com.speedment.runtime.core.exception.SpeedmentException;
import com.speedment.runtime.core.provider.DelegateInfoComponent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toMap;
import static org.junit.jupiter.api.Assertions.*;

final class AbstractJavaClassTranslatorTest {

    private static final String MY_PROJECT_NAME = "myProject";

    private Map<String, AtomicInteger> visitedMap;

    @BeforeEach
    void setup() {
        visitedMap = new ConcurrentHashMap<>();
    }

    private <D extends HasId & HasMainInterface> void visit(D doc) {
        visitedMap.computeIfAbsent(keyFor(doc.mainInterface(), doc.getId()), $ -> new AtomicInteger()).incrementAndGet();
    }

    private <D extends Document> String keyFor(java.lang.Class<D> clazz, String id) {
        return clazz.getSimpleName() + "#" + id;
    }

    @Test
    void testEmptyProjectForEveryProject() {
        testWithEmptyProject(b ->
            b.forEveryProject((clazz, p) -> {
                visit(p);
            }),
            () -> assertVisitedMapEquals(keyFor(Project.class, MY_PROJECT_NAME), 1)
        );
    }

    @Test
    void testEmptyProjectForEveryDbms() {
        testWithEmptyProject(b ->
            b.forEveryDbms((clazz, d) -> {
                visit(d);
            }),
            this::assertVisitedMapEquals
        );
    }

    @Test
    void testProjectWithOneDbmsForEveryProject() {
        testWithOneDbms(b ->
                b.forEveryProject((clazz, p) -> {
                    visit(p);
                }),
            () -> assertVisitedMapEquals(keyFor(Project.class, MY_PROJECT_NAME), 1)
        );
    }


    @Test
    void testProjectWithOneDbmsForEveryDbms() {
        testWithOneDbms(b ->
                b.forEveryDbms((clazz, d) -> {
                    visit(d);
                }),
            () -> assertVisitedMapEquals(keyFor(Dbms.class, "dbms0"), 1)
        );
    }

    @Test
    void testProjectWithTwoDbmsesForEveryDbms() {
        testWithTwoDbms(b ->
                b.forEveryDbms((clazz, d) -> {
                    visit(d);
                }),
            () -> assertVisitedMapEquals(
                entry(keyFor(Dbms.class, "dbms0"), 1),
                entry(keyFor(Dbms.class, "dbms1"), 1)
            )
        );
    }

    private void testWithEmptyProject(UnaryOperator<Translator.Builder<Class>> operator, Runnable assertor) {
        MyProject project = new MyProject();
        MyTranslator translator = new MyTranslator(project, operator);
        test(translator);
        MyFile file = new MyFile();
        translator.makeCodeGenModel(file);
        assertor.run();
    }

    private void testWithOneDbms(UnaryOperator<Translator.Builder<Class>> operator, Runnable assertor) {
        Map<String, Object> doc = new HashMap<>();
        doc.put(HasIdUtil.ID, MY_PROJECT_NAME);
        addDbms(doc, "dbms0");
        MyProject project = new MyProject(doc);
        MyTranslator translator = new MyTranslator(project, operator);
        test(translator);
        MyFile file = new MyFile();
        translator.makeCodeGenModel(file);
        assertor.run();
    }

    private void testWithTwoDbms(UnaryOperator<Translator.Builder<Class>> operator, Runnable assertor) {
        Map<String, Object> doc = new HashMap<>();
        doc.put(HasIdUtil.ID, MY_PROJECT_NAME);
        addDbms(doc, "dbms0");
        addDbms(doc, "dbms1");
        MyProject project = new MyProject(doc);
        MyTranslator translator = new MyTranslator(project, operator);
        test(translator);
        MyFile file = new MyFile();
        translator.makeCodeGenModel(file);
        assertor.run();
    }

    private void test(MyTranslator translator) {
        assertNotNull(translator.injector());
        assertNotNull(translator.infoComponent());
        assertNotNull(translator.typeMappers());
        assertNotNull(translator.generator());
        assertNotNull(translator.getSupport());
        assertNotNull(translator.generated());
        assertNotNull(translator.getClassOrInterfaceName());
        assertNotNull(translator.getJavaDoc());
        assertNotNull(translator.getCodeGenerator());
        assertNotNull(translator.emptyConstructor());
    }

    private void addDbms(Map<String, Object> map, String dbmsId) {
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> list = (List<Map<String, Object>>)map.get(ProjectUtil.DBMSES);
        if (list == null) {
            list = new ArrayList<>();
            map.put(ProjectUtil.DBMSES, list);
        }
        list.add(mapWithId(dbmsId));
    }

    private static final class MyTranslator extends AbstractJavaClassTranslator<Project, Class> {

        private final UnaryOperator<Builder<Class>> operator;

        private MyTranslator(Project project, UnaryOperator<Builder<Class>> operator) {
            super(createInjector(), project, Class::of);
            this.operator = requireNonNull(operator);

        }

        @Override
        protected String getClassOrInterfaceName() {
            return "MyClass";
        }

        @Override
        protected Class makeCodeGenModel(File file) {
            Translator.Builder<Class> builder = newBuilder(file, getClassOrInterfaceName());
            builder = operator.apply(builder);
            return builder.build();
        }

        @Override
        protected String getJavadocRepresentText() {
            return null;
        }
    }


    private static final class MyProject extends BaseDocument implements Project {

        private MyProject(String id) {
            this(mapWithId(id));
        }

        private MyProject() {
            this(MY_PROJECT_NAME);
        }

        private MyProject(Map<String, Object> data) {
            super(null, data);
        }

        @Override
        public String getName() {
            // Must implement getName because Project does not have any parent.
            return getAsString(HasNameUtil.NAME).orElse(ProjectUtil.DEFAULT_PROJECT_NAME);
        }

        @Override
        public Optional<? extends Document> getParent() {
            return Optional.empty();
        }

        @Override
        public Stream<Document> ancestors() {
            return Stream.empty();
        }

        @Override
        public Stream<Dbms> dbmses() {
            return children(ProjectUtil.DBMSES, MyDbmsImpl::new);
        }
    }

    final static class MyDbmsImpl extends BaseDocument implements Dbms {

        private MyDbmsImpl(Project parent, Map<String, Object> data) {
            super(parent, data);
        }

        @Override
        public Stream<Schema> schemas() {
            return children(DbmsUtil.SCHEMAS, Schema::create);
        }

        @Override
        public Optional<Project> getParent() {
            @SuppressWarnings("unchecked")
            final Optional<Project> parent = (Optional<Project>) super.getParent();
            return parent;
        }

    }

    private static class MyFile implements File {

        private String name;

        @Override
        public List<ClassOrInterface<?>> getClasses() {
            return null;
        }

        @Override
        public File copy() {
            return this;
        }

        @Override
        public List<Import> getImports() {
            return emptyList();
        }

        @Override
        public File set(Javadoc doc) {
            return this;
        }

        @Override
        public Optional<Javadoc> getJavadoc() {
            return Optional.empty();
        }

        @Override
        public File set(LicenseTerm doc) {
            return this;
        }

        @Override
        public Optional<LicenseTerm> getLicenseTerm() {
            return Optional.empty();
        }

        @Override
        public File setName(String name) {
            this.name = name;
            return this;
        }

        @Override
        public String getName() {
            return name;
        }
    }

    private void assertVisitedMapEquals(String key, Integer value) {
        assertVisitedMapEquals(entry(key, value));
    }

    private void assertVisitedMapEquals() {
        assertVisitedMapEquals(emptyMap());
    }

    @SafeVarargs
    @SuppressWarnings("varargs")
    private final void assertVisitedMapEquals(Map.Entry<String, Integer>... entries) {
        assertVisitedMapEquals(
            Stream.of(entries)
                .collect(
                    toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue
                    )
                )
        );
    }

    private static Map<String, Object> mapWithId(String id) {
        return Stream.<Map.Entry<String, Object>>of(
            new AbstractMap.SimpleEntry<>(HasIdUtil.ID, requireNonNull(id))
        ).collect(
            toMap(
                Map.Entry::getKey,
                Map.Entry::getValue
            )
        );
    }

    private void assertVisitedMapEquals(Map<String, Integer> map) {
        assertEquals(map.size(), visitedMap.size(), "The size of the maps differ");
        map.forEach((k, v) -> {
            assertTrue(visitedMap.containsKey(k), "Expected: " + map + ", Actual: " + visitedMap);
            assertEquals((int) v, visitedMap.get(k).get());
        });
    }

    private <T> UnaryOperator<T> compose(UnaryOperator<T> first, UnaryOperator<T> second) {
        return (T t) -> second.apply(first.apply(t));
    }

    private static <K, V> AbstractMap.Entry<K, V> entry(K k, V v) {
        return new AbstractMap.SimpleEntry<>(k, v);
    }

    private static Injector createInjector() {
        try {
            return Injector.builder()
                .withComponent(DelegateInfoComponent.class)
                .withComponent(StandardJavaGenerator.class)
                .withComponent(StandardTypeMapperComponent.class)
                .withComponent(StandardJavaLanguageNamer.class)
                .build();
        } catch (InstantiationException ie) {
            throw new SpeedmentException(ie);
        }
    }


}
