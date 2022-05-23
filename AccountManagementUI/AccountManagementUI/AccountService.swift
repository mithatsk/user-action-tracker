//
//  AccountService.swift
//  AccountManagementUI
//
//  Created by mithat samet kaskara on 22.05.2022.
//

import Foundation

protocol AccountServiceProtocol {
    func fetchAccounts(completion: (([Account]?, NetworkError?) -> Void)?)
    func fetchAccount(request: AccountRequest, completion: ((Account?, NetworkError?) -> Void)?)
    func fetchBalance(request: AccountRequest, completion: ((Double?, NetworkError?) -> Void)?)
    func fetchName(request: AccountRequest, completion: ((String?, NetworkError?) -> Void)?)
    func createAccount(request: NewAccountRequest, completion: ((Account?, NetworkError?) -> Void)?)
    func updateName(request: ChangeAccountNameRequest, completion: ((EmptyModel?, NetworkError?) -> Void)?)
    func withdraw(request: AccountAmountRequest, completion: ((EmptyModel?, NetworkError?) -> Void)?)
    func deposit(request: AccountAmountRequest, completion: ((EmptyModel?, NetworkError?) -> Void)?)
}

final class AccountService: AccountServiceProtocol {
    func fetchAccounts(completion: (([Account]?, NetworkError?) -> Void)?) {
        BaseService.shared.get(endpoint: "account/list", requestBody: EmptyModel(), responseType: [Account].self) { result in
            switch result {
            case let .success(data):
                completion?(data, nil)
            case let .failure(error):
                completion?(nil, error)
            }
        }
    }
    
    func fetchAccount(request: AccountRequest, completion: ((Account?, NetworkError?) -> Void)?) {
        let parameters = ["accountNumber": request.accountNumber]
        BaseService.shared.get(endpoint: "account", requestBody: request, responseType: Account.self, parameters: parameters) { result in
            switch result {
            case let .success(data):
                completion?(data, nil)
            case let .failure(error):
                completion?(nil, error)
            }
        }
    }
    
    func fetchBalance(request: AccountRequest, completion: ((Double?, NetworkError?) -> Void)?) {
        let parameters = ["accountNumber": request.accountNumber]
        BaseService.shared.get(endpoint: "account/balance", requestBody: request, responseType: Double.self, parameters: parameters) { result in
            switch result {
            case let .success(data):
                completion?(data, nil)
            case let .failure(error):
                completion?(nil, error)
            }
        }
    }
    
    func fetchName(request: AccountRequest, completion: ((String?, NetworkError?) -> Void)?) {
        let parameters = ["accountNumber": request.accountNumber]
        BaseService.shared.get(endpoint: "account/name", requestBody: request, responseType: String.self, parameters: parameters) { result in
            switch result {
            case let .success(data):
                completion?(data, nil)
            case let .failure(error):
                completion?(nil, error)
            }
        }
    }
    
    func createAccount(request: NewAccountRequest, completion: ((Account?, NetworkError?) -> Void)?) {
        BaseService.shared.post(endpoint: "account/new", requestBody: request, responseType: Account.self) { result in
            switch result {
            case let .success(data):
                completion?(data, nil)
            case let .failure(error):
                completion?(nil, error)
            }
        }
    }
    
    func updateName(request: ChangeAccountNameRequest, completion: ((EmptyModel?, NetworkError?) -> Void)?) {
        BaseService.shared.put(endpoint: "account/changeName", requestBody: request, responseType: EmptyModel.self) { result in
            switch result {
            case let .success(data):
                completion?(data, nil)
            case let .failure(error):
                completion?(nil, error)
            }
        }
    }
    
    func withdraw(request: AccountAmountRequest, completion: ((EmptyModel?, NetworkError?) -> Void)?) {
        BaseService.shared.put(endpoint: "account/withdraw", requestBody: request, responseType: EmptyModel.self) { result in
            switch result {
            case let .success(data):
                completion?(data, nil)
            case let .failure(error):
                completion?(nil, error)
            }
        }
    }
    
    func deposit(request: AccountAmountRequest, completion: ((EmptyModel?, NetworkError?) -> Void)?) {
        BaseService.shared.put(endpoint: "account/deposit", requestBody: request, responseType: EmptyModel.self) { result in
            switch result {
            case let .success(data):
                completion?(data, nil)
            case let .failure(error):
                completion?(nil, error)
            }
        }
    }
}
