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
    
    private let accountService: AccountServiceProtocol
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
    
    init(accountService: AccountServiceProtocol) {
        self.accountService = accountService
        super.init(nibName: nil, bundle: nil)
    }
    
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
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
        Task(priority: .background) { [weak self] in
            guard let self = self else { return }
            let (accounts, error) = await self.accountService.fetchAccounts()
            guard error == nil else { return }
            if let accounts = accounts {
                self.accounts = accounts
            }
        }
    }
    
    private func createAccount() {
        let accountName = accountNameTextField.text ?? "Saving Account"
        Task(priority: .background) { [weak self] in
            guard let self = self else { return }
            let (account, error) = await self.accountService.createAccount(request: .init(accountName: accountName))
            guard error == nil else { return }
            if account != nil {
                self.fetchAccounts()
            }
        }
    }
}

extension AccountListViewController: UITableViewDelegate {
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        let accountNumber = accounts[indexPath.row].accountNumber
        Task(priority: .background) { [weak self] in
            guard let self = self else { return }
            let (account, error) = await self.accountService.fetchAccount(request: .init(accountNumber: accountNumber))
            guard error == nil, let account = account else { return }
            let viewController = AccountViewController(account: account, accountService: self.accountService)
            viewController.delegate = self
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

