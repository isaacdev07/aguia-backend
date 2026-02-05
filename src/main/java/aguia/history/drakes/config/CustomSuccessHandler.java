package aguia.history.drakes.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        
        // logica para redirecionar o usuário para a página de partidas após o login bem-sucedido
        String targetUrl = "/matches";
        
        // define a URL de destino padrão
        setDefaultTargetUrl(targetUrl);
        
        // se voce clicou em um link protegido antes de logar, ele redireciona para lá
        super.onAuthenticationSuccess(request, response, authentication);
    }
}