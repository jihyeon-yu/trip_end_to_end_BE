package com.trip.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@ComponentScan({ "com.ssafy.trip.*.controller", "com.ssafy.trip.util" })
public class WebMVCConfig implements WebMvcConfigurer {

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new CommonInterceptor())
//                .addPathPatterns("/**")
//                .excludePathPatterns("/", "/loginform", "/login", "/signupform", "/signup");
//    }

//	@Override
//	public void addResourceHandlers(ResourceHandlerRegistry registry) {
//		registry.addResourceHandler("/assets/**").addResourceLocations("classpath:/static/assets/");
//		String projectRoot = System.getProperty("user.dir");
//		String filesPath = "file:" + projectRoot + "\\src\\main\\webapp\\WEB-INF\\img\\";
//		registry.addResourceHandler("/files/**").addResourceLocations(filesPath);
//	}
//    @Bean
//    ViewResolver viewResolver() {
//        InternalResourceViewResolver irvr = new InternalResourceViewResolver();
//        irvr.setPrefix("/WEB-INF/views/");
//        irvr.setSuffix(".jsp");
//        return irvr;
//    }

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("index");
	}

//    @Configuration
//    public class WebConfig implements WebMvcConfigurer {
//        @Override
//        public void addCorsMappings(CorsRegistry registry) {
//            registry.addMapping("/**")
//                    .allowedOriginPatterns("http://localhost:5173") // 특정 출처 허용
//                    .allowedMethods("GET", "POST", "PUT", "DELETE") // 허용할 HTTP 메소드
//                    .allowCredentials(true); // 크레덴셜 허용 설정
//        }
//    }
}