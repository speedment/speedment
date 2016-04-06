Wrap your database into Java 8!
==========================================

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.speedment/speedment/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.speedment/speedment)
[![Javadoc](https://javadoc-emblem.rhcloud.com/doc/com.speedment/speedment/badge.svg)](http://www.javadoc.io/doc/com.speedment/speedment)
[![Join the chat at https://gitter.im/speedment/speedment](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/speedment/speedment?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

<p>
<img src="https://raw.githubusercontent.com/speedment/speedment-resources/master/src/main/resources/logo/Speedhare_240x211.png" alt="Spire the Hare" title="Spire" align="right" /><span>Speedment accelerates your development speed and makes programming so easy and fun!</span>

When you use Speedment for database querying, you do not have to learn a new APIs or use complex ORMs. Everything is <strong>standard Java 8</strong> and works 
out of the box!
</p>

This site covers the <strong>Speedment Open Source</strong> project available under the [Apache 2 license](http://www.apache.org/licenses/LICENSE-2.0). If you are interested in the enterprise product with support for commercial databases and in-memory acceleration, check out [www.speedment.com](http://speedment.com/)!

Documentation
-------------
You can read the [the API quick start here](https://github.com/speedment/speedment/wiki/Speedment-API-Quick-Start)!

Tutorials
---------
* [Tutorial 1 - Get started with the UI](https://github.com/speedment/speedment/wiki/Tutorial:-Get-started-with-the-UI)
* [Tutorial 2 - Hello Speedment](https://github.com/speedment/speedment/wiki/Tutorial:-Hello-Speedment)
* [Tutorial 3 - Build a Social Network](https://github.com/speedment/speedment/wiki/Tutorial:-Build-a-Social-Network)
* [Tutorial 4 - Log errors in a database](https://github.com/speedment/speedment/wiki/Tutorial:-Log-errors-in-a-database)
* [Tutorial 5 - Using Speedment with Java EE](https://github.com/speedment/speedment/wiki/Tutorial:-Use-Speedment-with-Java-EE)
* [Tutorial 6 - Writing your own extensions](https://github.com/speedment/speedment/wiki/Tutorial:-Writing-your-own-extensions)

Examples
--------
Here are a few examples of how you could use Speedment from your code:

###### Easy initialization
A `HareApplication` class is generated from the database.
```java
Speedment speedment = new HareApplication().withPassword("myPwd729").build();
Manager<Hare> hares = speedment.managerOf(Hare.class);
```

###### Optimised predicate short-circuit
Search for Hares by a certain age:
```java
// Searches are optimized in the background!
Optional<Hare> harry = hares.stream()
    .filter(NAME.equal("Harry").and(AGE.lessThan(5)))
    .findAny();
```

Results in the following SQL query:
```sql
SELECT * FROM `Hare` 
    WHERE `Hare`.`name` = "Harry"
    AND `Hare`.`age` < 5
    LIMIT 1;
```

###### Easy persistence
Entities can be persisted in a database using the "Active Record Pattern"
```java
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

Hare dbHarry = EntityManager.get(speedment).persist(harry);
```

###### Entities are linked
No need for complicated joins!
```java
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

###### Or collect the entire stream
```java
// List all hares as one JSON object
String json = hares.stream()
    .collect(CollectorUtil.toJson());
```

Features
--------
Here are some of the many features packed into the Speedment framework!

### Database centric
Speedment is using the database as the source-of-truth, both when it comes to the domain model and the actual data itself. Perfect if you are tired of configuring and debuging complex ORMs. After all, your data is more important than programming tools, is it not?

### Code generation
Speedment inspects your database and can automatically generate code that reflects the latest state of your database. Nice if you have changed the data structure (like columns or tables) in your database. Optionally, you can change the way code is generated [using an intuitive UI](https://github.com/speedment/speedment/wiki/Tutorial:-Get-started-with-the-UI) or programatically using your own code.

### Modular Design
Speedment is built with the ambition to be completely modular! If you don't like the current implementation of a certain function, plug in you own! Do you have a suggestion for an alternative way of solving a complex problem? Share it with the community!

### Type Safety
When the database structure changes during development of a software there is always a risk that bugs sneak into the application. Thats why type-safety is such a big deal! With Speedment, you will notice if something is wrong seconds after you generate your code instead of weeks into the testing phase.

### Null Protection
Ever seen a `NullPointerException` suddenly casted out of nowhere? Null-pointers have been called the billion-dollar-mistake of java, but at the same time they are used in almost every software project out there. To minimize the production risks of using null values, Speedment analyzes if null values are allowed by a column in the database and wraps the values as appropriate in Java 8 Optionals.


Using Maven
-----------
To use Speedment, just add the following lines (between the ... marker lines) to your project's `pom.xml` file.

### MySQL

```xml
<build>
    <plugins>
        ...
        <plugin>
            <groupId>com.speedment</groupId>
            <artifactId>speedment-maven-plugin</artifactId>
            <version>2.3.0</version>
            <dependencies>
                <dependency>
                    <groupId>mysql</groupId>
                    <artifactId>mysql-connector-java</artifactId>
                    <version>5.1.38</version>
                </dependency>
            </dependencies> 
        </plugin>
        ...
    </plugins>
</build>
<dependencies>
    ...
    <dependency>
        <groupId>com.speedment</groupId>
        <artifactId>speedment</artifactId>
        <version>2.3.0</version>
    </dependency>
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>5.1.38</version>
    </dependency>
    ...
</dependencies>
```

### PostgreSQL

```xml
<build>
    <plugins>
        ...
        <plugin>
            <groupId>com.speedment</groupId>
            <artifactId>speedment-maven-plugin</artifactId>
            <version>2.3.0</version>
            <dependencies>
                <dependency>
                    <groupId>org.postgresql</groupId>
                    <artifactId>postgresql</artifactId>
                    <version>9.4-1206-jdbc4</version>
                </dependency>
            </dependencies> 
        </plugin>
        ...
    </plugins>
</build>
<dependencies>
    ...
    <dependency>
        <groupId>com.speedment</groupId>
        <artifactId>speedment</artifactId>
        <version>2.3.0</version>
    </dependency>
    <dependency>
        <groupId>org.postgresql</groupId>
        <artifactId>postgresql</artifactId>
        <version>9.4-1206-jdbc4</version>
    </dependency>
    ...
</dependencies>
```

### MariaDB

```xml
<build>
    <plugins>
        ...
        <plugin>
            <groupId>com.speedment</groupId>
            <artifactId>speedment-maven-plugin</artifactId>
            <version>2.3.0</version>
            <dependencies>
                <dependency>
                    <groupId>org.mariadb.jdbc</groupId>
                    <artifactId>mariadb-java-client</artifactId>
                    <version>1.4.0</version>
                </dependency>
            </dependencies> 
        </plugin>
        ...
    </plugins>
</build>
<dependencies>
    ...
    <dependency>
        <groupId>com.speedment</groupId>
        <artifactId>speedment</artifactId>
        <version>2.3.0</version>
    </dependency>
    <dependency>
        <groupId>org.mariadb.jdbc</groupId>
        <artifactId>mariadb-java-client</artifactId>
        <version>1.4.0</version>
    </dependency>
    ...
</dependencies>
```


Make sure that you use the latest `${speedment.version}` available.


### Requirements
Speedment comes with support for the following databases out-of-the-box:
* MySQL
* MariaDB
* PostgreSQL

As of version 2.0, Speedment requires `Java 8` or later. Make sure your IDE configured to use JDK 8.

License
-------

Speedment is available under the [Apache 2 License](http://www.apache.org/licenses/LICENSE-2.0).


#### Copyright

Copyright (c) 2015, Speedment, Inc. All Rights Reserved.
Visit [www.speedment.org](http://www.speedment.org/) for more info.

[![Analytics](https://ga-beacon.appspot.com/UA-64937309-1/speedment/main)](https://github.com/igrigorik/ga-beacon)

[![Beacon](http://stat.speedment.com:8081/Beacon?site=GitHub&path=main)](https://some-site.com)
