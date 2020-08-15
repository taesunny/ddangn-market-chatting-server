package com.sunny.chat.config
//
//import org.springframework.http.server.ServerHttpRequest
//import org.springframework.http.server.ServerHttpResponse
//import org.springframework.http.server.ServletServerHttpRequest
//import org.springframework.web.socket.WebSocketHandler
//import org.springframework.web.socket.server.HandshakeInterceptor
//import java.lang.Exception
//import java.security.Principal
//
//class HttpHandshakeInterceptor: HandshakeInterceptor {
//
//    override fun beforeHandshake(request: ServerHttpRequest, response: ServerHttpResponse, wsHandler: WebSocketHandler, attributes: MutableMap<String, Any>): Boolean {
////        if(request is ServletServerHttpRequest) {
////            var serveletRequest: ServletServerHttpRequest = request as ServletServerHttpRequest
//            var pricipal: Principal? = request.principal ?: return false
//            attributes["PRINCIPAL"] = pricipal!!
////        }
//        return true
//    }
//
//    override fun afterHandshake(request: ServerHttpRequest, response: ServerHttpResponse, wsHandler: WebSocketHandler, exception: Exception?) {
//
//    }
//}