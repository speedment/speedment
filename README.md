Speedment ORM - An accelerated ORM
==================================
![Harry the Hare](http://www.speedment.com/images/Speedhare_240x205.png)
About
-----
The Speedment ORM accelerates your JVM based database applications and makes programming so easy!

Here is a few examples of how you could use Speedment from your code:
### Easy initialization
```java
// A configuration-class is generated from the database.
new HelloSpeedment().start();
```

### Easy persistence
```java
// A Builder-pattern can be used to create an entity.
Hare harry = HareManager.get().builder()
    .setName("Harry")
    .setColor("Gray")
    .setAge(3);
        
HareManager.get().persist(harry);
```

### Easy querying
```java
// Large quantities of data is reduced in-memory using predicates.
List<Hare> oldHares = HareManager.get().stream()
    .filter(h -> h.getAge() > 8)
    .collect(toList());
```

### Optimised key-value cache
```java
// Key-value searches are optimised in the background!
Optional<Hare> harry = HareManager.get().stream()
    .filter(h -> "Harry".equals(h.getName()))
    .findAny();
```
    
### Entities are linked
```java
// Different tables form a traversable graph in memory.
Optional<Carrot> carrot = HareManager.get().stream()
    .filter(h -> "Harry".equals(h.getName()))
    .map(h -> h.findCarrot()) // Carrot is a foreign key.
    .findAny();
```
    
### Easy multi-threading
```java
// Find all hares that share name with a human using multiple 
// threads.
HareManager.get().stream()
    .parallel()
    .filter(h -> HumanManager.get().stream()
        .filter(n -> h.getName().equals(n.getName()))
        .anyMatch()
    ).forEach(System.out::println);
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
