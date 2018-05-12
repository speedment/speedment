Speedment Examples
==================

This module shows basic examples of how to use Speedment including standard problems solving such as many-to-many, paging etc.

[![Join the chat at https://gitter.im/speedment/speedment](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/speedment/speedment?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

<img src="https://raw.githubusercontent.com/speedment/speedment-resources/master/src/main/resources/wiki/frontpage/Homer.png" alt="Spire the Hare" title="Spire" align="right" width="240px" />

### Requirements
The examples make use of the "sakila" example database available directly from Oracle [here](https://dev.mysql.com/doc/index-other.html). 
Download and install the database content on your target machine before running any of the examples.

# Examples 

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
     `sakila`.`film
WHERE
    (`length` > 120)
```

### Query with Optimised Paging
Show page 3 of PG-13 rated films sorted by length:
```java
private static final long PAGE_SIZE = 50;

// Even complex streams can be optimized!
long page = 3;
List<Film> stream = films.stream()
    .filter(Film.RATING.equal("PG-13"))
    .sorted(Film.LENGTH.comparator())
    .skip(page * PAGE_SIZE)
    .limit(PAGE_SIZE)
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
Define a Join relation:
```
Join<Tuple2<Film, Language>> join = joinComponent
    .from(FilmManager.IDENTIFIER)
    .innerJoinOn(Language.LANGUAGE_ID).equal(Film.LANGUAGE_ID)
    .build(Tuples::of);
```

Apply that join relation and print all films and their corresponding language:
```java
join.stream()
    .map(t2 -> String.format("The film '%s' is in %s", t2.get0().getTitle(), t2.get1().getName()))
    .forEach(System.out::println);
```

Apply that join relation and construct a Map with all languages and their corresponding films:
```java
Map<Language, List<Tuple2<Film, Language>>> languageFilmMap = join.stream()
    .collect(
        // Apply this classifier
        groupingBy(Tuple2::get1)
    ); 
```
### One-to-many
Print all Languages and their corresponding Films (A specific language can be spoken in many films):
```Java
Join<Tuple2<Language, Film>> join = joinComponent
    .from(LanguageManager.IDENTIFIER)
    .innerJoinOn(Film.LANGUAGE_ID).equal(Language.LANGUAGE_ID)
    .build(Tuples::of);

join.stream()
    .forEach(System.out::println);
```
### Many-to-one
Print all Films and their corresponding Languages (A Film can only have one language):
```Java
Join<Tuple2<Film, Language>> join = joinComponent
    .from(FilmManager.IDENTIFIER)
    .innerJoinOn(Language.LANGUAGE_ID).equal(Film.LANGUAGE_ID)
    .build(Tuples::of);

join.stream()
    .forEach(System.out::println);
````

### Many-to-many
Construct a Map with all Actors and the corresponding Films they have acted in:
```java
Join<Tuple3<FilmActor, Film, Actor>> join = joinComponent
    .from(FilmActorManager.IDENTIFIER)
    .innerJoinOn(Film.FILM_ID).equal(FilmActor.FILM_ID)
    .innerJoinOn(Actor.ACTOR_ID).equal(FilmActor.ACTOR_ID)
    .build(Tuples::of);
    
Map<Actor, List<Film>> filmographies = join.stream()
    .collect(
        groupingBy(Tuple3::get2, // Applies Actor as classifier
            mapping(
                Tuple3::get1, // Extracts Film from the Tuple
                 toList() // Use a List collector for downstream aggregation.
            )
        )
    );
```


### Entities are Linked
No need for joins when there is a foreign key!
```java
// Find any film where english is spoken
Optional<Film> anyFilmInEnglish = languages.stream()
    .filter(Language.NAME.equal("English"))
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


### Transactions
```java
txHandler().createAndAccept(tx -> {
    final List<Film> filmsToUpdate = films.stream()
        .filter(Film.LENGTH.greaterThan(75))
        .collect(toList()); // Collect all Films with length > 75
    filmsToUpdate.stream()
        .map(f -> f.setRentalDuration(f.getRentalDuration() + 1)) // Applies a lambda that increases their rental duration by one
        .forEach(films.updater());   // Applies the updater function to the selected films
    tx.commit(); // Atomically commits all updates 
})
```
Values can also be returned form a transaction as shown hereunder:
```java
long rowCount = txHandler().createAndApply(tx -> 
    films.stream().count() + actors.stream().count()
    // Computes and returns the sum of rows in the two tables atomically   
)
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

#### Copyright

Copyright (c) 2014-2017, Speedment, Inc. All Rights Reserved.
Visit [www.speedment.com](http://www.speedment.com) for more info.

