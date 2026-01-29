package aguia.history.drakes.domain;

import aguia.history.drakes.domain.enums.EventType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_match_events")
public class MatchEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // a partida onde o evento ocorreu
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "match_id", nullable = false)
    private Match match;

    // o player envolvido no evento
    @ManyToOne
    @JoinColumn(name = "player_id") 
    private Player player;

    @Enumerated(EnumType.STRING)
    private EventType eventType;


}
