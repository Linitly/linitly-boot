package org.linitly.boot.base.config;

import com.fasterxml.jackson.databind.module.SimpleModule;
import org.linitly.boot.base.constant.admin.AdminCommonConstant;
import org.linitly.boot.base.interceptor.web.AdminTokenInterceptor;
import org.linitly.boot.base.interceptor.web.SystemInterceptor;
import org.linitly.boot.base.utils.filter.xss.XssDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author linxiunan
 * @Description 拦截器配置文件
 * @date 2018年9月11日
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	@Autowired
	private AdminTokenInterceptor adminTokenInterceptor;
	@Autowired
	private SystemInterceptor systemInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(systemInterceptor).addPathPatterns("/**").excludePathPatterns("/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html", "/error");
		registry.addInterceptor(adminTokenInterceptor).addPathPatterns(AdminCommonConstant.URL_PREFIX + "/**");
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html")
				.addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**")
				.addResourceLocations("classpath:/META-INF/resources/webjars/");
	}

	@Bean
	public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
		MappingJackson2HttpMessageConverter htmlEscapingConverter = new MappingJackson2HttpMessageConverter();
		SimpleModule simpleModule = new SimpleModule();
		simpleModule.addDeserializer(String.class, new XssDeserializer());
		htmlEscapingConverter.getObjectMapper().registerModule(simpleModule);
		return htmlEscapingConverter;
	}
}
