package aguia.history.drakes.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import aguia.history.drakes.domain.Coach;
import aguia.history.drakes.domain.Match;
import aguia.history.drakes.domain.MatchEvent;
import aguia.history.drakes.domain.MatchLineup;
import aguia.history.drakes.domain.Player;
import aguia.history.drakes.domain.Season;
import aguia.history.drakes.domain.Team;
import aguia.history.drakes.domain.User;
import aguia.history.drakes.dtos.LineupDTO;
import aguia.history.drakes.dtos.MatchCreateDTO;
import aguia.history.drakes.dtos.MatchEventDTO;
import aguia.history.drakes.repositories.CoachRepository;
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

    @Autowired
    private CoachRepository coachRepository;

    // criar partida completa com elenco e eventos
    @Transactional
    public Match createMatch(MatchCreateDTO dto) {

        Season season = seasonRepository.findById(dto.getSeasonId())
                .orElseThrow(() -> new RuntimeException("Temporada com ID " + dto.getSeasonId() + " não encontrada."));

        // verifica se a temporada está associada a um time
        if (season.getTeam() == null) {
            throw new RuntimeException("Esta temporada não está associada a nenhum time.");
        }

        // valida se quem esta criando a partida é o dono do time
        validarDono(season.getTeam());

        // pega o id do time da temporada para validações futuras  
        Long seasonTeamId = season.getTeam().getId();

        // cria a partida (Match)
        Match match = new Match();
        match.setSeason(season);
        match.setOpponentName(dto.getOpponentName());
        match.setMatchDate(dto.getDate());
        match.setLocation(dto.getLocation());
        match.setMatchType(dto.getType());
        match.setGoalsFor(dto.getGoalsFor());
        match.setGoalsAgainst(dto.getGoalsAgainst());
        match.setPenaltiesFor(dto.getPenaltiesFor());
        match.setPenaltiesAgainst(dto.getPenaltiesAgainst());

        // se tiver tecnico, busca o tecnico e associa
        if (dto.getCoachId() != null) {
            // se o ID do técnico foi informado no JSON, busca o técnico pelo ID
            Coach coach = coachRepository.findById(dto.getCoachId())
                    .orElseThrow(() -> new RuntimeException("Técnico com ID " + dto.getCoachId() + " não encontrado."));
            match.setCoach(coach);
        } else if (season.getTeam().getCurrentCoach() != null) {
            // se o ID do técnico não foi informado, associa o técnico atual do time da
            // temporada
            match.setCoach(season.getTeam().getCurrentCoach());
        }

        Set<Long> playersInMatchIds = new HashSet<>();

        // processa o elenco (Lineup)
        if (dto.getLineup() != null) {
            for (LineupDTO item : dto.getLineup()) {
                Player player = playerRepository.findById(item.getPlayerId())
                        .orElseThrow(() -> new RuntimeException("Jogador não encontrado ID: " + item.getPlayerId()));

                // o jogador pertence ao time da temporada? se não, erro de integridade
                if (!player.getTeam().getId().equals(seasonTeamId)) {
                    throw new RuntimeException("Erro de Integridade: O jogador " + player.getName() +
                            " pertence ao time " + player.getTeam().getName() +
                            ", mas esta partida é do time " + season.getTeam().getName());
                }

                MatchLineup lineupEntity = new MatchLineup();
                lineupEntity.setMatch(match);
                lineupEntity.setPlayer(player);
                lineupEntity.setStatus(item.getStatus());

                match.getLineup().add(lineupEntity);

                // Adiciona o ID na lista de "jogadores em campo/banco"
                playersInMatchIds.add(player.getId());
            }
        }

        // administra os eventos (Events)
        if (dto.getEvents() != null) {
            for (MatchEventDTO item : dto.getEvents()) {

                Player player = null;

                if (item.getPlayerId() != null) {
                    // o jogador do evento deve estar no elenco da partida, caso contrário, erro de
                    // integridade
                    if (!playersInMatchIds.contains(item.getPlayerId())) {
                        throw new RuntimeException("Erro de Integridade: O jogador ID " + item.getPlayerId() +
                                " tentou registrar um evento (" + item.getType()
                                + "), mas não foi escalado na partida (Lineup).");
                    }

                    player = playerRepository.findById(item.getPlayerId())
                            .orElseThrow(() -> new RuntimeException(
                                    "Jogador do evento não encontrado ID: " + item.getPlayerId()));
                }

                MatchEvent eventEntity = new MatchEvent();
                eventEntity.setMatch(match);
                eventEntity.setPlayer(player); // pode ser nulo para eventos sem jogador associado
                eventEntity.setEventType(item.getType());

                match.getEvents().add(eventEntity);
            }
        }
        // salva a partida completa
        return matchRepository.save(match);
    }

    // listar todas as partidas
    public List<Match> findAllMatches() {
        return matchRepository.findAll();
    }

    // listar partida por id
    public Match findMatchById(Long id) {
        return matchRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Partida com ID " + id + " não encontrada."));
    }

    // listar partidas por id da equipe da temporada
    public List<Match> findMatchesByTeam(Long teamId) {
        return matchRepository.findBySeasonTeamId(teamId);
    }

    // metodo auxiliar para validar dono do time
    private void validarDono(Team team) {
        String emailLogado = getEmailLogado();
        
        // Verifica se o time tem dono e se o email bate
        if (team.getOwner() == null || !team.getOwner().getEmail().equals(emailLogado)) {
            throw new RuntimeException("ACESSO NEGADO: Você não é o dono deste time.");
        }
    }

    // metodo auxiliar para pegar email do usuário logado
    private String getEmailLogado() {
        var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof User) {
            return ((User) principal).getUsername();
        } else {
            return principal.toString();
        }
    }

}
