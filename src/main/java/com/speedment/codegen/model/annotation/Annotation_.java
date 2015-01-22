package com.speedment.codegen.model.annotation;

import java.lang.annotation.Annotation;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author pemi
 */
public class Annotation_ {

    public static final Annotation_ OVERRIDE = new UnmodifiableAnnotation_(Override.class);
    public static final Annotation_ DEPRECATED = new UnmodifiableAnnotation_(Deprecated.class);

    private Class<? extends Annotation> annotationClass;
//    private String annotaionClassName;
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
        getValuePairs().put(key, value);
        return this;
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
