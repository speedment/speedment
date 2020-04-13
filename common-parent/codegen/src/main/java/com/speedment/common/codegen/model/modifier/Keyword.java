/*
 *
 * Copyright (c) 2006-2020, Speedment, Inc. All Rights Reserved.
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
package com.speedment.common.codegen.model.modifier;

import com.speedment.common.codegen.model.trait.HasModifiers;

import static com.speedment.common.codegen.model.modifier.Modifier.*;

/**
 * Utility class that holds inner interfaces that are specializations of the
 * {@link HasModifiers}-trait.
 *
 * @author Emil Forslund
 * @since  2.0.0
 */
public final class Keyword {

    private Keyword() {}

    /**
     * Specialization of the {@link HasModifiers}-trait that adds methods for
     * adding and removing the particular keyword.
     *
     * @param <T>  the extending type
     */
    interface Public<T extends Public<T>> extends HasModifiers<T> {

        /**
         * Adds the {@code public} keyword to this model.
         *
         * @return  a reference to this model
         */
        @SuppressWarnings("unchecked")
        default T public_() {
            getModifiers().add(PUBLIC);
            return (T) this;
        }

        /**
         * Removes the {@code public} keyword from this model.
         *
         * @return  a reference to this model
         */
        @SuppressWarnings("unchecked")
        default T notPublic() {
            getModifiers().remove(PUBLIC);
            return (T) this;
        }
    }

    /**
     * Specialization of the {@link HasModifiers}-trait that adds methods for
     * adding and removing the particular keyword.
     *
     * @param <T>  the extending type
     */
    interface Protected<T extends Protected<T>> extends HasModifiers<T> {

        /**
         * Adds the {@code protected} keyword to this model.
         *
         * @return  a reference to this model
         */
        @SuppressWarnings("unchecked")
        default T protected_() {
            getModifiers().add(PROTECTED);
            return (T) this;
        }

        /**
         * Removes the {@code protected} keyword from this model.
         *
         * @return  a reference to this model
         */
        @SuppressWarnings("unchecked")
        default T notProtected() {
            getModifiers().remove(PROTECTED);
            return (T) this;
        }
    }

    /**
     * Specialization of the {@link HasModifiers}-trait that adds methods for
     * adding and removing the particular keyword.
     *
     * @param <T>  the extending type
     */
    interface Private<T extends Private<T>> extends HasModifiers<T> {

        /**
         * Adds the {@code private} keyword to this model.
         *
         * @return  a reference to this model
         */
        @SuppressWarnings("unchecked")
        default T private_() {
            getModifiers().add(PRIVATE);
            return (T) this;
        }

        /**
         * Removes the {@code private} keyword from this model.
         *
         * @return  a reference to this model
         */
        @SuppressWarnings("unchecked")
        default T notPrivate() {
            getModifiers().remove(PRIVATE);
            return (T) this;
        }
    }

    /**
     * Specialization of the {@link HasModifiers}-trait that adds methods for
     * adding and removing the particular keyword.
     *
     * @param <T>  the extending type
     */
    interface Static<T extends Static<T>> extends HasModifiers<T> {

        /**
         * Adds the {@code static} keyword to this model.
         *
         * @return  a reference to this model
         */
        @SuppressWarnings("unchecked")
        default T static_() {
            getModifiers().add(STATIC);
            return (T) this;
        }

        /**
         * Removes the {@code static} keyword from this model.
         *
         * @return  a reference to this model
         */
        @SuppressWarnings("unchecked")
        default T notStatic() {
            getModifiers().remove(STATIC);
            return (T) this;
        }
    }

    /**
     * Specialization of the {@link HasModifiers}-trait that adds methods for
     * adding and removing the particular keyword.
     *
     * @param <T>  the extending type
     */
    interface Final<T extends Final<T>> extends HasModifiers<T> {

        /**
         * Adds the {@code final} keyword to this model.
         *
         * @return  a reference to this model
         */
        @SuppressWarnings("unchecked")
        default T final_() {
            getModifiers().add(FINAL);
            return (T) this;
        }

        /**
         * Removes the {@code final} keyword from this model.
         *
         * @return  a reference to this model
         */
        @SuppressWarnings("unchecked")
        default T notFinal() {
            getModifiers().remove(FINAL);
            return (T) this;
        }
    }

    /**
     * Specialization of the {@link HasModifiers}-trait that adds methods for
     * adding and removing the particular keyword.
     *
     * @param <T>  the extending type
     */
    interface Abstract<T extends Abstract<T>> extends HasModifiers<T> {

        /**
         * Adds the {@code abstract} keyword to this model.
         *
         * @return  a reference to this model
         */
        @SuppressWarnings("unchecked")
        default T abstract_() {
            getModifiers().add(ABSTRACT);
            return (T) this;
        }

        /**
         * Removes the {@code abstract} keyword from this model.
         *
         * @return  a reference to this model
         */
        @SuppressWarnings("unchecked")
        default T notAbstract() {
            getModifiers().remove(ABSTRACT);
            return (T) this;
        }
    }

    /**
     * Specialization of the {@link HasModifiers}-trait that adds methods for
     * adding and removing the particular keyword.
     *
     * @param <T>  the extending type
     */
    interface Strictfp<T extends Strictfp<T>> extends HasModifiers<T> {

        /**
         * Adds the {@code strictfp} keyword to this model.
         *
         * @return  a reference to this model
         */
        @SuppressWarnings("unchecked")
        default T strictfp_() {
            getModifiers().add(STRICTFP);
            return (T) this;
        }

        /**
         * Removes the {@code strictfp} keyword from this model.
         *
         * @return  a reference to this model
         */
        @SuppressWarnings("unchecked")
        default T notStrictfp() {
            getModifiers().remove(STRICTFP);
            return (T) this;
        }
    }

    /**
     * Specialization of the {@link HasModifiers}-trait that adds methods for
     * adding and removing the particular keyword.
     *
     * @param <T>  the extending type
     */
    interface Synchronized<T extends Synchronized<T>> extends HasModifiers<T> {

        /**
         * Adds the {@code synchronized} keyword to this model.
         *
         * @return  a reference to this model
         */
        @SuppressWarnings("unchecked")
        default T synchronized_() {
            getModifiers().add(SYNCHRONIZED);
            return (T) this;
        }

        /**
         * Removes the {@code synchronized} keyword from this model.
         *
         * @return  a reference to this model
         */
        @SuppressWarnings("unchecked")
        default T notSynchronized() {
            getModifiers().remove(SYNCHRONIZED);
            return (T) this;
        }
    }

    /**
     * Specialization of the {@link HasModifiers}-trait that adds methods for
     * adding and removing the particular keyword.
     *
     * @param <T>  the extending type
     */
    interface Transient<T extends Transient<T>> extends HasModifiers<T> {

        /**
         * Adds the {@code transient} keyword to this model.
         *
         * @return  a reference to this model
         */
        @SuppressWarnings("unchecked")
        default T transient_() {
            getModifiers().add(TRANSIENT);
            return (T) this;
        }

        /**
         * Removes the {@code volatile} keyword from this model.
         *
         * @return  a reference to this model
         */
        @SuppressWarnings("unchecked")
        default T notTransient() {
            getModifiers().remove(TRANSIENT);
            return (T) this;
        }
    }

    /**
     * Specialization of the {@link HasModifiers}-trait that adds methods for
     * adding and removing the particular keyword.
     *
     * @param <T>  the extending type
     */
    interface Volatile<T extends Volatile<T>> extends HasModifiers<T> {

        /**
         * Adds the {@code volatile} keyword to this model.
         *
         * @return  a reference to this model
         */
        @SuppressWarnings("unchecked")
        default T volatile_() {
            getModifiers().add(VOLATILE);
            return (T) this;
        }

        /**
         * Removes the {@code volatile} keyword from this model.
         *
         * @return  a reference to this model
         */
        @SuppressWarnings("unchecked")
        default T notVolatile() {
            getModifiers().remove(VOLATILE);
            return (T) this;
        }
    }

    /**
     * Specialization of the {@link HasModifiers}-trait that adds methods for
     * adding and removing the particular keyword.
     *
     * @param <T>  the extending type
     */
    interface Native<T extends Native<T>> extends HasModifiers<T> {

        /**
         * Adds the {@code native} keyword to this model.
         *
         * @return  a reference to this model
         */
        @SuppressWarnings("unchecked")
        default T native_() {
            getModifiers().add(NATIVE);
            return (T) this;
        }

        /**
         * Removes the {@code native} keyword from this model.
         *
         * @return  a reference to this model
         */
        @SuppressWarnings("unchecked")
        default T notNative() {
            getModifiers().remove(NATIVE);
            return (T) this;
        }
    }

    /**
     * Specialization of the {@link HasModifiers}-trait that adds methods for
     * adding and removing the particular keyword.
     *
     * @param <T>  the extending type
     */
    interface Default<T extends Default<T>> extends HasModifiers<T> {
        /**
         * Adds the {@code default} keyword to this model.
         *
         * @return  a reference to this model
         */
        @SuppressWarnings("unchecked")
        default T default_() {
            getModifiers().add(DEFAULT);
            return (T) this;
        }

        /**
         * Removes the {@code default} keyword from this model.
         *
         * @return  a reference to this model
         */
        @SuppressWarnings("unchecked")
        default T notDefault() {
            getModifiers().remove(DEFAULT);
            return (T) this;
        }
    }

}