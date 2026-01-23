package aguia.history.drakes.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aguia.history.drakes.domain.Match;
import aguia.history.drakes.domain.MatchEvent;
import aguia.history.drakes.domain.MatchLineup;
import aguia.history.drakes.domain.Player;
import aguia.history.drakes.domain.Season;
import aguia.history.drakes.dtos.LineupDTO;
import aguia.history.drakes.dtos.MatchCreateDTO;
import aguia.history.drakes.dtos.MatchEventDTO;
import aguia.history.drakes.repositories.MatchRepository;
import aguia.history.drakes.repositories.PlayerRepository;
import aguia.history.drakes.repositories.SeasonRepository;
import jakarta.transaction.Transactional;

@Service
public class MatchService {
    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private SeasonRepository seasonRepository;

    @Autowired
    private PlayerRepository playerRepository;

    // criar partida completa com elenco e eventos
    @Transactional
    public Match createMatch(MatchCreateDTO dto) {
    
        Season season = seasonRepository.findById(dto.getSeasonId())
                .orElseThrow(() -> new RuntimeException("Temporada com ID " + dto.getSeasonId() + " não encontrada."));

        // cria a partida (Match)
        Match match = new Match();
        match.setSeason(season);
        match.setOpponentName(dto.getOpponentName());
        match.setMatchDate(dto.getDate());
        match.setLocation(dto.getLocation());
        match.setMatchType(dto.getType());
        match.setGoalsFor(dto.getGoalsFor());
        match.setGoalsAgainst(dto.getGoalsAgainst());

        // processa o elenco (Lineup)
   if (dto.getLineup() != null) {
            for (LineupDTO item : dto.getLineup()) {
                Player player = playerRepository.findById(item.getPlayerId())
                        .orElseThrow(() -> new RuntimeException("Jogador não encontrado ID: " + item.getPlayerId()));

                // cria o MatchLineup
                MatchLineup lineupEntity = new MatchLineup();
                lineupEntity.setMatch(match); // associa a partida
                lineupEntity.setPlayer(player);
                lineupEntity.setStatus(item.getStatus());

                // adiciona na lista da partida
                match.getLineup().add(lineupEntity);
            }
        }

        // administra os eventos (Events)
        if (dto.getEvents() != null) {
            for (MatchEventDTO item : dto.getEvents()) {
                
                Player player = null;
                // se o evento tiver jogador associado busca o jogador
                // se nao tiver, deixa nulo (ex: gol contra)
                if (item.getPlayerId() != null) {
                    player = playerRepository.findById(item.getPlayerId())
                            .orElseThrow(() -> new RuntimeException("Jogador do evento não encontrado ID: " + item.getPlayerId()));
                }
                // cria o MatchEvent
                MatchEvent eventEntity = new MatchEvent();
                eventEntity.setMatch(match);
                eventEntity.setPlayer(player);
                eventEntity.setEventType(item.getType());

                // adiciona os eventos na lista da partida
                match.getEvents().add(eventEntity);
            }
        }

        // salva a partida completa
        return matchRepository.save(match);
    }

    //listar todas as partidas
    public List<Match> findAllMatches() {
        return matchRepository.findAll();
    }

    //listar partida por id
    public Match findMatchById(Long id) {
        return matchRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Partida com ID " + id + " não encontrada."));
    }

    //listar partidas por id da equipe da temporada
    public List<Match> findMatchesByTeam(Long teamId) {
        return matchRepository.findBySeasonTeamId(teamId);
    }
    
}
