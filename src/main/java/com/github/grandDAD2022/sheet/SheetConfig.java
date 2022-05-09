package com.github.grandDAD2022.sheet;

import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.session.MapSession;
import org.springframework.session.hazelcast.Hazelcast4IndexedSessionRepository;
import org.springframework.session.hazelcast.HazelcastIndexedSessionRepository;
import org.springframework.session.hazelcast.HazelcastSessionSerializer;
import org.springframework.session.hazelcast.PrincipalNameExtractor;
import org.springframework.session.hazelcast.config.annotation.SpringSessionHazelcastInstance;
import org.springframework.session.hazelcast.config.annotation.web.http.EnableHazelcastHttpSession;
import org.springframework.web.filter.ForwardedHeaderFilter;

import com.hazelcast.config.AttributeConfig;
import com.hazelcast.config.Config;
import com.hazelcast.config.IndexConfig;
import com.hazelcast.config.IndexType;
import com.hazelcast.config.JoinConfig;
import com.hazelcast.config.SerializerConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
@EnableHazelcastHttpSession
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
	
	@Configuration
	public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
		@Autowired
		SheetUserDetailsService userDetailsService;
		
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			// TODO: return 401 on REST API instead of 302
			// TODO: disable redirect to old request, cancel instead
			
			http.authorizeRequests()
				.antMatchers("/login").permitAll()
				.antMatchers("/signup").permitAll()
				.antMatchers("/css/*").permitAll()
				.antMatchers("/js/*").permitAll()
				.antMatchers("/@*").permitAll()
				.antMatchers("/users/*/pfp").permitAll()
				.antMatchers("/swagger-ui/*").permitAll()
				.antMatchers("/v3/api-docs/**").permitAll()
				.anyRequest().authenticated();
			
			http.formLogin()
				.loginPage("/login")
				.usernameParameter("username")
				.passwordParameter("password")
				.defaultSuccessUrl("/");
			
			http.logout()
				.logoutUrl("/logout")
				.logoutSuccessUrl("/login");
		}
		
		@Bean
		public PasswordEncoder passwordEncoder() {
			return new BCryptPasswordEncoder(10, new SecureRandom());
		}
		
		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
		}
		
		@Bean
		@SpringSessionHazelcastInstance
		public HazelcastInstance hazelcastInstance() {
			Config config = new Config();
			JoinConfig joinConfig = config.getNetworkConfig().getJoin();
			
			AttributeConfig attributeConfig = new AttributeConfig()
					.setName(HazelcastIndexedSessionRepository.PRINCIPAL_NAME_ATTRIBUTE)
					.setExtractorClassName(PrincipalNameExtractor.class.getName());
			
			config.getMapConfig(HazelcastIndexedSessionRepository.DEFAULT_SESSION_MAP_NAME) 
					.addAttributeConfig(attributeConfig).addIndexConfig(
							new IndexConfig(IndexType.HASH, Hazelcast4IndexedSessionRepository.PRINCIPAL_NAME_ATTRIBUTE));
			SerializerConfig serializerConfig = new SerializerConfig();
			serializerConfig.setImplementation(new HazelcastSessionSerializer()).setTypeClass(MapSession.class);
			config.getSerializationConfig().addSerializerConfig(serializerConfig);
			
			joinConfig.getMulticastConfig().setEnabled(true);
			joinConfig.getTcpIpConfig().setEnabled(false);
			
			return Hazelcast.newHazelcastInstance(config);
		}
	}
}
