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
            reason = "The authentication was malformed and could not be read"
        )
        class MalformedAuthException : AuthException("The authentication was malformed and could not be read")

        @ResponseStatus(
            value = HttpStatus.UNAUTHORIZED,
            reason = "The entered credentials are incorrect!"
        )
        class InvalidCredentialsException: AuthException("The entered credentials are incorrect!")

    }

    @ResponseStatus(
        value = HttpStatus.NOT_FOUND,
        reason = "Could not find the requested item!"
    )
    data class NotFoundException(val item: Any) :
        AboutMeException("Could not find item with classifier '$item'", HttpStatus.NOT_FOUND)

    @ResponseStatus(
        value = HttpStatus.CONFLICT,
        reason = "The request conflicted with internal state"
    )
    data class Conflict(val item: Any) :
        AboutMeException("Could not add item with classifier '$item' as it is already used", HttpStatus.CONFLICT)

    @ResponseStatus(
        value = HttpStatus.BAD_REQUEST,
        reason = "The data did not match given constraints"
    )
    data class InvalidData(val description: String) :
            AboutMeException("The data did not match given constraints: '$description'", HttpStatus.BAD_REQUEST)
}