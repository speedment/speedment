MapBuilder
=========

Easy way to construct immutable java maps using a Builder Pattern.

## Example Usage
```java
Map<String, Integer> map = MapBuilder.mapBuilder()
    .key("foo").value(1)
    .key("bar").value(2)
    .key("baz").value(3)
    .build();
```

It is also possible to use the shorter API if keys and values are always known at the same time:

Map<String, Integer> map = 
    MapBuilder.mapBuilder("foo", 1)
        .entry("bar", 2)
        .entry("baz", 3)
        .build();
```

If you want to use static imports, replace `MapBuilder.mapBuilder()` with `mapBuilder()`.

## Maven
To use `MapBuilder` in your own projects, add the following to your `pom.xml`-file.
```xml
<dependency>
    <groupId>com.speedment.common</groupId>
    <artifactId>mapbuilder</artifactId>
    <version>1.0.0</version>
</dependency>
```
