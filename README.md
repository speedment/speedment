Wrap your database into Java 8!
==========================================

[![Join the chat at https://gitter.im/speedment/speedment](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/speedment/speedment?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

<p>
<img src="data:image/svg+xml;base64,PHN2ZyB4bWxuczpyZGY9Imh0dHA6Ly93d3cudzMub3JnLzE5OTkvMDIvMjItcmRmLXN5bnRheC1ucyMiIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyIgaGVpZ2h0PSIxNDVtbSIgd2lkdGg9IjE2NW1tIiB2ZXJzaW9uPSIxLjEiIHhtbG5zOmNjPSJodHRwOi8vY3JlYXRpdmVjb21tb25zLm9yZy9ucyMiIHhtbG5zOmRjPSJodHRwOi8vcHVybC5vcmcvZGMvZWxlbWVudHMvMS4xLyIgdmlld0JveD0iMCAwIDU4NC42NDU2OCA1MTMuNzc5NTIiPgo8ZyB0cmFuc2Zvcm09Im1hdHJpeCgtMSAwIDAgMSA1NzkuMTcgLTU0Mi40MykiPgo8cGF0aCBmaWxsLXJ1bGU9ImV2ZW5vZGQiIGZpbGw9IiNjZmYiIGQ9Im0xNzMuODkgNjUwLjAxYy03OS41NTUtMjMuMDg2IDIuMDA1NSAyOC40NjUtODAuNzggMTQyLjI5LTEzLjE5MSAxLjU4ODMtNzkuMDI2LTMyLjk0Ni03OS45NTQtMzEuMjA5LTMuODMxNyAxMS4xNDIgNi45MjkzIDMwLjQ1NCAzMy4zNzIgNDkuOTIyLTAuMzAxNTktNC4zMTYyIDI0Ni41NCAxMjIuMjQgMzQzLjQ4IDIzMy43IDIwLjEgMTkuMjU4IDI2LjQwMS00Ni45MjMgMy42NDA3LTcwLjkyNi01LjEzNDEtMTAuNTkyIDEwMC45My00OS4wMSAxMDUuOTItNDQuOTMgNDguNDUyIDM5LjY0MyA0OC4yMzUgNDUuNDk1IDUyLjM5NyAzNS44NyA5LjQ0MjQtMjEuODMzLTguMDQyNS01MC40NDItMjEuNTg0LTY4LjEzOSAzNi40ODQtMTIwLjQ1LTEwLjIzOS0yODYuOTktMjc4LjAxLTMzOC4zNy00LjA3NTUtMC43ODE5NiA1OS4wNTYgNDEuOTAyIDEyOCAxMjMuOTQtNS44MzA0IDEuMTY1OS0xMS40NjkgMy4wODItMTcuNDk3IDMuNDc2LTMzLjg4My0yNS44My03MS45MjEtMzYuNjIxLTEwNy4yOS00NS4yOThsLTY0Ljc1My02My4wMTN6Ii8+CjxwYXRoIGZpbGwtcnVsZT0iZXZlbm9kZCIgZmlsbD0iIzljZiIgZD0ibTI1Ny4yNSA1NTguNzZjMzc2Ljc4IDE2MS45MyAzNC4zMzcgMTc2LjQxIDc0LjY1OCAxODguMjIgMTQuNTYgNC4yNjcyIDIwOC43OCAzNS42IDIwMy40NyAxMTYuNDctMC40MzIwNyA2LjU4NjktOS40NTc0IDIzLjA0NS0zLjE0NDQgMzAuMzA2IDExLjI4MSAxMi45NzQgNy4zNTYyIDEwLjY5OSA3LjIwMTEgMTUuMTM4LTAuMzY1NTcgMTAuNDYyLTEzLjM5OCAxMy41MTUtNi43MTA5IDQ2LjkzMy0wLjU5NDM5IDEuMTA1IDYuMDUzNS0yLjQyNjgtMjYuMjQ4LTIyLjI0NSAwIDAtMTAuNzM4LTIxLjI4OC0xMTIuNDYgNDEuNjQ4LTIxLjE1NyA3Ljc3NjMtMTQuNDM5IDUxLjczNC0zLjczOTMgNjkuNTU5LTEwMy4zMy0xMDQuMjctMzEzLjgyLTIyMC4wNS0zNDcuMy0yMzMuMjMtMzcuNDg2LTM1LjYtMzAuNjkxLTQ2LjMyLTMxLjg2OS00OS40NC0xLjEyODgtMy45Nzc1IDQ4LjM5MyA1LjI5NDQgNTcuOTI3LTEuMzEgMCAwIDQ4LjIyLTc2LjM4MyA2NC45NDEtODYuOTI4LTIuNzUyNC0zLjE2NzIgOS42ODMtMzguOTAyIDUuMDI3My0zMi40MjctMTYuNTkgMjMuMDczIDEuMjA2OSA2MS4xMzkgNDEuMzcyIDY2Ljg2NyAxLjQ3MTUgMC4yMDk4Ny04Ljk5MzUtNTMuMDAxLTQuMTI4Ny02Mi4wMTlsMS4wMDE2LTIwLjY5MmMxLjQ1MTEtMjkuOTc4IDExLjQ4MS01MC4wOSAxMy4zMjItNTEuMDM5LTUuMjY4OSAxLjc1NzQgNDAuOTI0IDE2Mi40MSA2Mi4wNTEgMTQ2LjEyIDEyLjUxLTE1LjU4MS0yMC43NDYtNTkuNjA2LTQuOTMzOC03NC4zNSAyOC4yMDgtMjYuMzAyIDMyLjk5NiA3MS45NzMgMTMyLjU5IDM1LjAwNC02OC4zMDQtODEuMDgtODkuODA3LTgwLjMzNy0xMjMuMDMtMTIyLjZ6Ii8+CjxnIGZpbGw9Im5vbmUiPgo8cGF0aCBkPSJtMjU2LjQzIDYzOS4zOWMzNS4zNzEgOC42Nzc3IDc1LjY5NyAyMS45NzMgMTA5LjU4IDQ3LjgwMm0tMTUwLjEgMTAyLjkzYy02OC4zMS0xMTcuMjEtMjYuNjItMjE2Ljg5LTI2LjYyLTIxNi44OSA2Ljg5ODUgOS4xNTM2IDEzMS4wMSAxMzAuNiAxNDEuMDIgMTI2LjU4IDguMjk2LTMuMzI3IDUwLjkxMi0xNi4wNzEgNDguMjcyLTE4Ljk3Ni02OS40NjUtNzkuODU0LTExNC42Ni0xMTIuNzEtMTI2LjIyLTEyMi43MiAyNjcuNzcgNTEuMzc3IDMxMy43MSAyMTUuMDkgMjc3LjIyIDMzNS41NCAxNy43OTIgMTcuMTk3IDM0Ljk5OSA3Mi4yIDIwLjI4MSA3Ni40MjUtOC4yOTEgMi4zNzk5LTEuMTQ0Ni01LjEwOTctNTMuMzc5LTQxLjU5NS0zLjY3MS0yLjU2NDItMTA1LjM2IDM5LjcyMi0xMDIuMDEgNDUuODQyIDIyLjc2IDI0LjAwMyAxNC4zNCA4OS44NS01Ljc1OTQgNzAuNTkzLTk2LjkzLTExMS40My0zMjMuNTgtMjIxLjE4LTM0Mi4xOC0yMzMuOTgtMjYuNDQtMTkuNDctMzcuMjAyLTM4Ljc4LTMzLjM3LTQ5LjkzIDAuOTI3OTMtMS43MzcxIDQzLjk5NiAzLjEzNzggNTcuMTg3IDEuNTQ5NSAzNC45NzMtNTkuOTEgNjMuNjQzLTEwMS45NiAxMDYuMTEtMTEzLjg2IiBzdHJva2U9IiMzMjY3YzkiIHN0cm9rZS1saW5lY2FwPSJyb3VuZCIgc3Ryb2tlLXdpZHRoPSI2Ii8+CjxwYXRoIHN0cm9rZS1saW5lam9pbj0icm91bmQiIGQ9Im0xNjkuMTMgNjUwLjA5Yy0xMi41MjktMy42Nzg1LTIxLjkyNy0xMC45MDQtMjguNTE1LTQuOTEyLTQuOTk1OCA0Ljk4OTItOC42NjIxIDIxLjYzMy01LjY0MDcgMjUuNDU2IiBzdHJva2U9IiMzMjY3YzkiIHN0cm9rZS1saW5lY2FwPSJyb3VuZCIgc3Ryb2tlLXdpZHRoPSI2Ii8+CjwvZz4KPGVsbGlwc2Ugc3Ryb2tlLW9wYWNpdHk9IjAiIHJ4PSI0Mi41MDgiIHJ5PSI0OS4wMSIgdHJhbnNmb3JtPSJtYXRyaXgoLS45ODI5NCAtLjE4Mzk0IC0uMTQyMTEgLjk4OTg1IDAgMCkiIGN5PSI3NDkuODgiIGN4PSItNDc4LjM3IiBmaWxsPSIjMzI2N2M5Ii8+CjxlbGxpcHNlIHN0cm9rZS1vcGFjaXR5PSIwIiByeD0iMjYuNTg1IiByeT0iNDIuNTU3IiB0cmFuc2Zvcm09Im1hdHJpeCgtLjg2NDcyIC41MDIyNSAuMjkxNzMgLjk1NjUgMCAwKSIgY3k9Ijk1MC40OCIgY3g9Ii0yNjIuNTciIGZpbGw9IiMzMjY3YzkiLz4KPGcgc3Ryb2tlLWxpbmVjYXA9InJvdW5kIiBmaWxsPSJub25lIj4KPGcgc3Ryb2tlLXdpZHRoPSI2IiBzdHJva2U9IiMzMjY3YzkiPgo8cGF0aCBkPSJtMzYzLjkyIDgzNC4yOWM3NC4zNzEtMTYuMjI4IDE0MS4yNi00My4zMjQgMTMxLjcyLTY1LjY5NiA3LjIzNTkgMTkuMDc5LTIwLjgwNCA4MC4xMDYtMjYuMzU4IDgzLjU0Ni05LjkxNDUtMi40MjQ3LTkwLjQ4MS0xOC44MTEtMTA1LjM3LTE3Ljg1MXoiLz4KPHBhdGggZD0ibTQ4Mi4zIDg1OS40NWMtNi41OTggMjUuMjYtMjcuNTA2IDE1LjIwOC0zMC43ODUgMTAuNTY5Ii8+CjxwYXRoIGQ9Im00NjkuMjUgODUzLjY4IDEuNjE5IDIwLjYwNyIvPgo8L2c+CjxwYXRoIHN0cm9rZS1saW5lam9pbj0icm91bmQiIGQ9Im01MTYuNjQgNzY0LjQ1Yy04Ljc1NzUtMzAuMjQ0LTMxLjQ1My0xMC4zMTUtMTguNDM3IDI5Ljk1MSIgc3Ryb2tlPSIjZmZmIiBzdHJva2Utd2lkdGg9IjExLjUiLz4KPC9nPgo8cGF0aCBzdHJva2UtbGluZWpvaW49InJvdW5kIiBkPSJtMzkwLjEyIDgzMy45N2M1LjIyNy0zNy4xMTItNDEuNTEtNDIuMDA1LTUxLjQzNi0zLjY5NDQiIHN0cm9rZT0iI2ZmZiIgc3Ryb2tlLWxpbmVjYXA9InJvdW5kIiBzdHJva2Utd2lkdGg9IjE1IiBmaWxsPSJub25lIi8+CjwvZz4KPC9zdmc+Cg==" alt="Spire the Hare" title="Spire" align="right" /><span>Speedment accelerates your development speed and makes programming so easy and fun!</span>

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

Hare dbHarry = EntityManager.get(speedment).persist(harry);
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
