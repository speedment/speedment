module com.speedment.runtime.typemapper {
    exports com.speedment.runtime.typemapper.other;
    exports com.speedment.runtime.typemapper.longs;
    exports com.speedment.runtime.typemapper.largeobject;
    exports com.speedment.runtime.typemapper.doubles;
    exports com.speedment.runtime.typemapper.bigdecimal;
    exports com.speedment.runtime.typemapper.string;
    exports com.speedment.runtime.typemapper.integer;
    exports com.speedment.runtime.typemapper.internal;
    exports com.speedment.runtime.typemapper;
    requires java.sql.rowset;
    requires java.sql;
    requires com.speedment.runtime.config;
}
