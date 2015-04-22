Speedment ORM - An accelerated ORM
==================================
![Harry the Hare](http://www.speedment.com/images/Speedhare_240x205.png)
About
-----
The Speedment ORM accelerates your JVM based database applications and makes programming so easy!

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

if you want to use JPA-like persistence instead, you can do that too like this:

```java
// A JPA-like way of persisting an Entity
Hare harry = Hare.builder()
    .setName("Harry")
    .setColor("Gray")
    .setAge(3);

Optional<Hare> dbHarry = EntityManager.get().persist(harry);
```


### Easy querying
```java
// Large quantities of data can be reduced in-memory using predicates.
List<Hare> oldHares = Hare.stream()
    .filter(h -> h.getAge() > 8)
    .collect(toList());
```

### Optimised key-value cache
```java
// Key-value searches are optimised in the background!
Optional<Hare> harry = Hare.stream()
    .filter(h -> "Harry".equals(h.getName()))
    .findAny();
```
    
### Entities are linked
```java
// Different tables form a traversable graph in memory.
Optional<Carrot> carrot = Hare.stream()
    .filter(h -> "Harry".equals(h.getName()))
    .flatMap(h -> h.carrots()) // Carrot is a foreign key table.
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
 
### Development Status
Speedment is still very early and we are currently moving in large blocks from the existing closed source product. You can not run it now but you can contribute to the code and see how it evolves over time.

---
### Using Maven
When ready, you can just add the following to your project's `pom.xml` to use Speedment.
```xml
<dependency>
    <groupId>com.speedment</groupId>
    <artifactId>orm</artifactId>
    <version>0.0.1</version>
</dependency>
```

N.B. Speedment is not yet on Maven Central Repository so you can't use it in your pom yet unless you manually build and install it.
