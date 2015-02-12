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
public abstract interface ConfigEntity<T extends ConfigEntity<T, P, C>, P extends ConfigEntity<?, ?, ?>, C extends ConfigEntity<?, ?, ?>> extends
        Comparable<T> {

    static final int INDEX_FIRST = 0;
    static final int ORDINAL_FIRST = 1;
    static final int ORDINAL_UNSET = 0;

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

    Optional<Class<? extends P>> getParentInterfaceMainClass();
    
    default boolean isRoot() {
        return getParent().isPresent();
    }

    // Children
    T add(final C child);

    T remove(final C child);

    boolean contains(final C child);

    Stream<? extends C> stream();

    default boolean hasChildren() {
        return stream().findAny().isPresent();
    }

    // Configuration
    <E> T add(ConfigParameter<? extends E> configParameter);

    <E> T remove(ConfigParameter<? extends E> configParameter);

    <E> boolean contains(ConfigParameter<? extends E> configParameter);

    Stream<ConfigParameter<?>> configStream();

    default boolean hasConfig() {
        return configStream().findAny().isPresent();
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    static final Function<ConfigEntity, Optional<ConfigEntity>> PARENT_TRAVERSER = (ConfigEntity c) -> c.getParent();

//    static <T extends ConfigEntity<T, P, ?>, P extends ConfigEntity<P,?,T>> Function<T, Optional<P>> parentTraverser() {
//        return ConfigEntity::getParent;
//    }
//
//    static <T extends ConfigEntity<T, ?, ?>> Function<T, String> nameMapper() {
//        return ConfigEntity::getName;
//    }
    default String getRelativeName(ConfigEntity<?, ?, ?> from) {
        return Trees.walkOptional(this, PARENT_TRAVERSER, Trees.WalkingOrder.BACKWARD).map(ConfigEntity::getName).collect(Collectors.joining("."));
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
    String toGroovy(int indentLevel);

    default String toGrovy() {
        return toGroovy(0);
    }

}
