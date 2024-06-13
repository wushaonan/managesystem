package com.sn.manageservice.component;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @auther 向南
 * @date 2021/12/26 18:30
 * @description
 */
@Configuration
public class MyMvcConfig extends WebMvcConfigurerAdapter {
//    @Override
//    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/index").setViewName("login");
//    }
//
//    //注册拦截器
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//
//        registry.addInterceptor(new LoginHandlerInterceptor()).addPathPatterns("/**")
//                .excludePathPatterns("/index.html","/","/user/login","/webjars/**","/**/*.css", "/**/*.js");
//    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // 拦截所有的请求
                .allowedOriginPatterns("*")  // 可跨域的域名，可以为 *
                .allowCredentials(true)
                .allowedMethods("*")   // 允许跨域的方法，可以单独配置
                .allowedHeaders("*");  // 允许跨域的请求头，可以单独配置
    }

    //使用WebMvcConfigurerAdapter来扩展SpringMVC的功能
    //所有的WebMvcConfigurerAdapter都会生效
    //注意要写在标有@Configuration的类中，要在方法上标上@Bean注解
    @Bean
    public WebMvcConfigurerAdapter webMvcConfigurerAdapter(){
        WebMvcConfigurerAdapter webMvcConfigurerAdapter = new WebMvcConfigurerAdapter(){
            //视图空值器，用于定义访问哪些路径时定位到哪些视图
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                //访问http://localhost:8080/ 和 http://localhost:8080/index.html都会寻找静态资源下的templates/login.html
                registry.addViewController("/").setViewName("login");
                registry.addViewController("/index.html").setViewName("login");
                //访问"/main.html"会寻找静态资源下的templates/login.html
                registry.addViewController("/main.html").setViewName("dashboard");
            }
            //注册拦截器，用于拦截用户需要先登录才能访问资源
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(new LoginHandlerInterceptor()).addPathPatterns("/**")
                        .excludePathPatterns("/index.html","/","/manage/login","/manage/appLogin","/rest/manage/login","/webjars/**","/**/*.css", "/**/*.js");
            }
        };
        return webMvcConfigurerAdapter;
    }

}

