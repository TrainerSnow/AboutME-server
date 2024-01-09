package com.snow.aboutme.data;

import com.snow.aboutme.data.graphql.InstantCoercing
import graphql.scalars.ExtendedScalars
import graphql.schema.GraphQLScalarType
import graphql.schema.idl.TypeRuntimeWiring
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.graphql.execution.RuntimeWiringConfigurer


@Configuration
class GraphQLConfig {

    @Bean
    fun runtimeWiringConfigurer(): RuntimeWiringConfigurer = RuntimeWiringConfigurer { builder ->
        builder
            .scalar(ExtendedScalars.Date)
            .scalar(ExtendedScalars.GraphQLLong)
            .scalar(
                GraphQLScalarType.newScalar()
                    .name("Instant")
                    .coercing(InstantCoercing())
                    .build()
            )
    }

}