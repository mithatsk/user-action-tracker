//
//  Account.swift
//  AccountManagementUI
//
//  Created by mithat samet kaskara on 22.05.2022.
//

import Foundation

typealias AccountList = [Account]

struct Account: Codable {
    let accountNumber: String
    let accountName: String
    let balance: Double
}

struct AccountAmountRequest: Codable {
    let accountNumber: String
    let amount: Double
}

struct AccountRequest: Codable {
    let accountNumber: String
}

struct ChangeAccountNameRequest: Codable {
    let accountNumber: String
    let newName: String
}

struct NewAccountRequest: Codable {
    let accountName: String
}
