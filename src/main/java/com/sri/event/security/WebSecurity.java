package com.sri.event.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.sri.event.repo.OwnerRepo;
import com.sri.event.repo.UserRepo;
import com.sri.event.service.OwnerService;
import com.sri.event.service.UserService;

@EnableMethodSecurity(securedEnabled = true,prePostEnabled = true)
@Configuration
@EnableWebSecurity
public class WebSecurity {
	
	@Autowired
	private UserService userService;
	
	
	@Autowired
	private OwnerService ownerService;
	
	@Autowired
	private UserRepo userRepo;
	
	
	@Autowired
	private OwnerRepo ownerRepo;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Bean
	protected  SecurityFilterChain configure(HttpSecurity http)throws Exception{
		AuthenticationManagerBuilder authenticationManagerBuilder=
				http.getSharedObject(AuthenticationManagerBuilder.class);
		
		authenticationManagerBuilder.userDetailsService(userService)
									.passwordEncoder(bCryptPasswordEncoder);
		
		authenticationManagerBuilder.userDetailsService(ownerService)
        							.passwordEncoder(bCryptPasswordEncoder);
		
		AuthenticationManager authenticationManager=authenticationManagerBuilder.build();
		
		http.cors(cors->cors.configurationSource(corsConfigurationSource()))
			.csrf((csrf)->csrf.disable())
			.authorizeHttpRequests((authz)->authz
			.requestMatchers("/user/login", "/owner/login")
			.permitAll()
			.requestMatchers(HttpMethod.POST,"/users/createuser")
			.permitAll()
			.requestMatchers(new AntPathRequestMatcher("/h2-console/**"))
			.permitAll()
			.requestMatchers("/owners/**")
			.permitAll()
			.requestMatchers("/events/**")
			.permitAll()
			.anyRequest().authenticated())
			.addFilter(new AuthenticationFilter(authenticationManager))
			.addFilter(new OwnerAuthenticationFilter(authenticationManager))
			.addFilter(new AuthorizationFilter(authenticationManager,userRepo,ownerRepo))			
			.authenticationManager(authenticationManager)
			.sessionManagement((session)->session
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
		
		http.headers((headers)->
		headers.frameOptions((frameOptions)->
		frameOptions.sameOrigin()));
		
		return http.build();
	}
	
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration config=new CorsConfiguration();
		config.setAllowedOrigins(Arrays.asList("http://localhost:4200", "https://sri-todo.netlify.app"));
		config.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE","OPTIONS"));
		config.setAllowedHeaders(Arrays.asList("*"));
		config.setExposedHeaders(Arrays.asList("*"));
		config.setAllowCredentials(true);
		
		UrlBasedCorsConfigurationSource source=new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		
		return  source;
		
		
	}
}
