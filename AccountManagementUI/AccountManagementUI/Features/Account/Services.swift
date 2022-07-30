//
//  Services.swift
//  AccountManagementUI
//
//  Created by mithat samet kaskara on 30.07.2022.
//

import Foundation
import Networking

enum Services {
    static let validStatusCodeRange = 200..<300
    static var networkClient = NetworkClient(validStatusCodeRange: validStatusCodeRange)
    
    static let accountService = AccountService(networkClient: networkClient)
}
