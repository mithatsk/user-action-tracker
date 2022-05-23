//
//  AccountListViewController.swift
//  AccountManagementUI
//
//  Created by mithat samet kaskara on 21.05.2022.
//

import UIKit

final class AccountListViewController: UIViewController {
    struct Constants {
        static let padding: CGFloat = 20
        static let buttonSize: CGFloat = 60
        static let cellIdentifier = "cell"
    }
    
    private let accountService = AccountService()
    private var accounts = [Account]() {
        didSet {
            DispatchQueue.main.async {
                self.tableView.reloadData()
            }
        }
    }
    
    private lazy var tableView: UITableView = {
        let tableView = UITableView()
        tableView.register(UITableViewCell.self, forCellReuseIdentifier: Constants.cellIdentifier)
        tableView.delegate = self
        tableView.dataSource = self
        tableView.translatesAutoresizingMaskIntoConstraints = false
        return tableView
    }()
    
    private let createAccountButton: UIButton = {
        let button = UIButton()
        button.setBackgroundImage(.add, for: .normal)
        button.layer.cornerRadius = Constants.buttonSize / 2
        button.clipsToBounds = true
        button.addTarget(self, action: #selector(createAccountButtonTapped), for: .touchUpInside)
        button.translatesAutoresizingMaskIntoConstraints = false
        return button
    }()
    
    private let createAccountStackView: UIStackView = {
        let stackview = UIStackView()
        stackview.axis = .horizontal
        stackview.spacing = Constants.padding
        stackview.alignment = .leading
        stackview.translatesAutoresizingMaskIntoConstraints = false
        return stackview
    }()
    
    private let accountNameTextField: UITextField = {
        let textField = UITextField()
        textField.borderStyle = .roundedRect
        textField.backgroundColor = .secondarySystemGroupedBackground
        textField.placeholder = "Account Name"
        textField.translatesAutoresizingMaskIntoConstraints = false
        return textField
    }()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        title = "Account List"
        setup()
        fetchAccounts()
    }
    
    private func setup() {
        view.addSubview(tableView)
        view.addSubview(createAccountStackView)
        createAccountStackView.addArrangedSubview(accountNameTextField)
        createAccountStackView.addArrangedSubview(createAccountButton)
        
        tableView.leadingAnchor.constraint(equalTo: view.leadingAnchor).isActive = true
        tableView.trailingAnchor.constraint(equalTo: view.trailingAnchor).isActive = true
        tableView.topAnchor.constraint(equalTo: view.topAnchor, constant: Constants.padding).isActive = true
        tableView.bottomAnchor.constraint(equalTo: createAccountStackView.topAnchor).isActive = true
        
        createAccountStackView.leadingAnchor.constraint(equalTo: view.leadingAnchor, constant: Constants.padding).isActive = true
        createAccountStackView.trailingAnchor.constraint(equalTo: view.trailingAnchor, constant: -Constants.padding).isActive = true
        createAccountStackView.bottomAnchor.constraint(equalTo: view.safeAreaLayoutGuide.bottomAnchor, constant: -Constants.padding).isActive = true
        createAccountStackView.heightAnchor.constraint(equalToConstant: Constants.buttonSize).isActive = true
        
        accountNameTextField.heightAnchor.constraint(equalToConstant: Constants.buttonSize).isActive = true
        
        createAccountButton.heightAnchor.constraint(equalToConstant: Constants.buttonSize).isActive = true
        createAccountButton.widthAnchor.constraint(equalToConstant: Constants.buttonSize).isActive = true
    }
    
    @objc
    private func createAccountButtonTapped() {
        createAccount()
    }
    
    private func fetchAccounts() {
        accountService.fetchAccounts { [weak self] accounts, error in
            guard error == nil else { return }
            guard let self = self else { return }
            if let accounts = accounts {
                self.accounts = accounts
            }
        }
    }
    
    private func createAccount() {
        let accountName = accountNameTextField.text ?? "Saving Account"
        accountService.createAccount(request: .init(accountName: accountName)) { [weak self] account, error in
            guard error == nil else { return }
            guard let self = self else { return }
            if account != nil {
                self.fetchAccounts()
            }
        }
    }
}

extension AccountListViewController: UITableViewDelegate {
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        let accountNumber = accounts[indexPath.row].accountNumber
        let viewController = AccountViewController(account: accounts[indexPath.row])
        viewController.delegate = self
        accountService.fetchAccount(request: .init(accountNumber: accountNumber)) { [weak self] account, error in
            guard error == nil else { return }
            guard let self = self else { return }
            DispatchQueue.main.async {
                self.present(UINavigationController(rootViewController: viewController), animated: true)
            }
        }
    }
}

extension AccountListViewController: UITableViewDataSource {
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        accounts.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: Constants.cellIdentifier)!
        let account = accounts[indexPath.row]
        cell.textLabel?.text = "\(account.accountName) - Balance: \(account.balance)"
        cell.selectionStyle = .none
        return cell
    }
}

extension AccountListViewController: AccountListDelegate {
    func reloadAccounts() {
        fetchAccounts()
    }
}

