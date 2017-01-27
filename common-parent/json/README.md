## JSON Parser
A lightweight JSON encoder and decoding library without any external dependencies.

### Encoding
```java
Map<String, Object> data = new HashMap<>();
data.put("foo", "string");
data.put("bar", 123);
data.put("baz", new HashMap<>());

String json = Json.toJson(data);
```

This will return the following string:
```json
{
  "foo" : "string",
  "bar" : 123,
  "baz" : {}
}
```

### Decoding
```java
Map<String, Integer> obj = (Map<String, Integer>) Json.fromJson("{\"number\":20}");
List<String> list = (List<String>) Json.fromJson("[\"a\", \"b\", \"c\"]");
```

### Download
```xml
<dependency>
    <groupId>com.speedment.common</groupId>
    <artifactId>json</artifactId>
    <version>1.0.2</version>
</dependency>
```
