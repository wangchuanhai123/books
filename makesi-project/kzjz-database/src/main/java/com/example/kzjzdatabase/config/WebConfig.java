package com.example.kzjzdatabase.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;


@Configuration
public class WebConfig extends WebMvcConfigurationSupport {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 下载映射
        registry.addResourceHandler("/usr/local/files/**").addResourceLocations("file:" + "/usr/local/files/");

        //swagger映射
        registry.addResourceHandler("/swagger-ui/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/");
        super.addResourceHandlers(registry);
    }

//    @Override
//    protected void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor()
//                .addPathPatterns("/**")
//                .excludePathPatterns("/swagger**/**")
//                .excludePathPatterns("/webjars/**")
//                .excludePathPatterns("/v3/**")
//                .excludePathPatterns("/doc.html");
//        super.addInterceptors(registry);
//    }

}