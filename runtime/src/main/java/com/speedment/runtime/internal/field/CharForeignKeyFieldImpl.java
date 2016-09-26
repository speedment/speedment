package com.speedment.runtime.internal.field;

import com.speedment.runtime.config.identifier.FieldIdentifier;
import com.speedment.runtime.config.mapper.TypeMapper;
import com.speedment.runtime.field.CharField;
import com.speedment.runtime.field.CharForeignKeyField;
import com.speedment.runtime.field.finder.FindFrom;
import com.speedment.runtime.field.method.CharGetter;
import com.speedment.runtime.field.method.CharSetter;
import com.speedment.runtime.field.method.Finder;
import com.speedment.runtime.field.predicate.FieldPredicate;
import com.speedment.runtime.field.predicate.Inclusion;
import com.speedment.runtime.internal.field.comparator.CharFieldComparator;
import com.speedment.runtime.internal.field.comparator.CharFieldComparatorImpl;
import com.speedment.runtime.internal.field.finder.FindFromChar;
import com.speedment.runtime.internal.field.predicate.chars.CharBetweenPredicate;
import com.speedment.runtime.internal.field.predicate.chars.CharEqualPredicate;
import com.speedment.runtime.internal.field.predicate.chars.CharGreaterOrEqualPredicate;
import com.speedment.runtime.internal.field.predicate.chars.CharGreaterThanPredicate;
import com.speedment.runtime.internal.field.predicate.chars.CharInPredicate;
import com.speedment.runtime.manager.Manager;
import java.util.Set;
import java.util.function.Predicate;
import static java.util.Objects.requireNonNull;

/**
 * @param <ENTITY>    entity type
 * @param <D>         database type
 * @param <FK_ENTITY> foreign entity type
 * 
 * @author Emil Forslund
 * @since  3.0.0
 */
public final class CharForeignKeyFieldImpl<ENTITY, D, FK_ENTITY> implements CharField<ENTITY, D>, CharForeignKeyField<ENTITY, D, FK_ENTITY> {
    
    private final FieldIdentifier<ENTITY> identifier;
    private final CharGetter<ENTITY> getter;
    private final CharSetter<ENTITY> setter;
    private final CharField<FK_ENTITY, ?> referenced;
    private final Finder<ENTITY, FK_ENTITY> finder;
    private final TypeMapper<D, Character> typeMapper;
    private final boolean unique;
    
    public CharForeignKeyFieldImpl(FieldIdentifier<ENTITY> identifier, CharGetter<ENTITY> getter, CharSetter<ENTITY> setter, CharField<FK_ENTITY, ?> referenced, Finder<ENTITY, FK_ENTITY> finder, TypeMapper<D, Character> typeMapper, boolean unique) {
        this.identifier = requireNonNull(identifier);
        this.getter     = requireNonNull(getter);
        this.setter     = requireNonNull(setter);
        this.referenced = requireNonNull(referenced);
        this.finder     = requireNonNull(finder);
        this.typeMapper = requireNonNull(typeMapper);
        this.unique     = unique;
    }
    
    @Override
    public FieldIdentifier<ENTITY> identifier() {
        return identifier;
    }
    
    @Override
    public CharSetter<ENTITY> setter() {
        return setter;
    }
    
    @Override
    public CharGetter<ENTITY> getter() {
        return getter;
    }
    
    @Override
    public FindFrom<ENTITY, FK_ENTITY, Character> findFrom(Manager<FK_ENTITY> foreignManager) {
        return new FindFromChar<>(this, referenced, foreignManager);
    }
    
    @Override
    public Finder<ENTITY, FK_ENTITY> finder() {
        return finder;
    }
    
    @Override
    public TypeMapper<D, Character> typeMapper() {
        return typeMapper;
    }
    
    @Override
    public boolean isUnique() {
        return unique;
    }
    
    @Override
    public CharFieldComparator<ENTITY, D> comparator() {
        return new CharFieldComparatorImpl<>(this);
    }
    
    @Override
    public CharFieldComparator<ENTITY, D> comparatorNullFieldsFirst() {
        return comparator();
    }
    
    @Override
    public CharFieldComparator<ENTITY, D> comparatorNullFieldsLast() {
        return comparator();
    }
    
    @Override
    public FieldPredicate<ENTITY, Character> equal(Character value) {
        return new CharEqualPredicate<>(this, value);
    }
    
    @Override
    public FieldPredicate<ENTITY, Character> greaterThan(Character value) {
        return new CharGreaterThanPredicate<>(this, value);
    }
    
    @Override
    public FieldPredicate<ENTITY, Character> greaterOrEqual(Character value) {
        return new CharGreaterOrEqualPredicate<>(this, value);
    }
    
    @Override
    public FieldPredicate<ENTITY, Character> between(Character start, Character end, Inclusion inclusion) {
        return new CharBetweenPredicate<>(this, start, end, inclusion);
    }
    
    @Override
    public FieldPredicate<ENTITY, Character> in(Set<Character> set) {
        return new CharInPredicate<>(this, set);
    }
    
    @Override
    public Predicate<ENTITY> notEqual(Character value) {
        return new CharEqualPredicate<>(this, value).negate();
    }
    
    @Override
    public Predicate<ENTITY> lessOrEqual(Character value) {
        return new CharGreaterThanPredicate<>(this, value).negate();
    }
    
    @Override
    public Predicate<ENTITY> lessThan(Character value) {
        return new CharGreaterOrEqualPredicate<>(this, value).negate();
    }
    
    @Override
    public Predicate<ENTITY> notBetween(Character start, Character end, Inclusion inclusion) {
        return new CharBetweenPredicate<>(this, start, end, inclusion).negate();
    }
    
    @Override
    public Predicate<ENTITY> notIn(Set<Character> set) {
        return new CharInPredicate<>(this, set).negate();
    }
}