package com.snow.aboutme.data.graphql

import graphql.schema.Coercing
import graphql.schema.CoercingParseLiteralException
import graphql.schema.CoercingParseValueException
import graphql.schema.CoercingSerializeException
import java.time.Instant
import java.time.format.DateTimeParseException

@Suppress("OVERRIDE_DEPRECATION")
class InstantCoercing: Coercing<Instant, String> {

    override fun serialize(dataFetcherResult: Any) = (dataFetcherResult as? Instant)?.toString()
        ?: throw CoercingSerializeException("Expected a type of java.time.Instant")

    override fun parseValue(input: Any): Instant? = try {
        if (input is String) {
            Instant.parse(input)
        } else {
            throw CoercingParseValueException("Expected a String")
        }
    } catch (e: DateTimeParseException) {
        throw CoercingParseValueException("Not a valid instant: '$input'.", e)
    }

    override fun parseLiteral(input: Any): Instant? = if (input is String) {
        try {
            Instant.parse(input)
        } catch (e: DateTimeParseException) {
            throw CoercingParseLiteralException(e)
        }
    } else {
        throw CoercingParseLiteralException("Expected a String.")
    }
}