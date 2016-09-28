package com.speedment.runtime.internal.field;

import com.speedment.runtime.config.identifier.FieldIdentifier;
import com.speedment.runtime.config.mapper.TypeMapper;
import com.speedment.runtime.field.ByteField;
import com.speedment.runtime.field.ByteForeignKeyField;
import com.speedment.runtime.field.method.BackwardFinder;
import com.speedment.runtime.field.method.ByteGetter;
import com.speedment.runtime.field.method.ByteSetter;
import com.speedment.runtime.field.method.FindFrom;
import com.speedment.runtime.field.method.Finder;
import com.speedment.runtime.field.predicate.FieldPredicate;
import com.speedment.runtime.field.predicate.Inclusion;
import com.speedment.runtime.internal.field.comparator.ByteFieldComparator;
import com.speedment.runtime.internal.field.comparator.ByteFieldComparatorImpl;
import com.speedment.runtime.internal.field.finder.FindFromByte;
import com.speedment.runtime.internal.field.predicate.bytes.ByteBetweenPredicate;
import com.speedment.runtime.internal.field.predicate.bytes.ByteEqualPredicate;
import com.speedment.runtime.internal.field.predicate.bytes.ByteGreaterOrEqualPredicate;
import com.speedment.runtime.internal.field.predicate.bytes.ByteGreaterThanPredicate;
import com.speedment.runtime.internal.field.predicate.bytes.ByteInPredicate;
import com.speedment.runtime.internal.field.streamer.BackwardFinderImpl;
import com.speedment.runtime.manager.Manager;
import java.util.Set;
import java.util.function.Predicate;
import javax.annotation.Generated;
import static java.util.Objects.requireNonNull;

/**
 * @param <ENTITY>    entity type
 * @param <D>         database type
 * @param <FK_ENTITY> foreign entity type
 * 
 * @author Emil Forslund
 * @since  3.0.0
 */
@Generated(value = "Speedment")
public final class ByteForeignKeyFieldImpl<ENTITY, D, FK_ENTITY> implements ByteField<ENTITY, D>, ByteForeignKeyField<ENTITY, D, FK_ENTITY> {
    
    private final FieldIdentifier<ENTITY> identifier;
    private final ByteGetter<ENTITY> getter;
    private final ByteSetter<ENTITY> setter;
    private final ByteField<FK_ENTITY, ?> referenced;
    private final Finder<ENTITY, FK_ENTITY> finder;
    private final TypeMapper<D, Byte> typeMapper;
    private final boolean unique;
    
    public ByteForeignKeyFieldImpl(FieldIdentifier<ENTITY> identifier, ByteGetter<ENTITY> getter, ByteSetter<ENTITY> setter, ByteField<FK_ENTITY, ?> referenced, Finder<ENTITY, FK_ENTITY> finder, TypeMapper<D, Byte> typeMapper, boolean unique) {
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
    public ByteSetter<ENTITY> setter() {
        return setter;
    }
    
    @Override
    public ByteGetter<ENTITY> getter() {
        return getter;
    }
    
    @Override
    public ByteField<FK_ENTITY, ?> getReferencedField() {
        return referenced;
    }
    
    @Override
    public BackwardFinder<FK_ENTITY, ENTITY> backwardFinder(Manager<ENTITY> manager) {
        return new BackwardFinderImpl<>(this, manager);
    }
    
    @Override
    public FindFrom<ENTITY, FK_ENTITY> finder(Manager<FK_ENTITY> foreignManager) {
        return new FindFromByte<>(this, referenced, foreignManager);
    }
    
    @Override
    public TypeMapper<D, Byte> typeMapper() {
        return typeMapper;
    }
    
    @Override
    public boolean isUnique() {
        return unique;
    }
    
    @Override
    public ByteFieldComparator<ENTITY, D> comparator() {
        return new ByteFieldComparatorImpl<>(this);
    }
    
    @Override
    public ByteFieldComparator<ENTITY, D> comparatorNullFieldsFirst() {
        return comparator();
    }
    
    @Override
    public ByteFieldComparator<ENTITY, D> comparatorNullFieldsLast() {
        return comparator();
    }
    
    @Override
    public FieldPredicate<ENTITY> equal(Byte value) {
        return new ByteEqualPredicate<>(this, value);
    }
    
    @Override
    public FieldPredicate<ENTITY> greaterThan(Byte value) {
        return new ByteGreaterThanPredicate<>(this, value);
    }
    
    @Override
    public FieldPredicate<ENTITY> greaterOrEqual(Byte value) {
        return new ByteGreaterOrEqualPredicate<>(this, value);
    }
    
    @Override
    public FieldPredicate<ENTITY> between(Byte start, Byte end, Inclusion inclusion) {
        return new ByteBetweenPredicate<>(this, start, end, inclusion);
    }
    
    @Override
    public FieldPredicate<ENTITY> in(Set<Byte> set) {
        return new ByteInPredicate<>(this, set);
    }
    
    @Override
    public Predicate<ENTITY> notEqual(Byte value) {
        return new ByteEqualPredicate<>(this, value).negate();
    }
    
    @Override
    public Predicate<ENTITY> lessOrEqual(Byte value) {
        return new ByteGreaterThanPredicate<>(this, value).negate();
    }
    
    @Override
    public Predicate<ENTITY> lessThan(Byte value) {
        return new ByteGreaterOrEqualPredicate<>(this, value).negate();
    }
    
    @Override
    public Predicate<ENTITY> notBetween(Byte start, Byte end, Inclusion inclusion) {
        return new ByteBetweenPredicate<>(this, start, end, inclusion).negate();
    }
    
    @Override
    public Predicate<ENTITY> notIn(Set<Byte> set) {
        return new ByteInPredicate<>(this, set).negate();
    }
}