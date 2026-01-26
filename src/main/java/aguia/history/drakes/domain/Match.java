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


    public Match() {}

    public Match(Season season, String opponentName, LocalDateTime matchDate, String location,
            MatchType matchType, Integer penaltiesFor, Integer penaltiesAgainst, Coach coach) {
        this.season = season;
        this.opponentName = opponentName;
        this.matchDate = matchDate;
        this.location = location;
        this.matchType = matchType;
        this.coach = coach;
        this.penaltiesFor = penaltiesFor;
        this.penaltiesAgainst = penaltiesAgainst;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public List<MatchEvent> getEvents() {
        return events;
    }
    public void setEvents(List<MatchEvent> events) {
        this.events = events;
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
    public List<MatchLineup> getLineup() {
        return lineup;
    }
    public void setLineup(List<MatchLineup> lineup) {
        this.lineup = lineup;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public LocalDateTime getMatchDate() {
        return matchDate;
    }
    public void setMatchDate(LocalDateTime matchDate) {
        this.matchDate = matchDate;
    }
    public MatchType getMatchType() {
        return matchType;
    }
    public void setMatchType(MatchType matchType) {
        this.matchType = matchType;
    }
    public String getOpponentName() {
        return opponentName;
    }
    public void setOpponentName(String opponentName) {
        this.opponentName = opponentName;
    }
    public Season getSeason() {
        return season;
    }
    public void setSeason(Season season) {
        this.season = season;
    }
    public Coach getCoach() {
        return coach;
    }   
    public void setCoach(Coach coach) {
        this.coach = coach;
    }
}
