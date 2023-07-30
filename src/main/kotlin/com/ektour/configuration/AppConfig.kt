package com.ektour.configuration

import com.ektour.common.logging.ApiLoggingInterceptor
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableHandlerMethodArgumentResolver
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class AppConfig(
    private val apiLoggingInterceptor: ApiLoggingInterceptor
) : WebMvcConfigurer {
    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowedOrigins("*")
    }

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(apiLoggingInterceptor)
            .addPathPatterns("/admin/**", "/estimate/**")
            .order(1)
    }

    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        val resolver = PageableHandlerMethodArgumentResolver()
        resolver.setFallbackPageable(PageRequest.of(0, 20, Sort.by(Sort.Direction.DESC, "id")))
        resolvers.add(resolver)
    }
}
