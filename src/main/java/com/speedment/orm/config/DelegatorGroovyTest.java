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
package com.speedment.orm.config;

import com.speedment.util.stream.CollectorUtil;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Closure;
import groovy.util.DelegatingScript;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.function.Consumer;
import java.util.function.Supplier;
import org.codehaus.groovy.control.CompilationFailedException;
import org.codehaus.groovy.control.CompilerConfiguration;

/**
 *
 * @author pemi
 */
public class DelegatorGroovyTest {

    // http://blog.andresteingress.com/2013/10/19/groovy-sneak-peak-the-delegatingscript-base-class/
    public static void main(String[] args) throws CompilationFailedException, IOException {

        final Binding binding = new Binding();
        binding.setVariable("foo", 2);
        //binding.setVariable("myClass", new MyClass("Olle"));

        final CompilerConfiguration configuration = new CompilerConfiguration();
        configuration.setScriptBaseClass(DelegatingScript.class.getName());
        configuration.setDebug(true);
        configuration.setVerbose(true);
        configuration.setRecompileGroovySource(true);

        final GroovyShell shell = new GroovyShell(DelegatorGroovyTest.class.getClassLoader(), binding, configuration);

        DelegatingScript script = (DelegatingScript) shell.parse(new File("config.groovy"));

        final Project project = new Project();

        script.setDelegate(project);

        Object value = script.run();

        System.out.println(value);

        System.out.println(binding.getVariables());

        System.out.println(project.toString());

    }

    public static class ProjectPackage {

        public String location = "src/main/java";
        public String name = "com.company";

        public void doCall() {
            System.out.println("doCall()");
        }

        @Override
        public String toString() {
            return ProjectPackage.class.getSimpleName() + " { location='" + location + "', name='" + name + "'}";
        }

    }

    public static class Dbms extends DbEntity<Schema> {

        public String name, hostname, defaultSchema, password, username;
        public int port;
//        public List<String> additionalSchemas;

        public Dbms() {
//            additionalSchemas = new ArrayList<>();
        }

        public Schema schema(Closure c) {
            System.out.println("schema");
            return delegatorHelper(c, Schema::new, getChilds()::add);
        }

        @Override
        public Map<String, Object> getParameters() {
            return CollectorUtil.of(LinkedHashMap::new, m -> {
                m.put("name", name);
                m.put("hostname", hostname);
                m.put("port", port);
//                m.put("defaultSchema", defaultSchema);
                m.put("password", password);
                m.put("username", username);
//                m.put("additionalSchemas", additionalSchemas);
            });
        }

//        @Override
//        public String toString() {
//            return Methods.supplyAndModifyAndFinish(() -> new StringJoiner(", ", "{ ", " }"),
//                    sj -> {
//                        getParameters().entrySet().stream().forEach(e -> {
//                            if (e.getValue() != null) {
//                                final String value = (e.getValue() instanceof String) ? "'" + Objects.toString(e.getValue()) + "'" : Objects.toString(e.getValue());
//                                sj.add(e.getKey() + "=" + value);
//                            }
//                        });
//                    },
//                    StringJoiner::toString);
//        }
    }

    public static class MySql extends Dbms {

    }

    public static class Oracle extends Dbms {

        String sid;

        @Override
        public Map<String, Object> getParameters() {
            return CollectorUtil.of(() -> super.getParameters(), m -> m.put("sid", sid));
        }

    }

    public static class Schema extends DbEntity<Table> {

        public Table table(Closure c) {
            System.out.println("table");
            return delegatorHelper(c, Table::new, getChilds()::add);
        }
    }

    public static class Table extends DbEntity<Column> {

        public Column column(Closure c) {
            System.out.println("Column");
            return delegatorHelper(c, Column::new, getChilds()::add);
        }
    }

    public static class Column extends DbEntity<DbEntity<?>> {

    }

    public static class Project extends DbEntity {

        private ProjectPackage projectPackage;

        public Project() {
            log("Created");
            projectPackage = new ProjectPackage();
        }

        public Project(String name) {
            this();
        }

        public void bazz() {
            log("bazz()");
        }

        public ProjectPackage projectPackage(Closure c) {
            log("projectPackage()");
            final ProjectPackage pp = new ProjectPackage();
            c.setDelegate(pp);
            c.setResolveStrategy(Closure.DELEGATE_ONLY);
            Object result = c.call();
            projectPackage = pp;
            return pp;
        }

        public MySql mySql(Closure c) {
            System.out.println("mySql");
            System.out.println(c.getMaximumNumberOfParameters());
            System.out.println(c);
            return delegatorHelper(c, MySql::new, getChilds()::add);
        }

        public Oracle oracle(Closure c) {
            return delegatorHelper(c, Oracle::new, getChilds()::add);
        }

        private static void log(String msg) {
            System.out.println(msg);
        }

        public ProjectPackage getProjectPackage() {
            return projectPackage;
        }

        public void setProjectPackage(ProjectPackage projectPackage) {
            this.projectPackage = projectPackage;
        }

        @Override
        public Map<String, Object> getParameters() {
            return CollectorUtil.of(() -> super.getParameters(), m -> m.put("projectPackage", projectPackage));
        }

    }

    private static <S> S delegatorHelper(Closure c, Supplier<? extends S> supplier, Consumer<? extends S> updater) {
        Objects.requireNonNull(supplier);
        Objects.requireNonNull(updater);
        final S result = supplier.get();
        c.setDelegate(result);
        c.setResolveStrategy(Closure.DELEGATE_ONLY);
        ((Consumer) updater).accept(result);
        c.call();
        return result;
    }

    public static class DbEntity<C extends DbEntity<?>> {

        private String name;
        private final List<C> childs;

        public DbEntity() {
            childs = new ArrayList<C>();
            
//            {
//                public boolean add(C c) {
//                    System.out.println("Added " + c.toString());
//                    return super.add(c);
//                }
//            };
        }

        public Map<String, Object> getParameters() {
            return CollectorUtil.of(LinkedHashMap::new, m -> {
                m.put("name", getName());
                m.put("childs", getChilds());
            });
        }

        @Override
        public String toString() {
            return getClass().getSimpleName() + " " + CollectorUtil.of(() -> new StringJoiner(", ", "{ ", " }"),
                    sj -> {
                        getParameters().entrySet().stream().forEach(e -> {
                            if (e.getValue() != null) {
                                final Object initialValue = e.getValue();
                                final String stringValue;
                                if (initialValue instanceof DbEntity) {
                                    final DbEntity dbEntity = (DbEntity) initialValue;
                                    stringValue = dbEntity.toString();
                                } else if (initialValue instanceof String) {
                                    stringValue = "\"" + Objects.toString(initialValue) + "\"";
                                } else {
                                    stringValue = Objects.toString(initialValue);
                                }
                                sj.add(e.getKey() + "=" + stringValue);
                            }
                        });
                    },
                    StringJoiner::toString);
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<C> getChilds() {
            return childs;
        }

//        protected C childDelegatorHelper(Closure c, Supplier<? extends C> supplier) {
//            return DelegatorGroovyTest.delegatorHelper(c, supplier, getChilds()::add);
//        }
        public void postInject() {

        }

    }

}
