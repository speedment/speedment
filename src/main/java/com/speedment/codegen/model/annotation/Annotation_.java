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
package com.speedment.codegen.model.annotation;

import com.speedment.codegen.model.AbstractCodeModel;
import com.speedment.codegen.model.CodeModel;
import com.speedment.codegen.model.CodeModel.Type;
import java.lang.annotation.Annotation;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author pemi
 */
public class Annotation_ extends AbstractCodeModel<Annotation_> implements CodeModel {

    public static final Annotation_ OVERRIDE = new UnmodifiableAnnotation_(Override.class);
    public static final Annotation_ DEPRECATED = new UnmodifiableAnnotation_(Deprecated.class);

    private Class<? extends Annotation> annotationClass;
    //private CharSequence annotaionClassName;
    private final Map<String, Object> valuePairs;

    public Annotation_() {
        this.valuePairs = new LinkedHashMap<>();
    }

    public Annotation_(Class<? extends Annotation> annotationClass) {
        this();
        this.annotationClass = annotationClass;
    }

//    public Annotation_(String annotaionClassName) {
//        this();
//        this.annotaionClassName = annotaionClassName;
//    }
    public Annotation_ put(final String key, final Object value) {
        return add(key, value, (k, v) -> {
            getValuePairs().put(k, v);
        });
    }

    public Class<? extends Annotation> getAnnotationClass() {
        return annotationClass;
    }

//    public String getAnnotaionClassName() {
//        return annotaionClassName;
//    }
    public Map<String, Object> getValuePairs() {
        return valuePairs;
    }

    @Override
    public Type getModelType() {
        return Type.ANNOTATION;
    }

    public static final class UnmodifiableAnnotation_ extends Annotation_ {

        public UnmodifiableAnnotation_(Class<? extends Annotation> annotationClass) {
            super(annotationClass);
        }

        @Override
        public Annotation_ put(String key, Object value) {
            throw new UnsupportedOperationException("Annotation is unmodifiable");
        }

    }

}
