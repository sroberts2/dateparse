# dateparse
A simple application to parse dates. This application uses the [Spring Boot](http://projects.spring.io/spring-boot/) Java framework.

## Requirements
To run the Application locally you'll need the following installed on your machine:

- [Java 21](https://www.oracle.com/java/technologies/downloads/#java21)
- [Maven](https://maven.apache.org/download.cgi)

## Testing
This application contains a single static Class called `DateUtil` this class holds a single public method called `parse` which is designed to be used as a utility class. This should be called in the following manner `DateUtil.parse("2024-07-15T12:00:00Z-10m");`. The input parameter is broken up as follows:

+ A date string in the following format `yyyy-MM-ddThh-mm-ssZ`
+ Zero or more operations which can contain:
    - A prefix with either `+` = add time, `-` minus time or `@` to snap to the nearest specified unit.
    - A value in the form of an Integer
    - One of the following for a time digit `s` = second, `m` = minute, `h` = hour, `d` = day, `mon` = month

Example calls are:
+ `DateUtil.parse("2024-07-15T12:00:00Z-10m");`
+ `DateUtil.parse("2024-07-15T12:00:00Z+10s");`
+ `DateUtil.parse("2024-07-15T12:00:00Z+10m+10mon");`
+ `DateUtil.parse("2024-07-15T12:00:00Z+10h-3d@mon");`
