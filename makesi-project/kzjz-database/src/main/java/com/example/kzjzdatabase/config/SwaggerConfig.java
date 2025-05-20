
package com.example.kzjzdatabase.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@EnableOpenApi
public class SwaggerConfig {
    public SwaggerConfig() {
    }

    @Bean
    public Docket docket() {
        return (new Docket(DocumentationType.OAS_30)).apiInfo(this.apiInfo()).enable(true).select().apis(RequestHandlerSelectors.basePackage("com.example.kzjzdatabase.controller")).paths(PathSelectors.any()).build();
    }

    private ApiInfo apiInfo() {
        return (new ApiInfoBuilder()).title("抗震救灾网站接口文档").description("抗震救灾网站").contact(new Contact("抗震救灾", "https://xyhwh.gitee.io/blog/", "2847043211@qq.com")).version("1.0").build();
    }

}
