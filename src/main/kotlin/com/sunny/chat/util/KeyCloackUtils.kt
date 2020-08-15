package com.sunny.chat.util

import org.keycloak.KeycloakPrincipal
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken
import java.security.Principal
import javax.servlet.http.HttpServletRequest


object KeyCloakUtils {
    fun getUserId(request: HttpServletRequest): String {
        val token = request.userPrincipal as KeycloakAuthenticationToken
        val principal = token.principal as KeycloakPrincipal<*>
        val session = principal.keycloakSecurityContext
        val accessToken = session.token
        return accessToken.subject
    }

    fun getUserEmail(principal: Principal): String {
        val keycloakPrincipal = principal as KeycloakPrincipal<*>
        val session = keycloakPrincipal.keycloakSecurityContext
        val accessToken = session.token
        return accessToken.email
    }

    fun getUserEmail(request: HttpServletRequest): String {
        val token = request.userPrincipal as KeycloakAuthenticationToken
        val principal = token.principal as KeycloakPrincipal<*>
        val session = principal.keycloakSecurityContext
        val accessToken = session.token
        return accessToken.email
    }
}