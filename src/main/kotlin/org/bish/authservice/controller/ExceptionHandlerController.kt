package org.bish.authservice.controller

import jakarta.servlet.http.HttpServletResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.InsufficientAuthenticationException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.client.HttpServerErrorException
import java.io.IOException

@RestControllerAdvice
class ExceptionHandlerController {

    private final val log : Logger = LoggerFactory.getLogger(this.javaClass)

    @ExceptionHandler(AccessDeniedException::class)
    @Throws(IOException::class)
    fun handleAccessDeniedException(ex: AccessDeniedException, res: HttpServletResponse) {
        log.error("Handled Access Denied Exception", ex)
        res.sendError(HttpStatus.FORBIDDEN.value(), "Access denied.")
    }

    @ExceptionHandler(HttpServerErrorException::class)
    @Throws(IOException::class)
    fun handleHttpServerErrorException(ex: HttpServerErrorException, res: HttpServletResponse) {
        log.error("Handled HTTP Server Error Exception", ex)
        res.sendError(ex.statusCode.value(), ex.message)
    }

    @ExceptionHandler(BadCredentialsException::class)
    @Throws(IOException::class)
    fun handleBadCredentialsException(ex: BadCredentialsException, res: HttpServletResponse) {
        log.error("Handled Bad Credentials Exception", ex)
        res.sendError(HttpStatus.FORBIDDEN.value(), "Invalid credentials supplied.")
    }

    @ExceptionHandler(InsufficientAuthenticationException::class)
    @Throws(IOException::class)
    fun handleInsufficientAuthenticationException(ex: InsufficientAuthenticationException, res: HttpServletResponse) {
        log.error("Handled Insufficient Authentication Exception", ex)
        res.sendError(HttpStatus.FORBIDDEN.value(), "Insufficient authentication.")
    }

    @ExceptionHandler(Exception::class)
    @Throws(IOException::class)
    fun handleException(ex: Exception, res: HttpServletResponse) {
        log.error("Handled Internal Error Exception", ex)
        res.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Something went wrong.")
    }

}