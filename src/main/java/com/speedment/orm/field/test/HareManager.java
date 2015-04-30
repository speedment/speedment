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
package com.speedment.orm.field.test;

import com.speedment.orm.config.model.Column;
import com.speedment.orm.config.model.Table;
import com.speedment.orm.core.Buildable;
import com.speedment.orm.core.manager.Manager;
import com.speedment.orm.field.Field;
import com.speedment.orm.field.Operator;
import com.speedment.orm.field.PredicateBuilder;
import com.speedment.orm.field.StandardBinaryOperator;
import com.speedment.orm.field.reference.BinaryPredicateBuilder;
import com.speedment.orm.platform.Platform;
import com.speedment.orm.platform.component.ManagerComponent;
import com.speedment.util.Cast;
import com.speedment.util.stream.builder.ReferenceStreamBuilder;
import com.speedment.util.stream.builder.action.Action;
import com.speedment.util.stream.builder.action.reference.FilterAction;
import com.speedment.util.stream.builder.pipeline.BasePipeline;
import com.speedment.util.stream.builder.pipeline.Pipeline;
import com.speedment.util.stream.builder.streamterminator.StreamTerminator;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 *
 * @author pemi
 */
public class HareManager implements Manager<String, Hare, Buildable<Hare>> {

    Map<String, Hare> names;
    Map<String, Map<String, Hare>> colors;
    Map<Integer, Map<String, Hare>> ages;

    private final Table table;

    public HareManager() {
        names = new HashMap<>();
        colors = new HashMap<>();
        ages = new HashMap<>();
        add(new HareHarry());
        add(new HareOlivia());

        table = Table.newTable();
        table.setName("hare");

        Column nameColumn = Column.newColumn();
        nameColumn.setName("name");
        table.add(nameColumn);

        Column colorColumn = Column.newColumn();
        colorColumn.setName("color");
        table.add(colorColumn);

        Column ageColumn = Column.newColumn();
        ageColumn.setName("age");
        table.add(ageColumn);

        Platform.get().get(ManagerComponent.class).put(this);

    }

    private void add(Hare hare) {
        final String pk = hare.getName();
        names.put(pk, hare);
        colors.computeIfAbsent(hare.getColor(), k -> new HashMap<>()).put(pk, hare);
        ages.computeIfAbsent(hare.getAge(), k -> new HashMap<>()).put(pk, hare);
    }

    public Stream<Hare> stream() {
        StreamTerminator streamTerminator = new StreamTerminator() {

            @Override
            public <T extends Pipeline> T optimize(T initialPipeline) {
                if (!initialPipeline.isEmpty()) {
                    Action<?, ?> firstAction = initialPipeline.getFirst();
                    if (firstAction instanceof FilterAction) {
                        final FilterAction<Hare> filterAction = (FilterAction<Hare>) firstAction;
                        final Predicate<? super Hare> predicate = filterAction.getPredicate();
                        if (predicate instanceof PredicateBuilder) {

                            final PredicateBuilder predicateBuilder = (PredicateBuilder) predicate;
                            final Field<Hare> field = predicateBuilder.getField();
                            final Column column = field.getColumn();

                            if (field.equals(HareField.NAME)) {

                                final Operator operator = predicateBuilder.getOperator();
                                final Optional<BinaryPredicateBuilder> oPredicateBuilder = Cast.cast(predicateBuilder, BinaryPredicateBuilder.class);

                                if (oPredicateBuilder.isPresent() && operator == StandardBinaryOperator.EQUAL) {
                                    final String name = (String) oPredicateBuilder.get().getValue();
                                    initialPipeline.removeFirst();
                                    initialPipeline.setInitialSupplier(() -> Stream.of(names.get(name)));
                                }
                            }

//                        final SpecialPredicate<Hare, ?> specialPredicate = (SpecialPredicate<Hare, ?>) predicate;
//                        final Function<Hare, String> getName = Hare::getName;
//                        final Function<Hare, String> getColor = Hare::getColor;
//                        final Function<Hare, Integer> getAge = Hare::getAge;
//
//                        final Method applyMethod = findApplyMethod(specialPredicate.getMapper());
//                        final Method getNameMethod = findApplyMethod(getName);
//                        final Method getColorMethod = findApplyMethod(getColor);
//                        final Method getAgeMethod = findApplyMethod(getAge);
//
//                        final Lookup lookup = MethodHandles.lookup();
//
////                        Field hareNameField = null;
////                        try {
////                            hareNameField = Hare.class.getField("name");
////                        } catch (NoSuchFieldException | SecurityException ex) {
////                            ex.printStackTrace();
////                        }
//                        MethodHandle mhHareName = null;
//                        try {
//                            mhHareName = lookup.unreflect(getNameMethod);
//                        } catch (IllegalAccessException ex) {
//                            ex.printStackTrace();
//                        }
//
//                        MethodHandle mh = null;
//                        try {
//                            mh = lookup.unreflect(applyMethod);
//                        } catch (IllegalAccessException ex) {
//                            ex.printStackTrace();
//                        }
//
//                        if (mh.equals(mhHareName)) {
//                            String name = (String) specialPredicate.getValue();
//                            initialPipeline.removeFirst();
//                            initialPipeline.setInitialSupplier(() -> Stream.of(names.get(name)));
//                        }
////                        if (applyMethod.equals(getNameMethod)) {
////                            String name = (String) specialPredicate.getValue();
////                            initialPipeline.removeFirst();
////                            initialPipeline.setInitialSupplier(() -> Stream.of(names.get(name)));
////                        }
//                        if (applyMethod.equals(getColorMethod)) {
//                            String color = (String) specialPredicate.getValue();
//                            initialPipeline.removeFirst();
//                            initialPipeline.setInitialSupplier(() -> colors.get(color).values().stream());
//                        }
//                        if (applyMethod.equals(getAgeMethod)) {
//                            Integer age = (Integer) specialPredicate.getValue();
//                            initialPipeline.removeFirst();
//                            initialPipeline.setInitialSupplier(() -> ages.get(age).values().stream());
//                        }
                        }
                    }
                }
                return initialPipeline;
            }

        };

        return new ReferenceStreamBuilder<>(new BasePipeline(() -> names.values().stream()), streamTerminator);
    }

    private Method findApplyMethod(Function<?, ?> function) {
        final Method[] predicateMethods = function.getClass().getMethods();
        for (Method method : predicateMethods) {
            if (method.getName().equals("apply")) {
                return method;
            }
        }
        return null;
    }

    @Override
    public String primaryKeyFor(Hare entity) {
        return entity.getName();
    }

    @Override
    public Object get(Hare entity, Column column) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void set(Buildable<Hare> builder, Column column, Object value) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object find(Hare entity, Column column) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Table getTable() {
        return table;
    }

    @Override
    public Class<HareManager> getManagerClass() {
        return HareManager.class;
    }

    @Override
    public Class<Hare> getEntityClass() {
        return Hare.class;
    }

    @Override
    public Class<Buildable<Hare>> getBuilderClass() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Buildable<Hare> builder() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Buildable<Hare> toBuilder(Hare entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String toJson(Hare entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onInsert(Hare entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onUpdate(Hare entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onDelete(Hare primaryKey) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Optional<Hare> persist(Hare entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Optional<Hare> update(Hare entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Optional<Hare> remove(Hare entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Boolean initialize() {
        return true;
    }

    @Override
    public Boolean resolve() {
        return true;
    }

    @Override
    public Boolean start() {
        return true;
    }

    @Override
    public Boolean stop() {
        return true;
    }

}
