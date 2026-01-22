package aguia.history.drakes.dtos;

import aguia.history.drakes.domain.enums.LineupStatus;

public class LineupDTO {
    private Long playerId;
    private LineupStatus status;

    public LineupDTO() {}

    public LineupDTO(Long playerId, LineupStatus status) {
        this.playerId = playerId;
        this.status = status;
    }

    public Long getPlayerId() {
        return playerId;
    }
    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }
    public LineupStatus getStatus() {
        return status;
    }
    public void setStatus(LineupStatus status) {
        this.status = status;
    }
    
}
