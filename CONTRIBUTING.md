Contributing to Speedment
=========================

Contributor License Agreement
-----------------------------

In order to protect your rights to your contributions and in order to protect the users of Speedment, you need
to sign a Contributor License Agreement with Speedment, Inc. before we can accept your contributions.

Please review and <a href="https://www.clahub.com/agreements/speedment/speedment">sign the Contributor License Agreement here</a>

(Please ignore the fields in the actual agreement text and fill in your data at the bottom of the form instead)

When you have signed the CLA, your name will be added to the project's ```<contributors>...</contributors>``` tags :-)

Code Style
----------

Please take a moment and check the existing code style before writing your own 
contributions. This will make it much easier to review and accept your pull
requests. Some of the main code style features includes:

* Break up stream commands on separate lines:
```java
    list.stream()
        .filter(...)
        .map(...)
        .collect(...)
```

* Make your variables ```final``` when possible
* Keep methods short
* Keep classes short (yeah, we failed on this on some occasions)
* Use Java 8's features like lambdas, ```Function```, ```Supplier```, etc
* Break up method parameters on separate lines if you have more than three parameters
* Never return ```null``` values, use ```Optional``` or empty ```Collections``` instead
* Favor composition over inheritance
* Write JavaDoc to your methods and classes
* Add Unit Tests to your code
