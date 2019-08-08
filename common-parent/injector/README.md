# Injector
A dependency injection system used internally in Speedment.

## Usage
The dependency injector is initiated using a builder pattern. An `InjectorBuilder` instance is created, followed by a number of configurations. When the build is finalized using the `.build()`-method, every injectable class is instantiated and configured using a predefined configuration cycle.

##### Creating an Injector
Create a new injector, using the `withComponent()`-method to make classes injectable.
```java
final Injector injector = Injector.builder()
    .withComponent(FooComponent.class)
    .withComponent(BarComponent.class)
    .build();
```

##### Using the @Inject-annotation
Injectable classes can use the `@Inject`-annotation to declare dependencies. These will be set automatically when the `build()`-method in the injector is invoked.

```java
public class FooComponent {
    private @Inject BarComponent bar; // Will be set by the injector.
}
```

Every component is only initialized once. If multiple components depend on the same component, those instances will be set to the same instace.

##### Using Bundles
If multiple components are typically used together, a bundle can be created to avoid having to list them all every time they are used. Bundles are defined by implementing the `InjectBundle` interface.

```java
public class FooBarBundle implements InjectBundle {
    @Override
    public Stream<Class<?>> injectables() {
        return Stream.of(
            FooComponent.class,
            BarComponent.class
        );
    }
}
```

When you later want to use the bundle, you can simply include it in the `InjectorBuilder` using the `withBundle()`-method.

```java
final Injector injector = Injector.builder()
    .withBundle(FooBarBundle.class)
    .build(); // Both FooComponent and BarComponent will be included.
```

##### Interface and Implementation Separation
Typically you don't want to expose your implementation classes when building an API. `Injector`is therefore designed to allow interface/implementation-separation.

```java
public interface BazComponent {

}
...
public class BazComponentImpl() implements BazComponent {

}
...
final Injector injector = Injector.builder()
    .withComponent(BazComponentImpl.class)  // <-- The implementation class is specified here
    .build();
...
private @Inject BazComponent baz; // <-- The injector will inject the BazComponentImpl here.
```

##### Control Which Implementation using @InjectKey
There are times when you want to inherit from an interface without preventing another implementation from being instantiated. In these cases, it is a good idea to add the `@InjectKey`-annotation to the component.

```java
public interface EncoderComponent {}
public interface HasToString {}
public interface HasToBytes {}

@InjectKey(value=EncoderComponent.class, overwrite=false)
public class JsonEncoderComponent implements EncoderComponent, HasToString {}

@InjectKey(value=EncoderComponent.class, overwrite=false) // <-- Both will be created.
public class BinaryEncoderComponent implements EncoderComponent, HasToBytes {}
```

##### Control How Instances Are Constructed
To get more control over how instances are created, a specific constructor can be specified. This is also done using the `@Inject`-annotation.

```java
public class SpecificConstructor {
    
    public SpecificConstructor() {
        // This constructor will NOT be used since another constructor with the
        // @Inject-annotation exists.
    }
    
    @Inject
    public SpecificConstructor(OtherComponent other) {
        // This constructor will be used if `OtherComponent` is present. 
        // Otherwise an exception will be thrown then the Injector is built.
    }
    
}
```

To make the injector use the default constructor or some other constructor as a fallback, you can set the `@OnlyIfMissing`-annotation.

```java
public class SpecificConstructor {
    
    @Inject
    @OnlyIfMissing(OtherComponent.class)
    public SpecificConstructor() {
        // This constructor will ONLY be used if no implementation of 
        // OtherComponent is known by the Injector.
    }
    
    @Inject
    public SpecificConstructor(OtherComponent other) {
        // This constructor will be used if `OtherComponent` is present. If the
        // implementation is known, then the Injector will make sure that they
        // are called in the correct order.
    }
    
}
```

##### Execute Code During Initialization
`InjectorBuilder` uses an configuration cycle when building the `Injector` instance that has the following phases:

1. CREATED
2. INITIALIZED
3. RESOLVED
4. STARTED

If the `.stop()` method is later invoked, components will enter the last phase:

5. STOPPED

The builder will make sure that every component is `INITIALIZED` before anyone enters the phase `RESOLVED`, and that everyone is `RESOLVED` before anyone enters the phase `STARTED`.

To execute code as part of the initialization cycle, annotate a method in an injectable class with `@ExecuteBefore`.

```java
public class FooComponent {
    
    private StringBuilder phases;
    
    @ExecuteBefore(INITIALIZED)
    void onInitialized() {
        phases = new StringBuilder("init...");
    }
    
    @ExecuteBefore(RESOLVED)
    void onInitialized() {
        phases.append("resolved...");
    }
    
    @ExecuteBefore(CREATED)
    void onInitialized() {
        phases.append("created...");
    }
    
}
```

If other components are required to initialize a component, they can be injected as method parameters. To make sure the injected parameter has a particular state, use the `@WithState`-annotation.

```java
public class FooComponent {

    @ExecuteBefore(INITIALIZED)  
    void doSomething(@WithState(INITIALIZED) BarComponent bar) {
        // Will be invoked AFTER BarComponent has been initialized
    }

}
```

##### Configurable Parameters
Configurable Parameters can be injected into classes using the `@Config` annotation. The values for these can later be specified either as part of the `InjectorBuilder` process using the `withParam()`-method or by creating a `settings.properties`-file in the same folder as the application.

```java
public class ConfigurableComponent {

    // These will be loaded in first hand from the InjectorBuilder.withParam()-method 
    // and in second hand from the settings.properties-file.
    private @Config(name="dbms.schema", value="test_schema") String databaseSchema;
    private @Config(name="dbms.host", value="localhost") String databaseHost;
    private @Config(name="dbms.port", value="3306") int databasePort;
    
    private Connection conn;
    
    @ExecuteBefore(RESOLVED)
    void testConnection() {
        try {
            final String url = String.format(
                "jdbc:mysql://%s:%d/%s?useSSL=false",
                databaseHost, databasePort, databaseSchema
            );

            DriverManager.getConnection(url, username, password).close();
            return;
        } catch (final SQLException ex) {
            throw new RuntimeException(String.format(
                "Could not establish connection to %s:%d.", 
                databaseHost, databasePort
            ), ex);
        }
    }
}
```

Sometimes it is desirable to only invoke a method if all the dependencies are available. To accomplish this, the `missingArgument` parameter can be set.

```java
@ExecuteBefore(value = RESOLVED, missingArgument = SKIP_INVOCATION)
void installComponent(@WithState(RESOLVED) OtherComponent other) {
    // This method will only be invoked if the other component is available and
    // has the state RESOLVED. If the dependency injector doesn't know how to
    // produce an instance of type OtherComponent, then this fact is logged and
    // this method is ignored.
}
```

##### Runtime Access to Injector
Sometimes it is not convenient to make a class injectable, but you still want to access the injectable instances it holds. To do this, the `Injector` interface contains a number of methods to access its state after it is built.

```java
    final Injector injector = ...;
    
    // Returns a stream of every injectable instance that 
    // implements the 'HasName' interface.
    Stream<HasName> stream = injector.stream(HasName.class); 
    
    // Returns one instance that implements the 'HasName'-interface.
    HasName inst = injector.getOrThrow(HasName.class);
    
    // Check if any implementation of an interface exists.
    boolean hasInst = injector.get(HasName.class).isPresent();
    
    // Set every @Inject and @Config member in an instance
    final FooComponent customFoo = new FooComponent();
    injector.inject(customFoo);
    
    // Invoke the STOPPED cycle in every created instance.
    injector.stop();
```

## Download
To use `function` in your own projects, include the following dependency in your `pom.xml`-file.
```xml
<dependency>
    <groupId>com.speedment.common</groupId>
    <artifactId>injector</artifactId>
    <version>3.1.17</version>
</dependency>
```

## License
This project is available under the [Apache 2 license](http://www.apache.org/licenses/LICENSE-2.0). 
