Speedment is a Java 8 Stream ORM Toolkit and Runtime
====================================================

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.speedment/runtime/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.speedment/runtime)
[![Javadoc](https://javadoc-emblem.rhcloud.com/doc/com.speedment/runtime-deploy/badge.svg)](http://www.javadoc.io/doc/com.speedment/runtime-deploy)
[![Build Status](https://travis-ci.org/speedment/speedment.svg?branch=develop-3.0)](https://travis-ci.org/speedment/speedment)
[![Hex.pm](https://img.shields.io/hexpm/l/plug.svg?maxAge=2592000)](https://raw.githubusercontent.com/speedment/speedment/master/LICENSE)
[![Join the chat at https://gitter.im/speedment/speedment](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/speedment/speedment?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

<img src="https://raw.githubusercontent.com/speedment/speedment-resources/master/src/main/resources/wiki/frontpage/Forest.png" alt="Spire the Hare" title="Spire" align="right" width="240px" />

The toolkit analyzes the metadata of an existing legacy SQL database 
and creates a Java representation of the data model which together with 
the Speedment runtime allows the user to create scalable and efficient 
Java applications using **standard Java 8** streams without any
specific query language or any new API. 

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
     FROM `sakila`.`film
WHERE
    (`length` > 120)
```

No need for manually writing SQL-queies any more. Remain in a pure Java world!

### Expressing SQL as Java 8 Streams
When we started the open-source project Speedment, the main objective was to remove the polyglot requirement for Java database application developers. After all, we all love Java and why should we need to know SQL when, instead, we could derive the same semantics directly from Java streams? When one takes a closer look at this objective, it turns out that there is a remarkable resemblance between Java streams and SQL as summarized in this simplified table:

| SQL         | Java 8 Stream Equivalent          |
| :---------- | :-------------------------------- |
| `FROM`       | `stream()`   |
| `SELECT`     | `map()`      |
| `WHERE`      | `filter()` (before collecting) |
| `HAVING`     | `filter()` (after collecting) |
| `JOIN`       | `flatMap()`  |
| `DISTINCT`   | `distinct()` |
| `UNION`      | `concat(s0, s1).distinct()` |
| `ORDER BY`   | `sorted()`   |
| `OFFSET`     | `skip()`     |
| `LIMIT`      | `limit()`    |
| `GROUP BY`   | `collect(groupingBy())` |
| `COUNT`      | `count()`    |


## Documentation 
You can read the online [Speedment User's Guide here](https://speedment.github.io/speedment-doc/introduction.html)!

## Tutorials

The tutorials are divided into three sections. The basics are covered in the first section without any expected prior knowledge of Speedment. This builds a foundation of knowledge needed to fully benefit from the following tutorials.

### Basics
* [Tutorial 1 - Set up the IDE](https://github.com/speedment/speedment/wiki/Tutorial:-Set-up-the-IDE)
* [Tutorial 2 - Get started with the UI](https://github.com/speedment/speedment/wiki/Tutorial:-Get-started-with-the-UI)
* [Tutorial 3 - Hello Speedment](https://github.com/speedment/speedment/wiki/Tutorial:-Hello-Speedment)
* [Tutorial 4 - A First Stream from Speedment](https://github.com/speedment/speedment/wiki/Tutorial:-A-First-Stream-from-Speedment)

### Sample applications
* [Tutorial 5 - Speedment Spring Boot Integration; REST assured - it is easy](https://github.com/speedment/speedment/wiki/Tutorial:-Speedment-Spring-Boot-Integration)
* [Tutorial 6 - Speedment filters based on Json Web Tokens](https://github.com/speedment/speedment/wiki/Tutorial:-Speedment-Stream-Filters-Using-JWT-Data)
* [Tutorial 7 - Build a Social Network](https://github.com/speedment/speedment/wiki/Tutorial:-Build-a-Social-Network)
* [Tutorial 8 - Log errors in a database](https://github.com/speedment/speedment/wiki/Tutorial:-Log-errors-in-a-database)
* [Tutorial 9 - Use Speedment with Java EE](https://github.com/speedment/speedment/wiki/Tutorial:-Use-Speedment-with-Java-EE)
* [Tutorial 10 - Create Event Sourced Systems](https://github.com/speedment/speedment/wiki/Tutorial:-Create-an-Event-Sourced-System)

### Extending Speedment
* [Tutorial 11 - Writing your own extensions](https://github.com/speedment/speedment/wiki/Tutorial:-Writing-your-own-extensions)
* [Tutorial 12 - Plug-in a Custom TypeMapper](https://github.com/speedment/speedment/wiki/Tutorial:-Plug-in-a-Custom-TypeMapper)

Quick Start
-----------
Assuming you have Maven installed and a relational database available, you can try out Speedment in a minute either by setting up a POM file for your project or launching a Maven archetype template.

#### Setup a POM file
Use the Speedment [Initializer](https://www.speedment.com/initializer/) to get a POM template for your project.

#### Launch Archtype Directly
Running the following from a command-line:

###### MySQL
```
mvn archetype:generate -DgroupId=com.company -DartifactId=speedment-demo -DarchetypeArtifactId=speedment-archetype-mysql -DarchetypeGroupId=com.speedment.archetypes -DinteractiveMode=false -DarchetypeVersion=3.0.11 && cd speedment-demo && mvn speedment:tool
```

###### PostgreSQL
```
mvn archetype:generate -DgroupId=com.company -DartifactId=speedment-demo -DarchetypeArtifactId=speedment-archetype-postgresql -DarchetypeGroupId=com.speedment.archetypes -DinteractiveMode=false -DarchetypeVersion=3.0.11 && cd speedment-demo && mvn speedment:tool
```

###### MariaDB
```
mvn archetype:generate -DgroupId=com.company -DartifactId=speedment-demo -DarchetypeArtifactId=speedment-archetype-mariadb -DarchetypeGroupId=com.speedment.archetypes -DinteractiveMode=false -DarchetypeVersion=3.0.11 && cd speedment-demo && mvn speedment:tool
```

A graphical dialog will prompt for database connection details.

1. Enter database name and credentials and press **Connect**.
2. Press the **Generate** button and then quit the tool. 

Now you have a demo project set up with generated application code in the directory `speedment-demo`. To learn more about how to leverage the generated Speedment classes and the Speedment runtime in your project, please see the following tutorials and guides.

Examples
--------
Here are a few examples of how you could use Speedment from your code assuming that you have an exemplary MySQL database called "Sakila" avaiable. Sakila can be downloaded directly form Oracle [here](https://dev.mysql.com/doc/index-other.html) 


### Query with Optimised Stream Predicate Short-Circuit
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
     FROM `sakila`.`film
WHERE
    (`length` > 120)
```

### Query with Optimised Paging
Show page 3 of PG-13 rated films sorted by length:
```java
private static final long PAGE_SIZE = 50;

// Even complex streams can be optimized!
long page = 3;
List<Film> stream = films.stream();
    .filter(Film.RATING.equal("PG-13"));
    .sorted(Film.LENGTH.comparator())
    .skip(page * PAGE_SIZE)
    .limit(PAGE_SIZE);
    .collect(toList());
``` 

Results in the following SQL query:
```sql
SELECT 
    `film_id`,`title`,`description`,`release_year`,
    `language_id`,`original_language_id`,`rental_duration`,`rental_rate`,
    `length`,`replacement_cost`,`rating`,`special_features`,
    `last_update` 
FROM 
    `sakila`.`film` 
WHERE 
    (`rating`  = 'PG-13' COLLATE utf8_bin) 
ORDER BY 
    `sakila`.`film`.`length` ASC 
LIMIT 50 OFFSET 150;
```

### Classification
Create a Map with film ratings and the corresponding films:
```
Map<String, List<Film>> map = films.stream()
    .collect(
        Collectors.groupingBy(
            // Apply this classifier
            Film.RATING.getter()
        )
    );
```
This will produce a Map like this:
```
Rating PG-13 maps to 223 films 
Rating R     maps to 195 films 
Rating NC-17 maps to 210 films 
Rating G     maps to 178 films 
Rating PG    maps to 194 films 
```

### Joins
Construct a Map with all film languages and the corresponding films:
```java
Map<Language, List<Film>> languageFilmMap = films.stream()
    .collect(
        // Apply this foreign key classifier
        groupingBy(languages.finderBy(Film.LANGUAGE_ID))
    );
```

### Many-to-many
Construct a Map with all Actors and the corresponding Films they have acted in:
```java
Map<Actor, List<Film>> filmographies = filmActors.stream()
    .collect(
        groupingBy(actors.finderBy(FilmActor.ACTOR_ID), // Applies the FilmActor to ACTOR classifier
            mapping(
                films.finderBy(FilmActor.FILM_ID), // Applies the FilmActor to Film finder
                toList()                           // Use a List collector for downstream aggregation.
            )
        )
    );
```
Note: FilmActor is an entity with foreign keys to both the Film and the Actor table.

### Entities are Linked
No need for complicated joins!
```java
// Find any film where english is spoken
Optional<Film> anyFilmInEnglish = languages.stream()
    .flatMap(films.finderBackwardsBy(Film.LANGUAGE_ID))
    .findAny();

// Find the language of the film with id 42
Optional<Language> languageOfFilmWithId42 = films.stream()
    .filter(Film.FILM_ID.equal(42))
    .map(languages.finderBy(Film.LANGUAGE_ID))
    .findAny();
```

### Easy Initialization
The `SakilaApplication`, `SakilaApplicationBuilder` and `FilmManager` classes are generated automatically from the database.
```java
final SakilaApplication app = new SakilaApplicationBuilder()
    .withPassword("myPwd729")
    .build();
    
final FilmManager      films      = app.getOrThrow(FilmManager.class);
final LanguageManager  languages  = app.getOrThrow(CarrotManager.class);
final ActorManager     actors     = app.getOrThrow(ActorManager.class);
final FilmActorManager filmActors = app.getOrThrow(FilmActorManager.class);
```

### Easy Persistence
Entities can easily be persisted in a database.
```java
Film newFilm = new FilmImpl();  // Creates a new empty Film
newFilm.setTitle("Police Academy 13");
newFilm.setRating("G");
newFilm.setLength(123);
...

// Auto-Increment-fields have been set by the database
Film persistedFilm = films.persist(newFilm); 
```

### Update
```java
films.stream()
    .filter(Film.ID.equal(42))   // Filters out all Films with ID = 42 (just one)
    .map(Film.LENGTH.setTo(143)) // Applies a setter that sets the length to 143
    .forEach(films.updater());   // Applies the updater function
```
or another example
```java
films.stream()
    .filter(Film.ID.between(48, 102))   // Filters out all Films with ID between 48 and 102
    .map(f -> f.setRentalDuration(f.getRentalDuration() + 1)) // Applies a lambda that increases their rental duration by one
    .forEach(films.updater());          // Applies the updater function to the selected films
```

### Remove
```java
films.stream()
    .filter(Film.ID.equal(71))  // Filters out all Films with ID = 71 (just one)
    .forEach(films.remover());  // Applies the remover function
```


### Full Transparency
By appending a logger to the builder, you can follow exactly what happens behind the scenes.
```java
SakilaApplication app = new SakilaApplicationBuilder()
    .withPassword("myPwd729")
    .withLogging(ApplicationBuilder.LogType.STREAM)
    .withLogging(ApplicationBuilder.LogType.PERSIST)
    .withLogging(ApplicationBuilder.LogType.UPDATE)
    .withLogging(ApplicationBuilder.LogType.REMOVE)
    .build();
```

### Integration with Spring Boot
It is easy to integrate Speedment with Spring Boot. Here is an example of a Configuration file for Spring:
```java
@Configuration
public class AppConfig {
    private @Value("${dbms.username}") String username;
    private @Value("${dbms.password}") String password;
    private @Value("${dbms.schema}") String schema;

    @Bean
    public SakilaApplication getSakilaApplication() {
        return new SakilaApplicationBuilder()
            .withUsername(username)
            .withPassword(password)
            .withSchema(schema)
            .build();
    }

    // Individual managers
    @Bean
    public FilmManager getFilmManager(SakilaApplication app) {
        return app.getOrThrow(FilmManager.class);
    }
}
```

So when we need to use a manager in a SpringMVC Controller, we can now simply autowire it:
```java
    private @Autowired FilmManager films;
```


Features
--------
Here are some of the many features packed into the Speedment framework!

### Database Centric
Speedment is using the database as the source-of-truth, both when it comes to the domain model and the actual data itself. Perfect if you are tired of configuring and debuging complex ORMs. After all, your data is more important than programming tools, is it not?

### Code Generation
Speedment inspects your database and can automatically generate code that reflects the latest state of your database. Nice if you have changed the data structure (like columns or tables) in your database. Optionally, you can change the way code is generated [using an intuitive UI](https://github.com/speedment/speedment/wiki/Tutorial:-Get-started-with-the-UI) or programatically using your own code.

### Modular Design
Speedment is built with the ambition to be completely modular! If you don't like the current implementation of a certain function, plug in you own! Do you have a suggestion for an alternative way of solving a complex problem? Share it with the community!

### Type Safety
When the database structure changes during development of a software there is always a risk that bugs sneak into the application. Thats why type-safety is such a big deal! With Speedment, you will notice if something is wrong seconds after you generate your code instead of weeks into the testing phase.

### Null Protection
Ever seen a `NullPointerException` suddenly casted out of nowhere? Null-pointers have been called the billion-dollar-mistake of java, but at the same time they are used in almost every software project out there. To minimize the production risks of using null values, Speedment analyzes if null values are allowed by a column in the database and wraps the values as appropriate in Java 8 Optionals.


Using Maven
-----------
The easiest way to get started with Speedment and Maven is to use one of [the existing archetypes](https://github.com/speedment/speedment-archetypes). An archetype is similar to a template project. When you start a new project, it will add all the dependencies you need to your `pom.xml`-file so that you can begin program immediately.

If you do not want to use an archetype, for an example if you already have a project you want to use Speedment with, you can always write your `pom.xml`-file manually. Just add the following lines (between the ... marker lines) to your project's `pom.xml` file. Make sure to use the latest `${speedment.version}` available

#### MySQL
```xml
<build>
    <plugins>
        
        <plugin>
            <groupId>com.speedment</groupId>
            <artifactId>speedment-maven-plugin</artifactId>
            <version>${speedment.version}</version>
        </plugin>
        
    </plugins>
</build>
<dependencies>
    
    <dependency>
        <groupId>com.speedment</groupId>
        <artifactId>runtime</artifactId>
        <version>${speedment.version}</version>
        <type>pom</type>
    </dependency>
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>5.1.42</version>
        <scope>runtime</scope>
    </dependency>
    
</dependencies>
```


#### PostgreSQL
```xml

<build>
    <plugins>
        
        <plugin>
            <groupId>com.speedment</groupId>
            <artifactId>speedment-maven-plugin</artifactId>
            <version>${speedment.version}</version>
        </plugin>
        
    </plugins>
</build>
<dependencies>
    
    <dependency>
        <groupId>com.speedment</groupId>
        <artifactId>runtime</artifactId>
        <version>${speedment.version}</version>
        <type>pom</type>
    </dependency>
    <dependency>
        <groupId>org.postgresql</groupId>
        <artifactId>postgresql</artifactId>
        <version>42.0.0</version>
        <scope>runtime</scope>
    </dependency>
    
</dependencies>

```

#### MariaDB
```xml
<build>
    <plugins>
        
        <plugin>
            <groupId>com.speedment</groupId>
            <artifactId>speedment-maven-plugin</artifactId>
            <version>${speedment.version}</version>
        </plugin>
        
    </plugins>
</build>
<dependencies>
    
    <dependency>
        <groupId>com.speedment</groupId>
        <artifactId>runtime</artifactId>
        <version>${speedment.version}</version>
        <type>pom</type>
    </dependency>
    <dependency>
        <groupId>org.mariadb.jdbc</groupId>
        <artifactId>mariadb-java-client</artifactId>
        <version>2.0.1</version>
        <scope>runtime</scope>
    </dependency>
    
</dependencies>

```

Again, make sure that you use the latest `${speedment.version}` available.

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

