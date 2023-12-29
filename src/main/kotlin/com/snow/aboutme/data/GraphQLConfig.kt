package com.snow.aboutme.data;

import graphql.scalars.ExtendedScalars
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
    }

}