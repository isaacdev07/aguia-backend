package aguia.history.drakes.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import aguia.history.drakes.domain.enums.MatchType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "tb_matches")
public class Match {

    //atributos 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // relacionamento com temporada
    @ManyToOne
    @JoinColumn(name = "season_id", nullable = false)
    private Season season;

    private Boolean isActive = true; // partida ativa por padrão
    private String opponentName;     // adversário
    private LocalDateTime matchDate; // data e hora
    private String location;         // local do jogo

    @Enumerated(EnumType.STRING)
    private MatchType matchType;   // tipo de partida

    // placar da partida
    private Integer goalsFor = 0;     // Gols a favor
    private Integer goalsAgainst = 0; // Gols contra

    //placar nos penaltis se tiver
    private Integer penaltiesFor = 0;     // Gols a favor nos penaltis
    private Integer penaltiesAgainst = 0; // Gols contra nos penaltis

    // relação com escalação e eventos
    
    // Lista de jogadores escalados (Cascade ALL = Salvar a partida salva a escalação junto)
    @OneToMany(mappedBy = "match", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MatchLineup> lineup = new ArrayList<>();

    // Lista de eventos (gols, cartões)
    @OneToMany(mappedBy = "match", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MatchEvent> events = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "coach_id", nullable = true) // Pode não ter técnico no jogo
    private Coach coach;

}
