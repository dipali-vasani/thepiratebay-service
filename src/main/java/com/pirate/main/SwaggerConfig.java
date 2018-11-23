package com.pirate.main;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * The Class SwaggerConfig.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

	/** The version. */
	@Value("${application.version}")
	public String version;

	/**
	 * Api.
	 *
	 * @return the docket
	 */
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.pirate.controller")).paths(PathSelectors.any())
				.build().apiInfo(apiInfo());
	}

	/**
	 * Api info.
	 *
	 * @return the api info
	 */
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("MS RCA API").version(version).license(null).build();
	}
}