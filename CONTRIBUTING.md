Contributing to Speedment
=========================

Contributor License Agreement
-----------------------------

In order to protect your rights to your contributions and in order to protect the users of Speedment, you need
to sign a Contributor License Agreement with Speedment, Inc. before we can accept your contributions.

Please review and <a href="https://www.clahub.com/agreements/speedment/speedment">sign the Contributor License Agreement here</a>

(Please ignore the fields in the actual agreement text and fill in your data at the bottom of the form instead)

When you have signed the CLA, your name will be added to the project's ```<contributors>...</contributors>``` tags :-)


Creating a Pull Request
-----------------------------
Make sure that you are creating a **pull request against the 'develop' branch**. This makes it much easier for us to merge the pull request in the proper way (since all new stuff is funneled through the 'develop' branch). It also enables your name as a contributor to be retained for the changes files, which is nice.


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

* Keep methods short
* Keep classes short (yeah, we failed on this on some occasions)
* Use Java 8's features like lambdas, ```Function```, ```Supplier```, etc
* Break up method parameters on separate lines if you have more than three parameters
* Never return ```null``` values, use ```Optional``` or empty ```Collections``` instead
* Favor composition over inheritance
* Write JavaDoc to your methods and classes
* Add Unit Tests to your code
* Make your variables ```final``` when possible
