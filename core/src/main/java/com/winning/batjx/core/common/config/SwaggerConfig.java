package com.winning.batjx.core.common.config;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;
import java.util.stream.Stream;

import static com.google.common.base.Predicates.and;


/**
 * @author yang_kang@winning.com.cn
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig implements WebMvcConfigurer {

    @Value("${swagger.enable}")
    private boolean enableSwagger;


    private ApiKey apiKey() {
        return new ApiKey("JWT", "Authorization", "header");
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        Stream<AuthorizationScope> scope = Stream.of(authorizationScope);

        return Lists.newArrayList(new SecurityReference("JWT", scope.toArray(AuthorizationScope[]::new)));
    }


    ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Winning WPH API")
                .description("Winning WPH API")
                .termsOfServiceUrl("")
                .version("1.0.0")
                .contact(new Contact("Winning Health", "http://www.winning.com.cn", ""))
                .build();
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .build();
    }

    @Bean
    public Docket createRestApi() {

        return new Docket(DocumentationType.SWAGGER_2)
                .enable(enableSwagger)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.winning.batjx"))
                .paths(filteringPushRules())
                .build()
                .groupName("Core")
                .apiInfo(this.apiInfo())
                .securityContexts(Lists.newArrayList(securityContext()))
                .securitySchemes(Lists.newArrayList(apiKey()));
    }


    private Predicate<String> filteringPushRules() {
        return and(
                PathSelectors.regex("^(?!/ecare_.*_push).*"),
                PathSelectors.regex("^(?!/baidu.*).*"),
                PathSelectors.regex("^(?!/push/baidu/fence/.*).*")
        );
    }


    @Bean
    public Docket createBaseApi() {

        return new Docket(DocumentationType.SWAGGER_2)
                .enable(enableSwagger)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.winning.jbase"))
                .paths(PathSelectors.any())
                .build()
                .groupName("jBase")
                .apiInfo(this.apiInfo())
                .securityContexts(Lists.newArrayList(securityContext()))
                .securitySchemes(Lists.newArrayList(apiKey()));
    }






    /**
     * swagger ui资源映射
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        //使用http://localhost:8089/batjx/doc.html 访问。展示bootstrap风格主题样式
        registry.addResourceHandler("doc.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars*")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    /**
     * swagger-ui.html路径映射，浏览器中使用/api-docs访问
     *
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/api-docs", "/swagger-ui.html");
    }

}
