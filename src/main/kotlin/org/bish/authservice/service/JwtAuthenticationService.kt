package org.bish.authservice.service

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Service
import java.util.*

@Service
class JwtAuthenticationService {

    private val SECRET = "my-secret-key-for-hs512"
    private val EXPIRATION_TIME: Long = 86400000 // 1 day

    fun generateToken(username: String?): String {
        return Jwts.builder()
            .setSubject(username)
            .setExpiration(Date(System.currentTimeMillis() + EXPIRATION_TIME))
            .signWith(SignatureAlgorithm.HS512, SECRET)
            .compact()
    }

    fun getUsername(token: String) : String {
        return Jwts.parser()
            .setSigningKey(SECRET)
            .parseClaimsJws(token)
            .body
            .subject
    }

    fun validate(token: String) : Boolean {
        try {
            val claims = Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .body

            // Check token has not expired
            val now = Date()
            return !now.after(claims.expiration)
        } catch (e: Exception) {
            // Token validation failed
            return false
        }
    }

    fun invalidate(token: String) : Boolean {
        return false
    }

}