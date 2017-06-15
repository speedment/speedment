module com.speedment.runtime.field {
    exports com.speedment.runtime.field.internal.predicate;
    exports com.speedment.runtime.field.comparator;
    exports com.speedment.runtime.field.util;
    exports com.speedment.runtime.field.trait;
    exports com.speedment.runtime.field.method;
    exports com.speedment.runtime.field.predicate;
    exports com.speedment.runtime.field;
    requires com.speedment.common.invariant;
    requires com.speedment.common.function;
    requires com.speedment.common.annotation;
    requires com.speedment.runtime.typemapper;
    requires com.speedment.runtime.config;
    requires com.speedment.common.tuple;
}
