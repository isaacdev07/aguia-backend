package aguia.history.drakes.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_teams")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //id com auto incremento
    private Long id;

    @Column(nullable = false)
    private String name;  //nome do time
    private LocalDate foundationDate; //data de fundação
    private String city; //cidade do time
    // relação com jogadores
    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Player> players = new ArrayList<>();
    // relação com temporadas
    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Season> seasons = new ArrayList<>();

    // construtor padrão
    public Team() {}
    
    // construtor com parâmetros
    public Team(String name, String city) {
        this.name = name;
        this.city = city;
    }

    // getters e setters
    public Long getId() {
        return id;
    }   
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public LocalDate getFoundationDate() {
        return foundationDate;
    }
    public void setFoundationDate(LocalDate foundationDate) {
        this.foundationDate = foundationDate;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
}
