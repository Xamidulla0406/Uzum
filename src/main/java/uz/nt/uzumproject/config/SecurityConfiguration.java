package uz.nt.uzumproject.config;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
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
import uz.nt.uzumproject.dto.ResponseDto;
import uz.nt.uzumproject.secutiry.JwtFilter;
import uz.nt.uzumproject.service.UsersService;
import uz.nt.uzumproject.service.validator.AppStatusCodes;

import javax.sql.DataSource;
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {
@Value("{spring.datasource.url")
String url;


@Value("{spring.datasource.username")

String username;

@Value("{spring.datasource.password")

String password;


@Autowired
@Lazy
private UsersService usersService;

@Autowired
private JwtFilter jwtFilter;

@Autowired
private Gson gson;

//    @Autowired
//    public void authorization(AuthenticationManagerBuilder authorizationManager) throws Exception {
//            authorizationManager
//                    .authenticationProvider(provider());
////                    .jdbcAuthentication()
////                    .dataSource(dataSource())
////                    .usersByUsernameQuery("select email as username,password,enabled where email = ?");
//
////                    .inMemoryAuthentication()
////                    .withUser("Saidafzalxon")
////                    .password(passwordEncoder().encode("1620"))
////                    .roles("Admin","User")
////                    .and()
////                    .withUser("a")
////                    .password(passwordEncoder().encode("1"))
////                    .roles("User");
//
//    }

    @Bean
    public AuthenticationProvider provider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(usersService);
        return provider;
    }


//@Bean
//DataSource dataSource(){
//        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
//        dataSource.setDriverClass(Driver.class);
//        dataSource.setUrl(url);
//        dataSource.setUsername(username);
//        dataSource.setPassword(password);
//        return dataSource;
//}
   @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
           http
                   .csrf().disable()
               .authorizeHttpRequests()
                   .requestMatchers(HttpMethod.POST,"/user").permitAll()
                   .requestMatchers(HttpMethod.GET,"/user/user").permitAll()
               .anyRequest().authenticated()
                   .and()
                   .authenticationProvider(provider())
                   .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                   .exceptionHandling(e-> e.authenticationEntryPoint(entryPoint()));

               return http.build();
   }

    private AuthenticationEntryPoint entryPoint(){
        return (request, response, authException) ->
        {
            response.getWriter().println(gson.toJson(ResponseDto.<Void>builder()
                    .message("Token is not valid")
                    .code(AppStatusCodes.VALIDATION_ERROR_CODE)));
            response.setStatus(403);
            response.setContentType("application/json");
        };
    }

   @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
   }
}
