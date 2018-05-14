RestService
=========

A lightweight http-server API that can be implemented in different ways depending on which framework the application is using. For an example, one application might be using Spring and another might be using NanoHTTPD to serve requests. Using this wrapper, a library developer can support both environments.

## Maven
To use `MapBuilder` in your own projects, add the following to your `pom.xml`-file.
```xml
<dependency>
    <groupId>com.speedment.common</groupId>
    <artifactId>rest-service</artifactId>
    <version>1.0.0</version>
</dependency>
```
