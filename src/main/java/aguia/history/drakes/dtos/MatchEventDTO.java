package aguia.history.drakes.dtos;

import aguia.history.drakes.domain.enums.EventType;

public class MatchEventDTO {
    private Long playerId; // ID do jogador envolvido no evento
    private EventType type; // Tipo do evento

    public MatchEventDTO() {}

    public MatchEventDTO(Long playerId, EventType type) {
        this.playerId = playerId;
        this.type = type;
    }
    public Long getPlayerId() {
        return playerId;
    }
    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }
    public EventType getType() {
        return type;
    }
    public void setType(EventType type) {
        this.type = type;
    }
    
}
