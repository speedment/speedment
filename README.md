Speedment ORM - An accelerated ORM
==================================

About
-----
The Speedment ORM accelerates your JVM applications!

Here is an example of how you could use Speedment from your code:
```java
	Employee employee = EmployeeDao.getInstance().findById("1001");

	List<Company> companies = CompanyDao.getInstance()
                                      .streamByStateAndType("CA", "INC")
                                      .filter(Company::isLarge)
                                      .limit(20)
                                      .sort()
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

N.B. Speedment is not yet on Maven Central Repository so you can't use it in your pom yet...
