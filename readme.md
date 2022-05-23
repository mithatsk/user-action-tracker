# User Action Tracker

We want to build a bot that can replicate actions taken by user. To do this we have built a basic class that can create an account, deposit, withdraw, update name and other functions. So user can perform one of these actions. We created a REST API to enable interaction with `AccountService.java` class. We also built an UI application.

We are logging each action taken by user in AccountService.java class and store them in `actions.log` file. We used AOP to track the Service class and stored the information using a logger that we have created.

Within the BOT we read the stored information to create the class and call the methods. We invoke the methods and intialize the object using java reflection.

Followings are the technologies that we have used:

1. Java with Spring Boot for backend. This includes:
	- `AccountService.java` that we want to track and a controller for endpoints.
	- Tracking Layer `LoggingAspect.java`
	- Logger `ASELogger`
	- Bot `ASEBot`
	- Unit and Integration Tests
2. Swift for iOS (UI) application
3. Yaml for creating a CI workflow. We defined 3 jobs in the workflow
	- Linter -> Build -> Run Tests - Generate Test Report

For details, checkout the [wiki page](https://github.com/mithatsk/user-action-tracker/wiki) on this repository.

# How to test

Download `account-management.postman_collection.json` then import it to Postman. This represents a collection of http requests in json which can be imported into Postman.

First call the create (account/new) then call other methods using the returned accountNumber value.

[Link to download Postman](https://www.postman.com/downloads/)

Another option is to run our iOS application which provides as user interface for interacting with our main codebase. The UI was not a requirement for this project but we have built it to prevent non-systematic behaviors. This way, a user can not call a method with wrong parameters or wrong order.
