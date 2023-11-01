# jwt-service

Generating and Verifying JWT with RSA in Java Spring Boot

## Requirements

The list of tools required to build and run the project:

* Open JDK 17
* Apache Maven 3.9

## Configuration

Configuration can be updated in `application.properties` or using environment variables.

# Test a web service to generate a JWT using curl

----
  curl --location 'localhost:2020/ws-jwt/tokens' \
  --header 'Content-Type: application/json' \
  --data '{
      "sub":"toto"
  }'
----

# Test a web service to verify a JWT using curl

curl --location 'localhost:2020/ws-jwt/tokens/verify' \
--header 'Content-Type: application/json' \
--data '{
    "token":"eyJraWQiOiJiZTdlOTA1Ni0zZGQ1LTRlMmYtOTczMi05YjQzN2FhY2MwMjciLCJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ0b3RvIiwiaXNzIjoiaXNzIiwiYXVkIjoiYXVkIiwianRpIjoiZDljOTUwZjYtZTkwYS00YzJkLWFkMmUtOGJkN2Q1NTZlZmRmIiwiaWF0IjoxNjk4NzQzMjg3LCJleHAiOjE2OTg3NTQwODd9.ZKxctplKsnFYtHnJId3yF6pzQXi1c9f-HJL8TAVjviuFdshsLJbKiMU5XV76doTflDVYsj7vWn5SRw1qDfQUmH_w9ze-LUuRDcM0pXbbZCNLgYJK6mDa6iNI36zsG1WAKbJDH86JJg1QRr-pBF127BCrQhxYQukBfJbQnNMnfBBZSEIDTmfDo2IOaw-yq_gzwmnk3f8AVWsVt34v_6_Ej9nZiVVxhPrV55JoU6pqWKvcvsfnqQYvoWWQpo_P7ELSqvJeLwqOEGWSf9NaInz3IRJdgC8sNSlfmH7MQcIaJ5V0M-m1yxFMQEsU3adJm7zSLJU7ZnpRvrbnm-v0b6NRGQ"
}'

# Create a PKCS#12 (P12) certificate file using OpenSSL

## Step 1: Create a Private Key
openssl genpkey -algorithm RSA -out privKey.pem

## Step 2: Create a Self-Signed Certificate
openssl req -new -key privKey.pem -x509 -days 365 -out certificate.pem

## Step 3: Combine the Private Key and Certificate
openssl pkcs12 -export -out certificate.p12 -inkey privKey.pem -in certificate.pem

## Step 4: Verify the Contents
openssl pkcs12 -info -in certificate.p12


## License

Project is licensed under the [MIT](LICENSE) license.

## Author

Copyright &copy;  2023, Nabil Harakat
