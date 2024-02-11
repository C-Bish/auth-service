package org.bish.authservice.config

import org.bish.authservice.filters.JwtAuthenticationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@Configuration
@EnableWebSecurity
class SecurityConfig(
    val encoder: PasswordEncoder,
    val userDetailsService: UserDetailsService,
    val jwtAuthenticationFilter: JwtAuthenticationFilter
) {

    @Bean
    fun authenticationProvider() : AuthenticationProvider {
        val authProvider = DaoAuthenticationProvider()
        authProvider.setUserDetailsService(userDetailsService)
        authProvider.setPasswordEncoder(encoder)
        return authProvider
    }

    @Bean
    fun authenticationManager(config: AuthenticationConfiguration) : AuthenticationManager {
        return config.authenticationManager
    }

    @Bean
    fun filterChain(http: HttpSecurity) : SecurityFilterChain {
        http
            .cors { cors ->
                cors.disable()
            }
            .csrf { csrf ->
                csrf.disable()
            }
            .authorizeHttpRequests { authorize ->
                authorize.requestMatchers("/api/**").permitAll()
                authorize.requestMatchers("/error").permitAll()
                authorize.anyRequest().authenticated()
            }
            .sessionManagement { session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .addFilterBefore(
                jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter::class.java
            )
            .userDetailsService(userDetailsService)
            .authenticationProvider(authenticationProvider())
            .logout { logout ->
                logout.logoutUrl("api/logout").permitAll()
            }
        return http.build()
    }

}