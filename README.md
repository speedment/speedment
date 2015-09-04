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
You can read the [the API quick start here](https://github.com/speedment/speedment/wiki/Speedment-API-Quick-Start)!

Tutorials
---------
* [Tutorial 1 - Get started with the GUI](https://github.com/speedment/speedment/wiki/Tutorial:-Get-started-with-the-GUI)
* [Tutorial 2 - Hello Speedment](https://github.com/speedment/speedment/wiki/Tutorial:-Hello-Speedment)
* [Tutorial 3 - Build a Social Network](https://github.com/speedment/speedment/wiki/Tutorial:-Build-a-Social-Network)
* [Tutorial 4 - Log errors in a database](https://github.com/speedment/speedment/wiki/Tutorial:-Log-errors-in-a-database)

Examples
--------
Here are a few examples of how you could use Speedment from your code:

###### Easy querying using standard Java 8 predicates
```java
// Large quantities of data can be reduced in-memory using predicates.
List<Hare> oldHares = hares.stream()
    .filter(h -> h.getAge() > 8)
    .collect(toList());
```

###### Optimised predicate short-circuit
```java
// Searches are optimized in the background!
Optional<Hare> harry = hares.stream()
    .filter(NAME.equal("Harry").and(AGE.lessThan(5)))
    .findAny();
```

###### Easy persistence
```java
// Entities can be persisted in a database
// using the "Active Record Pattern"
Hare dbHarry = hares.newInstance()
    .setName("Harry")
    .setColor("Gray")
    .setAge(3)
    .persist();
```

###### JPA-style persistance if you prefer that over the "Active Record Pattern".
```java
Hare harry = hares.newInstance()
    .setName("Harry")
    .setColor("Gray")
    .setAge(3);

<Hare dbHarry = EntityManager.get(speedment).persist(harry);
```

    
###### Entities are linked
```java
// Different tables form a traversable graph in memory. No need for joins!
Optional<Carrot> carrot = hares.stream()
    .filter(NAME.equal("Harry"))
    .flatMap(Hare::findCarrots) // Carrot is a foreign key table.
    .findAny();
```

###### Convert to JSON
```java
// List all hares in JSON format
hares.stream()
    .map(Hare::toJson)
    .forEach(System.out::println);
```

###### Easy initialization
```java
// A HareApplication class is generated from the database.
Speedment speedment = new HareApplication().withPassword("myPwd729").build();
Manager<Hare> hares = speedment.managerOf(Hare.class);
```

Database centric
----------------
Speedment is using the database as the source-of-truth, both when it comes to the domain model and the actual data itself. Perfect if you are tired of configuring and debuging complex ORMs. After all, your data is more important than programming tools, is it not?

Code generation
---------------
Speedment inspects your database and can automatically generate code that reflects the latest state of your database. Nice if you have changed the data structure (like columns or tables) in your database. Optionally, you can change the way code is generated [using an intuitive GUI](https://github.com/speedment/speedment/wiki/Tutorial:-Get-started-with-the-GUI) or programatically using your own code.

Development Status
------------------
Speedment is still early and we are currently moving in large blocks from the existing closed source product. 
We will be adding new cool stuff like transactions, reactor, caching and support for more database types in coming releases. 


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

[![Analytics](https://ga-beacon.appspot.com/UA-64937309-1/speedment/main)](https://github.com/igrigorik/ga-beacon)

[![Beacon](http://speedment.com:8081/Beacon?site=GitHub&path=main)](https://some-site.com)
