package com.snow.aboutme.annotation;

import org.springframework.security.access.prepost.PreAuthorize

@PreAuthorize("isFullyAuthenticated()")
@Target(AnnotationTarget.FUNCTION)
annotation class GraphQLAuthenticated

@PreAuthorize("isAnonymous()")
@Target(AnnotationTarget.FUNCTION)
annotation class GraphQLAnonymous