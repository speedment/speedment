<img align="center" src="https://github.com/speedment/speedment-resources/blob/master/src/main/resources/wiki/frontpage/Duke-Spire.png?raw=true." alt="Spire the Hare" title="Spire" width="900px">

Java Stream ORM
====================================================

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.speedment/runtime/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.speedment/runtime)
[![Javadocs](http://www.javadoc.io/badge/com.speedment/runtime-deploy.svg)](http://www.javadoc.io/doc/com.speedment/runtime-deploy)
[![Build Status](https://travis-ci.org/speedment/speedment.svg?branch=develop-3.0)](https://travis-ci.org/speedment/speedment)
[![Hex.pm](https://img.shields.io/hexpm/l/plug.svg?maxAge=2592000)](https://raw.githubusercontent.com/speedment/speedment/master/LICENSE)
[![Join the chat at https://gitter.im/speedment/speedment](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/speedment/speedment?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

Speedment is an open source Java Stream ORM toolkit and runtime. The toolkit analyzes the metadata of an existing SQL database and automatically creates a Java representation of the data model. This powerful ORM enables you to create scalable and efficient Java applications using **standard Java** streams with no need to type SQL or use any new API. 

Speedment was originally developed by researchers and engineers based in Palo Alto with the purpose to simplify and streamline the development of Java database applications by leveraging the Java 8 Stream API. The current version is Speedment Homer (3.1), which like all major Speedment versions is named after a street in Palo Alto.

Speedment is licensed under the business-friendly Apache 2 license. Contribution from users are encouraged. Please feel free to request new features, suggest improvements and file bug reports.

<img src="https://github.com/speedment/speedment-resources/blob/master/src/main/resources/wiki/frontpage/Spire-Quick-Start.png?raw=true" alt="Spire the Hare" title="Spire" align="right" width="200px" />

Quick Start
-----------

Assuming you have Maven installed and a relational database available, you can start using Speedment in a minute:

* [Start a New Speedment Maven Project](https://github.com/speedment/speedment/wiki/Start-a-New-Speedment-Maven-Project)
* [Connect to Your Database](https://github.com/speedment/speedment/wiki/Connect-to-Your-Database)


Resources
-----------

### Expressing SQL as Java 8 Streams
There is a remarkable resemblance between Java streams and SQL as summarized in the simplified table. This means there is no need for manually writing SQL-queries any more. You can remain in a pure Java world!

<img align="left" src="https://github.com/speedment/speedment-resources/blob/master/src/main/resources/wiki/frontpage/SQL-Stream.png?raw=true." alt="Streams to SQL" width="400px">

### One-liner
Search for a long film (of length greater than 120 minutes):
```java
// Searches are optimized in the background!
Optional<Film> longFilm = films.stream()
    .filter(Film.LENGTH.greaterThan(120))
    .findAny();
``` 

Results in the following SQL query:
```sql
SELECT 
    `film_id`,`title`,`description`,`release_year`,
    `language_id`,`original_language_id`,`rental_duration`,`rental_rate`,
    `length`,`replacement_cost`,`rating`,`special_features`,
    `last_update` 
FROM 
     `sakila`.`film
WHERE
    (`length` > 120)
```


# Resources 

### Documentation 
You can read the online [Speedment User's Guide here](https://speedment.github.io/speedment-doc/introduction.html)!

### Examples 
You can find many examles in the User's Guide as well as in our examples folder here on GitHub.  https://github.com/speedment/speedment/tree/master/example-parent

### Tutorials
The tutorials are divided into three sections. The basics are covered in the first section without any expected prior knowledge of Speedment. This builds a foundation of knowledge needed to fully benefit from the following tutorials.

#### Basics
* [Tutorial 1 - Hello Speedment](https://github.com/speedment/speedment/wiki/Tutorial:-Hello-Speedment)
* [Tutorial 2 - A First Stream from Speedment](https://github.com/speedment/speedment/wiki/Tutorial:-A-First-Stream-from-Speedment)

#### Sample applications
* [Tutorial 3 - Speedment Spring Boot Integration; REST assured - it is easy](https://github.com/speedment/speedment/wiki/Tutorial:-Speedment-Spring-Boot-Integration)
* [Tutorial 4 - Speedment filters based on Json Web Tokens](https://github.com/speedment/speedment/wiki/Tutorial:-Speedment-Stream-Filters-Using-JWT-Data)
* [Tutorial 5 - Build a Social Network](https://github.com/speedment/speedment/wiki/Tutorial:-Build-a-Social-Network)
* [Tutorial 6 - Log errors in a database](https://github.com/speedment/speedment/wiki/Tutorial:-Log-errors-in-a-database)
* [Tutorial 7 - Use Speedment with Java EE](https://github.com/speedment/speedment/wiki/Tutorial:-Use-Speedment-with-Java-EE)
* [Tutorial 8 - Create Event Sourced Systems](https://github.com/speedment/speedment/wiki/Tutorial:-Create-an-Event-Sourced-System)

#### Extending Speedment
* [Tutorial 9 - Writing your own extensions](https://github.com/speedment/speedment/wiki/Tutorial:-Writing-your-own-extensions)
* [Tutorial 10 - Plug-in a Custom TypeMapper](https://github.com/speedment/speedment/wiki/Tutorial:-Plug-in-a-Custom-TypeMapper)

Features
--------
Here are some of the many features packed into the Speedment framework!

### Database Centric
Speedment is using the database as the source-of-truth, both when it comes to the domain model and the actual data itself. Perfect if you are tired of configuring and debuging complex ORMs. After all, your data is more important than programming tools, is it not?

### Code Generation
Speedment inspects your database and can automatically generate code that reflects the latest state of your database. Nice if you have changed the data structure (like columns or tables) in your database. Optionally, you can change the way code is generated using an intuitive UI or programatically using your own code.

### Modular Design
Speedment is built with the ambition to be completely modular! If you don't like the current implementation of a certain function, plug in you own! Do you have a suggestion for an alternative way of solving a complex problem? Share it with the community!

### Type Safety
When the database structure changes during development of a software there is always a risk that bugs sneak into the application. Thats why type-safety is such a big deal! With Speedment, you will notice if something is wrong seconds after you generate your code instead of weeks into the testing phase.

### Null Protection
Ever seen a `NullPointerException` suddenly casted out of nowhere? Null-pointers have been called the billion-dollar-mistake of java, but at the same time they are used in almost every software project out there. To minimize the production risks of using null values, Speedment analyzes if null values are allowed by a column in the database and wraps the values as appropriate in Java 8 Optionals.

### Requirements
Speedment comes with support for the following databases out-of-the-box:
* MySQL
* MariaDB
* PostgreSQL

This site covers the **Speedment Open Source** project available under the 
[Apache 2 license](http://www.apache.org/licenses/LICENSE-2.0). The 
enterprise product with support for commercial 
databases (i.e. Oracle, MS SQL Server, DB2, AS400) and in-JVM-memory acceleration can be found at 
[www.speedment.com](http://speedment.com/).

Speedment requires `Java 8` or later. Make sure your IDE configured to use JDK 8 (version 1.8.0_40 or newer).

License
-------

Speedment is available under the [Apache 2 License](http://www.apache.org/licenses/LICENSE-2.0).


#### Copyright

Copyright (c) 2014-2017, Speedment, Inc. All Rights Reserved.
Visit [www.speedment.com](http://www.speedment.com/) for more info.

[![Analytics](https://ga-beacon.appspot.com/UA-64937309-1/speedment/main)](https://github.com/igrigorik/ga-beacon)

[![Beacon](http://stat.speedment.com:8081/Beacon?site=GitHub&path=main)](https://some-site.com)

[Github activity visualized](https://www.youtube.com/watch?v=Rmc_3lLZQpM)

