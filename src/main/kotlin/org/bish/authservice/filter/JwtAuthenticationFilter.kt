package org.bish.authservice.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.aspectj.util.LangUtil.isEmpty
import org.bish.authservice.service.JwtAuthenticationService
import org.bish.authservice.service.UserService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
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

    private final val log: Logger = LoggerFactory.getLogger(this.javaClass)

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        log.debug("Processing request checking for a JSON Web Token.")

        // Get authorization header from request
        val header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (isEmpty(header) || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        log.debug("Authorization header found. Performing JWT token validation.")

        // Validate JWT token
        val token = header.split(" ")[1].trim();
        if (!jwtAuthenticationService.validate(token)) {
            log.warn("Failed validation for provided token.")
            filterChain.doFilter(request, response);
            return;
        }

        log.debug("JWT token validation complete. Populating security context.")

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