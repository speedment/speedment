Speedment ORM - An accelerated ORM
==================================

About
-----
The Speedment ORM accelerates your JVM applications!

Here is an example of how you could use Speedment from your code:
```java
	Employee employee = EmployeeMgr.getInstance().findById("1001");
	CompanyMap companies = CompanyMgr.getInstance().findByStateAndType("CA", "INC");
```

### Using Maven

Add the following to your project's `pom.xml` to configure Speedment.

	<dependency>
		<groupId>com.speedment</groupId>
		<artifactId>ace-core</artifactId>
		<version>1.0.xx-RELEASE</version>
	</dependency>
