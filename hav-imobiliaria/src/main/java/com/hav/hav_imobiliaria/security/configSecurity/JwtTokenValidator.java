package com.hav.hav_imobiliaria.security.configSecurity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.hav.hav_imobiliaria.security.modelSecurity.UserSecurity;
import com.hav.hav_imobiliaria.security.repositorySecurity.UserRepositorySecurity;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.List;

public class JwtTokenValidator extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String jwt = request.getCookies() != null ? 
            java.util.Arrays.stream(request.getCookies())
                .filter(c -> "token".equals(c.getName()))
                .map(c -> c.getValue())
                .findFirst()
                .orElse(null) : null;

        System.out.println(jwt);
        
        if (jwt != null) {
            try {
                System.out.println(jwt);
                SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());
                Claims claims = Jwts.parser().setSigningKey(key).build().parseClaimsJws(jwt).getBody();

                String username = String.valueOf(claims.get("email"));
                String role = String.valueOf(claims.get("role"));
                System.out.println(role);

                List<GrantedAuthority> auths =
                        AuthorityUtils.commaSeparatedStringToAuthorityList(role);

                Authentication authentication =
                        new UsernamePasswordAuthenticationToken(username, null, auths);
                
                ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
                UserRepositorySecurity userRepositorySecurity = context.getBean(UserRepositorySecurity.class);
                
                UserSecurity userSecurity = userRepositorySecurity.findUserSecurityByEmail(username);
                if(userSecurity == null){
                    System.out.println("User not found");
                }
                SecurityContextHolder.getContext().setAuthentication(authentication);
                System.out.println("Auth no contexto: " + SecurityContextHolder.getContext().getAuthentication());

            } catch (Exception e) {

                System.out.println("Invalid JWT token");
            }
        }
        filterChain.doFilter(request, response);
    }
}
