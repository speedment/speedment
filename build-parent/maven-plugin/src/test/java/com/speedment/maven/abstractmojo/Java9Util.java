package com.speedment.maven.abstractmojo;

/**
 *
 * @author Per Minborg
 */
public final class Java9Util {

    public static final void main(String... args) {
        System.out.println(isJava9());
    }

    static boolean isJava9() {
        final String javaVmSpecificationVersion = System.getProperty("java.vm.specification.version");
        System.out.format("Java VM specification version is %s %n", javaVmSpecificationVersion);
        return javaVmSpecificationVersion.startsWith("9");
    }

}
