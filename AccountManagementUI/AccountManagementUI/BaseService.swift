//
//  BaseService.swift
//  AccountManagementUI
//
//  Created by mithat samet kaskara on 22.05.2022.
//

import Foundation

public class BaseService {
    
    public static let shared = BaseService()
    
    private init() {}
    
    let defaultSession = URLSession(configuration: .default)
    private var baseUrl = "http://127.0.0.1:8080/"
    
    public typealias Completion<ResponseModel: Decodable> = (Result<ResponseModel?, NetworkError>) -> Void
    
    private func request<RequestModel: Encodable, ResponseModel: Decodable>(
        endpoint: String,
        method: HTTPMethod,
        requestBody: RequestModel,
        responseType: ResponseModel.Type,
        parameters: [String: String] = [String: String](),
        completion: @escaping Completion<ResponseModel>
    ) {
        var components = URLComponents(string: "\(baseUrl)\(endpoint)")!
        components.queryItems = parameters.map { (key, value) in
            URLQueryItem(name: key, value: value)
        }
        components.percentEncodedQuery = components.percentEncodedQuery?.replacingOccurrences(of: "+", with: "%2B")
        var urlRequest = URLRequest(url: components.url!)
        
        urlRequest.httpMethod = method.rawValue
        urlRequest.addValue("application/json", forHTTPHeaderField: "Content-Type")
        
        if method != .get, let httpBody = try? JSONEncoder().encode(requestBody) {
            urlRequest.httpBody = httpBody
        }
        
        let task = defaultSession.dataTask(with: urlRequest) { data, response, error in
            guard let response = response as? HTTPURLResponse else {
                completion(.failure(.init(message: "Did not get an http response!")))
                return
            }
            
            if let error = error as NSError? {
                completion(.failure(.init(message: "\(response.statusCode): \(error.localizedDescription)")))
                return
            }
            
            do {
                if let value = data, !value.isEmpty {
                    var responseValue: ResponseModel
                    if ResponseModel.self is String.Type {
                        responseValue = String(decoding: value, as: UTF8.self) as! ResponseModel
                    } else {
                        responseValue = try JSONDecoder().decode(ResponseModel.self, from: value)
                    }
                    
                    completion(.success(responseValue))
                    
                } else {
                    let responseObject = EmptyModel()
                    if responseObject is ResponseModel {
                        completion(.success(responseObject as? ResponseModel))
                    } else {
                        let error = NetworkError(message: "Could not serialize!")
                        completion(.failure(error))
                    }
                }
            } catch let err {
                let error = NetworkError(message: "Could not decode!")
                print(err.localizedDescription)
                completion(.failure(error))
            }
        }
        
        task.resume()
    }
    
    public func post<RequestModel: Encodable, ResponseModel: Decodable>(
        endpoint: String,
        requestBody: RequestModel,
        responseType: ResponseModel.Type,
        completion: @escaping Completion<ResponseModel>
    ) {
        request(endpoint: endpoint, method: .post, requestBody: requestBody, responseType: responseType, completion: completion)
    }
    
    public func put<RequestModel: Encodable, ResponseModel: Decodable>(
        endpoint: String,
        requestBody: RequestModel,
        responseType: ResponseModel.Type,
        completion: @escaping Completion<ResponseModel>
    ) {
        request(endpoint: endpoint, method: .put, requestBody: requestBody, responseType: responseType, completion: completion)
    }
    
    public func get<RequestModel: Encodable, ResponseModel: Decodable>(
        endpoint: String,
        requestBody: RequestModel,
        responseType: ResponseModel.Type,
        parameters: [String: String] = [String: String](),
        completion: @escaping Completion<ResponseModel>
    ) {
        request(endpoint: endpoint, method: .get, requestBody: requestBody, responseType: responseType, parameters: parameters, completion: completion)
    }
}

public struct NetworkError: Error {
    let message: String
}

public enum HTTPMethod: String {
    case post = "POST"
    case put = "PUT"
    case get = "GET"
}

public struct EmptyModel: Codable {}
