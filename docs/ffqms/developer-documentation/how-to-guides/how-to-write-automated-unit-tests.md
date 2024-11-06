# How to write automated unit tests

#### We are trying to cover all functionality with unit tests.

The approach is not specific to our project so please refer the available general documentation and tutorials about testing of Spring Boot applications.

The following resources may be helpful:

* [Spring Boot Testing](https://docs.spring.io/spring-framework/reference/testing/introduction.html)
* [Mockito](https://javadoc.io/static/org.mockito/mockito-core/5.13.0/org/mockito/Mockito.html)

#### Tips & Guidelines

#### Mock all dependencies

Unit tests for a class should usually cover that "unit" of code, i.e. your implementation of that class but not of all the referenced other services.

Try to "mock" all services that your implementation depends on.
