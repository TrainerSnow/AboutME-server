package com.snow.aboutme.exception;

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

sealed class AboutMeException(

    open val reason: String,

    open val status: HttpStatus

) : RuntimeException(reason) {

    sealed class AuthException(

        override val reason: String

    ) : AboutMeException(reason, HttpStatus.UNAUTHORIZED) {

        @ResponseStatus(
            value = HttpStatus.UNAUTHORIZED,
            reason = "No valid authentication header was found"
        )
        class NoAuthException : AuthException("No valid authentication header was found")

        @ResponseStatus(
            value = HttpStatus.UNAUTHORIZED,
            reason = "The authentication was malformed and could not be read"
        )
        class MalformedAuthException : AuthException("The authentication was malformed and could not be read")

        @ResponseStatus(
            value = HttpStatus.UNAUTHORIZED,
            reason = "The authentication is expired"
        )
        class ExpiredAuthException : AuthException("The authentication is expired")

        @ResponseStatus(
            value = HttpStatus.UNAUTHORIZED,
            reason = "The authentication is missing data"
        )
        class InsufficientAuthException : AuthException("The authentication is missing data")

        @ResponseStatus(
            value = HttpStatus.UNAUTHORIZED,
            reason = "The entered credentials are incorrect!"
        )
        class InvalidCredentialsException: AuthException("The entered credentials are incorrect!")

    }

    data class NotFoundException(val item: Any) :
        AboutMeException("Could not find item with classifier '$item'", HttpStatus.NOT_FOUND)

    data class Conflict(val item: Any) :
        AboutMeException("Could not add item with classifier '$item' as it is already used", HttpStatus.CONFLICT)
}