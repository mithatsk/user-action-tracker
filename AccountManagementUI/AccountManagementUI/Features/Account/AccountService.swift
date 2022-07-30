//
//  AccountService.swift
//  AccountManagementUI
//
//  Created by mithat samet kaskara on 22.05.2022.
//

import Foundation
import Networking

protocol AccountServiceProtocol {
    func fetchAccount(request: AccountRequest) async -> (Account?, NetworkError?)
    func fetchAccounts() async -> ([Account]?, NetworkError?)
    func fetchBalance(request: AccountRequest) async -> (Double?, NetworkError?)
    func fetchName(request: AccountRequest) async -> (String?, NetworkError?)
    func createAccount(request: NewAccountRequest) async -> (Account?, NetworkError?)
    func updateName(request: ChangeAccountNameRequest) async -> (EmptyModel?, NetworkError?)
    func withdraw(request: AccountAmountRequest) async -> (EmptyModel?, NetworkError?)
    func deposit(request: AccountAmountRequest) async -> (EmptyModel?, NetworkError?)
}

final class AccountService: AccountServiceProtocol {
    private let networkClient: NetworkClientProtocol
    
    init(networkClient: NetworkClientProtocol) {
        self.networkClient = networkClient
    }
    
    func fetchAccount(request: AccountRequest) async -> (Account?, NetworkError?) {
        let parameters = ["accountNumber": request.accountNumber]
        let result = await networkClient.request(endpoint: AccountAPI.fetchAccount(request: request, parameters: parameters), responseType: Account.self)
        switch result {
        case let .success(data):
            return (data, nil)
        case let .failure(error):
            return (nil, error)
        }
    }
    
    func fetchAccounts() async -> ([Account]?, NetworkError?) {
        let result = await networkClient.request(endpoint: AccountAPI.fetchAccounts, responseType: [Account].self)
        switch result {
        case let .success(data):
            return (data, nil)
        case let .failure(error):
            return (nil, error)
        }
    }
    
    func fetchBalance(request: AccountRequest) async -> (Double?, NetworkError?) {
        let parameters = ["accountNumber": request.accountNumber]
        let result = await networkClient.request(endpoint: AccountAPI.fetchBalance(request: request, parameters: parameters), responseType: Double.self)
        switch result {
        case let .success(data):
            return (data, nil)
        case let .failure(error):
            return (nil, error)
        }
    }
    
    func fetchName(request: AccountRequest) async -> (String?, NetworkError?) {
        let parameters = ["accountNumber": request.accountNumber]
        let result = await networkClient.request(endpoint: AccountAPI.fetchName(request: request, parameters: parameters), responseType: String.self)
        switch result {
        case let .success(data):
            return (data, nil)
        case let .failure(error):
            return (nil, error)
        }
    }
    
    func createAccount(request: NewAccountRequest) async -> (Account?, NetworkError?) {
        let result = await networkClient.request(endpoint: AccountAPI.createAccount(request: request), responseType: Account.self)
        switch result {
        case let .success(data):
            return (data, nil)
        case let .failure(error):
            return (nil, error)
        }
    }
    
    func updateName(request: ChangeAccountNameRequest) async -> (EmptyModel?, NetworkError?) {
        let result = await networkClient.request(endpoint: AccountAPI.updateName(request: request), responseType: EmptyModel.self)
        switch result {
        case let .success(data):
            return (data, nil)
        case let .failure(error):
            return (nil, error)
        }
    }
    
    func withdraw(request: AccountAmountRequest) async -> (EmptyModel?, NetworkError?) {
        let result = await networkClient.request(endpoint: AccountAPI.withdraw(request: request), responseType: EmptyModel.self)
        switch result {
        case let .success(data):
            return (data, nil)
        case let .failure(error):
            return (nil, error)
        }
    }
    
    func deposit(request: AccountAmountRequest) async -> (EmptyModel?, NetworkError?) {
        let result = await networkClient.request(endpoint: AccountAPI.deposit(request: request), responseType: EmptyModel.self)
        switch result {
        case let .success(data):
            return (data, nil)
        case let .failure(error):
            return (nil, error)
        }
    }
}
