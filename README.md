Speedment ORM - An accelerated ORM
==================================

About
-----
The Speedment ORM accelerates your JVM applications and makes programming so easy to do!

Here is an example of how you could use Speedment from your code:
```java
	Employee employee = EmployeeManager().get()
		.stream()
		.filter(e -> e.getId() == 1001)
		.findAny();

	List<Company> companies = CompanyManager().get()
                .stream()
                .filter(c -> c.getState().equals("CA"))
                .filter(c -> c.getType().equals("INC"))
                .filter(Company::isLarge)
                .limit(20)
                .sorted()
                .collect(toList());
```


### Development Status
Speedment is still very early and we are currently moving in large blocks from the existing closed source product. You can not run it now but you can contribute to the code and see how it evolves over time.

### Using Maven

When ready, you can just add the following to your project's `pom.xml` to use Speedment.

```xml
	<dependency>
		<groupId>com.speedment</groupId>
		<artifactId>orm</artifactId>
		<version>0.0.1</version>
	</dependency>
```

N.B. Speedment is not yet on Maven Central Repository so you can't use it in your pom yet unless you manually build and install it.
