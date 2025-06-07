package com.prueba.tecnica.nttdata.gestion_employee.security;
import java.io.IOException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FilterAuthentication extends OncePerRequestFilter {
    
    private final JwtUtils utils;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(  @SuppressWarnings("null") HttpServletRequest request, @SuppressWarnings("null") HttpServletResponse response, @SuppressWarnings("null") FilterChain filterChain)
            throws ServletException, IOException {
                
            try {
                final String tokenHeader = request.getHeader("Authorization");

                if(tokenHeader !=null && tokenHeader.startsWith("Bearer ")){
                String token = tokenHeader.substring(7);
                String username= utils.extractUsername(token);
               

                if(username != null && SecurityContextHolder.getContext().getAuthentication() == null ){
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                    if(utils.tokenIsValid(token, userDetails)){
                            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null , userDetails.getAuthorities());
                            
                            auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request) );
                            SecurityContextHolder.getContext().setAuthentication(auth);
                    }

                }
            }

             filterChain.doFilter(request, response);
            } catch (ExpiredJwtException e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.getWriter().write("Token Expirado");
            } catch (MalformedJwtException e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.getWriter().write("Token Corrupto");
            } catch (SignatureException e){
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.getWriter().write("Firma Invalida");
            } catch(Exception e){
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.getWriter().write("Ocurrio un Error "+e.getMessage());
            }
           
    }
    

}
