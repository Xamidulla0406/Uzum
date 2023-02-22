package uz.nt.uzumproject.config;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
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

import javax.sql.DataSource;
import org.postgresql.Driver;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import uz.nt.uzumproject.dto.ResponseDto;
import uz.nt.uzumproject.security.JwtFilter;
import uz.nt.uzumproject.security.JwtService;
import uz.nt.uzumproject.service.UsersService;
import uz.nt.uzumproject.service.validator.AppStatusCodes;

import java.security.AuthProvider;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    @Autowired
    @Lazy
    private UsersService usersService;

    private final Gson gson;


    private final JwtFilter jwtFilter;
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${spring.datasource.username}")
    private String username;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.POST, "/user").permitAll()
                .requestMatchers("/user/login").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .exceptionHandling(e -> {e.authenticationEntryPoint(entryPoint());})
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationEntryPoint entryPoint(){
        return (req, res, ex) -> {
            res.getWriter().println(gson.toJson(ResponseDto.builder()
                    .message("Token is invalid: " + ex.getMessage())
                    .code(AppStatusCodes.VALIDATION_ERROR_CODE)
                    .build()));
            res.setContentType("application/json");
            res.setStatus(400);
        };
    }



    @Autowired
    public void authenticationManager(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());



//                jdbcAuthentication()
//                .dataSource(dataSource())
//                .usersByUsernameQuery("select email as username, password, enabled from users where email = ?");


//                .inMemoryAuthentication()
//                .withUser("Tom")
//                .password(passwordEncoder().encode("2"))
//                .roles("USER")
//                .and()
//                .withUser("Bob")
//                .password(passwordEncoder().encode("3"))
//                .roles("ADMIN", "USER");
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(usersService);
        return provider;
    }

    @Bean
    public DataSource dataSource(){
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(Driver.class);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setUrl(url);

        return dataSource;
    }



    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


}
