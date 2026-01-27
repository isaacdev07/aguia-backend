package aguia.history.drakes.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Collection;
import java.util.List;


@Table(name = "tb_users")
@Entity(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true) // email é único
    private String email;

    private String password;

    private String role = "USER"; 

    // construtor personalizado
    public User(String email, String password, String role){
        this.email = email;
        this.password = password;
        this.role = role;
    }

    // metodos userdetails

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // se for admin, retorna ambas as roles, senao retorna apenas user
        if(this.role.equals("ADMIN")) return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        else return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getUsername() {
        return email; 
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return password; 
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true; 
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true; 
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true; 
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true; 
    }
}