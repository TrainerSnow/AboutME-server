package com.snow.aboutme.exception;

import graphql.ErrorType
import graphql.GraphQLError
import graphql.schema.DataFetchingEnvironment
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter
import org.springframework.stereotype.Component

@Component
class ErrorResolver : DataFetcherExceptionResolverAdapter() {

    override fun resolveToSingleError(ex: Throwable, env: DataFetchingEnvironment): GraphQLError? {
        return if (ex is AboutMeException) {
            GraphQLError
                .newError()
                .errorType(ErrorType.ValidationError)
                .message(ex.reason)
                .extensions(mapOf("code" to ex.status.value()))
                .build()
        } else {
            null
        }
    }

}