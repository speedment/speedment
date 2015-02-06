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
import com.speedment.orm.config.ConfigParameter;
import com.speedment.util.Trees;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Copyright (c), Speedment AB
 *
 * @author pemi
 * @param <T> The type of the implementing class
 * @param <P> The type of the parent class.
 * @param <C> The type of the child class.
 */
//public abstract interface ConfigEntity<T extends ConfigEntity<T, P, C>, P extends ConfigEntity<P, ?, T>, C extends ConfigEntity<?, ?, ?>> extends Comparable<T> {
@Api(version = 0)
public abstract interface ConfigEntity<T extends ConfigEntity<T, P, C>, P extends ConfigEntity<?, ?, ?>, C extends ConfigEntity<?, ?, ?>> extends Comparable<T> {

    boolean isEnabled();

    T setEnabled(boolean enabled);

    String getName();

    T setName(CharSequence name);

    /*
     Set<DBEntityType> getContainingTypes();

     DBEntityType getType();
     */
    // Parent
    Optional<P> getParent();

    T setParent(final P parent);

    default boolean isRoot() {
        return getParent().isPresent();
    }

    // Children
    T add(final C child);

    T remove(final C child);

    boolean contains(final C child);

    Stream<? extends C> childrenStream();

    default boolean hasChildren() {
        return childrenStream().findAny().isPresent();
    }

    // Configuration
    <E> T add(ConfigParameter<? extends E> configParameter);

    <E> T remove(ConfigParameter<? extends E> configParameter);

    <E> boolean contains(ConfigParameter<? extends E> configParameter);

    Stream<ConfigParameter<?>> configStream();

    default boolean hasConfig() {
        return configStream().findAny().isPresent();
    }

    static final Function<ConfigEntity, Optional<ConfigEntity>> PARENT_TRAVERSER = (ConfigEntity c) -> c.getParent();
    static final Function<ConfigEntity, CharSequence> NAME_MAPPER = ConfigEntity<?, ?, ?>::getName;

    default String getRelativeName(ConfigEntity<?, ?, ?> from) {
        return Trees.walkOptional(this, PARENT_TRAVERSER, Trees.WalkingOrder.BACKWARD).map(NAME_MAPPER).collect(Collectors.joining("."));
    }

    default <E extends ConfigEntity> Optional<E> getParent(final Class<E> clazz) {
        return (Optional<E>) Trees.walkOptional((ConfigEntity) this, PARENT_TRAVERSER, Trees.WalkingOrder.FORWARD)
                .filter(e -> clazz.isAssignableFrom(e.getClass()))
                .findFirst();
    }

    /**
     * Returns a value if the ConfigEntity by definition is not existing. For
     * example, a Project does not have an overlying Dbms.
     *
     * @param <T>
     * @return
     */
    static <T extends ConfigEntity<?, ?, ?>> Optional<T> emptyConfigEntity() {
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
}
