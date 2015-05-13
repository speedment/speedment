Speedment - An accelerated database framework
==================================
![Spire the Hare](http://www.speedment.com/images/Speedhare_240x205.png)
About
-----
The Speedment accelerates your JVM based database applications and makes programming so easy!

Here are a few examples of how you could use Speedment from your code:
### Easy initialization
```java
// A configuration-class is generated from the database.
new HelloSpeedment().start();
```

### Easy persistence
```java
// A Builder-pattern can be used to create an entity in a database
// using the "Active Record Pattern"
Optional<Hare> dbHarry = Hare.builder()
    .setName("Harry")
    .setColor("Gray")
    .setAge(3)
    .persist();
```

The `.persist()` method above is just a convenience deligator short-cut for the following:

```java
// The EntityManager is still responsible for persistence
Hare harry = Hare.builder()
    .setName("Harry")
    .setColor("Gray")
    .setAge(3);

Optional<Hare> dbHarry = EntityManager.get().persist(harry);
```


### Easy querying using standard Java 8 predicates
```java
// Large quantities of data can be reduced in-memory using predicates.
List<Hare> oldHares = Hare.stream()
    .filter(h -> h.getAge() > 8)
    .collect(toList());
```

### Optimised predicate short-circuit
```java
// Searches are optimised in the background!
Optional<Hare> harry = Hare.stream()
    .filter(NAME.equal("Harry").and(AGE.lessThan(5)))
    .findAny();
```
    
### Entities are linked
```java
// Different tables form a traversable graph in memory.
Optional<Carrot> carrot = Hare.stream()
    .filter(NAME.equal("Harry"))
    .flatMap(Hare::carrots) // Carrot is a foreign key table.
    .findAny();
```
    
### Easy multi-threading
```java
// Find all hares that share name with a human using multiple 
// threads.
Hare.stream()
    .parallel()
    .filter(h -> Human.stream()
        .filter(n -> h.getName().equals(n.getName()))
        .findAny().isPresent()
    ).forEach(System.out::println);
```
   
### Convert to JSON
```java
// List all hares in JSON format
Hare.stream()
    .map(Hare::toJson)
    .forEach(System.out::println);
```
 
### Full access to transaction metadata
```java
// If an SQL storage engine is used, you may
// want to obtain the actual transaction metadata.
Optional<Hare> harry = Hare.builder()
    .setName("Harry")
    .setColor("Gray")
    .setAge(3)
    .persist(meta -> {
        meta.getSqlMetaResult().ifPresent(sql -> {
            System.out.println("sql = " + sql.getQuery());
            System.out.println("params = " + sql.getParameters());
            System.out.println("thowable = " + sql.getThrowable()
                .map(t -> t.getMessage())
                .orElse("nothing thrown :-) ")
            );
        });
    });
```
 

### Development Status
Speedment is still very early and we are currently moving in large blocks from the existing closed source product. You can not run it now but you can contribute to the code and see how it evolves over time.

---
### Using Maven
When ready, you can just add the following to your project's `pom.xml` to use Speedment.
```xml
<dependency>
    <groupId>com.speedment</groupId>
    <artifactId>speedment</artifactId>
    <version>${speedment.version}</version>
</dependency>
```
You must also add a JDBC dependency for the database you are using.

N.B. Speedment is not yet on Maven Central Repository so you can't use it in your pom yet unless you manually build and install it.
