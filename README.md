Wrap your database into Java 8!
==========================================
![Spire the Hare](http://www.speedment.com/images/Speedhare_240x205.png)
About
-----
Speedment accelerates your development speed and makes programming so easy and fun!

When you use Speedment for database querying, you do not have to learn a new APIs or use complex ORMs. Everything is standard Java 8 and works 
out of the box!

Documentation
-------------
You can read about [the API here](https://github.com/speedment/speedment-orm/wiki/Speedment-API)!

Tutorials
---------
* [Tutorial 1 - Get started with the GUI](https://github.com/speedment/speedment/wiki/Tutorial:-Get-started-with-the-GUI)
* [Tutorial 2 - Build a Social Network](https://github.com/speedment/speedment/wiki/Tutorial:-Build-a-Social-Network)

Examples
--------
Here are a few examples of how you could use Speedment from your code:

### Easy initialization
```java
// A configuration-class is generated from the database.
new HelloSpeedment().start();
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

    
### Entities are linked
```java
// Different tables form a traversable graph in memory. No need for joins!
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

### Database centric
Speedment is using the database as the source-of-truth, both when it comes to the domain model and the actual data itself. Perfect if you are tired of
configuring and debuging complex ORMs. After all, your data is more important than programming tools, is it not?

### Code generation
Speedment inspects your database and can automatically generate code that reflects the latest state of your database. Perfect if you
have changed the data structure (like columns or tables) in your database. Optionally, you can change the way code is generated
using an intuitive GUI or programatically using your own code.

### Development Status
Speedment is still very early and we are currently moving in large blocks from the existing closed source product.

---
### Using Maven
To use Speedment, just add the following lines to your project's `pom.xml` file.
```xml
<dependency>
    <groupId>com.speedment</groupId>
    <artifactId>speedment</artifactId>
    <version>${speedment.version}</version>
</dependency>
```
Remember that you must also add a JDBC dependency that matches the database(s) you are using.


### License

Speedment is available under the Apache 2 License.


### Copyright

Copyright (c) 2008-2015, Speedment, Inc. All Rights Reserved.

Visit [www.speedment.com](http://www.speedment.com/) for more info.
