package uz.nt.uzumproject.security;

import com.google.gson.Gson;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import uz.nt.uzumproject.dto.UsersDto;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class JwtFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final Gson gson;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")){
            if (jwtService.isExpired(token.substring(7))){
                response.getWriter()
                        .println("Token is invalid ");
            }
            else {
                UsersDto user = jwtService.isVerified(token.substring(7));
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }
}
