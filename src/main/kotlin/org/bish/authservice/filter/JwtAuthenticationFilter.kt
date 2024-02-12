package org.bish.authservice.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.aspectj.util.LangUtil.isEmpty
import org.bish.authservice.service.JwtAuthenticationService
import org.bish.authservice.service.UserService
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    val jwtAuthenticationService: JwtAuthenticationService,
    val userService: UserService
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        // Get authorization header from request
        val header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (isEmpty(header) || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Validate JWT token
        val token = header.split(" ")[1].trim();
        if (!jwtAuthenticationService.validate(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        // Populate spring context with user details
        val userDetails: UserDetails? = userService
            .loadUserByUsername(jwtAuthenticationService.getUsername(token))

        val authentication = UsernamePasswordAuthenticationToken(
            userDetails, null,
            if (userDetails == null) listOf() else userDetails.authorities
        )

        authentication.details = WebAuthenticationDetailsSource().buildDetails(request)

        SecurityContextHolder.getContext().authentication = authentication
        filterChain.doFilter(request, response)
    }

}