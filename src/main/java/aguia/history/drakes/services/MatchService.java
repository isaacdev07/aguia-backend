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
import aguia.history.drakes.dtos.MatchUpdateDTO;
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

    // criar partida completa com elenco e eventos (gols e cartões)
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
        
        // variaveis iniciais de placar
        match.setGoalsFor(dto.getGoalsFor() != null ? dto.getGoalsFor() : 0);
        match.setGoalsAgainst(dto.getGoalsAgainst());
        match.setPenaltiesFor(dto.getPenaltiesFor());
        match.setPenaltiesAgainst(dto.getPenaltiesAgainst());
        
        // inicia os cartões zerados
        match.setYellowCards(0);
        match.setRedCards(0);

        // se tiver tecnico, busca o tecnico e associa
        if (dto.getCoachId() != null) { 
            Coach coach = coachRepository.findById(dto.getCoachId())
                    .orElseThrow(() -> new RuntimeException("Técnico com ID " + dto.getCoachId() + " não encontrado."));
            match.setCoach(coach);
        } else if (season.getTeam().getCurrentCoach() != null) {
            match.setCoach(season.getTeam().getCurrentCoach());
        }

        Set<Long> playersInMatchIds = new HashSet<>();

        // processa o elenco (Lineup)
        if (dto.getLineup() != null) {
            for (LineupDTO item : dto.getLineup()) {
                Player player = playerRepository.findById(item.getPlayerId())
                        .orElseThrow(() -> new RuntimeException("Jogador não encontrado ID: " + item.getPlayerId()));

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

                playersInMatchIds.add(player.getId());
            }
        }

        // contagem automatica de gols e cartões a partir dos eventos
        int calculatedGoals = 0;
        int calculatedYellow = 0;
        int calculatedRed = 0;

        // administra os eventos (Events)
        if (dto.getEvents() != null) {
            for (MatchEventDTO item : dto.getEvents()) {

                Player player = null;

                if (item.getPlayerId() != null) {
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
                eventEntity.setPlayer(player);
                eventEntity.setEventType(item.getType());

                match.getEvents().add(eventEntity);

                // contabiliza automaticamente gols e cartões
                String type = item.getType().toString().toUpperCase();
                
                if ("GOL".equals(type)) {
                    calculatedGoals++;
                } else if ("YELLOW_CARD".equals(type)) {
                    calculatedYellow++;
                } else if ("RED_CARD".equals(type)) {
                    calculatedRed++;
                }
            }
        }

        // salva os cartões calculados
        match.setYellowCards(calculatedYellow);
        match.setRedCards(calculatedRed);

        // se tiver gols calculados, sobrescreve o que veio no dto
        if (calculatedGoals > 0) {
            match.setGoalsFor(calculatedGoals);
        }

        // salva a partida completa
        return matchRepository.save(match);
    }
    
    // atualizar dados básicos da partida
    public Match updateMatch(Long matchId, MatchUpdateDTO dto) {
        // busca a partida
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new RuntimeException("Partida não encontrada com ID: " + matchId));

        // verifica se é o dono do time
        validarDono(match.getSeason().getTeam());

        // atualiza os dados basicos
        match.setMatchDate(dto.date());
        match.setOpponentName(dto.opponent());
        match.setLocation(dto.location());

        // atualiza os placares se vierem no dto
        if (dto.goalsFor() != null) match.setGoalsFor(dto.goalsFor());
        if (dto.goalsAgainst() != null) match.setGoalsAgainst(dto.goalsAgainst());

        // salva e retorna a partida atualizada
        return matchRepository.save(match);
    }

    // apagar partida (soft delete)
    public void deleteMatch(Long matchId) {
        // busca a partida
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new RuntimeException("Partida não encontrada com ID: " + matchId));

        // verifica se é o dono do time
        validarDono(match.getSeason().getTeam());

        // deixa a partida inativa (soft delete)
        match.setIsActive(false); 

        // salva a alteração
        matchRepository.save(match);
    }

    // adicionar novo evento na partida
    public Match addEventToMatch(Long matchId, MatchEventDTO dto) {
        // usa somente partida ativa
        Match match = matchRepository.findByIdAndIsActiveTrue(matchId)
                .orElseThrow(() -> new RuntimeException("Partida não encontrada."));

        // valida o dono do time
        validarDono(match.getSeason().getTeam());

        // se tiver jogador envolvido, busca o jogador
        Player player = null;
        if (dto.getPlayerId() != null) {
            player = playerRepository.findById(dto.getPlayerId())
                    .orElseThrow(() -> new RuntimeException("Jogador não encontrado."));
            
            // verificação simples, o jogador pertence ao time da partida?
            if (!player.getTeam().getId().equals(match.getSeason().getTeam().getId())) {
                 throw new RuntimeException("Este jogador não pertence ao time desta partida.");
            }
        }

        // cria o novo evento
        MatchEvent event = new MatchEvent();
        event.setMatch(match);
        event.setPlayer(player);
        event.setEventType(dto.getType()); // Assumindo que seu DTO já converte ou é Enum

        // atualiza o placar se for gol automaticamente
        if ("GOL".equalsIgnoreCase(dto.getType().toString())) {
            match.setGoalsFor(match.getGoalsFor() + 1);
        }

        // Adiciona na lista e Salva
        match.getEvents().add(event);
        return matchRepository.save(match);
    }
    
      // apagar evento da partida
    public Match removeEventFromMatch(Long matchId, Long eventId) {
        // usa somente partida ativa
        Match match = matchRepository.findByIdAndIsActiveTrue(matchId)
                .orElseThrow(() -> new RuntimeException("Partida não encontrada."));
        
        // valida o dono do time
        validarDono(match.getSeason().getTeam());

        // remove o evento da lista e ajusta o placar se for gol
        match.getEvents().removeIf(event -> {
            if (event.getId().equals(eventId)) {
                if ("GOL".equalsIgnoreCase(event.getEventType().toString())) {
                     match.setGoalsFor(match.getGoalsFor() - 1);
                }
                return true; // remove da lista
            }
            return false;
        });

        return matchRepository.save(match);
    }

    // listar todas as partidas
    public List<Match> findAllMatches() {
        return matchRepository.findByIsActiveTrue();
    }

    // listar partida por id
    public Match findMatchById(Long id) {
        return matchRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new RuntimeException("Partida com ID " + id + " não encontrada."));
    }

    // listar partidas por id da equipe da temporada
    public List<Match> findMatchesByTeam(Long teamId) {
        return matchRepository.findBySeasonTeamIdAndIsActiveTrue(teamId);
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
