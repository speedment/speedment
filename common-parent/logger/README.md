Logger
======

A lightweight logging framework inspired by Tengil.

## Example Usage
This is a basic usage example:
```java
public class MyClass {
    private final static Logger LOGGER = LoggerManager.getLogger(MyClass.class);

    public void myMethod() {
        LOGGER.info("This is just some info.");
        LOGGER.warn("This is a serious warning.");
        LOGGER.debug("This can be important for debugging.");
        LOGGER.error("Something bad has happened.");
    }
}
```

To log error messages caused by exceptions:
```java
try {
    someDangerousMethod();
} catch (final Exception ex) {
    LOGGER.error(ex, "Something really bad happened.");
}
```

To show debug messages in the log:
```java
LoggerManager.getLogger(MyClass.class).setLevel(Level.DEBUG);
```

## Install
To use Logger in your own projects, add the following to your `pom.xml`-file:
```xml
<dependency>
    <groupId>com.speedment.common</groupId>
    <artifactId>logger</artifactId>
    <version>1.0.3</version>
</dependency>
```
