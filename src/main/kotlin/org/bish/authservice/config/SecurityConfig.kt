package org.bish.authservice.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer.withDefaults
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain


@Configuration
@EnableWebSecurity
class SecurityConfig(val userDetailsService: UserDetailsService) {

    @Bean
    fun filterChain(http: HttpSecurity) : SecurityFilterChain {

        // Configure AuthenticationManagerBuilder
        val authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder::class.java)
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder())

        // Get AuthenticationManager
        val authenticationManager = authenticationManagerBuilder.build()

        http
            .cors(withDefaults())
            .csrf { csrf ->
                csrf.disable()
            }
            .authorizeHttpRequests { authorize ->
                authorize.requestMatchers("/api/register").permitAll()
                authorize.anyRequest().authenticated()
            }
            .authenticationManager(authenticationManager)
            .formLogin { login ->
                login.loginProcessingUrl("/api/login").permitAll()
            }
            .logout { logout ->
                logout.logoutUrl("api/logout").permitAll()
            }
        return http.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}