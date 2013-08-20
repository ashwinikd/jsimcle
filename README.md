# Java Simple In-Memory Caching Library

## Introduction
jSimcl *(pronounced j-simcle (as in simple))* is a minimal in memory caching utility written for Java. It provides fast and reliable chache store for multithreaded concurrent systems.

## Features
* Fast in memory cache
* Ephemeral store. Your data will be gone once JVM shuts down.
* Support for TTL on keys.

## Installation

### Requirements
* Java 6
* Maven 2

### Install
Clone the repository using:

`git clone https://github.com/ashwinikd/jsimcle`

or download the zip file. Then run `mvn clean install -DskipTests -DperformRelease`

If you are using maven in your project then use following dependency:

```xml
<dependency>
  <groupId>com.ashwinikd.</groupId>
  <artifactId>jsincle</artifactId>
  <version>0.1</version>
</dependency>
```

Or add the jar in target directory as external library.

## Usage

```java
//Get cache manager
CacheManager cache = CacheManager.getInstance();
//Add key with infinite TTL
cache.put("foo", "i will not expire");
//Add key which expires in 1 minute
cache.put("bar", "i will expire in 60 seconds", 60);
//Delete a key
cache.clear("foo");
//Clear all cache data;
cache.clear();
```