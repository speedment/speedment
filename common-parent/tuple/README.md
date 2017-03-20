Tuples allow dynamic type safe classes
======================================
Tuples are like a `List` but where each element can be stored with its type.

Tuples are immutable and comes in two flavors; Tuples without `null` values and Tuples that allows `null` values.

## Tuples without null values

Here is an example:
``` java 
  Tuple2<String, Integer> tuple2 = Tuples.of("Alice", 2);
  System.out.format("%s is %d years old", tuple2.get0(), tuple.get1());
```

Here is another example:
``` java
   Tuple3<Integer, String, Float> tuple3 = Tuples.of(1, "Alice", 1023.0);
   
   int id = tuple3.get0();
   String name = tuple3.get1();
   fload saldo = tuple3.get2();
```


## Tuples that allows null values
If we need to be able to handle `null' values, there is a variant of the Tuple that does this:
``` java
  TupleOfNullable2<String, String> tuple2 = Tuples.ofNullable("Alice", null);
  Optional<String> name = tuple2.get0();
  Optional<String> alias = tuple2.get1();
```

All tuples (e.g. Tuple2, Tuple3 and TupleOfNullables2) support the following methods:

| Method      | Parameter  | Outcome
| :---------- | :--------- | :--------
| `length`    | -          | Returns the number of elements in the Tuple. E.g. Tuple2::length will return 2 whereas Tuple3::length will return 3 
| `get`       | `int`      | Returns the element at the given position
| `stream'    | `Class`    | Returns a `Stream` of all non-null elements of the given class


Tuples without null values support the following methods:
| Method      | Parameter  | Outcome
| :---------- | :--------- | :--------
| `stream`    | -          | Returns a `Stream` of all elements in the Tuple

TupleOfNullables (allowing null values) support the following methods:
| Method      | Parameter  | Outcome
| :---------- | :--------- | :--------
| `stream`    | -          | Returns a `Stream` of `Optional`s with elements in the TupleOfNullables. Null values are `Optional.empty()`



