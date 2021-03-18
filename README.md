# LDAP Authentication Service
## Repository Description
This repository contains an implementation of a microservice which exposes a **REST API for LDAP authentication**.

## API Description

### BASE URL

The structure of the API's base URL is the following:

```http
https://0.0.0.0:443/
```

**Note**

- The API enforces (works only with) HTTP**S**.

### Performing an authentication

**METHOD** POST

**REQUEST BODY**

The API uses **JSON** as the format of the payload.

```json
{"username": "<here the username>", "password": "<here the password>"}
```

**RETURNS**

- 200 (OK) if the authentication was successful (the passed credentials are valid). **No** response body.
- 401 (Unauthorized) if the authentication was unsuccessful (the passed credentials are invalid). **No** response body.
- 500 (Internal Server Error) if an unexpected error occurred. In such case check the error message in the response body and also the error logged to the stderr. 

## Configuring

The microservice is configured by the following environment variables:

- **LDAP_SERVER_URL** the URL of the LDAP Server to connect with. If the LDAP server supports LDAP**S** just use the `ldaps` schema in the URL. For instance `ldaps://ad.company.com:636`, the exchange will be then encrypted.
- **LDAP_USERNAME_ATTRIBUTE** the attribute within the active directory containing the username. For instance `cn`.
- **LDAP_SEARCH_BASE** the search base. For instance `ou=people,dc=wonderland,dc=in`.
- **KEYSTORE_FULL_NAME** the absolute name of the key store used for HTTPS connection. **It must be a .p12 key store**.
- **KEYSTORE_PASSWORD** the key store's password.

## Compiling, Dockerizing and Running

This is a **sbt** based project. 

#### Compiling

```scala
sbt compile
```

### Running

```scala
sbt run
```

#### Dockerizing 

```scala
sbt docker:publishLocal
```

* This will create a docker image without the need to write a custom Dockerfile.

