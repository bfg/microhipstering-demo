# Microhipstering demo

This repository contains code supporting [Microhipstering with Micronaut](https://slides.com/gracnar/vert-x/)
presentation

# Building

**PREREQUISITES**:

* JDK12 (yup, no kidding, even though code compiles to bytecode version 8)

```
./gradlew clean build
```

If you also want to build docker image:

```
./gradlew micronaut-service:docker
```

# Running

## development server

```
MICRONAUT_ENVIRONMENTS=dev ./gradlew  micronaut-service:run -qt
```

## built artifact

```
./run-service-jar serial micronaut-service/build/libs/micronaut-service-1.0.0-SNAPSHOT-all.jar 
```

# Performance

Tests were executed on Intel `i7-8550U` with JDK12 using command:

```
ab -n500000 -k -c100 http://localhost:8080/foo/bar
```

| GC | peak rps | rss mem |
|----|----------|---------|
| g1                   | 68069 | 359M |
| serial               | 74052 | 304M |
| pgc                  | 74871 | 326M |
| cms                  | 72741 | 342M |
| shenandoah (compact) | 30515 | 309M |
| shenandoah           | 63114 | 504M |
| zgc                  | 46986 | 840M |

See [Oracle available GC document for details](https://docs.oracle.com/en/java/javase/12/gctuning/available-collectors.html).


NOTE: comparison with dummy [spring-boot](spring-boot-service/) with netty based server:

| GC | peak rps | rss mem |
|----|----------|---------|
| serial               | 17820 | 398M |

