package org.bish.authservice.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
@EnableWebSecurity
class SecurityConfig(val userDetailsService: UserDetailsService) {

    @Bean
    fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder())
    }

    @Bean
    fun configure(http: HttpSecurity) {
        http
            .authorizeHttpRequests { authorize ->
                authorize.requestMatchers("/api/register").permitAll()
                authorize.anyRequest().authenticated()
            }
            .formLogin { login ->
                login.loginProcessingUrl("/api/login").permitAll()
            }
            .logout { logout ->
                logout.logoutUrl("api/logout").permitAll()
            }
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}