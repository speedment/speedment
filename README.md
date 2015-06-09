Wrap your database into Java 8!
==========================================

[![Join the chat at https://gitter.im/speedment/speedment](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/speedment/speedment?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

<p>
<img src="https://raw.githubusercontent.com/speedment/speedment-resources/master/src/main/resources/logo/Speedhare_240x205.png" alt="Spire the Hare" title="Spire" align="right" /><span>Speedment accelerates your development speed and makes programming so easy and fun!</span>

When you use Speedment for database querying, you do not have to learn a new APIs or use complex ORMs. Everything is <strong>standard Java 8</strong> and works 
out of the box!
</p>

Documentation
-------------
You can read a quick start about [the API here](https://github.com/speedment/speedment/wiki/Speedment-API-Quick-Start)!

Tutorials
---------
* [Tutorial 1 - Get started with the GUI](https://github.com/speedment/speedment/wiki/Tutorial:-Get-started-with-the-GUI)
* [Tutorial 2 - Hello Speedment](https://github.com/speedment/speedment/wiki/Tutorial:-Hello-Speedment)
* [Tutorial 3 - Build a Social Network](https://github.com/speedment/speedment/wiki/Tutorial:-Build-a-Social-Network)

Examples
--------
Here are a few examples of how you could use Speedment from your code:

###### Easy querying using standard Java 8 predicates
```java
// Large quantities of data can be reduced in-memory using predicates.
List<Hare> oldHares = Hare.stream()
    .filter(h -> h.getAge() > 8)
    .collect(toList());
```

###### Optimised predicate short-circuit
```java
// Searches are optimized in the background!
Optional<Hare> harry = Hare.stream()
    .filter(NAME.equal("Harry").and(AGE.lessThan(5)))
    .findAny();
```

###### Easy persistence
```java
// A Builder-pattern can be used to create an entity in a database
// using the "Active Record Pattern"
Optional<Hare> dbHarry = Hare.builder()
    .setName("Harry")
    .setColor("Gray")
    .setAge(3)
    .persist();
```

The `.persist()` method above is just a convenience delegator short-cut for the following:

```java
Hare harry = Hare.builder()
    .setName("Harry")
    .setColor("Gray")
    .setAge(3);

// The EntityManager is still responsible for persistence
Optional<Hare> dbHarry = EntityManager.get().persist(harry);
```

    
###### Entities are linked
```java
// Different tables form a traversable graph in memory. No need for joins!
Optional<Carrot> carrot = Hare.stream()
    .filter(NAME.equal("Harry"))
    .flatMap(Hare::carrots) // Carrot is a foreign key table.
    .findAny();
```
    
   
###### Convert to JSON
```java
// List all hares in JSON format
Hare.stream()
    .map(Hare::toJson)
    .forEach(System.out::println);
```


###### Easy initialization
```java
// A configuration-class is generated from the database.
new HareApplication().start();
```
 

Database centric
----------------
Speedment is using the database as the source-of-truth, both when it comes to the domain model and the actual data itself. Perfect if you are tired of configuring and debuging complex ORMs. After all, your data is more important than programming tools, is it not?

Code generation
---------------
Speedment inspects your database and can automatically generate code that reflects the latest state of your database. Nice if you have changed the data structure (like columns or tables) in your database. Optionally, you can change the way code is generated [using an intuitive GUI](https://github.com/speedment/speedment/wiki/Tutorial:-Get-started-with-the-GUI) or programatically using your own code.

Development Status
------------------
Speedment is still very early and we are currently moving in large blocks from the existing closed source product. 
We will be adding new cool stuff like transactions, caching and support for more database types in coming releases. We will also add JavaDocs that are missing in the current release.


Using Maven
-----------
To use Speedment, just add the following lines (between the ... marker lines) to your project's `pom.xml` file.
```xml
<build>
    <plugins>
        ...
        <plugin>
            <groupId>com.speedment</groupId>
            <artifactId>speedment-maven-plugin</artifactId>
            <version>${speedment.version}</version>
        </plugin>
        ...
    </plugins>
</build>
<dependencies>
    ...
    <dependency>
        <groupId>com.speedment</groupId>
        <artifactId>speedment</artifactId>
        <version>${speedment.version}</version>
    </dependency>
    ...
</dependencies>
```

Make sure that you use the latest ${speedment.version} available.


Requirements
------------

Speedment *will only work for Java 8 or later*. Make sure your IDE is using Java 8 as the default Java platform.
Currently, Speedment only supports MySQL and MariaDB but support for more database types will come soon.

License
-------

Speedment is available under the [Apache 2 License](http://www.apache.org/licenses/LICENSE-2.0).


#### Copyright

Copyright (c) 2008-2015, Speedment, Inc. All Rights Reserved.

Visit [www.speedment.com](http://www.speedment.com/) for more info.
