gaeBase [![Build Status](https://drone.io/github.com/jroperto/gaeBase/status.png)](https://drone.io/github.com/jroperto/gaeBase/latest)
============

Google App Engine base Spring project to reduce ramp-up.

## Tools & Technologies

+ [Gradle 2.0](http://www.gradle.org/) (gradle wrapper) 
    + [PMD](http://pmd.sourceforge.net/) 
    + [Checkstyle](http://checkstyle.sourceforge.net/)
    + Exploded war task
+ [Spring MVC](http://projects.spring.io/spring-framework/) 4.0.2.RELEASE
+ [Objectify](https://code.google.com/p/objectify-appengine/) 5.0.3

## Build
    gradlew clean build -PgaeVersion=${_yourGaeAppVersion_}
