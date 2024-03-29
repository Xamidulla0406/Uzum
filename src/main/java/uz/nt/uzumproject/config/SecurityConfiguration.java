package uz.nt.uzumproject.config;

import com.google.gson.Gson;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.postgresql.Driver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import uz.nt.uzumproject.dto.ResponseDto;
import uz.nt.uzumproject.security.JwtFilter;
import uz.nt.uzumproject.service.UsersService;
import uz.nt.uzumproject.service.validator.AppStatusCodes;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@SecurityScheme(name = "Authorization",
                in = SecuritySchemeIn.HEADER,
                type = SecuritySchemeType.APIKEY)
public class SecurityConfiguration {
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;
    @Autowired
    @Lazy
    private UsersService usersService;
    @Autowired
    private JwtFilter jwtFilter;
    @Autowired
    private Gson gson;
    @Autowired
    public void authenticationManager(AuthenticationManagerBuilder aut) throws Exception {
        aut.authenticationProvider(authenticationProvider());
    }

    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(usersService);
        return provider;
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().configurationSource(corsConfigurationSource());
        return http
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.POST,"/user").permitAll()
                .requestMatchers("/user/login").permitAll()
                .requestMatchers("/swagger-ui/**","/v3/api-docs/**", "/swagger-ui.html").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .exceptionHandling(e -> e.authenticationEntryPoint(entryPoint()))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
    private CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.addAllowedOrigin("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
    public AuthenticationEntryPoint entryPoint(){
        return ((req, res, ex) -> {
            res.getWriter().println(gson.toJson(ResponseDto.builder()
                    .code(AppStatusCodes.VALIDATION_ERROR_CODE)
                    .message("Token is not valid " + ex.getMessage() +
                            (ex.getCause() != null ? ex.getCause().getMessage() : ""))
                    .build()));
            res.setContentType("application/json");
            res.setStatus(400);
        });
    }
    public DataSource dataSource(){
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(Driver.class);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }
}
