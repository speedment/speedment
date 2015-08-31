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
package com.speedment.internal.codegen.lang.controller;

import com.speedment.internal.codegen.lang.interfaces.HasFields;
import com.speedment.internal.codegen.lang.interfaces.HasMethods;
import com.speedment.internal.codegen.lang.models.Field;
import com.speedment.internal.codegen.lang.models.Method;
import static com.speedment.internal.codegen.lang.models.constants.DefaultType.*;
import java.util.function.Consumer;
import static com.speedment.internal.codegen.util.Formatting.*;
import com.speedment.internal.codegen.lang.interfaces.HasImports;
import com.speedment.internal.codegen.lang.interfaces.HasName;
import com.speedment.internal.codegen.lang.interfaces.HasSupertype;
import com.speedment.internal.codegen.lang.models.Import;
import com.speedment.internal.codegen.lang.models.Javadoc;
import com.speedment.internal.codegen.lang.models.Type;
import static com.speedment.internal.codegen.lang.models.constants.DefaultAnnotationUsage.OVERRIDE;
import static com.speedment.internal.codegen.lang.models.constants.DefaultJavadocTag.PARAM;
import static com.speedment.internal.codegen.lang.models.constants.DefaultJavadocTag.RETURN;
import java.util.Objects;
import static java.util.Objects.requireNonNull;
import java.util.Optional;
import java.util.stream.Collectors;
import static java.util.Objects.requireNonNull;

/**
 * This control can be applied on a class, enum or similar to auto generate
 * an <code>equals</code> and a <code>hashCode</code> method. The control uses
 * all the fields to determine the salt.
 * <p>
 * The control must be instantiated with the <code>File</code> as a parameter.
 * The reason for this is that the generated methods might require new 
 * dependencies to be imported.
 * <p>
 * To use <code>AutoEquals</code>, follow this example:
 * <pre>
 *     file.add(
 *         Class.of("Vector2f")
 *             .add(Field.of("x", FLOAT_PRIMITIVE)
 *                 .public_()
 *                 .set(new NumberValue(0)))
 * 
 *             .add(Field.of("y", FLOAT_PRIMITIVE)
 *                 .public_()
 *                 .set(new NumberValue(0)))
 * 
 *             .call(new AutoEquals&lt;&gt;(file))
 *     );
 * </pre>
 * <p>
 * If one of the methods already exists, it will not be overwritten.
 * 
 * @author Emil Forslund
 * @param <T> The extending type
 */
public final class AutoEquals<T extends HasFields<T> & HasMethods<T> & HasName<T>> 
implements Consumer<T> {
    
    protected final HasImports<?> importer;
    protected final static String EQUALS = "equals",
        HASHCODE = "hashCode";
    
    /**
     * Instantiates the <code>AutoEquals</code> using something that imports
     * can be added to. This can for an example be a 
     * {@link com.speedment.internal.codegen.lang.models.File}.
     * 
     * @param importer  the importer
     */
    public AutoEquals(HasImports<?> importer) {
        this.importer = requireNonNull(importer);
    }
    
    /**
     * Adds an <code>equals()</code> and a <code>hashCode()</code> method to 
     * the specified model.
     * <p>
     * If one of the methods already exists, it will not be overwritten.
     * 
     * @param model  the model 
     */
    @Override
    public void accept(T model) {
        requireNonNull(model);
        
        if (!hasMethod(model, EQUALS, 1)) {
            acceptEquals(model);
        }
        
        if (!hasMethod(model, HASHCODE, 0)) {
            acceptHashcode(model);
        }
    }
    
    /**
     * The <code>equals()</code>-part of the <code>accept</code> method.
     * 
     * @param model  the model
     */
    protected void acceptEquals(T model) {
        requireNonNull(model);
            
        if (importer != null) {
            importer.add(Import.of(Type.of(Objects.class)));
            importer.add(Import.of(Type.of(Optional.class)));
        }
        
        model.add(Method.of(EQUALS, BOOLEAN_PRIMITIVE)
            .set(
                Javadoc.of(
                    "Compares this object with the specified one for equality. " +
                    "The other object must be of the same type and not null for " +
                    "the method to return true."
                )
                .add(PARAM.setValue("other").setText("The object to compare with."))
                .add(RETURN.setText("True if the objects are equal."))
            ).public_()
            .add(OVERRIDE)
            .add(Field.of("other", OBJECT))
            .add("return Optional.ofNullable(other)")
            .call(m -> {
                if (HasSupertype.class.isAssignableFrom(model.getClass())) {
                    final Optional<Type> supertype = ((HasSupertype<?>) model).getSupertype();
                    if (supertype.isPresent()) {
                        m.add(tab() + ".filter(o -> super.equals(o))");
                    }
                }
            })
            .add(tab() + ".filter(o -> getClass().equals(o.getClass()))")
            .add(tab() + ".map(o -> (" + model.getName() + ") o)")
            .add(tab() + model.getFields().stream().map(f -> compare(f)).collect(
                    Collectors.joining(nl() + tab())
                ))
            .add(tab() + ".isPresent();")
        );
        
    }
    
    /**
     * The <code>hashCode()</code>-part of the <code>accept</code> method.
     * 
     * @param model  the model
     */
    protected void acceptHashcode(T model) {
        requireNonNull(model);
            
        model.add(Method.of(HASHCODE, INT_PRIMITIVE)
            .set(
                Javadoc.of(
                    "Generates a hashCode for this object. If any field is " +
                    "changed to another value, the hashCode may be different. " +
                    "Two objects with the same values are guaranteed to have " +
                    "the same hashCode. Two objects with the same hashCode are " +
                    "not guaranteed to have the same hashCode."
                )
                .add(RETURN.setText("The hash code."))
            ).public_()
            .add(OVERRIDE)
            .add("int hash = 7;")
            .add(model.getFields().stream()
                .map(f -> hash(f))
                .collect(Collectors.joining(nl()))
            )
            .add("return hash;")
        );
    }
    
    /**
     * Generates code for comparing the specified field in this and another
     * object.
     * 
     * @param f  the field
     * @return   the comparing code
     */
    protected String compare(Field f) {
        requireNonNull(f);
        
        final StringBuilder str = new StringBuilder(".filter(o -> ");
        if (isPrimitive(f.getType())) {
            str.append("(this.")
                .append(f.getName())
                .append(" == o.")
                .append(f.getName())
                .append(")");
        } else {
            str.append("Objects.equals(this.")
                .append(f.getName())
                .append(", o.")
                .append(f.getName())
                .append(")");
        }
        
        return str.append(")").toString();
    }
    
    /**
     * Generates code for hashing the specified field.
     * 
     * @param f  the field
     * @return   the hashing code
     */
    protected String hash(Field f) {
        requireNonNull(f);
        
        final String prefix = "hash = 31 * hash + (";
        final String suffix = ".hashCode(this." + f.getName() + "));";
        
        switch (f.getType().getName()) {
            case "byte":
                return prefix + "Byte" + suffix;
            case "short":
                return prefix + "Short" + suffix;
            case "int":
                return prefix + "Integer" + suffix;
            case "long":
                return prefix + "Long" + suffix;
            case "float":
                return prefix + "Float" + suffix;
            case "double":
                return prefix + "Double" + suffix;
            case "boolean":
                return prefix + "Boolean" + suffix;
            case "char":
                return prefix + "Character" + suffix;
            default:
                return prefix + "(this." + f.getName() + " == null) ? 0 : this." + f.getName() + ".hashCode();";
        }
    }
    
    /**
     * Returns <code>true</code> if the specified type is a primitive type.
     * 
     * @param type  the type
     * @return      <code>true</code> if primitive, else <code>false</code>
     */
    protected boolean isPrimitive(Type type) {
        requireNonNull(type);
        
        switch (type.getName()) {
            case "byte":
            case "short":
            case "int":
            case "long":
            case "float":
            case "double":
            case "boolean":
            case "char":
                return true;
            default:
                return false;
        }
    }
    
    /**
     * Returns the a method with the specified signature exists.
     * 
     * @param model   the model
     * @param method  the method name to look for
     * @param params  the number of parameters in the signature
     * @return        <code>true</code> if found, else <code>false</code>
     */
    protected boolean hasMethod(T model, String method, int params) {
        requireNonNull(model);
        requireNonNull(method);
        requireNonNull(params);
        
        Method found = null;
        
        for (Method m : model.getMethods()) {
            if (method.equals(m.getName())
                && m.getFields().size() == params) {
                found = m;
                break;
            }
        }
        
        return found != null;
    }
}