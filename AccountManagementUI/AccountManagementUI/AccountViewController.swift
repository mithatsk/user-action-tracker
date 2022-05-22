//
//  AccountViewController.swift
//  AccountManagementUI
//
//  Created by mithat samet kaskara on 22.05.2022.
//

import UIKit

final class AccountViewController: UIViewController {
    
    struct Constants {
        static let padding: CGFloat = 15
        static let buttonHeight: CGFloat = 60
        static let buttonWidth: CGFloat = 120
    }
    
    private let accountNameTextField: UITextField = {
        let textField = UITextField()
        textField.borderStyle = .roundedRect
        textField.backgroundColor = .secondarySystemGroupedBackground
        textField.placeholder = "Account Name"
        textField.translatesAutoresizingMaskIntoConstraints = false
        return textField
    }()
    
    private let amountTextField: UITextField = {
        let textField = UITextField()
        textField.borderStyle = .roundedRect
        textField.backgroundColor = .secondarySystemGroupedBackground
        textField.placeholder = "amount"
        textField.keyboardType = .decimalPad
        textField.translatesAutoresizingMaskIntoConstraints = false
        return textField
    }()
    
    private let depositButton: UIButton = {
        let button = UIButton()
        button.layer.cornerRadius = Constants.buttonHeight / 4
        button.backgroundColor = .systemGreen
        button.setTitle("Deposit", for: .normal)
        button.addTarget(self, action: #selector(depositButtonTapped), for: .touchUpInside)
        button.translatesAutoresizingMaskIntoConstraints = false
        return button
    }()
    
    private let withdrawButton: UIButton = {
        let button = UIButton()
        button.layer.cornerRadius = Constants.buttonHeight / 4
        button.backgroundColor = .systemGreen
        button.setTitle("Withdraw", for: .normal)
        button.addTarget(self, action: #selector(withdrawButtonTapped), for: .touchUpInside)
        button.translatesAutoresizingMaskIntoConstraints = false
        return button
    }()
    
    private let updateNameButton: UIButton = {
        let button = UIButton()
        button.layer.cornerRadius = Constants.buttonHeight / 4
        button.backgroundColor = .systemGreen
        button.setTitle("Update Name", for: .normal)
        button.addTarget(self, action: #selector(updateAccountNameButtonTapped), for: .touchUpInside)
        button.translatesAutoresizingMaskIntoConstraints = false
        return button
    }()
    
    private lazy var accountNameStackView = configureStackView(axis: .horizontal)

    private lazy var accountAmountStackView = configureStackView(axis: .horizontal)
    
    private lazy var containerStackView = configureStackView(axis: .vertical)
    
    var account: Account
    private let accountService = AccountService()
    
    weak var delegate: AccountListDelegate?
    
    init(account: Account) {
        self.account = account
        super.init(nibName: nil, bundle: nil)
    }
    
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        view.backgroundColor = .systemBackground
        setup()
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        super.viewWillDisappear(animated)
        delegate?.reloadAccounts()
    }
    
    private func setup() {
        view.addSubview(containerStackView)
        containerStackView.addArrangedSubview(accountNameStackView)
        containerStackView.addArrangedSubview(accountAmountStackView)
        accountNameStackView.addArrangedSubview(accountNameTextField)
        accountNameStackView.addArrangedSubview(updateNameButton)
        accountAmountStackView.addArrangedSubview(amountTextField)
        accountAmountStackView.addArrangedSubview(depositButton)
        accountAmountStackView.addArrangedSubview(withdrawButton)
        
        containerStackView.leadingAnchor.constraint(equalTo: view.leadingAnchor, constant: Constants.padding).isActive = true
        containerStackView.trailingAnchor.constraint(equalTo: view.trailingAnchor, constant: -Constants.padding).isActive = true
        containerStackView.topAnchor.constraint(equalTo: view.safeAreaLayoutGuide.topAnchor, constant: Constants.padding).isActive = true
        containerStackView.bottomAnchor.constraint(equalTo: view.safeAreaLayoutGuide.bottomAnchor, constant: -Constants.padding).isActive = true
        
        accountNameTextField.heightAnchor.constraint(equalToConstant: Constants.buttonHeight).isActive = true
        updateNameButton.heightAnchor.constraint(equalToConstant: Constants.buttonHeight).isActive = true
        updateNameButton.widthAnchor.constraint(equalToConstant: Constants.buttonWidth).isActive = true
        
        amountTextField.heightAnchor.constraint(equalToConstant: Constants.buttonHeight).isActive = true
        depositButton.heightAnchor.constraint(equalToConstant: Constants.buttonHeight).isActive = true
        depositButton.widthAnchor.constraint(equalToConstant: Constants.buttonWidth).isActive = true
        withdrawButton.heightAnchor.constraint(equalToConstant: Constants.buttonHeight).isActive = true
        withdrawButton.widthAnchor.constraint(equalToConstant: Constants.buttonWidth).isActive = true
    }
    
    @objc
    private func updateAccountNameButtonTapped() {
        let accountName = accountNameTextField.text ?? account.accountName
        accountService.updateName(request: .init(accountNumber: account.accountNumber, newName: accountName), completion: nil)
    }
    
    @objc
    private func depositButtonTapped() {
        guard let amount = Double(amountTextField.text ?? "0.0") else { return }
        accountService.deposit(request: .init(accountNumber: account.accountNumber, amount: amount), completion: nil)
    }
    
    @objc
    private func withdrawButtonTapped() {
        guard let amount = Double(amountTextField.text ?? "0.0") else { return }
        accountService.withdraw(request: .init(accountNumber: account.accountNumber, amount: amount), completion: nil)
    }
    
    private func configureStackView(axis: NSLayoutConstraint.Axis) -> UIStackView {
        let stackview = UIStackView()
        stackview.axis = axis
        stackview.spacing = Constants.padding
        stackview.alignment = .leading
        stackview.translatesAutoresizingMaskIntoConstraints = false
        return stackview
    }
    
}

protocol AccountListDelegate: NSObject {
    func reloadAccounts()
}
