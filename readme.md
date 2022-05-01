# How to test

Save the below json to a file and name it "account-management.postman_collection.json" then import it to Postman. This represents a collection of http requests in json which can be imported into Postman.

First call the create (account/new) then call list request and use the returned accountNumber value in other requests since they take accountNumber as a body parameter.

[Link to download Postman](https://www.postman.com/downloads/)

```json
{
  "info": {
    "_postman_id": "159630ff-6695-44c1-abba-d9ab599b0507",
    "name": "account-management",
    "schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json"
  },
  "item": [
    {
      "name": "create",
      "request": {
        "method": "POST",
        "header": [],
        "body": {
          "mode": "raw",
          "raw": "{\n    \"accountName\": \"account\"\n}",
          "options": {
            "raw": {
              "language": "json"
            }
          }
        },
        "url": {
          "raw": "http://localhost:8080/account/new",
          "protocol": "http",
          "host": [
            "localhost"
          ],
          "port": "8080",
          "path": [
            "account",
            "new"
          ]
        }
      },
      "response": []
    },
    {
      "name": "list",
      "request": {
        "method": "GET",
        "header": [],
        "url": {
          "raw": "http://localhost:8080/account/list",
          "protocol": "http",
          "host": [
            "localhost"
          ],
          "port": "8080",
          "path": [
            "account",
            "list"
          ]
        }
      },
      "response": []
    },
    {
      "name": "balance",
      "protocolProfileBehavior": {
        "disableBodyPruning": true
      },
      "request": {
        "method": "GET",
        "header": [],
        "body": {
          "mode": "raw",
          "raw": "{\n    \"accountNumber\": \"f7f5d6a9-4724-4e1f-9380-d148f8586ceb\"\n}",
          "options": {
            "raw": {
              "language": "json"
            }
          }
        },
        "url": {
          "raw": "http://localhost:8080/account/balance",
          "protocol": "http",
          "host": [
            "localhost"
          ],
          "port": "8080",
          "path": [
            "account",
            "balance"
          ]
        }
      },
      "response": []
    },
    {
      "name": "deposit",
      "request": {
        "method": "PUT",
        "header": [],
        "body": {
          "mode": "raw",
          "raw": "{\n    \"accountNumber\": \"f7f5d6a9-4724-4e1f-9380-d148f8586ceb\",\n    \"amount\": 54.0\n}",
          "options": {
            "raw": {
              "language": "json"
            }
          }
        },
        "url": {
          "raw": "http://localhost:8080/account/deposit",
          "protocol": "http",
          "host": [
            "localhost"
          ],
          "port": "8080",
          "path": [
            "account",
            "deposit"
          ]
        }
      },
      "response": []
    },
    {
      "name": "withdraw",
      "request": {
        "method": "PUT",
        "header": [],
        "body": {
          "mode": "raw",
          "raw": "{\n    \"accountNumber\": \"f7f5d6a9-4724-4e1f-9380-d148f8586ceb\",\n    \"amount\": 10.0\n}",
          "options": {
            "raw": {
              "language": "json"
            }
          }
        },
        "url": {
          "raw": "http://localhost:8080/account/withdraw",
          "protocol": "http",
          "host": [
            "localhost"
          ],
          "port": "8080",
          "path": [
            "account",
            "withdraw"
          ]
        }
      },
      "response": []
    },
    {
      "name": "change name",
      "request": {
        "method": "PUT",
        "header": [],
        "body": {
          "mode": "raw",
          "raw": "{\n    \"accountNumber\": \"f7f5d6a9-4724-4e1f-9380-d148f8586ceb\",\n    \"newName\": \"account new name\"\n}",
          "options": {
            "raw": {
              "language": "json"
            }
          }
        },
        "url": {
          "raw": "http://localhost:8080/account/changeName",
          "protocol": "http",
          "host": [
            "localhost"
          ],
          "port": "8080",
          "path": [
            "account",
            "changeName"
          ]
        }
      },
      "response": []
    },
    {
      "name": "account",
      "protocolProfileBehavior": {
        "disableBodyPruning": true
      },
      "request": {
        "method": "GET",
        "header": [],
        "body": {
          "mode": "raw",
          "raw": "{\n    \"accountNumber\": \"f7f5d6a9-4724-4e1f-9380-d148f8586ceb\"\n}",
          "options": {
            "raw": {
              "language": "json"
            }
          }
        },
        "url": {
          "raw": "http://localhost:8080/account",
          "protocol": "http",
          "host": [
            "localhost"
          ],
          "port": "8080",
          "path": [
            "account"
          ]
        }
      },
      "response": []
    },
    {
      "name": "name",
      "protocolProfileBehavior": {
        "disableBodyPruning": true
      },
      "request": {
        "method": "GET",
        "header": [],
        "body": {
          "mode": "raw",
          "raw": "{\n    \"accountNumber\": \"f7f5d6a9-4724-4e1f-9380-d148f8586ceb\"\n}",
          "options": {
            "raw": {
              "language": "json"
            }
          }
        },
        "url": {
          "raw": "http://localhost:8080/account/name",
          "protocol": "http",
          "host": [
            "localhost"
          ],
          "port": "8080",
          "path": [
            "account",
            "name"
          ]
        }
      },
      "response": []
    }
  ]
}
```