package aguia.history.drakes.dtos;

import java.time.LocalDateTime;
import java.util.List;

import aguia.history.drakes.domain.enums.MatchType;

public class MatchCreateDTO {

    private Long seasonId; // id da temporada
    
    private String opponentName; // nome do time adversário
    private LocalDateTime date; // data e hora da partida
    private String location; // local da partida
    private MatchType type; // tipo da partida
    private Long coachId; // id do técnico

     // placar da partida
    private Integer goalsFor;
    private Integer goalsAgainst;

    //placar nos penaltis se tiver
    private Integer penaltiesFor;
    private Integer penaltiesAgainst;

    // As listas aninhadas
    private List<LineupDTO> lineup;
    private List<MatchEventDTO> events;


    public MatchCreateDTO() {}

    public MatchCreateDTO(Long seasonId, String opponentName, LocalDateTime date, String location, MatchType type,
            Integer goalsFor, Integer goalsAgainst, Integer penaltiesFor, Integer penaltiesAgainst, List<LineupDTO> lineup, List<MatchEventDTO> events, Long coachId) {
        this.seasonId = seasonId;
        this.opponentName = opponentName;
        this.date = date;
        this.location = location;
        this.type = type;
        this.penaltiesFor = penaltiesFor;
        this.penaltiesAgainst = penaltiesAgainst;
        this.goalsFor = goalsFor;
        this.goalsAgainst = goalsAgainst;
        this.lineup = lineup;
        this.events = events;
        this.coachId = coachId;
    }

    public Long getSeasonId() {
        return seasonId;
    }
    public void setSeasonId(Long seasonId) {
        this.seasonId = seasonId;
    }
    public String getOpponentName() {
        return opponentName;
    }
    public void setOpponentName(String opponentName) {
        this.opponentName = opponentName;
    }
    public Long getCoachId() {
        return coachId;
    }
    public void setCoachId(Long coachId) {
        this.coachId = coachId;
    }
    public LocalDateTime getDate() {
        return date;
    }
    public void setDate(LocalDateTime date) {
        this.date = date;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public MatchType getType() {
        return type;
    }
    public void setType(MatchType type) {
        this.type = type;
    }
    public Integer getGoalsFor() {
        return goalsFor;
    }
    public void setGoalsFor(Integer goalsFor) {
        this.goalsFor = goalsFor;
    }
    public Integer getGoalsAgainst() {
        return goalsAgainst;
    }
    public void setGoalsAgainst(Integer goalsAgainst) {
        this.goalsAgainst = goalsAgainst;
    }
    public Integer getPenaltiesFor() {
        return penaltiesFor;
    }
    public void setPenaltiesFor(Integer penaltiesFor) {
        this.penaltiesFor = penaltiesFor;
    }
    public Integer getPenaltiesAgainst() {
        return penaltiesAgainst;
    }
    public void setPenaltiesAgainst(Integer penaltiesAgainst) {
        this.penaltiesAgainst = penaltiesAgainst;
    }
    public List<LineupDTO> getLineup() {
        return lineup;
    }
    public void setLineup(List<LineupDTO> lineup) {
        this.lineup = lineup;
    }
    public List<MatchEventDTO> getEvents() {
        return events;
    }
    public void setEvents(List<MatchEventDTO> events) {
        this.events = events;
    }
}
