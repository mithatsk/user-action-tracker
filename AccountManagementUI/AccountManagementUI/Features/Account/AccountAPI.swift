//
//  AccountAPI.swift
//  AccountManagementUI
//
//  Created by mithat samet kaskara on 30.07.2022.
//

import Foundation
import Networking

enum AccountAPI {
    case fetchAccount(request: AccountRequest, parameters: [String: String])
    case fetchAccounts
    case fetchBalance(request: AccountRequest, parameters: [String: String])
    case fetchName(request: AccountRequest, parameters: [String: String])
    case createAccount(request: NewAccountRequest)
    case updateName(request: ChangeAccountNameRequest)
    case withdraw(request: AccountAmountRequest)
    case deposit(request: AccountAmountRequest)
}

extension AccountAPI: Endpoint {
    var host: String {
        return "127.0.0.1"
    }
    
    var port: Int? {
        return 8080
    }
    
    var path: String {
        switch self {
        case .fetchAccount:
            return "/account"
        case .fetchAccounts:
            return "/account/list"
        case .fetchBalance:
            return "/account/balance"
        case .fetchName:
            return "/account/name"
        case .createAccount:
            return "/account/new"
        case .updateName:
            return "/account/changeName"
        case .withdraw:
            return "/account/withdraw"
        case .deposit:
            return "/account/deposit"
        }
    }
    
    var scheme: String {
        "http"
    }
    
    var method: HTTPMethod {
        switch self {
        case .fetchAccount, .fetchAccounts, .fetchBalance, .fetchName:
            return .get
        case .createAccount:
            return .post
        case .updateName, .withdraw, .deposit:
            return .put
        }
    }
    
    var queryParameters: [String: String] {
        switch self {
        case let .fetchAccount(_, parameters), let .fetchBalance(_, parameters), let .fetchName(_, parameters):
            return parameters
        default:
            return [:]
        }
    }
    
    var urlRequest: URLRequest? {
        switch self {
        case let .fetchAccount(request, _), let .fetchBalance(request, _), let .fetchName(request, _):
            return urlRequest(requestBody: request)
        case .fetchAccounts:
            return urlRequest(requestBody: EmptyModel())
        case let .createAccount(request):
            return urlRequest(requestBody: request)
        case let .updateName(request):
            return urlRequest(requestBody: request)
        case let .withdraw(request), let .deposit(request):
            return urlRequest(requestBody: request)
        }
    }
}
