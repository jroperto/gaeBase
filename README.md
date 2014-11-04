gaeBase [![Build Status](https://drone.io/github.com/jroperto/gaeBase/status.png)](https://drone.io/github.com/jroperto/gaeBase/latest)
============

Google App Engine base Spring project to reduce ramp-up.

## Tools & Technologies

+ [Gradle 2.1](http://www.gradle.org/) (gradle wrapper)
    + [PMD](http://pmd.sourceforge.net/) 
    + [Checkstyle](http://checkstyle.sourceforge.net/)
    + Exploded war task
+ [GAE](https://cloud.google.com/appengine/docs/java/) 1.9.14
+ [Spring MVC](http://projects.spring.io/spring-framework/) 4.1.1.RELEASE
+ [Objectify](https://code.google.com/p/objectify-appengine/) 5.1.1

## Build
    gradlew clean build -PgaeVersion=${_yourGaeAppVersion_}
