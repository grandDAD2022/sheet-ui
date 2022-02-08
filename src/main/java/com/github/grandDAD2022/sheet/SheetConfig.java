package com.github.grandDAD2022.sheet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.filter.ForwardedHeaderFilter;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SheetConfig {
	@Autowired
    Environment environment;

    @Autowired 
    BuildProperties buildProperties;
	
	@Bean
	public OpenAPI openAPI() {
		return new OpenAPI()
				.info(new Info().title("Sheet")
				.description("Share + Meet!")
				.version(buildProperties.getVersion())
				.license(new License()
						.name("CC0 (licencia tentativa, pendiente de acordar)")
						.url("https://creativecommons.org/publicdomain/zero/1.0/legalcode")))
				.externalDocs(new ExternalDocumentation()
						.description("Repositorio en Github")
						.url("https://github.com/grandDAD2022/sheet"));
	}
	
	@Bean
	ForwardedHeaderFilter forwardedHeaderFilter() {
	   return new ForwardedHeaderFilter();
	}
}
