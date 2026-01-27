package aguia.history.drakes.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import aguia.history.drakes.domain.enums.Position;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity 
@Table(name = "tb_players")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING) // salva o enum como string no banco
    private Position position;

    private Integer shirtNumber; //numero da camisa

    private Boolean isActive = true; // jogador ativo ou não
    @ManyToOne
    @JoinColumn(name = "team_id", nullable = false)
    @JsonIgnore // para evitar recursão infinita na serialização
    private Team team;

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
    public Position getPosition() {
        return position;
    }
    public void setPosition(Position position) {
        this.position = position;
    }
    public Integer getShirtNumber() {
        return shirtNumber;
    }
    public void setShirtNumber(Integer shirtNumber) {
        this.shirtNumber = shirtNumber;
    }
    public Boolean getIsActive() {
        return isActive;
    }
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
    public Team getTeam() {
        return team;
    }
    public void setTeam(Team team) {
        this.team = team;
    }

}