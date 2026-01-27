package aguia.history.drakes.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import aguia.history.drakes.repositories.UserRepository;
import aguia.history.drakes.services.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    TokenService tokenService;

    @Autowired
    UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = this.recoverToken(request); // pega o token no cabeçalho

        if(token != null){
            // valida o token  
            var email = tokenService.validateToken(token);
            
            if(!email.isEmpty()){
                // checa o usuario no banco
                UserDetails user = userRepository.findByEmail(email);

                // cria a autenticação
                if (user != null) {
                    var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    
                    // salva a autenticação 
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
        // continua a cadeia de filtros
        filterChain.doFilter(request, response);
    }

    // limpar o bearer do token
    private String recoverToken(HttpServletRequest request){
        var authHeader = request.getHeader("Authorization");
        if(authHeader == null) return null;
        return authHeader.replace("Bearer ", "");
    }
}