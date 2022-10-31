## printStatements example :
```shell
curl --location --request GET 'http://localhost:8080/bank-management/account/1' \
--header 'Authorization: Basic dXNlcjoxMjM='
```

## doOperation example :
```shell
curl --location --request PUT 'http://localhost:8080/bank-management/account/1?amount=10000&operation=deposit' \
--header 'Authorization: Basic dXNlcjoxMjM='
```
