package aguia.history.drakes.services;

import aguia.history.drakes.domain.User;
import aguia.history.drakes.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomOidcUserService extends OidcUserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        // carrega o usuario
        OidcUser oidcUser = super.loadUser(userRequest);

        // pega o email
        String email = oidcUser.getEmail();


        // se nao existe, cria no banco
        if (email != null) {
            // verifica se ja existe
            if (userRepository.findByEmail(email).isEmpty()) {
                
                User user = new User();
                user.setEmail(email);
                user.setPassword(""); 
                user.setRole("USER"); 
                
                userRepository.save(user);
            } else {
                System.out.println("Usuário Google já existia no banco.");
            }
        }

        return oidcUser;
    }
}