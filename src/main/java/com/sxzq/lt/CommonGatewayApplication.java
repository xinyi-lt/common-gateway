package com.sxzq.lt;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.Ordered;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Properties;

@SpringBootApplication
public class CommonGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(CommonGatewayApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}

	@Bean
	public FilterRegistrationBean corsFilter(){
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedOrigin("*");
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
		config.setMaxAge(3600L);
		source.registerCorsConfiguration("/**", config);

		FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
		bean.setOrder(Ordered.HIGHEST_PRECEDENCE + 100);
		return bean;
	}

	@Bean
	public Producer producerKaptcha() {
		DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
		Properties properties = new Properties();
		// 是否有边框 可选yes 或者 no
//		 properties.setProperty(Constants.KAPTCHA_BORDER, "yes");
		// 边框颜色
//		 properties.setProperty(Constants.KAPTCHA_BORDER_COLOR, "105,179,90");
		// 验证码文本字符颜色
//		 properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_FONT_COLOR, "blue");
		// 图片宽度
		properties.setProperty(Constants.KAPTCHA_IMAGE_WIDTH, "100");
		// 图片高度
		properties.setProperty(Constants.KAPTCHA_IMAGE_HEIGHT, "40");
		// properties.setProperty("kaptcha.session.key", "code");
		properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_CHAR_LENGTH, "4");
		properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_FONT_SIZE, "30");
//		properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_CHAR_STRING, "0123456789");
		properties.setProperty(Constants.KAPTCHA_NOISE_IMPL, "com.google.code.kaptcha.impl.NoNoise");
//		properties.setProperty(Constants.KAPTCHA_BORDER_COLOR, "red");
//		properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_FONT_COLOR, "red");
		// properties.setProperty("kaptcha.textproducer.font.names", "宋体,楷体,微软雅黑");
		Config config = new Config(properties);
		defaultKaptcha.setConfig(config);
		return defaultKaptcha;
	}
}
