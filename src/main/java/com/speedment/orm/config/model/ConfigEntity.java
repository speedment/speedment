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
package com.speedment.orm.config.model;

import com.speedment.orm.annotations.Api;
import com.speedment.util.Trees;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Copyright (c), Speedment AB
 *
 * @author pemi
 * @param <T> The type of the implementing class (self)
 * @param <P> The type of the parent class.
 * @param <C> The type of the child class.
 */
@Api(version = 0)
public abstract interface ConfigEntity<T extends ConfigEntity<T, P, C>, P extends ConfigEntity<?, ?, ?>, C extends ConfigEntity<?, ?, ?>> extends
        Comparable<T> {

    final int INDEX_FIRST = 0;
    final int INDEX_UNSET = -1;
    final int ORDINAL_FIRST = 1;
    final int ORDINAL_UNSET = -1;

    Class<T> getInterfaceMainClass();

    @External
    boolean isEnabled();

    @External
    T setEnabled(boolean enabled);

    @External
    String getName();

    @External
    T setName(String name);

    /*
     Set<DBEntityType> getContainingTypes();

     DBEntityType getType();
     */
    // Parent
    Optional<P> getParent();

    T setParent(final P parent);

    Optional<Class<P>> getParentInterfaceMainClass();

    default boolean isRoot() {
        return !getParent().isPresent();
    }

    // Children
    T add(final C child);

    T remove(final C child);

    boolean contains(final C child);

    Stream<? extends C> stream();

    default <CHILD_CLASS extends C> Stream<CHILD_CLASS> streamOf(final Class<CHILD_CLASS> clazz) {
        @SuppressWarnings({"cast", "unchecked"})
        final Stream<CHILD_CLASS> result = (Stream<CHILD_CLASS>) stream().filter(c -> clazz.isAssignableFrom(c.getClass()));
        return result;
    }

    default <CHILD_CLASS> Stream<? extends CHILD_CLASS> traversalOf(final Class<CHILD_CLASS> clazz) {
        @SuppressWarnings({"cast", "unchecked"})
        final Stream<CHILD_CLASS> result = (Stream<CHILD_CLASS>) Trees.traverse((ConfigEntity) this, ConfigEntity::stream, Trees.TraversalOrder.DEPTH_FIRST_PRE)
                .filter(ce -> clazz.isAssignableFrom(ce.getClass()));
        return result;
    }

    default boolean hasChildren() {
        return stream().findAny().isPresent();
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    static final Function<ConfigEntity, Optional<ConfigEntity>> PARENT_TRAVERSER = (ConfigEntity c) -> c.getParent();

    default String getRelativeName(ConfigEntity<?, ?, ?> from) {
        return Trees.walkOptional(this, PARENT_TRAVERSER, Trees.WalkingOrder.BACKWARD).map(ConfigEntity::getName).collect(Collectors.joining("."));
    }

    @SuppressWarnings("unchecked")
    default <E extends ConfigEntity<E, ?, ?>> Optional<E> getParent(final Class<E> clazz) {
        return Trees.walkOptional(this, PARENT_TRAVERSER, Trees.WalkingOrder.FORWARD)
                .filter(e -> clazz.isAssignableFrom(e.getClass()))
                .map((e) -> (E) e)
                .findFirst();
    }

    /**
     * Returns a value if the ConfigEntity by definition is not existing.
     *
     * @param <T>
     * @return
     */
    static <T extends ConfigEntity<T, ?, ?>> Optional<T> emptyConfigEntity() {
        return Optional.empty();
    }

    /*
     public String getRelativeQuotedName(DBEntity<?, ?> from) {
     return getRelativeQuotedName(from, false);
     }

     public String getRelativeQuotedName(DBEntity<?, ?> from, final boolean isWithinQuotes) {
     if (from == this) {
     return "";
     }
     final DBEntity<?, ?> p = getParent();
     if (from == p) {
     return getQuotedString(getName(), isWithinQuotes);
     }
     return (p.getRelativeQuotedName(from, isWithinQuotes)
     + getDbms().getDbmsType().getSchemaTableDelimiter()
     + getQuotedString(getName(), isWithinQuotes));
     }

     public String getQualifiedName() {
     final DBEntity<?, ?> p = getParent();
     if (p == null) {
     return getName();
     }
     return p.getQualifiedName() + "." + getName();
     }

     public String getQuotedName() {
     return getQuotedName(false);
     }

     public String getQuotedName(final boolean isWithinQuotes) {
     return getQuotedString(getName(), isWithinQuotes);
     }

     public String getQuotedString(final String s) {
     return getQuotedString(s, false);
     }

     public String getQuotedString(final String s, final boolean isWithinQuotes) {
     //final StringBuilder result = new StringBuilder();
     if (this instanceof Schema && getDbms().getDbmsType() == DbmsType.INFORMIX) {
     return new StringBuilder(getDbms().getFieldEncloserStart(isWithinQuotes)).append(s).append("@").append(getDbms().getSID()).append(getDbms().getFieldEncloserEnd(isWithinQuotes)).toString();
     } else {
     return new StringBuilder(getDbms().getFieldEncloserStart(isWithinQuotes)).append(s).append(getDbms().getFieldEncloserEnd(isWithinQuotes)).toString();
     }
     }

     public String getQualifiedQuotedName() {
     DBEntity<?, ?> ctn = getParent();
     if (this instanceof Schema) {
     return getQuotedName();
     }
     return ctn.getQualifiedQuotedName() + "." + getQuotedName();
     }

     */
    String toGroovy(int indentLevel);

    default String toGrovy() {
        return toGroovy(0);
    }

    public void fromGroovy(final Path path) throws IOException;

}
