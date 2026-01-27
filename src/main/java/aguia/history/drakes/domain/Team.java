package aguia.history.drakes.domain;


import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_teams")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //id com auto incremento
    private Long id;

    @Column(nullable = false)
    private String name;  //nome do time
    private String city; //cidade do time
    // relação com jogadores
    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Player> players = new ArrayList<>();
    // relação com temporadas
    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Season> seasons = new ArrayList<>();

    //relacionamento com técnicos
    @ManyToOne 
    @JoinColumn(name = "current_coach_id", nullable = true) 
    private Coach currentCoach;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

}
