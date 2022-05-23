//
//  AppDelegate.swift
//  AccountManagementUI
//
//  Created by mithat samet kaskara on 21.05.2022.
//

import UIKit

@main
class AppDelegate: UIResponder, UIApplicationDelegate {
    var window: UIWindow?

    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        // Override point for customization after application launch.
        window = UIWindow(frame: UIScreen.main.bounds)
        window?.rootViewController = UINavigationController(rootViewController: AccountListViewController())
        window?.makeKeyAndVisible()
        return true
    }

}

