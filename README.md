# jwt-service

# Test a web service to generate a JWT using curl

curl --location 'localhost:2020/ws-jwt/tokens' \
--header 'Content-Type: application/json' \
--data '{
    "sub":"toto"
}'
