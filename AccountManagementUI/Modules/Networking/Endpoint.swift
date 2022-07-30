//
//  Endpoint.swift
//  AccountManagementUI
//
//  Created by mithat samet kaskara on 30.07.2022.
//

import Foundation

public protocol Endpoint {
    var host: String { get }
    var port: Int? { get }
    var path: String { get }
    var scheme: String { get }
    var method: HTTPMethod { get }
    var headers: [String: String] { get }
    var queryParameters: [String: String] { get }
    var urlRequest: URLRequest? { get }
    
    func urlRequest<RequestBody: Codable>(requestBody: RequestBody) -> URLRequest?
}

extension Endpoint {
    public var headers: [String: String] {
        switch self {
        default:
            return ["Content-Type" : "application/json"]
        }
    }
    
    public var queryParameters: [String: String] { [:] }
    
    public func urlRequest<RequestBody: Codable>(requestBody: RequestBody) -> URLRequest? {
        var components = URLComponents()
        components.host = host
        components.scheme = scheme
        components.path = path
        components.port = port
        components.queryItems = queryParameters.map { (key, value) in
            URLQueryItem(name: key, value: value)
        }
        components.percentEncodedQuery = components.percentEncodedQuery?.replacingOccurrences(of: "+", with: "%2B")
        guard let url = components.url else {
            return nil
        }
        
        var urlRequest = URLRequest(url: url)
        
        urlRequest.httpMethod = method.rawValue
        urlRequest.allHTTPHeaderFields = headers
        
        if method != .get, let httpBody = try? JSONEncoder().encode(requestBody) {
            urlRequest.httpBody = httpBody
        }
        
        return urlRequest
    }
}
