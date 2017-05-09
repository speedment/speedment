package com.speedment.common.codegen.internal.model.value;

import com.speedment.common.codegen.internal.util.Copier;
import com.speedment.common.codegen.model.ClassOrInterface;
import com.speedment.common.codegen.model.Field;
import com.speedment.common.codegen.model.Initializer;
import com.speedment.common.codegen.model.Method;
import com.speedment.common.codegen.model.Value;
import com.speedment.common.codegen.model.trait.HasCopy;
import com.speedment.common.codegen.model.value.AnonymousValue;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Default implementation of the {@link AnonymousValue} interface.
 *
 * @author Emil Forslund
 * @since  2.4.6
 */
public final class AnonymousValueImpl
implements AnonymousValue {

    private final List<Value<?>> args;
    private final List<Type> typeParams;
    private final List<Field> fields;
    private final List<Method> methods;
    private final List<Initializer> initializers;
    private final List<ClassOrInterface<?>> innerClasses;
    private Type value;

    public AnonymousValueImpl() {
        this.args         = new ArrayList<>();
        this.typeParams   = new ArrayList<>();
        this.fields       = new ArrayList<>();
        this.methods      = new ArrayList<>();
        this.initializers = new ArrayList<>();
        this.innerClasses = new ArrayList<>();
    }

    private AnonymousValueImpl(AnonymousValue prototype) {
        this.args             = Copier.copy(prototype.getValues(), HasCopy::copy);
        this.typeParams       = new ArrayList<>(prototype.getTypeParameters());
        this.fields           = Copier.copy(prototype.getFields());
        this.methods          = Copier.copy(prototype.getMethods());
        this.initializers     = Copier.copy(prototype.getInitializers());
        this.innerClasses     = Copier.copy(prototype.getClasses(), HasCopy::copy);
        this.value            = prototype.getValue();
    }

    @Override
    public Type getValue() {
        return value;
    }

    @Override
    public List<Value<?>> getValues() {
        return args;
    }

    @Override
    public List<Type> getTypeParameters() {
        return typeParams;
    }

    @Override
    public List<Field> getFields() {
        return fields;
    }

    @Override
    public List<Method> getMethods() {
        return methods;
    }

    @Override
    public List<Initializer> getInitializers() {
        return initializers;
    }

    @Override
    public List<ClassOrInterface<?>> getClasses() {
        return innerClasses;
    }

    @Override
    public AnonymousValue setValue(Type value) {
        this.value = value;
        return this;
    }

    @Override
    public AnonymousValue copy() {
        return new AnonymousValueImpl(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AnonymousValue)) return false;

        AnonymousValue that = (AnonymousValue) o;

        if (args != null ? !args.equals(that.getValue()) : that.getValue() != null)
            return false;
        if (!typeParams.equals(that.getTypeParameters())) return false;
        if (!getFields().equals(that.getFields())) return false;
        if (!getMethods().equals(that.getMethods())) return false;
        if (!getInitializers().equals(that.getInitializers())) return false;
        return innerClasses.equals(that.getClasses());
    }

    @Override
    public int hashCode() {
        int result = args != null ? args.hashCode() : 0;
        result = 31 * result + typeParams.hashCode();
        result = 31 * result + getFields().hashCode();
        result = 31 * result + getMethods().hashCode();
        result = 31 * result + getInitializers().hashCode();
        result = 31 * result + innerClasses.hashCode();
        return result;
    }
}