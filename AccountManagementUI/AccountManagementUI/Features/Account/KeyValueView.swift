//
//  KeyValueView.swift
//  AccountManagementUI
//
//  Created by mithat samet kaskara on 23.05.2022.
//

import UIKit

final class KeyValueView: UIView {
    
    var key: String = "" {
        didSet {
            self.keyLabel.text = key
        }
    }
    
    var value: String = "" {
        didSet {
            self.valueLabel.text = value
        }
    }
    
    private let keyLabel: UILabel = {
        let label = UILabel()
        return label
    }()
    
    private let valueLabel: UILabel = {
        let label = UILabel()
        label.numberOfLines = 0
        return label
    }()
    
    private let containerStackView: UIStackView = {
        let stackview = UIStackView()
        stackview.axis = .horizontal
        stackview.spacing = 15
        stackview.distribution = .fillEqually
        stackview.translatesAutoresizingMaskIntoConstraints = false
        return stackview
    }()
    
    
    init() {
        super.init(frame: .zero)
        addSubview(containerStackView)
        containerStackView.addArrangedSubview(keyLabel)
        containerStackView.addArrangedSubview(valueLabel)
        
        containerStackView.leadingAnchor.constraint(equalTo: leadingAnchor).isActive = true
        containerStackView.trailingAnchor.constraint(equalTo: trailingAnchor).isActive = true
        containerStackView.topAnchor.constraint(equalTo: topAnchor).isActive = true
        containerStackView.bottomAnchor.constraint(equalTo: bottomAnchor).isActive = true
    }
    
    required init?(coder: NSCoder) {
        super.init(coder: coder)
    }
}
