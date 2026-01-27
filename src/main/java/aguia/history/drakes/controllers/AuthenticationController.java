package aguia.history.drakes.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import aguia.history.drakes.domain.User;
import aguia.history.drakes.dtos.AuthenticationDTO;
import aguia.history.drakes.dtos.LoginResponseDTO;
import aguia.history.drakes.dtos.RegisterDTO;
import aguia.history.drakes.repositories.UserRepository;
import aguia.history.drakes.services.TokenService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository repository;

    @Autowired
    private TokenService tokenService;

    // login
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data){
        // cria o objeto de autenticação do Spring Security
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        
        // vai ao banco validar o usuário e senha
        var auth = this.authenticationManager.authenticate(usernamePassword);

        // deu certo, gera o token
        var token = tokenService.generateToken((User) auth.getPrincipal());

        // retorna o token
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    // cadastro
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid RegisterDTO data){
        // verifica se o email existe no banco
        if(this.repository.findByEmail(data.email()) != null) {
            return ResponseEntity.badRequest().body("Este email já está em uso.");
        }

        // critografa a senha
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        
        // cria o usuário
        User newUser = new User(data.email(), encryptedPassword, data.role());

        // salva no banco
        this.repository.save(newUser);

        return ResponseEntity.ok().build();
    }
}
    

