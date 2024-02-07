package org.bish.authservice.config

import org.bish.authservice.filters.CustomFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer.withDefaults
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter


@Configuration
class SecurityConfig {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests { authorize ->
                authorize.requestMatchers("/public").permitAll()
                authorize.requestMatchers("/private").authenticated()
            }
            .oauth2Login { oauth2Login ->
                oauth2Login.userInfoEndpoint { userInfoEndpoint ->
                    userInfoEndpoint.userAuthoritiesMapper(grantedAuthoritiesMapper())
                }
            }
            .addFilterBefore(CustomFilter(), BasicAuthenticationFilter::class.java)
            .httpBasic(withDefaults())
        return http.build()
    }

    private fun grantedAuthoritiesMapper(): GrantedAuthoritiesMapper {
        return GrantedAuthoritiesMapper { authorities ->
            authorities.map { authority ->
                when (authority) {
                    is OidcUserAuthority ->
                        OidcUserAuthority("ROLE_USER", authority.idToken, authority.userInfo)
                    is OAuth2UserAuthority ->
                        OAuth2UserAuthority("ROLE_USER", authority.attributes)
                    else -> authority
                }
            }
        }
    }
}