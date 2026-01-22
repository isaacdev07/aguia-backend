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


    public MatchEvent() {}

    public MatchEvent(Match match, Player player, EventType eventType) {
        this.match = match;
        this.player = player;
        this.eventType = eventType;
    }


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public EventType getEventType() {
        return eventType;
    }
    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }
    public Match getMatch() {
        return match;
    }
    public void setMatch(Match match) {
        this.match = match;
    }
    public Player getPlayer() {
        return player;
    }
    public void setPlayer(Player player) {
        this.player = player;
    }
}
