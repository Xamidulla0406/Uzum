package uz.nt.uzumproject.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {


    @Autowired
    public void authorization(AuthenticationManagerBuilder authorizationManager) throws Exception {
            authorizationManager
                    .inMemoryAuthentication()
                    .withUser("Saidafzalxon")
                    .password(passwordEncoder().encode("1620"))
                    .roles("Admin","User")
                    .and()
                    .withUser("a")
                    .password(passwordEncoder().encode("1"))
                    .roles("User");
//            fjkladsfjaldskfa
    }

   @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
           http
               .authorizeHttpRequests()
               .anyRequest().authenticated()
               .and()
               .httpBasic();
               return http.build();
   }


   @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
   }
}
