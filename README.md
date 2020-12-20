# LDAP Authentication Service
## Description
This repository contains the implementation of a microservice which offers a REST API for LDAP authentication.

## API

### Authentication

#### Example request

In order to perform an authentication use the following HTTP**S** GET request:

```http
GET https://localhost/?username=<username>&password=<password>
```

- Note that this service enforces (works only with) HTTP**S**.

#### Response

##### Response Code

The response status code is 200 (OK) if the authentication succeed, otherwise 401 (Unauthorized).

##### Response Body

No additional response body is generated. 

## Running, Dockerizing and Testing

Compiling, testing, dockerizing and running is done with [sbt](https://www.scala-sbt.org/).

### Prerequisite

In order to successfully run this service, the following environment variables need to be set:

- LDAP_SERVER_URL
- LDAP_SEARCH_BASE
- KEYSTORE_FULL_NAME
- KEYSTORE_PASSWORD

### Running

After setting the necessary environment variables, the LDAP authentication service can be start with the following sbt command in the project's root directory **ldap-authentication-service/**:

```
sbt run
```

The following output should appear:

```powershell
ldap-authentication-service> sbt run
// some logs ...
Server online at https://0.0.0.0:443/
```

### Dockerizing

#### Building an image

With the following sbt command a docker image for this service can be build (without the need for a custom Dockerfile):

```
sbt docker:publishLocal
```

The following output should appear:

```powershell
ldap-authentication-service>sbt docker:publishLocal
// some logs ...
[info] Total reclaimed space: 51.71MB
[info] Built image ldap-authentication-service with tags [0.1.0-SNAPSHOT]
[success] Total time: 19 s, completed 13.12.2020, 16:48:35

ldap-authentication-service>docker images
REPOSITORY                    TAG              IMAGE ID       CREATED              SIZE
ldap-authentication-service   0.1.0-SNAPSHOT   616bcbf3aa63   About a minute ago   512MB
```

#### Running the container

Firstly the environment variables needs to be set properly. After that the docker container can be run for instance as follows:

```powershell
ldap-authentication-service>docker run -it -p 443:443 --env LDAP_SERVER_URL --env LDAP_SEARCH_BASE --env KEYSTORE_FULL_NAME --env KEYSTORE_PASSWORD -v "/c/keystore":"/root/cert" 616
```

**Remarks**

- While running the service within a docker container, the key store location on the host system needs to be mounted  to the container.
  - In the case of a Window host system the path need to be formed into a Unix path.
- In this example the key store is mounted to `/root/cert` but it could also be any other location.
- The 616 is the image ID. It will **differ** on your machine after running `sbt docker:publishLocal`. Make sure you use the appropriate one.

Output:

```powershell
[ldap-authentication-service-actor-system-akka.actor.default-dispatcher-5] INFO akka.event.slf4j.Slf4jLogger - Slf4jLogger started
Server online at https://0.0.0.0:443/
```

### Testing

#### Running the tests

In order to run the unit tests use the following command in the project's root directory **ldap-authentication-service/**:

```
sbt test
```

**Example call**

```powershell
ldap-authentication-service> sbt test
// some logs ...
[info] Tests: succeeded 15, failed 0, canceled 0, ignored 0, pending 0
[info] All tests passed.
[success] Total time: 13 s, completed 13.12.2020, 17:06:24
```
## Links
- [Ready to run docker container](https://hub.docker.com/repository/docker/dawidwalczak/ldap-authentication-service)
- [Me at LinkedIn](https://www.linkedin.com/in/dawid-l-8115141a2)