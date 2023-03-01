package uz.nt.uzumproject.security;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import uz.nt.uzumproject.dto.ResponseDto;
import uz.nt.uzumproject.dto.UsersDto;
import uz.nt.uzumproject.service.validator.AppStatusCodes;

import java.io.IOException;


@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String auth = request.getHeader("Authorization");
        if(auth != null && auth.startsWith("Bearer ")){
            String token = auth.substring(7);
            if(jwtService.isExpired(token)){
                response.getWriter().println(ResponseDto.builder()
                                .message("Is expired token!")
                                .code(AppStatusCodes.VALIDATION_ERROR_CODE)
                        .build());

                response.setContentType("application/json");
                response.setStatus(400);
            }else {
                UsersDto dto = jwtService.subject(token);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(dto, null, dto.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }
}
