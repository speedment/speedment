module com.speedment.runtime.config {
    exports com.speedment.runtime.config.exception;
    exports com.speedment.runtime.config.identifier.trait;
    exports com.speedment.runtime.config.identifier;
    exports com.speedment.runtime.config.internal;
    exports com.speedment.runtime.config.mutator.trait;
    exports com.speedment.runtime.config.mutator;
    exports com.speedment.runtime.config.parameter;
    exports com.speedment.runtime.config.trait;
    exports com.speedment.runtime.config.util;
    exports com.speedment.runtime.config;
    requires com.speedment.common.function;
    requires com.speedment.common.invariant;
    requires com.speedment.common.lazy;
    requires com.speedment.common.mapstream;
}
