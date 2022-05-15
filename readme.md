# How to test

Save the below json to a file and name it "account-management.postman_collection.json" then import it to Postman. This represents a collection of http requests in json which can be imported into Postman.

First call the create (account/new) then call list request and use the returned accountNumber value in other requests since they take accountNumber as a body parameter.

[Link to download Postman](https://www.postman.com/downloads/)

```json
{
	"info": {
		"_postman_id": "7a242b1d-562f-448b-8e95-2e3eafa00f18",
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
					"raw": "{\n    \"accountName\": \"saving account\"\n}",
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
			"name": "replicateActions",
			"request": {
				"method": "POST",
				"header": [],
				"body": {
					"mode": "raw",
					"raw": "",
					"options": {
						"raw": {
							"language": "json"
						}
					}
				},
				"url": {
					"raw": "http://localhost:8080/bot/replicateActions",
					"protocol": "http",
					"host": [
						"localhost"
					],
					"port": "8080",
					"path": [
						"bot",
						"replicateActions"
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
					"raw": "{\n    \"accountNumber\": \"2d532dd3-5d61-4265-bb1d-1bd2240b6dfd\"\n}",
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
					"raw": "{\n    \"accountNumber\": \"f9f275ef-a359-448d-a4ed-e4bb8be524ea\",\n    \"amount\": 2000.0\n}",
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
					"raw": "{\n    \"accountNumber\": \"1a343799-bcee-4dbb-a627-b8545d73e1b2\",\n    \"amount\": 1054.0\n}",
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
					"raw": "{\n    \"accountNumber\": \"1a343799-bcee-4dbb-a627-b8545d73e1b2\",\n    \"newName\": \"debit account\"\n}",
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
					"raw": "{\n    \"accountNumber\": \"1a343799-bcee-4dbb-a627-b8545d73e1b2\"\n}",
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
					"raw": "{\n    \"accountNumber\": \"1a343799-bcee-4dbb-a627-b8545d73e1b2\"\n}",
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
