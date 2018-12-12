package com.core.config;

import com.base.util.SpringBeanUtil;
import com.core.interceptor.LogInterceptor;
import com.core.interceptor.PaginationInterceptor;
import com.core.resolver.CustomExceptionResolver;
import com.core.resolver.WebExceptionResolver;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @Description: springMVC配置
 */
@Configuration
@EnableSwagger2//启用Swagger2
// springMVC自定义配置方案：
// 1.继承WebMvcConfigurationSupport,则需要自定义重写所有的配置,
// SpringBoot将不会进行springMvc自动配置,
// 同时在application.properties中的springMVC定义均无效.
// 2.实现WebMvcConfigurer,则除了重写的方法之外,其余的配置均采取SpringBoot自动配置，
// 其中,在application.properties中的springMVC定义，没有被重写则被采纳；重写了则优先采用Java配置.
public class MyWebMvcConfig implements WebMvcConfigurer {

    @Autowired
    LogInterceptor logInterceptor;

    /**
     * 注入获取spring容器的bean工具类
     *
     * @return
     */
    @Bean
    public SpringBeanUtil springBeanUtil() {
        return new SpringBeanUtil();
    }

    /**
     * 默认区域语言
     *
     * @return
     */
    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
        sessionLocaleResolver.setDefaultLocale(Locale.SIMPLIFIED_CHINESE);
        return sessionLocaleResolver;
    }

    /**
     * 根据区域改变语言
     *
     * @return
     */
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("lang");
        return localeChangeInterceptor;
    }

    /**
     * 异常处理器
     *
     * @param exceptionResolvers
     */
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        //可添加多个HandlerExceptionResolver
        //第一个
        exceptionResolvers.add(0, new CustomExceptionResolver());
        //第二个
        exceptionResolvers.add(1, new WebExceptionResolver());
        //无须处理的异常集合,直接返回null,交由其他的异常处理类处理
    }

    /**
     * 资源访问处理器.
     * 等同于:
     * <mvc:resources mapping="/static/**" location="/static/">
     *
     * @param registry
     */
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("/static/");
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    /**
     * 配置拦截器
     * 等价于:<mvc:interceptors>
     *
     * @param registry
     */
    public void addInterceptors(InterceptorRegistry registry) {
        // 多个拦截器组成一个拦截器链(添加多个addInterceptor)
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截
        //国际化
        registry.addInterceptor(localeChangeInterceptor()).addPathPatterns("/**");
        //分页拦截
        registry.addInterceptor(new PaginationInterceptor()).addPathPatterns("/**");
        //日志拦截
        List<String> list = new ArrayList<>();
        list.add("/web/manage");
        list.add("/mood/manage");
        list.add("/essay/manage");
        list.add("/picture/manage");
        list.add("/web/other");
        registry.addInterceptor(logInterceptor).addPathPatterns(list);
    }

    /**
     * 自定义消息转换器配置
     *
     * @param converters
     */
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        //对String的返回值做转化
        StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter();
        List<MediaType> supportedMediaTypes = new ArrayList<>();
        supportedMediaTypes.add(new MediaType(MediaType.TEXT_PLAIN, Charset.defaultCharset()));
        supportedMediaTypes.add(new MediaType(MediaType.TEXT_HTML, Charset.defaultCharset()));
        stringHttpMessageConverter.setSupportedMediaTypes(supportedMediaTypes);
        converters.add(stringHttpMessageConverter);
        //对Json的返回值做转化
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        List<MediaType> supportedMediaTypes1 = new ArrayList<>();
        supportedMediaTypes1.add(new MediaType(MediaType.APPLICATION_JSON, Charset.defaultCharset()));
        mappingJackson2HttpMessageConverter.setSupportedMediaTypes(supportedMediaTypes1);
        converters.add(mappingJackson2HttpMessageConverter);
    }

    /**
     * 增加url对应的view,直接返回视图逻辑名
     *
     * @param registry
     */
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/login.jsp");
    }

    /**
     * swagger2配置
     *
     * @return
     */
    @Bean
    public Docket createRestApi() {
        //扫描所有有注解的api
        ApiSelectorBuilder apiSelectorBuilder = new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select().apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class));
        apiSelectorBuilder.paths(PathSelectors.any());
        Docket docket = apiSelectorBuilder.build();
        return docket;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("APIs").description("接口文档，内容详细，极大的减少了前后端的沟通成本，同时确保代码与文档保持高度一致，极大的减少维护文档的时间。").termsOfServiceUrl("/").version("1.0.0").build();
    }
}