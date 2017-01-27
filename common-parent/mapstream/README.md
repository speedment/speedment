MapStream
=========

An utility class that expands the Java 8 Stream API to work with native Key-Value pair collections like `Map`. 

## Example Usage
```java
Map<String, Integer> numberOfCats = new HashMap<>();

numberOfCats.put("Anne", 3);
numberOfCats.put("Berty", 1);
numberOfCats.put("Cecilia", 1);
numberOfCats.put("Denny", 0);
numberOfCats.put("Erica", 0);
numberOfCats.put("Fiona", 2);

System.out.println(
  MapStream.of(numberOfCats)
      .filterValue(v -> v > 0)
      .sortedByValue(Integer::compareTo)
      .mapKey(k -> k + " has ")
      .mapValue(v -> v + (v == 1 ? " cat." : " cats."))
      .map((k, v) -> k + v)
      .collect(Collectors.joining("\n"))
);
```
Output:
```
Cecilia has 1 cat.
Berty has 1 cat.
Fiona has 2 cats.
Anne has 3 cats.
```

If you want to use static imports, replace `Collectors.joining("\n")` with `joining("\n")`.

## Maven
To use MapStream in your own projects, add the following to your `pom.xml`-file.
```xml
<dependency>
    <groupId>com.speedment.common</groupId>
    <artifactId>mapstream</artifactId>
    <version>2.3.5</version>
</dependency>
```
