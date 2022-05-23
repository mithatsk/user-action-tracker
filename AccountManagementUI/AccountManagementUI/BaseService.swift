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
        completion: @escaping Completion<ResponseModel>
    ) {
        guard let url = URL(string: "\(baseUrl)\(endpoint)") else { return }
        var urlRequest = URLRequest(url: url)
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
                if let value = data {
                    let responseValue = try JSONDecoder().decode(ResponseModel.self, from: value)
                    completion(.success(responseValue))
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
    
    public func get<ResponseModel: Decodable>(
        endpoint: String,
        responseType: ResponseModel.Type,
        completion: @escaping Completion<ResponseModel>
    ) {
        request(endpoint: endpoint, method: .get, requestBody: EmptyModel(), responseType: responseType, completion: completion)
    }
    
    private func parseResponse<Response: Codable>(result: Result<Data?, NetworkError>, type: Response.Type, completion: ((Response?, NetworkError?) -> Void)?) {
        switch result {
        case let .success(value):
            do {
                if let value = value {
                    let responseValue = try JSONDecoder().decode(Response.self, from: value)
                    completion?(responseValue, nil)
                }
            } catch let err {
                let error = NetworkError(message: "Could not decode!")
                print(err.localizedDescription)
                completion?(nil, error)
            }
        case let .failure(error):
            print(error)
        }
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
