module basic.example {
    requires transitive com.speedment.runtime.application;
    requires transitive com.speedment.runtime.connector.mysql; // Transitive required for jlink
    requires com.speedment.runtime.join;
    requires custom.typemapper;
    requires mysql.connector.java;
}