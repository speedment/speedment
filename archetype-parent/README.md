## Speedment Archetypes
The parent project for the archetypes available for Speedment. There is one archetype for every supported database.

### Usage
To use an archetype when creating a new Speedment project, run the following command:
```bash
mvn archetype:generate \
    -DgroupId=com.company \
    -DartifactId=speedment-demo \
    -DarchetypeArtifactId=speedment-archetype-mysql \
    -DarchetypeGroupId=com.speedment.archetypes \
    -DinteractiveMode=false \
    -DarchetypeVersion=3.0.2
```
Replace `speedment-archetype-mysql` with the archetype you want to use.

Check out the individual pages for more information:
* [MySQL](https://github.com/speedment/speedment/tree/master/archetype-parent/speedment-archetype-mysql)
* [MariaDB](https://github.com/speedment/speedment/tree/master/archetype-parent/speedment-archetype-mariadb)
* [PostgreSQL](https://github.com/speedment/speedment/tree/master/archetype-parent/speedment-archetype-postgresql)
