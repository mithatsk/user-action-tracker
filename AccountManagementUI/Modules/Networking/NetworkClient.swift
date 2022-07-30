//
//  BaseService.swift
//  AccountManagementUI
//
//  Created by mithat samet kaskara on 22.05.2022.
//

import Foundation

public typealias Completion<ResponseModel: Decodable> = (Result<ResponseModel?, NetworkError>) -> Void

public protocol NetworkClientProtocol: AnyObject {
    var session: URLSession { get }
    var validStatusCodeRange: Range<Int> { get }
    func request<E: Endpoint, ResponseModel: Decodable>(
        endpoint: E,
        responseType: ResponseModel.Type
    ) async -> Result<ResponseModel, NetworkError>
}

open class NetworkClient: NetworkClientProtocol {
    open var session: URLSession
    open var validStatusCodeRange: Range<Int>
    
    public init(
        session: URLSession = .shared,
        validStatusCodeRange: Range<Int>
    ) {
        self.session = session
        self.validStatusCodeRange = validStatusCodeRange
    }
    
    open func request<E: Endpoint, ResponseModel: Decodable>(
        endpoint: E,
        responseType: ResponseModel.Type
    ) async -> Result<ResponseModel, NetworkError> {
        guard let url = endpoint.urlRequest else {
            return .failure(.invalidUrl)
        }
        do {
            let (data, response) = try await session.data(for: url)
            guard let response = response as? HTTPURLResponse else {
                return .failure(.noresponse)
            }
            
            switch response.statusCode {
            case validStatusCodeRange:
                let decoder = JSONDecoder()
                guard let result = try? decoder.decode(ResponseModel.self, from: data) else {
                    return .failure(.failedDecoding)
                }
                return .success(result)
            case 401:
                return .failure(.unauthorized)
            default:
                return .failure(.unexpectedStatus)
            }
        } catch {
            return .failure(.network)
        }
    }
}

public enum NetworkError: Error {
    case invalidUrl
    case noresponse
    case failedDecoding
    case unauthorized
    case unexpectedStatus
    case network
}

public enum HTTPMethod: String {
    case post = "POST"
    case put = "PUT"
    case get = "GET"
}

public struct EmptyModel: Codable {
    public init() {}
}
