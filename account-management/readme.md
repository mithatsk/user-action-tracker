# How to test

Save the below json to a file and import it to Postman. This represents a collection of http requests in json which can be imported into Postman.
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
					"raw": "{\n    \"accountName\": \"account22\"\n}",
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
				"header": []
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
					"raw": "{\n    \"accountNumber\": \"3c396282-f29e-43bc-bd48-8a61eaf7cee4\"\n}",
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
			"protocolProfileBehavior": {
				"disableBodyPruning": true
			},
			"request": {
				"method": "GET",
				"header": [],
				"body": {
					"mode": "raw",
					"raw": "{\n    \"accountNumber\": \"5877638c-0db1-45a8-88da-4ffd3573b1f2\",\n    \"amount\": 10.0\n}",
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
				"method": "GET",
				"header": []
			},
			"response": []
		},
		{
			"name": "change name",
			"request": {
				"method": "GET",
				"header": []
			},
			"response": []
		},
		{
			"name": "account",
			"request": {
				"method": "GET",
				"header": []
			},
			"response": []
		},
		{
			"name": "name",
			"request": {
				"method": "GET",
				"header": []
			},
			"response": []
		}
	]
}
```