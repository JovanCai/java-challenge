### How to use this spring-boot project

- Install packages with `mvn package`
- Run `mvn spring-boot:run` for starting the application (or use your IDE)
- Login with
  ```
  name: admin
  password: admin
  ```

### Done

- [x] Rearranged codes with ddd
- [X] Added frontend pages
- [x] Added tests
- [x] Changed syntax and annotations
- [x] Added login page to protect employee controller
- [x] Added caching logic for database calls
- [x] Manage branches with structure below
  ```
  main
  └───release
      ├───feature/aaa
      └───feature/bbb
  ```
- [x] Added GitHub action to build and test every time push or added new pull-request

### The project can be improved by:

- We can consider change database type depending on the requirement.
- Use @ControllerAdvice and @ExceptionHandler to separate and handle error properly.
- we can set release branch’s name such as “release_20230210” if we know the sprint duration.

#### Your experience in Java

- I have 2 years experience in Kotlin and Java.
- I've experienced several projects mainly focused on backend development using Spring-boot.
