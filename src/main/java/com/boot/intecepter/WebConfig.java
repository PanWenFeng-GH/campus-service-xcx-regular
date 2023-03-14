package com.boot.intecepter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.boot.util.FilesUtils;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	@Autowired
	private Intecepter intecepter;
	@Autowired
	private ApiIntecepter apiIntecepter;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(intecepter)
				.addPathPatterns("/private/**","/index")
				.excludePathPatterns("/login/*","/api/**");
		registry.addInterceptor(apiIntecepter).addPathPatterns("/api/**").excludePathPatterns("/api/getOpenId");
	}
	/**
     * 跨域支持
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "DELETE", "PUT")
                .maxAge(3600 * 24);
    }
    
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/file/**").addResourceLocations("file:"+FilesUtils.path);
    }

}
