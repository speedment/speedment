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
package com.speedment.internal.gui.properties;

import com.speedment.Speedment;
import com.speedment.annotation.External;
import com.speedment.config.aspects.Child;
import com.speedment.internal.core.config.utils.MethodsParser;
import static com.speedment.internal.core.config.utils.MethodsParser.METHOD_IS_VISIBLE_IN_GUI;
import com.speedment.config.parameters.DbmsType;
import static com.speedment.internal.util.NullUtil.requireNonNulls;
import com.speedment.internal.util.JavaLanguage;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.scene.control.TreeView;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author Emil Forslund
 */
public final class TablePropertyManager {

    private final Speedment speedment;
    private final TreeView<Child<?>> tree;

    public TablePropertyManager(Speedment speedment, TreeView<Child<?>> view) {
        this.speedment = requireNonNull(speedment);
        this.tree      = requireNonNull(view);
    }

	@SuppressWarnings("unchecked")
    public Stream<TableProperty<?>> propertiesFor(List<Child<?>> nodes) {
        requireNonNulls(nodes);
        return nodes.stream().flatMap(node -> {
            return MethodsParser.streamOfExternalNoneSecretGetters(node.getClass())
                    .filter(METHOD_IS_VISIBLE_IN_GUI)
                    .sorted((m0, m1) -> m0.getName().compareTo(m1.getName()))
                    //.sorted(comparing(Method::getName))
                    .map(m -> {
                        final String javaName;
                        if (m.getName().startsWith("is")) {
                            javaName = m.getName().substring(2);
                        } else {
                            javaName = m.getName().substring(3);
                        }

                        final String propertyName = JavaLanguage.toHumanReadable(javaName);
                        final External e = MethodsParser.getExternalFor(m, node.getClass());

                        Class<?> type = m.getReturnType();
                        Class<?> innerType = e.type();
                        boolean optional = Optional.class.isAssignableFrom(type);

                        if (Boolean.class.isAssignableFrom(innerType)) {
                            return createBooleanProperty(propertyName, nodes,
                                    findGetter(node.getClass(), javaName, innerType, optional),
                                    findSetter(node.getClass(), javaName, Boolean.class)
                            );
                        } else if (String.class.isAssignableFrom(innerType)) {
                            if ("Password".equals(propertyName)) {
                                return createPasswordProperty(propertyName, nodes,
                                        findGetter(node.getClass(), javaName, innerType, optional),
                                        findSetter(node.getClass(), javaName, String.class)
                                );
                            } else {
                                return createStringProperty(propertyName, nodes,
                                        findGetter(node.getClass(), javaName, innerType, optional),
                                        findSetter(node.getClass(), javaName, String.class)
                                );
                            }
                        } else if (Class.class.isAssignableFrom(innerType)) {
                            return createClassProperty(propertyName, nodes,
                                    findGetter(node.getClass(), javaName, innerType, optional),
                                    findSetter(node.getClass(), javaName, Class.class)
                            );
                        } else if (Number.class.isAssignableFrom(innerType)) {
                            @SuppressWarnings("unchecked")
                            final Class<Number> castedInnerType = (Class<Number>) innerType;
                            return createNumberProperty(propertyName, nodes,
                                    findGetter(node.getClass(), javaName, innerType, optional),
                                    findSetter(node.getClass(), javaName, castedInnerType)
                            );
                        } else if (DbmsType.class.isAssignableFrom(innerType)) {
                            @SuppressWarnings("unchecked")
                            final Class<DbmsType> castedInnerType = (Class<DbmsType>) innerType;
                            return createDbmsTypeProperty(propertyName, nodes,
                                    findGetter(node.getClass(), javaName, innerType, optional),
                                    findSetter(node.getClass(), javaName, castedInnerType)
                            );
                        } else if (Enum.class.isAssignableFrom(innerType)) {
                            @SuppressWarnings("unchecked")
                            final Class<Enum> castedInnerType = (Class<Enum>) innerType;
                            @SuppressWarnings({"unchecked", "rawtypes"})
                            final Enum defaultValue = (Enum) type.getEnumConstants()[0];

                            return createEnumProperty(propertyName, nodes,
                                    findGetter(node.getClass(), javaName, Enum.class, optional),
                                    findSetter(node.getClass(), javaName, castedInnerType),
                                    defaultValue
                            );
                        } else {
                            throw new UnsupportedOperationException("Found method '" + m + "' in '" + node.getClass() + "' marked as @External of unsupported type " + type.getName());
                        }
                    });
        }).map(tp -> (TableProperty<?>) tp)
          .distinct()
          .sorted();
    }
    
    private <V> Function<Child<?>, V> findGetter(Class<?> nodeClass, String javaName, Class<?> innerType, boolean optional) {
        requireNonNulls(nodeClass, javaName, innerType);
        final String methodName;

        if (Boolean.class.isAssignableFrom(innerType)) {
            methodName = "is" + javaName;
        } else {
            methodName = "get" + javaName;
        }

        try {
            final Method method = nodeClass.getMethod(methodName);
            return c -> {
                try {
                    if (optional) {
						@SuppressWarnings("unchecked")
						final Optional<V> result = (Optional<V>) method.invoke(c);
                        return result.orElse(null);
                    } else {
						@SuppressWarnings("unchecked")
						final V result = (V) method.invoke(c);
                        return result;
                    }
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | ClassCastException ex) {
                    throw new RuntimeException("Could not invoke method '" + methodName + "' in class '" + nodeClass.getName() + "'.", ex);
                }
            };
        } catch (NoSuchMethodException ex) {
            throw new RuntimeException("Could not find @External method '" + methodName + "' in class '" + nodeClass.getName() + "'.", ex);
        }
    }

    private <V> BiConsumer<Child<?>, V> findSetter(Class<?> nodeClass, String javaName, Class<V> paramType) {
        requireNonNulls(nodeClass, javaName, paramType);
        final String methodName = "set" + javaName;

        try {
            final Method method = nodeClass.getMethod(methodName, paramType);
            return (c, v) -> {
                try {
                    method.invoke(c, v);
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                    throw new RuntimeException("Could not invoke method '" + methodName + "' in class '" + nodeClass.getName() + "'.", ex);
                }
            };
        } catch (NoSuchMethodException ex) {
            throw new RuntimeException("Could not find @External method '" + methodName + "' with param '" + paramType + "' in class '" + nodeClass.getName() + "'.", ex);
        }
    }

    private TableProperty<Boolean> createBooleanProperty(String label, List<Child<?>> nodes, Function<Child<?>, Boolean> selector, BiConsumer<Child<?>, Boolean> updater) {
        requireNonNulls(label, selector, updater);
        requireNonNulls(nodes);
        return createProperty(label, nodes, (a, b) -> new TableBooleanProperty(speedment, a, b), selector, updater);
    }

    private TableProperty<String> createStringProperty(String label, List<Child<?>> nodes, Function<Child<?>, String> selector, BiConsumer<Child<?>, String> updater) {
        requireNonNulls(label, selector, updater);
        requireNonNulls(nodes);
        return createProperty(label, nodes, (a, b) -> new TableStringProperty(speedment, a, b), selector, updater);
    }

    private TableProperty<String> createPasswordProperty(String label, List<Child<?>> nodes, Function<Child<?>, String> selector, BiConsumer<Child<?>, String> updater) {
        requireNonNulls(label, selector, updater);
        requireNonNulls(nodes);
        return createProperty(label, nodes, (a, b) -> new TablePasswordProperty(speedment, a, b), selector, updater);
    }

	@SuppressWarnings("rawtypes")
    private TableProperty<Class> createClassProperty(String label, List<Child<?>> nodes, Function<Child<?>, Class> selector, BiConsumer<Child<?>, Class> updater) {
        requireNonNulls(label, selector, updater);
        requireNonNulls(nodes);
        return createProperty(label, nodes, (a, b) -> new TableClassProperty(speedment, a, b), selector, updater);
    }

    private TableProperty<Number> createNumberProperty(String label, List<Child<?>> nodes, Function<Child<?>, Number> selector, BiConsumer<Child<?>, Number> updater) {
        requireNonNulls(label, selector, updater);
        requireNonNulls(nodes);
        return createProperty(label, nodes, (a, b) -> new TableIntegerProperty(speedment, a, b), selector, updater);
    }

    private TableProperty<DbmsType> createDbmsTypeProperty(String label, List<Child<?>> nodes, Function<Child<?>, DbmsType> selector, BiConsumer<Child<?>, DbmsType> updater) {
        requireNonNulls(label, selector, updater);
        requireNonNulls(nodes);
        return createProperty(label, nodes, (a, b) -> new TableDbmsTypeProperty(speedment, a, b), selector, updater);
    }

    private <V extends Enum<V>> TableProperty<V> createEnumProperty(String label, List<Child<?>> nodes, Function<Child<?>, V> selector, BiConsumer<Child<?>, V> updater, V defaultValue) {
        requireNonNulls(label, selector, updater);
        requireNonNulls(nodes);
        return createProperty(label, nodes, (a, b) -> new TableEnumProperty<>(speedment, a, b), selector, updater, defaultValue);
    }

    private <V> TableProperty<V> createProperty(String label, List<Child<?>> nodes, BiFunction<String, V, TableProperty<V>> initiator, Function<Child<?>, V> selector, BiConsumer<Child<?>, V> updater) {
        requireNonNulls(label, selector, updater);
        requireNonNulls(nodes);
        return createProperty(label, nodes, initiator, selector, updater, null);
    }

    private <V> TableProperty<V> createProperty(String label, List<Child<?>> nodes, BiFunction<String, V, TableProperty<V>> initiator, Function<Child<?>, V> selector, BiConsumer<Child<?>, V> updater, V defaultValue) {
        requireNonNulls(label, selector, updater);
        requireNonNulls(nodes);
        // default value nullable
        V option = getOption(nodes, selector);

        if (option == null) {
            option = defaultValue;
        }

        final TableProperty<V> property = initiator.apply(label, option);

        property.valueProperty().addListener((ob, o, newValue) -> {
            tree.getSelectionModel().getSelectedItems().stream()
                    .map(i -> i.getValue())
                    .forEachOrdered(c -> updater.accept(c, newValue));
        });

        return property;
    }

    private <V> V getOption(List<Child<?>> nodes, Function<Child<?>, V> selector) {
        requireNonNull(selector);
        requireNonNulls(nodes);
        final List<V> variants = nodes.stream().map(n -> selector.apply(n)).distinct().collect(Collectors.toList());
        if (variants.size() == 1) {
            return variants.get(0);
        } else {
            return null;
        }
    }
}
